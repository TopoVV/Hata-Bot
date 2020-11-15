package com.topov.estatesearcher.telegram.reply.component;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Hint {
    private final StringBuilder hintMessage = new StringBuilder();

    public Hint(java.lang.String hintMessage) {
        this.hintMessage.append(hintMessage);
    }

    public void appendHintMessage(java.lang.String hintMessage) {
        this.hintMessage.append(hintMessage);
    }

    public java.lang.String getHintMessage() {
        return this.hintMessage.toString();
    }
}
