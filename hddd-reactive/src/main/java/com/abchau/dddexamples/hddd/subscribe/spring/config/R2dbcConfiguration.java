package com.abchau.archexamples.hddd.subscribe.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.r2dbc.ConnectionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.transaction.ReactiveTransactionManager;

import org.springframework.boot.r2dbc.ConnectionFactoryBuilder;
// import org.springframework.boot.autoconfigure.sql.init.R2dbcInitializationConfiguration;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.log4j.Log4j2;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;

@Log4j2
@Configuration
@EnableR2dbcRepositories
class R2dbcConfiguration extends AbstractR2dbcConfiguration {

    @Bean
	@Override
    public ConnectionFactory connectionFactory() {   
		log.trace(() -> "connectionFactory()...");

		// This after 2021-11
		return ConnectionFactoryBuilder // ;DB_CLOSE_DELAY=-1
			.withUrl("r2dbc:h2:file:///~/testdb;DB_CLOSE_DELAY=-1;")
			// .withUrl("r2dbc:h2:mem:///testdb")
			.username("sa")
			.password("")
			.build();

		// see: https://github.com/spring-projects/spring-data-r2dbc/issues/269
        // return new H2ConnectionFactory(
		// 	 H2ConnectionConfiguration.builder()
		// 				.url("mem:testdb;DB_CLOSE_DELAY=-1;")
        //                 .username("sa")
        //                 // .password("password")
		// 				.build()
        // );
    }
	
	@Bean
    public ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
		log.trace(() -> "transactionManager()...");

        return new R2dbcTransactionManager(connectionFactory);
    }

	@Bean
	public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
		log.trace(() -> "initializer()...");
        log.debug(() -> "Actual Class of ConnectionFactory is: " + connectionFactory.getClass().toString());

        final CompositeDatabasePopulator populator = new CompositeDatabasePopulator();

        log.debug(() -> "Going to populate schame from 'schema.sql'");
        populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));

        log.debug(() -> "Going to populate data from 'data_h2.sql'");
        populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("data.sql")));

		final ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
		initializer.setConnectionFactory(connectionFactory);
		initializer.setDatabasePopulator(populator);

		return initializer;
	}

}