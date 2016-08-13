$(document).on({
    click: function(){
      var course = "";
      var code = "";
      var firstChild = $(this).find('td').first();
      if (firstChild.is('.CourseTitle')){
        course = firstChild.text();
    }else{
        var parent = $(this).prevAll().find('td.CourseTitle');
        course = parent.text();
        code = $(this).find('td').first().text();
    }
    course = course.trim().split(/[\s]{2,}/);
    title = course[0] + " " + course[1] + ": " + course[2];
    title = title.toUpperCase();
    var remindtext = "";
    if (!(code == ""))
        remindtext = "Remind me when " + title  + ", Section: " + code+ " is..."
    else
        remindtext = "Remind me when any class in " + title + " is..."
    
    $('#modal-remind-text').text(remindtext);
    $('#myModal').modal('toggle');
},
//    mouseover: function () {
//  console.log($(this));
//  $(this).css({"background-color": "#DDEEFF", "cursor": "pointer", "color": "red"});
// }
}, "tr[valign]");

$('#modal-setreminder').click(function(){
    alert("Setting reminder for class...");
});
