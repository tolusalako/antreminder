
   $(document).ready(function () {
        var url = window.location;
        // $(".nav-item").find(".active").removeClass("active");
        $('li.active').removeClass("active");
        $('li.nav-item a[href="'+ url +'"]').parent().addClass('active');
        $('li.nav-item a').filter(function() {
             return this.href == url;
        }).parent().addClass('active');
    });
