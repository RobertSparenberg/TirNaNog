////////////////
// VIEWS PAGE //
////////////////

//view related editing

function saveView() {
    var viewObject = {"name": $("#view-name p:first").text(),
                      "order": -1,
                      "rows": []
    };
    $.ajax({type: "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url: "/rest/admin/pages/page/views/save_order",
            data: JSON.stringify(viewObject),
            dataType: "json"});
}

function renameView() {
    e.preventDefault();
    var oldName = $("#new-name-input").value();
    $("#rename-view-dialog").dialog("option", "buttons", [
        {
            text: "Cancel",
            click: function() {
                $(this).dialog("close");
                $("#rename-view-dialog-error").text("");
                $("#new-name-input").value(oldName);
            }
        },
        {
            text: "Rename",
            click: function() {
                var newName = $("#new-name-input").value();
                if($("#views li:contains(" + newName + ")").length !== 0) {
                    $("#rename-view-dialog").text("The provided name has already been taken.");
                } else {
                    $("#rename-view-dialog-error").text("");
                    $("#views li:contains(" + oldName + ")").html("<a href=\"/admin/pages/page/views/view/" + newName.replace(/ /g, "_") + "\" class=\"view\">" + newName + "</a> <a class=\"delete\">X</a>");
                    $("#view-name p:first").text(newName);
                    saveView();
                    $(this).dialog( "close" );
                    oldName = newName;
                }
            }
        }
    ]);
    $("#view-delete-dialog").dialog("open");
}



//view related navigation

function showView(e) {
    var link = e.target.href;
    $("#views-content").load(link + " #content", function(responseText, statusText, response) {
        if(response.status == 200) {
            $("#rename-view-dialog").dialog({
                    modal: true,
                    width: 500,
                    height: 200,
                    autoOpen: false,
                    title: "Rename View"
            });
            $("#rename-view").click(renameView);
            //$("#views").sortable({stop:saveViewOrder});
        } else {
            alert("Failed to load '" + link + "'");
        }
    });
}

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
    $("#view-delete-dialog b:first").text(name);
    $("#view-delete-dialog").dialog("option", "buttons", [
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
    $("#view-delete-dialog").dialog("open");
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
    $("#views").append("<li><a href=\"/admin/pages/page/views/view/" + name.replace(/ /g, "_") + "\" class=\"view\">" + name + "</a> <a class=\"delete\">X</a></li>");
    $("#views .view:last").click(showView);
    $("#views .delete:last").click(deleteView);
    if(save) {
        saveViewOrder();
    }
}

function initViewsPage() {
    $("#view-delete-dialog").dialog({
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