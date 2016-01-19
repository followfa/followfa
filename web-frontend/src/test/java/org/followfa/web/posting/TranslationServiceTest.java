package org.followfa.web.posting;

import org.followfa.postings.command.CreatePostingService;
import org.followfa.postings.query.Posting;
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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
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
		when(queryPostingService.listPostingsFor(eq(1L), anyLong())).thenReturn(Arrays.asList(new Posting()));
		when(postingTranslator.translate(any())).thenReturn(new PostingViewModel("Hello World"));

		final List<PostingViewModel> postings = translationService.getNewestPostingsFor(1L, 50L).collect(Collectors.toList());

		assertNotNull(postings);
		assertThat(postings.size(), is(1));
		assertThat(postings, hasItem(hasProperty("postingText", is("Hello World"))));
	}
}