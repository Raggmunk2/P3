package Entity;

import javax.swing.*;
import java.io.Serializable;

public class Message implements Serializable {
    private User sender;
    private User[] recipients;
    private Icon profilePic;
    private String messageText;

    public Message(User sender, User[] recipients, String messageText,Icon profilePic){
        this.sender=sender;
        this.recipients=recipients;
        this.messageText=messageText;
        this.profilePic =profilePic;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User[] getRecipients() {
        return recipients;
    }

    public void setRecipients(User[] recipients) {
        this.recipients = recipients;
    }

    public Icon getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Icon profilePic) {
        this.profilePic = profilePic;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
    public void setUsername(String username){
        sender.setUsername(username);
    }
    public String toString(){
        return String.format("%s: %s",sender.getUsername(), messageText);
    }
}

