package org.followfa.web;

import org.followfa.web.posting.PostingTranslationService;
import org.followfa.web.posting.PostingViewModel;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HomeControllerTest {
	private HomeController homeController;
	private PostingTranslationService postingTranslationService;

	@Before
	public void setup() {
		postingTranslationService = mock(PostingTranslationService.class);
		homeController = new HomeController(postingTranslationService);
	}

	@Test
	public void createsNewPosting_WhenTheUserSubmitsFromTheNewPostingDialog() {
		PostingViewModel newPosting = new PostingViewModel();
		newPosting.setPostingText("Posting Text");

		homeController.newPosting(newPosting);

		verify(postingTranslationService).createNewPosting(anyLong(), eq("Posting Text"));
	}

	@Test
	public void redirectsToMainView_WhenTheUserSubmitsFromTheNewPostingDialog() {
		Object result = homeController.newPosting(mock(PostingViewModel.class));

		assertThat(result, is("redirect:/"));
	}

	@Test
	public void listsAllPostingsOfTheUser_WhenAccessingTheMainView_AndTheUserIsLoggedIn() {
		when(postingTranslationService.getNewestPostingsForCurrentUser(eq(1L), anyInt())).thenReturn(Stream.of(new PostingViewModel("Hello World")));
		final ModelAndView indexModelAndView = homeController.index();

		List<PostingViewModel> postings = (List<PostingViewModel>) indexModelAndView.getModel().get("postings");
		assertThat(postings, hasItem(hasProperty("postingText", is("Hello World"))));
	}
}