$(document).ready(function () {
    loginMessage = $('.text-bad-login').text();
    if (loginMessage.indexOf("Your account hasn't been validated") != -1)
    {
        $('.text-bad-login').html(
            loginMessage
        )
    }
});
