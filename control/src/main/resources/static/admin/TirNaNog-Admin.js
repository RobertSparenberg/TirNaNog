////////////////
// VIEWS PAGE //
////////////////

var subPages;
var currentSubPage;

//view related editing

function saveView() {
    var rowsFromDom = $("#view-rows > div");
    var rows = [];
    for(var i = 0; i < rowsFromDom.length; i++) {
        var rowFromDom = rowsFromDom[i];
        var pageItems = [];
        var pageItemsFromDom = rowFromDom.children();
        for(var j = 0; j < pageItemsFromDom.length; j++) {
            var pageItemFromDom = pageItemsFromDom[j];
            var pageItem = {type: pageItemFromDom.attr("class")}
            switch(pageItem.type) {
                case "GraphPageItem":
                    pageItem.recordName = pageItemFromDom.children('input[name="recordName"]').val();
                    pageItem.recordValue = pageItemFromDom.children('input[name="recordValue"]').val();
                    pageItem.updateDelay = pageItemFromDom.children('input[name="updateDelay"]').val();
                    pageItem.numberOfValuesToUse = pageItemFromDom.children('input[name="numberOfValuesToUse"]').val();
                    break;
                case "ParameterPageItem":
                    pageItem.parameterPath = pageItemFromDom.children('input[name="parameterPath"]').val();
                    break;
                case "SingleRecordPageItem":
                    pageItem.recordName = pageItemFromDom.children('input[name="recordName"]').val();
                    pageItem.valuesToDisplay = pageItemFromDom.children('input[name="valuesToDisplay"]').val();
                    break;
            }
            pageItems.push(pageItem);
        }
        rows.push({"pageItems": pageItems});
    }

    var viewObject = {"name": $("#view-name p:first").text(),
                      "order": -1,
                      "rows": rows
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

function selectViewRow() {
    var selectedRows =  $("#view-rows .selected");
    for(var i = 0; i < selectedRows.length; i++) {
        var oldSelectedRow = selectedRows[i];
        oldSelectedRow.removeClass("selected");
    }
    $(this).addClass("selected");
}

function addViewRow() {
    $("#view-rows").append("<div class=\"viewRow\"></div>");
    $("#view-rows > div:last").click(selectViewRow);
}

function addViewItem(e) {
    var type = $("#add-view-item option:selected")
    var viewItem;
    switch(type) {
        case "GraphPageItem":
            var recordNameInput = "<div class=\"viewItemInput\"><p class=\"label\">Record Name</p><input name=\"recordName\" type=\"text\"/></div>";
            var recordValueInput = "<div class=\"viewItemInput\"><p class=\"label\">Record Value</p><input name=\"recordValue\" type=\"text\"/></div>";
            var updateDelayInput = "<div class=\"viewItemInput\"><p class=\"label\">Update Delay</p><input name=\"updateDelay\" type=\"text\"/></div>";
            var numberOfValuesInput = "<div class=\"viewItemInput\"><p class=\"label\">Number of values to use</p><input name=\"numberOfValuesToUse\" type=\"text\"/></div>";
            viewItem = recordNameInput + recordValueInput + updateDelayInput + numberOfValuesInput;
            break;
        case "ParameterPageItem":
            viewItem = "<div class=\"viewItemInput\"><p class=\"label\">Parameter Path</p><input name=\"parameterPath\" type=\"text\"/></div>";
            break;
        case "SingleRecordPageItem":
            var recordNameInput = "<div class=\"viewItemInput\"><p class=\"label\">Record Name</p><input name=\"recordName\" type=\"text\"/></div>";
            var valuesToDisplayInput = "<div class=\"viewItemInput\"><p class=\"label\">Record Values</p><input name=\"valuesToDisplay\" type=\"text\"/></div>";
            viewItem = recordNameInput + valuesToDisplayInput;
            break;
    }
    viewItem = "<div class=\"viewItem\"><h1>" + $(this).val() + "</h1>" + viewItem + "</div>";
    $("#view-rows .selected").append(viewItem);
    $("#add-view-item").prop("selectedIndex", "");
}



//view related navigation

function showView(e) {
    var link = e.target.href;
    $("#views-content").load(link + " #content > *", function(responseText, statusText, response) {
        if(response.status == 200) {
            var splitLink = link.split("/");
            var viewName = splitLink[splitLink.length-1];
            for(var i = 0; i < subPages.length; i++) {
                var view = subPages[i].replace(/ /g, "_");
                if(view === viewName) {
                    $.ajax({url: "/rest/pages/page/" + view, success: function(result) {
                            currentSubPage = result;
                            initViewPage();
                        }});
                    break;
                }
            }
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
            title: "Delete"
    });
    $("#views").sortable({stop:saveViewOrder});

    $.ajax({url: "/rest/pages/links", success: function(result) {
        for(var i = 0; i < result.length; i++) {
            newView(result[i]);
        }
        subPages = result;
        $("#views a.view:first").click();
    }});
}

function initViewPage() {
    $("#view-rename-dialog").dialog({
            modal: true,
            width: 500,
            height: 200,
            autoOpen: false,
            title: "Rename"
    });
    $("#rename-view").click(renameView);
    $("#view-add-row").click(addViewRow);
    $("#add-view-item").change(addViewItem);
    $("#view-rows").sortable({stop:saveView});

    for(var i = 0; i < currentSubPage.rows.length; i++) {
        var row = currentSubPage.rows[i];
        addViewRow();
        for(var j = 0; j < row.items.length; j++) {
            var item = row.items[j];
//            addViewItem(item);
        }
    }
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
    $("#content").load(link + " #content > *", function(responseText, statusText, response) {
        if(response.status == 200) {
            var splitLink = link.split("/");
            var pageName = splitLink[splitLink.length-1];
            switch(pageName) {
                case "views":
                    initViewsPage();
                    break;
            }
        } else {
            alert("Failed to load '" + link + "'");
        }
    });
}