package org.followfa.postings.command;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class FlywayMigrationConfiguration {
	public static Flyway migrateDatabaseWithFlyway(final DataSource datasource) {
		final Flyway flyway = new Flyway();
		flyway.setLocations("dbschema/postings-command/common", "dbschema/postings-command/h2");
		//flyway.setTable("postings_command_schema_version"); FIXME do we need this???
		flyway.setDataSource(datasource);

		flyway.migrate();

		return flyway;
	}

}
