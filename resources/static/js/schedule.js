
$(document).on({
    click: function(){
        $('.modal-btn').removeClass('disabled');

        var course = "";
        var code = "";
        var firstChild = $(this).find('td').first();
        if (firstChild.is('.CourseTitle')){
            return;
        }else{
            var parent = $(this).prevAll().find('td.CourseTitle');
            course = parent[parent.length - 1].innerText;
            code = $(this).find('td').first().text();
            status = $(this).find('td').last().text();
            $(".modal-btn[value = '" + status.toUpperCase() + "']").addClass('disabled');
        }
        course = course.trim().split(/[\s]{2,}/);
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

msg_alert.swapShow = function(message, mode, code){
    obj = $('tr:has(td:contains(' + code +'))');
    originalHtml = obj.html();
    obj.html(
        '<td></td><td colspan = 14><div class="alert alert-' +mode+ ' alert-dismissible" role="alert"><span>'+message+'</span></div></td><td></td>'
    )
    setTimeout(function(){
        obj.html(originalHtml);
    }, 1000);
}

$(document).ready(function(){
    $(".modal-btn").click(function(event) {
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
                console.log(data)
                var json = JSON.parse(data);
                console.log(json)
                if (json.status == FAILED)
                    msg_alert.show(json.msg, "warning");
                else
                    msg_alert.swapShow(json.msg, "success", code);
            }).fail(function (data) {
                if(data.status == STATUS_FORBIDDEN){
                    //Redirect to login page
                    window.location.href = "/login";
                }else{
                    var json = JSON.parse(data);
                    msg_alert.show("Unexpected error: " + json.msg, "warning");
                }
            });
            // setTimeout(function(){
            //   $('.alert-dismissible > .close').click();
            // }, 2000);
            $('#schedule-modal').modal('toggle');
        });
        $("select[name='Breadth']").get(0).selectedIndex = 0;
    });
