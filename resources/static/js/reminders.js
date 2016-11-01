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

$(document).ready(function () {
	statusList = ['all', 'open', 'waitlist', 'newonly', 'full'];
	for (var i = 0; i < statusList.length; ++i){
		status = statusList[i];
		$('.label-'+status).text(countReminders(status));
	}
	$('.list-item').click(function(){
		var classes = $(this).attr('class');
		var statusClass = classes.split(' ');
		var status = statusClass[statusClass.length - 1].split('-')[2];
		//TODO sort
	});
	$('#reminder-table').dataTable({
		"language": {
			"emptyTable": "<b>You have no reminders. Click here to add some.</b>"
  		}
	});
	$('.dataTables_empty').click(function() {
		window.location.href = "/schedule";
	})
});
