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
    connect(user);
});