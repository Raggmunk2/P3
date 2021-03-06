package Controller;

import Boundry.*;
import Entity.*;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Controller implements Callback{
    private ChatBox chatBox;
    private Server server;
    private Buffer<Message> messageBuffer;
    private West west;
    private South south;
    private East east;
    private MessageManager messageManager;
    private LogInWindow logInWindow;
    private ArrayList<Message> messageArray;
    private User user;
    private Client client;

    public Controller(Buffer<Message> messageBuffer,MessageManager messageManager){
        this.logInWindow=logInWindow;
        this.messageBuffer=messageBuffer;
        this.messageManager=messageManager;
        this.client=new Client();
        client.addMessageListner(this);
    }
    public Controller(West west,East east, South south){
        this.west=west;
        this.east= east;
        this.south=south;
    }

    public Controller() {
        //denna används
    }

    public Message getMessage() throws InterruptedException {
        Message message = (Message) messageBuffer.get();
        return message;
    }

    public String getUsernameText(){
        String username =  logInWindow.getUsername();
        return username;
    }
    public String getPasswordText(){
        String password = logInWindow.getPassword();
        return password;
    }

    public boolean checkFileType(File file){
        boolean fileCheck = false;

        if (file != null) {
            if (file.getName().endsWith(".jpg")||
                    file.getName().endsWith(".tif") ||
                    file.getName().endsWith(".gif") ||
                    file.getName().endsWith(".tiff")||
                    file.getName().endsWith(".png")||
                    file.getName().endsWith(".JPG")||
                    file.getName().endsWith(".TIF") ||
                    file.getName().endsWith(".GIF") ||
                    file.getName().endsWith(".TIFF")||
                    file.getName().endsWith(".PNG")){
                fileCheck = true;
            } else {
                fileCheck = false;
            }
        }
        return fileCheck;
    }

    public void addNewUser(String username, ImageIcon imageIcon) {
        ArrayList<User> users = new ArrayList<>();
        users.add( new User(username,imageIcon));
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("files/Users.txt"),"UTF-8"));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("files/Users.txt"),"UTF-8"));
            String str = br.readLine();
            while(str != null){
                bw.write(username);
                bw.write(imageIcon.toString());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void compare(String username) {

        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("files/Users.txt"),"UTF-8"))){
            messageArray = new ArrayList<>();
            String exictingUsername;
            String image;
            while (br.ready()){
                exictingUsername = br.readLine();
                image = br.readLine();
                if(username.contains(exictingUsername)){
                    logInWindow.sendError("You chose an already exicting username!");
                }
                else{
                    logInWindow.sucess("You are sucessfully added to the chatbox");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendToServer(String messageText) {
        /*String username = user.getUsername();
        Icon profilePic = user.getImage();
        User sender = user.getUser();
        User[] recipient = user.getUserList();
        Message message = new Message(sender,null,messageText, profilePic);*/
        client.sendToServer(messageText);
    }

    public Icon getProfilePic() {
        Icon profilePic = user.getImage();
        return profilePic;
    }

    public String getMessageText() {
        String messageText = south.getText();
        return messageText;
    }

    public void connect(String username, String ip, int port) {
        User user1 = new User(username);
        client.setUser(user1);
        client.connect(ip,port);
        chatBox = new ChatBox(this);
    }

    @Override
    public void updateListView(String[] infoStrings) {
        chatBox.updateListView(infoStrings);
    }
}
