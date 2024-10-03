/**
 * Establishes a WebSocket connection to the server using SockJS and STOMP.
 * Once connected, it subscribes to the '/topic/messages' endpoint to receive messages.
 */
const socket = new SockJS('/chat-websocket');
const stompClient = Stomp.over(socket);

stompClient.connect({}, function(frame) {
    console.log('Connected: ' + frame);

    // Subscribe to the '/topic/messages' topic to receive messages from the server
    stompClient.subscribe('/topic/messages', function(message) {
        showMessage(JSON.parse(message.body));
    });
});

/**
 * Sends a chat message when the send button is clicked.
 * The message is sent to the server via the '/app/chat' endpoint.
 */
document.getElementById('send-button').addEventListener('click', function() {
    const messageInput = document.getElementById('message-input');
    const message = {
        sender: 'User',  // Default sender for the chat
        content: messageInput.value
    };

    // Send the message to the server via the WebSocket connection
    stompClient.send("/app/chat", {}, JSON.stringify(message));
    messageInput.value = '';  // Clear the input field after sending
});

/**
 * Displays a received message in the chat container.
 * Prevents duplicate messages from being added to the container.
 *
 * @param {Object} message - The message object containing the sender and content.
 */
function showMessage(message) {
    const messageContainer = document.getElementById('message-container');
    const lastMessage = messageContainer.lastElementChild;
    
    // Avoid displaying duplicate messages
    if (lastMessage && lastMessage.textContent === `${message.sender}: ${message.content}`) {
        return;
    }

    // Create a new message element and append it to the message container
    const messageElement = document.createElement('div');
    messageElement.textContent = `${message.sender}: ${message.content}`;
    messageContainer.appendChild(messageElement);

    // Scroll to the bottom of the message container to show the latest message
    messageContainer.scrollTop = messageContainer.scrollHeight;
}
