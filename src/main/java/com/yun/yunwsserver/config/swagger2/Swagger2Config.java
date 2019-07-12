package com.yun.yunwsserver.config.swagger2;

import com.yun.yunwsserver.util.GlobalConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * The itemType Swagger 2 config.
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    /**
     * Api sw 2 docket.
     * @return the docket
     */
    @Bean
    public Docket apiSw2() {
        //添加head参数start
        List<Parameter> pars = new ArrayList<Parameter>();
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name(GlobalConstant.Sys.TOKEN_AUTH_DTO).description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());

        ParameterBuilder accPar = new ParameterBuilder();
        accPar.name(GlobalConstant.Sys.ACCESS_AUTH_DTO).description("ACC").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(accPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 包路径
                .apis(RequestHandlerSelectors.basePackage("com.yun.yunwsserver"))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("eg APIs")
                .description("spring boot")
                // 条款地址
                // .termsOfServiceUrl("xxx")
                // .contact("aaa")
                .version("1.0")
                .build();
    }
}
