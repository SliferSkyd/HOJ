package com.sliferskyd.submissionservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {
    @Autowired
    private Environment env;

    @Override
    protected String getDatabaseName() {
        return env.getProperty("spring.data.mongodb.database");
    }

}
