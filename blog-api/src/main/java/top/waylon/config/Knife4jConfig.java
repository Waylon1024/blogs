package top.waylon.config;

import cn.hutool.core.collection.CollectionUtil;
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

import java.util.List;

@Configuration
@EnableSwagger2
public class Knife4jConfig {

    private List<Parameter> getParameters() {
        Parameter parameter = new ParameterBuilder()
                .name("Authorization")
                .description("Authorization")
                .modelRef(new ModelRef("string"))
                .defaultValue("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBZG1pbiIsImF1dGhvcml0aWVzIjoiUk9MRV9hZG1pbiwiLCJleHAiOjE2ODUwODQ1NTZ9.BcXeyLliMUjp8CjStB8txoTzqU0o8yYUDaZbITv0UpneM3-6vx45aXDe9X1Sb4hg2D7IaPxBvet-YDUm6eZXwA")  // 请求头默认值
                .parameterType("header")
                .required(false)
                .build();

        List<Parameter> parameters = CollectionUtil.newArrayList();
        parameters.add(parameter);
        return parameters;
    }

    @Bean
    public Docket adminApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("后台管理相关接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage("top.waylon.controller.admin"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket qianTaiApi() {
        List<Parameter> parameters = getParameters();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("前台相关接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage("top.waylon.controller.qianTai"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("waylon博客API")
//                .description("没有描述")
                .version("1.0.0")
                .build();
    }
}
