function countReminders(status) {
	cssSelector = "";
	if (status == "open")
		cssSelector = 'i.text-success'
	else if (status == "waitlist")
		cssSelector = 'i.text-warning'
	else if (status == "newonly")
		cssSelector = 'i.text-info'
	else if (status == "full")
		cssSelector = 'i.text-danger'
	else if (status == "all")
		cssSelector = 'i.fa-reminder-list'
	return $(cssSelector).length;
}

$(document).ready(function () {
	statusList = ['all', 'open', 'waitlist', 'newonly', 'full'];
	for (var i = 0; i < statusList.length; ++i){
		status = statusList[i];
		$('.label-'+status).text(countReminders(status));
	}
	var table = $('#reminder-table').dataTable();
 // $("div.toolbar").html('<b>Custom tool bar! Text/images etc.</b>');
 // btnParent = $('#reminder-table_wrapper > .row')[0];
 // //TODO: Move buttons
 // table.columns().every( function () {
 //  var that = this;
 //  console.log(1);
 //  $( 'input', this.footer() ).on( 'keyup change', function () {
 // 	 console.log(2);
 // 	 if ( that.search() !== this.value ) {
 // 		 console.log(3);
 // 		 that
 // 			 .search( this.value )
 // 			 .draw();
 // 	 }
 //  } );
 // });
});
