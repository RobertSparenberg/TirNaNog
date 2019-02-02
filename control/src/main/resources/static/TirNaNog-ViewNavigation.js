$(document).ready(function() {
    loadNavigation();
});

function loadNavigation() {
    $.ajax({url: "/rest/pages/links", success: function(result) {
        var navigationList = $("#navigation > ul");
        var linkPrototype = navigationList.find("li").first();
        var first = true;
        for(var index in result) {
            var link = result[index];
            if(first) {
                $("#content").load("/rest/pages/page/" + link + " #content");
                first = false;
            }
            var copy = linkPrototype.clone();
            copy.html("<a href=\"/rest/pages/page/" + link + "\">" + link + "</a>");
            copy.click(navigateToPage);
            navigationList.append(copy);
        }
    }});
}

function navigateToPage(e) {
    e.preventDefault();
    $("#content").load($(this).attr("href") + " #content");
}