$(document).ready(function () {
    // loginMessage = $('.text-bad-login').text();
    // if (loginMessage.indexOf("Your account hasn't been validated") != -1)
    // {
    //     $('.text-bad-login').html(
    //         loginMessage
    //     )
    // }
    //
    function clearMsg(){
        $('.text-bad-login').html('');
        $('.resend-bad-login').html('');
    }
    $('#form-login-email').focus(clearMsg);
    $('#form-login-password').focus(clearMsg);
    $('#form-login-confirmpassword').focus(clearMsg);

      $('#form-register-submit').click(function(event){
          if ($('#form-login-password').val() != $('#form-login-confirmpassword').val()){
              event.preventDefault();
              $('.text-bad-login').html('Passwords do not match!');
          }
      });
});
