package org.followfa.web.posting;

import org.followfa.postings.command.CreatePostingService;
import org.followfa.postings.query.QueriedPosting;
import org.followfa.postings.query.QueryPostingsService;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TranslationServiceTest {
	private CreatePostingService createPostingService;
	private TranslationService translationService;
	private QueryPostingsService queryPostingService;
	private PostingTranslator postingTranslator;

	@Before
	public void setup() {
		createPostingService = mock(CreatePostingService.class);
		queryPostingService = mock(QueryPostingsService.class);
		postingTranslator = mock(PostingTranslator.class);

		translationService = new TranslationService(createPostingService, queryPostingService, postingTranslator);
	}

	@Test
	public void usesPostingTranslatorToActuallyTranslatePostings() {
		final QueriedPosting queriedPosting = mock(QueriedPosting.class);

		when(queryPostingService.listPostingsForCurrentUser(eq(1L), anyInt())).thenReturn((List)Arrays.asList(queriedPosting));
		when(postingTranslator.translate(any())).thenReturn(new PostingViewModel("Hello World"));

		final List<PostingViewModel> postings = translationService.getNewestPostingsForCurrentUser(1L, 50).collect(Collectors.toList());

		assertNotNull(postings);
		assertThat(postings.size(), is(1));
		assertThat(postings, hasItem(hasProperty("postingText", is("Hello World"))));
	}
}