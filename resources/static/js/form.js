function retrieveGuests() {
    var url = '/th-spring-integration/spring/guests';
    
    if ($('#searchSurname').val() != '') {
        url = url + '/' + $('#searchSurname').val();
    }
    
    // $("#formContainer").load(url);
    $("#formContainer").load("/");
}