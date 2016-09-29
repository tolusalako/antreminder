$(document).on({
    click: function(){
        var course = "";
        var code = "";
        var firstChild = $(this).find('td').first();
        if (firstChild.is('.CourseTitle')){
            // course = firstChild.text();
            return;
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


msg_alert = function () {}
msg_alert.show = function(message, mode){
    $('.alert-message').html(
        '<div class="alert alert-' +mode+ ' alert-dismissible" role="alert">\
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>\
        <span>'+message+'</span></div>'
    )
}

$(document).ready(function(){
    $(".modal-btn").click(function(event) {
            // $("#modal-form").submit(function(event) {
        var FAILED = "FAILED";
        var STATUS_FORBIDDEN = 403;
        var MSG = "MSG";

        //       // Stop form from submitting normally
        event.preventDefault();

        var status = $(this).val();
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
                var json = JSON.parse(data);
                if (json.status == FAILED)
                    msg_alert.show(json.msg, "warning");
                else
                    msg_alert.show(json.msg, "success");
            }).fail(function (data) {
                if(data.status == STATUS_FORBIDDEN){
                    //Redirect to login page
                    window.location.href = "/login";
                }else{
                    var json = JSON.parse(data);
                    msg_alert.show("Unexpected error: " + json.msg, "warning");
                }
            });
            $('#schedule-modal').modal('toggle');
        });

    });
