package com.kanyojozsef96.model;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

public class Conversation {
    private IntegerProperty conversationId = new SimpleIntegerProperty(this, "conversationId");
    private IntegerProperty loginUserId = new SimpleIntegerProperty(this, "loginUserId");
    private IntegerProperty otherUserId = new SimpleIntegerProperty(this, "otherUserId");
    private StringProperty message = new SimpleStringProperty(this, "message");
    private StringProperty sender = new SimpleStringProperty(this, "sender");


    public int getConversationId() { return conversationId.get(); }
    public IntegerProperty conversationIdProperty() { return conversationId; }
    public void setConversationId(int conversationId) { this.conversationId.set(conversationId); }

    public int getLoginUserId() { return loginUserId.get(); }
    public IntegerProperty loginUserIdProperty() { return loginUserId; }
    public void setLoginUserId(int loginUserId) { this.loginUserId.set(loginUserId); }

    public int getOtherUserId() { return otherUserId.get(); }
    public IntegerProperty otherUserIdProperty() { return otherUserId; }
    public void setOtherUserId(int otherUserId) { this.otherUserId.set(otherUserId); }

    public String getMessage() { return message.get(); }
    public StringProperty messageProperty() { return message; }
    public void setMessage(String message) { this.message.set(message); }

    public String getSender() { return sender.get(); }

    public StringProperty senderProperty() { return sender; }

    public void setSender(String sender) { this.sender.set(sender); }

    @Override
    public String toString() { return getMessage(); }
}
