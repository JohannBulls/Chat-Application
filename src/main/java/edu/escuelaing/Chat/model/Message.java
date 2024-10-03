package edu.escuelaing.Chat.model;

/**
 * Model class representing a chat message.
 * It contains the sender's name and the content of the message.
 */
public class Message {

    /**
     * The name of the user sending the message.
     */
    private String sender;

    /**
     * The content of the message.
     */
    private String content;

    /**
     * Default constructor for creating an empty message.
     */
    public Message() {}

    /**
     * Constructor for creating a message with a sender and content.
     *
     * @param sender  The name of the message sender.
     * @param content The content of the message.
     */
    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    /**
     * Gets the name of the message sender.
     *
     * @return The sender's name as a String.
     */
    public String getSender() {
        return sender;
    }

    /**
     * Sets the name of the message sender.
     *
     * @param sender The sender's name to set.
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Gets the content of the message.
     *
     * @return The message content as a String.
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the message.
     *
     * @param content The message content to set.
     */
    public void setContent(String content) {
        this.content = content;
    }
}
