package com.sparta.newsfeed;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo())
                .tags(Arrays.asList(
                        new Tag().name("사용자").description("UserController API"),
                        new Tag().name("게시물").description("BoardController API"),
                        new Tag().name("댓글").description("CommentController API")
                ));
    }

    private Info apiInfo() {
        return new Info()
                .title("1바이트")
                .description("뉴스피드 팀과제")
                .version("0.0.1");
    }

}
