function initializeGrid() {
	var $grid = $('.grid').masonry({
		// options
		itemSelector: '.grid-item',
		columnWidth: '.grid-sizer',
		percentPosition: true
	});
}

function sendPostingToServer() {
	console.log("sending...");
	$('#new-posting-form').submit();
}

function initializeWritePostForm() {
	$('#send-posting-button').click(sendPostingToServer);
	$('#write-posting-button').click(function() {
		$('#posting-editor').css("display", "block");
	})
	$('#cancel-posting-button').click(function() {
		$('#posting-editor').css("display", "none");
	});
}
