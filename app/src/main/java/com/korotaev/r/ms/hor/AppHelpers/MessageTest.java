package com.korotaev.r.ms.hor.AppHelpers;

public class MessageTest {
    private String text; // message body
    private MemberData data; // data of the user that sent this message
    private boolean belongsToCurrentUser; // is this message sent by us?

    public MessageTest(String text, MemberData data, boolean belongsToCurrentUser) {
        this.text = text;
        this.data = data;
        this.belongsToCurrentUser = belongsToCurrentUser;
    }

    public String getText() {
        return text;
    }

    public MemberData getData() {
        return data;
    }

    public boolean isBelongsToCurrentUser() {
        return belongsToCurrentUser;
    }
}
