package org.followfa.postings.query;

import org.flywaydb.core.Flyway;
import org.followfa.DatabaseConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

@Configuration
@Import(DatabaseConfiguration.class)
public class PostingsQueryDatabaseConfiguration {
	@Bean
	public Flyway flywayForPostingsQuery(final DataSource dataSource) {
		return FlywayMigrationConfiguration.migrateDatabaseWithFlyway(dataSource);
	}
}
