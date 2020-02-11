package com.travel.community.travel_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;



@Configuration
@EnableSwagger2  //开启Swagger2
public class SwaggerConfig {

    //配置了Swagger2的Bean实例
    @Bean
    public Docket docket(Environment environment){

//        Profiles profiles = Profiles.of("dev","test");

//        boolean flag = environment.acceptsProfiles(profiles);//生产环境

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("wjc")
                .enable(true)
                .select()
                //RequestHandlerSelectors  配置需要扫描接口的方式
                //basePackage(): 指定要扫描的包 , any():扫描全部, none():全部不扫描 ,
                // withClassAnnotation():扫描类上的注解, withMethodAnnotation():扫描方法上的注解
                .apis(RequestHandlerSelectors.basePackage("com.travel.community.travel_demo.controller"))
                //过滤路径
                .paths(PathSelectors.any())
                .build();
    }

    //配置Swagger信息=apiInfo
    private ApiInfo apiInfo(){

        Contact myContact = new Contact("王嘉程","http://localhost:8886","280167437@qq.com");

        return new ApiInfo("王嘉程的travel_demo项目API列表",
                "这就是王嘉程的项目接口，没有其他了",
                "1.0",
                "urn:tos",
                myContact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }


}
