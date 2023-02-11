package project.avatar.api.config;

import org.hibernate.annotations.DialectOverride;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@Configuration
/*@EnableSwagger2
public class SwaggerConfiguration {
    private static final String TITLE = "AvatarS API";
    private static final String DESCRIPTION = "Descripcion AvatarS API";
    private static final String BASE_PACKAGE = "project.avatar.api.controller";
    private static final String VERSION = "v1";
    @Bean
    public Docket swagger() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .build()
                .forCodeGeneration(true)
                .apiInfo(new ApiInfoBuilder().title(TITLE).description(DESCRIPTION).version(VERSION).build());
    }


}*/

//@Configuration
@EnableSwagger2
public class SwaggerConfiguration implements WebMvcConfigurer {

    private ApiInfo swaggerInfo(){
        return new ApiInfoBuilder().title("Spring API Documentation")
                .description("앱 개발시 사용되는 서버 API 연동 문서")
                .version("1").build();
    }

    @Bean
    public Docket swaggerApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("AvatarS")
                .apiInfo(swaggerInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("project.avatar.api.controller"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/");
                //.useDefaultResponseMessages(false);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // enabling swagger-ui part for visual documentation
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };

}


