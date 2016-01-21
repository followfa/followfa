package org.followfa.postings.query;

import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

public class FlywayMigrationConfiguration {
	public static Flyway migrateDatabaseWithFlyway(final DataSource datasource) {
		final Flyway flyway = new Flyway();
		flyway.setLocations("dbschema/postings-query/h2");
		flyway.setDataSource(datasource);

		flyway.migrate();

		return flyway;
	}

}
