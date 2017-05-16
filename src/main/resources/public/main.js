"use strict";
var app = app || {};

app.refreshNotes = function() {
    $("#noteList").empty();
    $.get(
        "/api/note",
        function(data) {
            var i,
                li,
                value,
                noteList = $("#noteList"),
                span;

            for (i = 0; i < data.length; i++) {
                span = "<span onclick=\"app.fetchValue('{uuid}')\">" +
                    "get value" +
                    "</span>";
                span = span.replace(/{uuid}/, data[i].uuid);
                li = "<li data-uuid=\"{uuid}\">{title} - {get}</li>"
                    .replace(/{uuid}/, data[i].uuid)
                    .replace(/{title}/, data[i].title)
                    .replace(/{get}/, span);
                $("#noteList").append(li);
            }
        },
        "json");
};

app.fetchValue = function(uuid) {
    $.get(
        "/api/note/" + uuid,
        function(data) {
            var text = data.title + " - " + data.value;
            $("li[data-uuid='" + uuid + "']").text(text);
        },
        "json");
};

app.addNote = function(title, value, cb) {
    console.log("Add new note. Title: " + title + ", value: " + value);
    var jqXhr = $.ajax(
        "/api/note", {
            data: {
                title: title,
                value: value
            },
            type: "POST"
        });
    jqXhr.done(function() {
        cb();
    });
};

app.init = function() {
    $("#addNoteInput").on("keyup", function(e) {
        var inputValue,
            pieces,
            title,
            value;
        if (e.which !== 13) {
            return;
        }

        inputValue = $("#addNoteInput").val().trim();
        pieces = inputValue.split(":", 2);
        if (pieces.length < 2) {
            return;
        }
        title = pieces[0].trim();
        value = pieces[1].trim();
        if (title.length === 0 || value.length === 0) {
            return;
        }
        app.addNote(title, value, function() {
            $("#addNoteInput").val("");
            app.refreshNotes();
            $("#addNoteInput").focus();
        });
    });
    app.refreshNotes();
};

window.onload = app.init;
