package Entity;

import javax.swing.*;
import java.io.Serializable;

public class User implements Serializable { // Även användas i strömmar
    private String username;
    private ImageIcon image;
    // konstruktor, get-metoder, ...
    public int hashCode() {
        return username.hashCode();
    }
    public boolean equals(Object obj) {
        if(obj!=null && obj instanceof User)
            return username.equals(((User)obj).getUserName());
        return false;
    }

    private String getUserName() {
        return username;
    }
}