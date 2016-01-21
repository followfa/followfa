package org.followfa.postings.command;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.followfa.FlywayConfiguration;

import javax.sql.DataSource;

public class FlywayMigrationConfiguration {
	public static Flyway migrateDatabaseWithFlyway(final DataSource datasource) {
		return FlywayConfiguration.migrate(datasource, "postings_command_schema_version", FlywayConfiguration.schemaLocations("dbschema/postings-command/common", "dbschema/postings-command/h2"));
	}
}
