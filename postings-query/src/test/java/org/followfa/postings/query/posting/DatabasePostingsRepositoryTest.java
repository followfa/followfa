package org.followfa.postings.query.posting;

import org.flywaydb.core.Flyway;
import org.followfa.postings.query.FlywayMigrationConfiguration;
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

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DatabasePostingsRepositoryTest.Configuration.class })
public class DatabasePostingsRepositoryTest {
	@Autowired
	private PostingsRepository repository;
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Test
	public void getLastPostingEventId_ReturnsNullWhenThereIsNoEntry() {
		final Long lastEventId = repository.getLastPostingEventIdFor(1L);

		assertNull(lastEventId);
	}

	@Test
	public void getLastPostingEventId_ReturnsIdWhenAvailable() {
		final EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em.merge(new LastPostingEventIdByUser(2L, 17L));
		em.getTransaction().commit();
		em.close();

		final Long lastEventId = repository.getLastPostingEventIdFor(2L);

		assertThat(lastEventId, is(17L));
	}

	@Test
	public void setLastPostingEventId_InsertsIdToDatabase() {
		repository.setLastPostingEventIdFor(3L, 18L);

		final EntityManager em = entityManagerFactory.createEntityManager();
		final LastPostingEventIdByUser lastPostingEventId = em.find(LastPostingEventIdByUser.class, 3L);
		em.close();

		assertNotNull(lastPostingEventId);
		assertThat(lastPostingEventId.getLastPostingEventId(), is(18L));
	}

	@Test
	public void insertsNewPostingIntoTheDatabase() {
		Posting posting = new Posting.Builder()
				.withUserId(4L)
				.withPostingText("Hello World")
				.build();

		repository.savePosting(posting);

		final EntityManager em = entityManagerFactory.createEntityManager();
		final List<Posting> postings = em.createQuery("from Posting p where p.userId=:queryUserId")
				.setParameter("queryUserId", 4L)
				.getResultList();
		em.close();

		assertNotNull(postings);
		assertThat(postings.size(), is(1));
		assertThat(postings.get(0), allOf(
				hasProperty("userId", is(4L)),
				hasProperty("postingText", is("Hello World"))
		));
	}

	@Import(InMemoryDatabaseConfiguration.class)
	public static class Configuration {
		@Bean
		public Flyway flyway(final DataSource dataSource) {
			return FlywayMigrationConfiguration.migrateDatabaseWithFlyway(dataSource);
		}

		@Bean
		public PostingsRepository repository() {
			return new DatabasePostingsRepository();
		}
	}
}