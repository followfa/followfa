package org.followfa.postings.query;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.followfa.FlywayConfiguration;

import javax.sql.DataSource;

public class FlywayMigrationConfiguration {
	public static Flyway migrateDatabaseWithFlyway(final DataSource datasource) {
		return FlywayConfiguration.migrate(datasource, "postings_query_schema_version", FlywayConfiguration.schemaLocations("dbschema/postings-query/h2"));
	}
}
