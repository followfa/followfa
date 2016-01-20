package org.followfa.web;

import org.followfa.defensive.Args;
import org.followfa.web.posting.PostingTranslationService;
import org.followfa.web.posting.PostingViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import static org.followfa.defensive.ReturnValue.notNull;

@Controller
public class HomeController {
	private final PostingTranslationService postingTranslationService;

	@Autowired
	public HomeController(final PostingTranslationService postingTranslationService) {
		Args.notNull(postingTranslationService, "postingTranslationService");
		this.postingTranslationService = postingTranslationService;
	}

	@RequestMapping("/")
	public ModelAndView index() {
		Map<String, Object> model = new HashMap<>();

		model.put("newPosting", new PostingViewModel());
		model.put("postings", postingTranslationService.getNewestPostingsForCurrentUser(1L, 50L));

		return notNull(new ModelAndView("index", model));
	}

	@RequestMapping("/new-posting")
	public String newPosting(@ModelAttribute final PostingViewModel newPosting) {
		postingTranslationService.createNewPosting(1L, newPosting.getPostingText());
		return notNull("redirect:/");
	}
}
