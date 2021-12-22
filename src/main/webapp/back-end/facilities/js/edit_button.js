//edit Buttons
$("table").on("click", ".fa-minus-circle", function () {
  if (
    prompt(
      "Are You Sure You Want to Delete this Row? Type 'yes' to Confirm this"
    ) == "yes"
  ) {
    $(this).closest("tr").remove();
  } else {
  }
});

$("table").on("click", ".fa-edit, .fa-save", function () {
  var thisRow = $(this).parent().siblings();
  var editOn = $(this).hasClass("editMode");

  $("td:last-child").attr("contenteditable", "false");
  $("td:last-child").css("background-color", "transparent");

  if (editOn == false) {
    $(thisRow).attr("contenteditable", "true");
    $(thisRow).css("background-color", "#EBECF0");
    $(this).removeClass("fa-edit");
    $(this).addClass("fa-save editMode");
  } else if (editOn == true) {
    $(thisRow).attr("contenteditable", "false");
    $(thisRow).css("background-color", "transparent");
    $(this).removeClass("fa-save editMode");
    $(this).addClass("fa-edit");
  }
});

$("th", this).mouseout(function () {
  $(this).attr("contenteditable", "false");
});
