package com.financetracker.app.config

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

@SpringBootTest
@Testcontainers
class IntegrationTestConfig extends Specification {

    static GenericContainer mongodb = new GenericContainer("mongo:6.0-jammy")
        .withExposedPorts(27017)
        .withEnv("MONGO_INITDB_ROOT_USERNAME", "admin")
        .withEnv("MONGO_INITDB_ROOT_PASSWORD", "admin")
        .withEnv("MONGO_INITDB_DATABASE", "database")
        .waitingFor(Wait.forHttp("/"))

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        mongodb.start()

        registry.add("spring.data.mongodb.uri", () -> "mongodb://admin:admin@" + mongodb.getHost() + ":" + mongodb.getFirstMappedPort() + "/database?authSource=admin")
        registry.add("spring.data.mongodb.database", () -> "database")
        registry.add("spring.data.mongodb.username", () -> "admin")
        registry.add("spring.data.mongodb.password", () -> "admin")
    }

}
