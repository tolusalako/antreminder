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

        $('.modal-remind-text-dept').text(course[0]);
        $('#modal-remind-text-number').text(course[1]);
        $('#modal-remind-text-title').text(course[2]);
        if (!(code == "")){
            $('#modal-remind-text-section').text(", Section: ");
            $('#modal-remind-text-code').text(code);
        }
        $('#schedule-modal').modal('toggle');
    },
}, "tr[valign]");

$(document).ready(function(){
    $("#modal-form").submit(function(event) {
        //       // Stop form from submitting normally
        event.preventDefault();

        var status = $(this).attr("name");
        var dept = $("#modal-remind-text-dept").text();
        var number = $("#modal-remind-text-number").text();
        var title = $("#modal-remind-text-title").text();
        var code = $("#modal-remind-text-code").text();

        var url = $("#modal-form").attr( "action" );
        // Send the data using post

        console.log("Posting to " + url);
        var posting = $.post( url,
            {
                "status": status,
                "dept": dept,
                "number": number,
                "title": title,
                "code": code,
                "_csrf": $('#modal-form').find("input[name='_csrf']").val()
            }).done(function( data ) {
                console.log(data);
            }).fail(function (data) {
                console.log("fail" + data);
            });

            $('#schedule-modal').modal('toggle');
        });

    });
