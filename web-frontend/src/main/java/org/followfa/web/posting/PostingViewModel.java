package org.followfa.web.posting;

public class PostingViewModel {
	private String postingText;

	public PostingViewModel() {
	}

	public PostingViewModel(final String postingText) {
		this.postingText = postingText;
	}

	public String getPostingText() {
		return postingText;
	}

	public void setPostingText(final String postingText) {
		this.postingText = postingText;
	}
}
