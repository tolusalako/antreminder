function countReminders(status) {
	cssSelector = "";
	if (status == "open")
		cssSelector = 'td > i.text-success'
	else if (status == "waitlist")
		cssSelector = 'td > i.text-warning'
	else if (status == "newonly")
		cssSelector = 'td > i.text-info'
	else if (status == "full")
		cssSelector = 'td > i.text-danger'
	else if (status == "all")
		cssSelector = 'td > i.fa-reminder-list'
	return $(cssSelector).length;
}

function refresh(){
	statusList = ['all', 'open', 'waitlist', 'newonly', 'full'];
	for (var i = 0; i < statusList.length; ++i){
		status = statusList[i];
		$('.label-'+status).text(countReminders(status));
	}
}

$(document).ready(function () {
	refresh();
	//Datatable init
	var table = $('#reminder-table').DataTable({
		"language": {
			"emptyTable": "<b>You have no reminders. Click here to add some.</b>"
  		},
		"columnDefs": [
            {
                "targets": [ 2 ],
                "visible": false,
                "searchable": true
            }
		]

	});
	$('.dataTables_empty').click(function() {
		window.location.href = "/schedule";
	});
	//Sort reminders by status
	$('.list-item').click(function(){
		var classes = $(this).attr('class');
		var statusClass = classes.split(' ');
		var status = statusClass[statusClass.length - 1].split('-')[2];
		if (status == "all")
			status = "";
		table.column(2).search(status).draw();
	});
	//Remove reminders
	$('#btn-reminder-remove').click(function() {
		$("input:checkbox[name=reminder-checkbox]:checked").each(function(){
		    row = $(this).parent().parent();
			status_elm =  row.find('.reminder-status');
			status = $(status_elm).attr('name');
			code = row.find('.reminder-id').text();
			console.log("Removing",status, code);
			var posting = $.post( "/reminders/remove",
	            {
	                "status": status,
	                "code": code,
	                "_csrf": $('.user-head').find("input[name='_csrf']").val()
	            }).done(function( data ) {
					var json = JSON.parse(data);
	                if (json.status != "FAILED"){
						row.remove();
						refresh();
						location.reload();
					}
	            }).fail(function (data) {
					console.log(data)
	            });
		});
	});
});
