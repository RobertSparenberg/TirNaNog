////////////////
// VIEWS PAGE //
////////////////

function saveViewOrder() {
    var viewNames = [];
    $("#views li").each(function() {
        viewNames += $(this).find('a').text();
    });
    $.ajax({type: "POST", url: "/admin/pages/page/views/save_order", data: viewNames});
}

function deleteView(e) {
    e.preventDefault();
    var name = $(this).siblings().first().text();
    $("#views li:contains(" + name + ")").remove();
    saveViewOrder();
}

function newView(name) {
    var save = false;
    if (typeof name === 'undefined') {
        save = true;
        var newViewNumber = 1;
        $("#views li").each(function() {
            var numberPattern = /New View (\d+)/
            var name = $(this).find('a').text();
            var number = numberPattern.exec(name);
            if(number != null) {
                number = parseInt(number[1], 10);
            } else {
                number = 1;
            }
            if(number >= newViewNumber) {
                newViewNumber = number + 1;
            }
        });
        var name = "New View";
        if(newViewNumber > 1) {
            name += ' ' + newViewNumber;
        }
    }
    $("#views").append("<li><a href=\"/admin/pages/page/views/" + name.replace(" ", "_") + "/view\" class=\"view\">" + name + "</a> <a href=\"/admin/pages/page/views/" + name.replace(" ", "_") + "/delete\" class=\"delete\">X</a></li>");
    $("#views .view:last").click(navigateToPage);
    $("#views .delete:last").click(deleteView);
    if(save) {
        saveViewOrder();
    }
}

function initViewsPage() {
    $.ajax({url: "/rest/pages/links", success: function(result) {
        result.each(function(value) {
            newView(value);
        });
        $("#views").sortable({stop:saveViewOrder});
        }
    });
}



///////////////////////
// GENERAL FUNCTIONS //
///////////////////////

$(document).ready(function() {
    $("#content").load("/admin/pages/page/views #content");
    initViewsPage();
});

function navigateToPage(e) {
    e.preventDefault();
    $("#content").load($(this).attr("href") + " #content");
    initViewsPage();
}