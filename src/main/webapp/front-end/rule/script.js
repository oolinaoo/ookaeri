//top bar
var ruleShinker = function() {
    var rule = $('.rule-nav'),
        ruleHeight = $('.rule-nav').outerHeight(true);
        $(rule).parent().css('padding-top', ruleHeight);
    $(window).scroll(function() {
        var scrollOffset = $(window).scrollTop();
        if (scrollOffset < ruleHeight) {
            $(rule).css('height', (ruleHeight - scrollOffset));
        }
        if (scrollOffset > (ruleHeight - 215)) {
            rule.addClass('fixme');
        } else {
            rule.removeClass('fixme');
        };
    });
}
ruleShinker();

//json from servlet listall
$(function () {
	let url =
	  "/okaeri/rule/listAll";
	let xhr = new XMLHttpRequest();
	xhr.open("GET", url);
	xhr.send();
	xhr.onload = function () {
	  let data = JSON.parse(this.responseText);
	  console.log(data);
	  for (let i = 0; i < data.length; i++) {
		$(".page-content").append(
				"<P>" + 
				data[i].ruleContent +
				"</P>"
		);
	  }
	};
});