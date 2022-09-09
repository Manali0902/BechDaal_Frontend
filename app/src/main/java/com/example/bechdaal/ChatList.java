package com.example.bechdaal;

public class ChatList {
    String chatName,chatNumber;

    public ChatList(String chatName, String chatNumber) {
        this.chatName = chatName;
        this.chatNumber = chatNumber;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getChatNumber() {
        return chatNumber;
    }

    public void setChatNumber(String chatNumber) {
        this.chatNumber = chatNumber;
    }
}
