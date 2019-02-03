////////////////
// VIEWS PAGE //
////////////////

function saveViewOrder() {
    var viewNames = [];
    $("#views li a.view").each(function() {
        viewNames.push($(this).text());
    });
    $.ajax({type: "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url: "/rest/admin/pages/page/views/save_order",
            data: JSON.stringify(viewNames),
            dataType: "json"});
}

function deleteView(e) {
    e.preventDefault();
    var name = $(this).siblings().first().text();
    $("#viewDeleteDialog b:first").text(name);
    $("#viewDeleteDialog").dialog("option", "buttons", [
        {
            text: "Cancel",
            click: function() {
                $(this).dialog("close");
            }
        },
        {
            text: "Delete",
            click: function() {
                $("#views li:contains(" + name + ")").remove();
                saveViewOrder();
                $(this).dialog( "close" );
            }
        }
    ]);
    $("#viewDeleteDialog").dialog("open");
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
                number = -1;
            }
            if(number >= newViewNumber) {
                newViewNumber = number + 1;
            }
        });
        var name = "New View " + newViewNumber;
    }
    $("#views").append("<li><a href=\"/admin/pages/page/views/" + name.replace(" ", "_") + "/view\" class=\"view\">" + name + "</a> <a href=\"/admin/pages/page/views/" + name.replace(" ", "_") + "/delete\" class=\"delete\">X</a></li>");
    $("#views .view:last").click(navigateToPage);
    $("#views .delete:last").click(deleteView);
    if(save) {
        saveViewOrder();
    }
}

function initViewsPage() {
    $("#viewDeleteDialog").dialog({
            modal: true,
            width: 500,
            height: 200,
            autoOpen: false,
            title: "Confirm Delete"
    });
    $("#views").sortable({stop:saveViewOrder});

    $.ajax({url: "/rest/pages/links", success: function(result) {
        for(var i = 0; i < result.length; i++) {
            newView(result[i]);
        }
    }});
}



///////////////////////
// GENERAL FUNCTIONS //
///////////////////////

$(document).ready(function() {
    loadPage("/admin/pages/page/views");
});

function navigateToPage(e) {
    e.preventDefault();
    loadPage($(this).attr("href"));
}

function loadPage(link) {
    $("#content").load(link + " #content", function(responseText, statusText, response) {
        if(response.status == 200) {
            initViewsPage();
        } else {
            alert("Failed to load '" + link + "'");
        }
    });
}