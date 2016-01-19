package org.followfa.postings.command.event;

import org.flywaydb.core.Flyway;
import org.followfa.postings.command.FlywayMigrationConfiguration;
import org.followfa.testutil.InMemoryDatabaseConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.Timestamp;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DatabasePostingEventRepositoryTest.Configuration.class })
public class DatabasePostingEventRepositoryTest {
	@Autowired
	private PostingEventRepository repository;
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Test
	public void savesPostingEventToDatabase() {
		final PostingEvent createdEvent = new PostingEvent(1L, 2L, new Timestamp(System.currentTimeMillis()), "clientId", PostingEventType.CREATED, "Hello World");
		repository.addPostingEvent(createdEvent);

		final PostingEvent loadedEvent = entityManagerFactory.createEntityManager().find(PostingEvent.class, 1L);
		assertNotNull(loadedEvent);
		assertThat(loadedEvent, allOf(
				hasProperty("clientEventId", is("clientId")),
				hasProperty("postingText", is("Hello World"))));
	}

	@Import(InMemoryDatabaseConfiguration.class)
	public static class Configuration {
		@Bean
		public Flyway flyway(final DataSource dataSource) {
			return FlywayMigrationConfiguration.migrateDatabaseWithFlyway(dataSource);
		}

		@Bean
		public PostingEventRepository repository() {
			return new DatabasePostingEventRepository();
		}
	}
}