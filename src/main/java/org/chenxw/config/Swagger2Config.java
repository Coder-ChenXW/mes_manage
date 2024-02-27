package org.chenxw.config;


import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: ChenXW
 * @Date:2024/2/27 14:37
 * @Description: Swagger
 **/
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket docket1(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(adminApiInfo())
                .groupName("authenticationApi")
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("org.chenxw.authentication.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket docket2(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(adminApiInfo())
                .groupName("mesApi")
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("org.chenxw.mes.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo adminApiInfo(){
        return new ApiInfoBuilder()
                .title("工厂生产管理系统--api文档")
                .description("工厂生产管理系统接口描述")
                .version("1.0")
                .contact(new Contact("陈宣文","https://github.com/Coder-ChenXW","chenxw_cn@163.com"))
                .build();
    }

}
