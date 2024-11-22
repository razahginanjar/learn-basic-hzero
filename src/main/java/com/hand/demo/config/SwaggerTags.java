package com.hand.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.Tag;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger Api 描述配置
 */
@Configuration
public class SwaggerTags {

    public static final String EXAMPLE = "Example";
    public static final String TASK = "Task";
    public static final String USER = "user";
    public static final String MESSENGER = "Messenger";
    public static final String FILE = "FILE";

    @Autowired
    public SwaggerTags(Docket docket) {
        docket.tags(
                new Tag(EXAMPLE, "EXAMPLE 案例"),
                new Tag(TASK, "TASK Management"),
                new Tag(USER, "USER Management"),
                new Tag(MESSENGER, "MESSENGER Management"),
                new Tag(FILE, "FILE Management")
        );
    }
}
