package com.lianghongji.manageuser.service;

import com.lianghongji.ErrorCode;
import com.lianghongji.Exception.BusinessRuntimeException;
import com.lianghongji.manageuser.dto.req.ManageLoginDto;
import com.lianghongji.manageuser.dto.req.ManageUserSighUpDto;
import com.lianghongji.manageuser.dto.resp.ManageLoginResultDto;
import com.lianghongji.manageuser.entity.ManageResources;
import com.lianghongji.manageuser.entity.ManageUser;
import com.lianghongji.manageuser.entity.Role;
import com.lianghongji.manageuser.entity.RoleResourceRela;
import com.lianghongji.manageuser.repository.ManageUserRepository;
import com.lianghongji.manageuser.repository.RoleResourceRelaRepository;
import com.lianghongji.manageuser.repository.TokenRepository;
import com.lianghongji.service.AbstractService;
import com.lianghongji.util.PasswordEncryptor;
import com.lianghongji.util.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户管理Service
 *
 * @author liang.hongji
 */
@Service
public class ManageUserService extends AbstractService<ManageUserRepository, ManageUser, Long> {

    private static final Logger LOG = LoggerFactory.getLogger(ManageUserService.class);

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleResourceRelaRepository roleResourceRelaRepository;

    @Transactional
    public ManageLoginResultDto login(ManageLoginDto manageLoginDto, String loginIp) {
        ManageUser manageUser = this.repository.findByUserName(manageLoginDto.getUserName());
        if (manageUser == null) {
            throw new BusinessRuntimeException(ErrorCode.USER_NOT_FOUND);
        }
        PasswordEncryptor encoderMd5 = new PasswordEncryptor(manageUser.getSalt(), "sha-256");
        String encodedPassword = encoderMd5.encode(manageLoginDto.getPassword());
        if (!manageUser.getPassword().equals(encodedPassword)){
            throw new BusinessRuntimeException(ErrorCode.USER_NOT_FOUND);
        }
        manageUser.setLastLoginTime(new Date());
        manageUser.setLastLoginIp(loginIp);
        return genAndSaveToken(manageUser);
    }

    private ManageLoginResultDto genAndSaveToken(ManageUser manageUser) {
        String tokenSrc = manageUser.getUserName() + System.currentTimeMillis();
        String token = TokenUtils.generateToken(tokenSrc);
        tokenRepository.saveManageUser(token, manageUser.getId());
        return new ManageLoginResultDto(manageUser.getUserName(), token, manageUser.getId(),
                manageUser.getRole().getName());
    }

    @Transactional
    public ManageLoginResultDto sighup(ManageUserSighUpDto manageUserSighUpDto){
        if (!manageUserSighUpDto.getPassword().equals(manageUserSighUpDto.getVerifyPassword())){
            throw new BusinessRuntimeException(ErrorCode.PASSWORD_INCONSISTENT);
        }
        if (manageUserSighUpDto.getType() != 2 && manageUserSighUpDto.getType() != 3){
            throw new BusinessRuntimeException(ErrorCode.INVAILD_TYPE);
        }
        if(this.repository.findByUserName(manageUserSighUpDto.getUsername()) != null){
            throw new BusinessRuntimeException(ErrorCode.EXSIST_USER);
        }
        Role role = roleService.find(manageUserSighUpDto.getType());
        String salt = UUID.randomUUID().toString();
        PasswordEncryptor encoderMd5 = new PasswordEncryptor(salt, "sha-256");
        String encodedPassword = encoderMd5.encode(manageUserSighUpDto.getPassword());
        ManageUser manageUser = new ManageUser(manageUserSighUpDto.getUsername(), manageUserSighUpDto.getName(),
                encodedPassword, role, salt);
        this.repository.save(manageUser);
        return genAndSaveToken(manageUser);
    }

    @Transactional
    public long findUserIdByToken(String token){
        return tokenRepository.findManageUserAndUpdate(token);
    }

    @Transactional
    public Optional<ManageUser> findById(Long id){
        return this.repository.findById(id);
    }

    /**
     * 检查用户是否有访问该路径的权限,有返回true, 没有返回false
     *
     * @param path
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public boolean checkAccess(String path, Long userId) {
        ManageUser manageUser = find(userId, ErrorCode.USER_NOT_FOUND);
        if (manageUser.getRole().isAdmin()) {
            // 管理员可以访问所有地址
            return true;
        }
        List<RoleResourceRela> relas = roleResourceRelaRepository.findByRole(manageUser.getRole());
        for (RoleResourceRela roleResourceRela : relas) {
            ManageResources resource = roleResourceRela.getManageResources();
            Pattern pattern = Pattern.compile(resource.getResourceUrl());
            Matcher matcher = pattern.matcher(path);
            if (matcher.find()) {
                return true;
            }
        }
        return false;
    }
}
