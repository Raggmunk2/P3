package Entity;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Serializable { // Även användas i strömmar
    private String username;
    private ImageIcon image;
    private HashMap<String,String> users = new HashMap<String,String>();
    private ArrayList<User> contacts = new ArrayList<>();

    public User(String username, ImageIcon image){
        this.username=username;
        this.image=image;
        readUserFromFile();
    }

    public User(String username) {
        this.username=username;
    }

    public int hashCode() {
        return username.hashCode();
    }
    public boolean equals(Object obj) {
        if(obj!=null && obj instanceof User)
            return username.equals(((User)obj).getUsername());
        return false;
    }
    public User getUser(){
        User user = new User(getUsername(),getImage());
        return user;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }


    public void readUserFromFile(){
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("files/Users.txt"),"UTF-8"))){
            while (br.ready()){
                username = br.readLine();
                String image = br.readLine();
                //ImageIcon imageIcon = new ImageIcon(String.valueOf(image));
                System.out.println(username + "\n" + image);
                users.put(username, image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User[] getUserList() {
        return null;
    }
}