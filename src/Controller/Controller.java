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
    private Middle middle;
    private MessageManager messageManager;
    private LogInWindow logInWindow;
    private ArrayList<Message> messageArray;
    private User user;
    private Client client;
    private TraficLog traficLog;

    public Controller(Buffer<Message> messageBuffer,MessageManager messageManager){
        this.messageBuffer=messageBuffer;
        this.messageManager=messageManager;
        this.logInWindow = new LogInWindow();
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
        Message message = messageBuffer.get();
        return message;
    }

    public String getUsernameText(){
        String username =  logInWindow.getUsername();
        return username;
    }
    public String getIpText(){
        String ip = logInWindow.getIpText();
        return ip;
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
        user = new User(username,imageIcon);
        user.addUser(username,imageIcon);

    }

    /*public void compare(String username) {

        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("files/Users.txt"),"UTF-8"))){
            messageArray = new ArrayList<>();
            String exictingUsername;
            String image;
            String str = br.readLine();
            while (str != null){
                exictingUsername = br.readLine();
                image = br.readLine();
                if(username.contains(exictingUsername)){
                    logInWindow.sendError("You chose an already exicting username!");
                }
                else{
                    logInWindow.sucess("You are sucessfully added to the chatbox");
                }
                br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

    public void sendToServer(String messageText) {
        User[] receivers = chatBox.getSelectedReceivers();
        receivers[receivers.length-1] = user;
        Message message = new Message(user,receivers,messageText,user.getImage());
        client.sendToServer(message);
    }

    public Icon getProfilePic() {
        Icon profilePic = user.getImage();
        return profilePic;
    }

    public String getMessageText() {
        String messageText = south.getText();
        return messageText;
    }

    public void connect(String username, ImageIcon imageIcon, String ip, int port) {
        chatBox = new ChatBox(this);
        User user1 = new User(username,imageIcon);
        User[] contacts = readContactsFromFile();
        chatBox.addContactToList(contacts);
        client.setUser(user1);
        client.connect(ip,port);
    }

    private User[] readContactsFromFile() {
        ArrayList<User> contactList = new ArrayList<>();
        try{
            String filename = System.getProperty("user.dir") + "/" + user.getUsername() + "_Contacts.dat";
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            contactList = (ArrayList) ois.readObject();
            User[] contacts = new User[contactList.size()];
            for(int i =0;i< contacts.length;i++){
                contacts[i] = contactList.get(i);
            }
            ois.close();
            fis.close();
            return contacts;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateListView(Message[] messages) {
        chatBox.updateListView(messages);
    }

    @Override
    public void updateListView(User[] users) {
        chatBox.showUserOnline(users);
    }

    public void addToContact() {
        User user = (User) chatBox.getSelectedUser();
        User[] contacts = chatBox.getContacts();
        System.out.println(contacts.length);
        User[] newContacts = new User[contacts.length + 1];

        for(int i=0;i<contacts.length;i++){
            newContacts[i] = contacts[i];
        }
        newContacts[newContacts.length-1] = user;
        chatBox.addContactToList(newContacts);
    }


    public void logOut() {
        client.close();
        saveContacts();
        chatBox.close();
        //spara all trafik
        //system exit
        traficLog = new TraficLog();
        String[] infoString;//vart hämtar jag infon?!
        //traficLog.showTraficLog(infoString);
    }

    private void saveContacts() {
        ArrayList<User> contactList = new ArrayList<>();
        User[] contacts = chatBox.getContacts();
        for(User u : contacts){
            contactList.add(u);
        }
        try{
            String filename = System.getProperty("user.dir") + "/" + user.getUsername() + "_Contacts.dat";
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos =  new ObjectOutputStream(fos);
            oos.writeObject(contactList);
            oos.flush();
            oos.close();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
