package Entity;

import javax.swing.*;
import java.io.Serializable;

public class Message implements Serializable {
    private String username;
    private Icon profilePic;
    private Message message;
    private String line;

    public Message(String username, Icon profilePic){
        this.username =username;
        this.profilePic =profilePic;
    }

    public Message(String line) {
        this.line=line;
    }

    public Message(Message message) {
        this.message=message;
    }

    public void Message(String username, Icon profilePic){
        this.username =username;
        this.profilePic =profilePic;

    }

    public String getUsername() {
        return username;
    }

    public Icon getProfilePic() {
        return profilePic;
    }
}

