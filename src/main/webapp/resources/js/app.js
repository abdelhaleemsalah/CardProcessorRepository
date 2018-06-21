var stompClient = null;

function connect() {
    var socket = new SockJS('../socketEndpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect(user, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/notification/'+user, function (message) {
            console.log(message.body);

            toastr.success(message.body);
        });
    });
}


$(function () {
    toastr.options = {
        "closeButton": true,
        "debug": false,
        "progressBar": true,
        "preventDuplicates": false,
        "positionClass": "toast-bottom-right",
        "onclick": null,
        "showDuration": "400",
        "hideDuration": "1000",
        "timeOut": "3000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }

    $('#html').jstree();
    connect(user);
});