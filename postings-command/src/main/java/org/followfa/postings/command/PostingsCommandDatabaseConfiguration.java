package org.followfa.postings.command;

import org.flywaydb.core.Flyway;
import org.followfa.DatabaseConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Import(DatabaseConfiguration.class)
public class PostingsCommandDatabaseConfiguration {
	@Bean
	public Flyway flywayForPostingsCommand(final DataSource dataSource) {
		return FlywayMigrationConfiguration.migrateDatabaseWithFlyway(dataSource);
	}
}
