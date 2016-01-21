package org.followfa;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;

import javax.sql.DataSource;

public class FlywayConfiguration {
	public static String[] schemaLocations(final String... locations) {
		return locations;
	}

	public static Flyway migrate(final DataSource datasource, final String schemaVersionTable, final String[] schemaLocations) {
		final Flyway flyway = new Flyway();
		flyway.setLocations(schemaLocations);
		flyway.setTable(schemaVersionTable);
		flyway.setDataSource(datasource);

		try {
			flyway.migrate();
		} catch(FlywayException e) {
			if(e.getMessage().startsWith("Found non-empty schema")) {
				//FIXME add log.info
				flyway.baseline();
				flyway.migrate();
			} else {
				throw e;
			}
		}

		return flyway;

	}
}
