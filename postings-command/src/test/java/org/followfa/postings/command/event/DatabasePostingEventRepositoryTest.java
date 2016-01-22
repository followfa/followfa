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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
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

		final PostingEvent loadedEvent = entityManagerFactory.createEntityManager().find(PostingEvent.class, createdEvent.getPostingEventId());
		assertNotNull(loadedEvent);
		assertThat(loadedEvent, allOf(
				hasProperty("clientEventId", is("clientId")),
				hasProperty("postingText", is("Hello World"))));
	}

	@Test
	public void listsAllPostingEventsWhenLastEventIdIsNull() {
		createPostingEvents();

		final List<PostingEvent> events = repository.getPostingEventsFor(5L, null);

		assertThat(events.size(), is(2));
		assertThat(events, containsInAnyOrder(
				hasProperty("postingText", is("Hello 1")),
				hasProperty("postingText", is("Hello 2"))));
	}

	@Test
	public void listsSelectedPostingEventsWhenLastEventIdIsNotNull() {
		final long lastInsertedId = createPostingEvents();

		final List<PostingEvent> events = repository.getPostingEventsFor(5L, lastInsertedId);

		assertThat(events.size(), is(1));
		assertThat(events.get(0), is(hasProperty("postingText", is("Hello 2"))));
	}

	protected Long createPostingEvents() {
		final EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		PostingEvent lastPostingEvent = new PostingEvent(2L, 5L, new Timestamp(System.currentTimeMillis()), "clientId", PostingEventType.CREATED, "Hello 1");
		em.persist(lastPostingEvent);
		em.persist(new PostingEvent(3L, 5L, new Timestamp(System.currentTimeMillis()), "clientId", PostingEventType.CREATED, "Hello 2"));
		em.getTransaction().commit();

		return lastPostingEvent.getPostingEventId();
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