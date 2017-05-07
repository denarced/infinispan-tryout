"use strict";
var app = app || {};

app.refreshNotes = function() {
    $("#noteList").empty();
    $.get(
        "/api/note",
        function(data) {
            var i,
                value,
                noteList = $("#noteList");

            for (i = 0; i < data.length; i++) {
                $("#noteList").append("<li>" + data[i] + "</li>");
            }
        },
        "json");
};

app.addNote = function(value, cb) {
    console.log("Add new note: " + value);
    var jqXhr = $.ajax(
        "/api/note", {
            data: {
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
        var inputValue;
        if (e.which === 13) {
            inputValue = $("#addNoteInput").val().trim();
            if (inputValue.length > 0) {
                app.addNote(inputValue, function() {
                    $("#addNoteInput").val("");
                    app.refreshNotes();
                    $("#addNoteInput").focus();
                });
            }
        }
    });
    app.refreshNotes();
};

window.onload = app.init;
