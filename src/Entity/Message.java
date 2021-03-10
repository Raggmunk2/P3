package Entity;

import javax.swing.*;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private User sender;
    private User[] recipients;
    private ImageIcon profilePic;
    private String messageText;
    private String timeSent;
    private String timeReceived;

    public Message(User sender, User[] recipients, String messageText, String timeSent,String timeReceived, ImageIcon profilePic){
        this.sender=sender;
        this.recipients=recipients;
        this.messageText=messageText;
        this.timeSent = timeSent;
        this.timeReceived=timeReceived;
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

    public ImageIcon getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(ImageIcon profilePic) {
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

    public String getTimeSent() {

        return timeSent;
    }

    public void setTimeSent(String timeSent) {
        this.timeSent = timeSent;
    }

    public String getTimeReceived() {
        return timeReceived;
    }

    public void setTimeReceived(String timeReceived) {

        this.timeReceived = timeReceived;
    }

    public String toString(){
        return String.format("Sent: %s \nReceived: %s\n %s: %s ",timeSent,timeReceived,sender.getUsername(), messageText);
    }
}

