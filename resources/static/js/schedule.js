

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
    // title = title.toUpperCase();

    $('#modal-remind-text-dept').text(course[0]);
    $('#modal-remind-text-number').text(course[1]);
    $('#modal-remind-text-title').text(course[2]);
    if (!(code == "")){
      $('#modal-remind-text-section').text(", Section: ");
      $('#modal-remind-text-code').text(code);
    }
    $('#myModal').modal('toggle');
},
//    mouseover: function () {
//  console.log($(this));
//  $(this).css({"background-color": "#DDEEFF", "cursor": "pointer", "color": "red"});
// }
}, "tr[valign]");

$(document).ready(function(){
  $("#modal-setreminder").on('click', function(){
          console.log("Setting reminder for class...");
  });
});
