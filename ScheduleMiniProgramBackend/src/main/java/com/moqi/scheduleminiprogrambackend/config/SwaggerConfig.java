package com.moqi.scheduleminiprogrambackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2 //开启Swagger2
public class SwaggerConfig {


    //配置了Swagger的Docket的Bean实例
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.moqi.scheduleminiprogrambackend.controller"))
                .build();
    }

    private ApiInfo getApiInfo(){
        Contact contact=new Contact("张莫泣","https://user.qzone.qq.com/1402290478/main","191830207@smail.nju.edu.cn");
        return new ApiInfo(
                "菲姐陪你唠小程序Api",
                "Api Documentation",
                "1.0",
                "zyy和lyy",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
