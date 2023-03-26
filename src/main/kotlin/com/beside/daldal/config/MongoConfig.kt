package com.beside.daldal.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@EnableMongoRepositories(basePackages = ["com.beside.daldal.domain"])
@EnableMongoAuditing
@Configuration
class MongoConfig {
}