$(document).on({
    click: function () {
    	var firstChild = $(this).find('td').first();
    	var course = firstChild.text().trim();
    	if (firstChild.is('.CourseTitle')){
			course = course.split(/[\s]{2,}/)	
    	}
		alert('You clicked ' + course);
    },
 //    mouseover: function () {
	// 	console.log($(this));
	// 	$(this).css({"background-color": "#DDEEFF", "cursor": "pointer", "color": "red"});
	// }
}, "tr[valign]");

