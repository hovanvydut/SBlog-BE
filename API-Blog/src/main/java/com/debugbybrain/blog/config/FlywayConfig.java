package com.debugbybrain.blog.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Configuration;

/**
 * @author hovanvydut
 * Created on 8/8/21
 */

/**
 * This empty config to avoid flyway auto run migration check
 * Solution is define an empty class implements this Strategy
 */
@Configuration
public class FlywayConfig implements FlywayMigrationStrategy {
    @Override
    public void migrate(Flyway flyway) {

    }
}
