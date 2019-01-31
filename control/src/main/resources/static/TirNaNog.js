$(document).ready(function() {
    loadNavigation();
});

function loadNavigation() {
    $.ajax({url: "/rest/pages/links", success: function(result) {
        var navigationList = $("#navigation > ul");
        var linkPrototype = navigationList.find("li").first();
        for(var index in result) {
            var link = result[index];
            var copy = linkPrototype.clone();
            copy.html("<a href=\"/rest/pages/page/" + link + "\">" + link + "</a>");
            copy.click(navigateToPage);
            navigationList.append(copy);
        }
    }});
}

function navigateToPage(e) {
    e.preventDefault();
    $.ajax({url: $(this).attr("href"), success: function(result) {
        alert(result);
    }});
}