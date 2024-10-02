const socket = new SockJS('/chat-websocket'); 
const stompClient = Stomp.over(socket);
stompClient.connect({}, function(frame) {
    console.log('Connected: ' + frame);

    stompClient.subscribe('/topic/messages', function(message) {
        showMessage(JSON.parse(message.body));
    });
});


document.getElementById('send-button').addEventListener('click', function() {
    const messageInput = document.getElementById('message-input');
    const message = {
        sender: 'User',
        content: messageInput.value
    };

    stompClient.send("/app/chat", {}, JSON.stringify(message));
    messageInput.value = ''; 
});

function showMessage(message) {
    const messageContainer = document.getElementById('message-container');
    const messageElement = document.createElement('div');
    messageElement.textContent = `${message.sender}: ${message.content}`;
    messageContainer.appendChild(messageElement);
    messageContainer.scrollTop = messageContainer.scrollHeight;
}
