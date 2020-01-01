package com.lianghongji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * app主类
 *
 * @author liang.hongji
 *
 */
@EnableSwagger2
@SpringBootApplication
public class App {
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
}
