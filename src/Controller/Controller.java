package Controller;

import Boundry.*;
import Entity.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Controller implements Callback{
    private MainChatFrame mainChatFrame;
    private Server server;
    private Buffer<Message> messageBuffer;
    private TextBoxWest textBoxWest;
    private ButtonPanelSouth buttonPanelSouth;
    private OnlineUserEast onlineUserEast;
    private ContactListCenter contactListCenter;
    private MessageManager messageManager;
    private LogInWindow logInWindow;
    private ArrayList<Message> messageArray;
    private User user;
    private Client client;
    private TraficLog traficLog;

    //Konstruktor som instansierar meddagebuffer och messagemanager. Startar Client-tråden
    //och sätter en PropertyChangeListener på klienten
    public Controller(Buffer<Message> messageBuffer,MessageManager messageManager){
        this.messageBuffer=messageBuffer;
        this.messageManager=messageManager;
        this.logInWindow = new LogInWindow();
        this.client=new Client();
        client.addMessageListner(this);
    }
    public Controller(TextBoxWest textBoxWest,OnlineUserEast onlineUserEast, ButtonPanelSouth buttonPanelSouth){
       this.textBoxWest=textBoxWest;
       this.onlineUserEast=onlineUserEast;
       this.buttonPanelSouth=buttonPanelSouth;
    }

    //Denna konsturktor används i TraficLog-klassen för att skapa en instans av klassen.
    public Controller() {

    }

    //Metod som returnerar ett Message
    public Message getMessage() throws InterruptedException {
        Message message = messageBuffer.get();
        return message;
    }

    //Metod som hämntar användarnamnet
    public String getUsernameText(){
        String username =  logInWindow.getUsername();
        return username;
    }
    //Metod som hämtar ip
    public String getIpText(){
        String ip = logInWindow.getIpText();
        return ip;
    }

    //Metod som kollar om den valda bild-filen är av rätt typ
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

    //Metod som skapar och lägger till en ny användare.
    public void addNewUser(String username, ImageIcon imageIcon) {
        user = new User(username,imageIcon);
        user.addUser(username,imageIcon);

    }
    //Metod som jämför om användarnamnet redan finns
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

    //Metod som hämtar vilka mottagarna för meddelandet är, skapar ett nytt meddelande
    //och skickar vidare det till client-klassen
    public void sendToServer(String messageText) {
        User[] receivers = mainChatFrame.getSelectedReceivers();
        receivers[receivers.length-1] = user;
        Message message = new Message(user,receivers,messageText,null,null,user.getImage());
        client.sendToServer(message);
    }

    //Metod som returnerar probilbilden
    public Icon getProfilePic() {
        ImageIcon profilePic = user.getImage();
        return profilePic;
    }

    //Metod som returnerar text-meddelandet från ButtonPanelSouth
    public String getMessageText() {
        String messageText = buttonPanelSouth.getText();
        return messageText;
    }

    //Metod som läser in sina redan tillagda kontakter och sedan ansluter till servern
    public void connect(String username, ImageIcon imageIcon, String ip, int port) {
        mainChatFrame = new MainChatFrame(this);
        user = new User(username,imageIcon);
        User[] contacts = readContactsFromFile();
        if(contacts != null) {
            mainChatFrame.addContactToList(contacts);
        }
        client.setUser(user);
        client.connect(ip,port); //Här skapas en ny Connection(Clients inre klass) till servern
    }

    //Metod som läser in användarens kontakter från filen
    private User[] readContactsFromFile() {
        ArrayList<User> contactList;
        try{
            String filename = System.getProperty("user.dir") + "/P3/src/files/" + user.getUsername() + "_Contacts.dat";
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

    //Metod som skickar vidare meddelandena till GUI
    @Override
    public void updateListView(Message[] messages) {
        mainChatFrame.updateListView(messages);
    }

    //Metod som skickar vidare de Users som är online till GUI
    @Override
    public void updateListView(User[] users) {
        mainChatFrame.showUserOnline(users);
    }

    //Metod som lägger till en User till användarens kontaktlista m.h.a. en ny array
    public void addToContact() {
        User user = (User) mainChatFrame.getSelectedUser();
        User[] contacts = mainChatFrame.getContacts();
        User[] newContacts = new User[contacts.length + 1];

        for(int i=0;i<contacts.length;i++){
            newContacts[i] = contacts[i];
        }
        newContacts[newContacts.length-1] = user;
        mainChatFrame.addContactToList(newContacts);
    }

    //Metod som avslutar GUI:t och skapar en ny frame som visar teafik loggen
    public void logOut() {
        client.close();
        saveContacts();
        mainChatFrame.close();
        traficLog = new TraficLog();
        writeTraficLog();
    }
    public void writeTraficLog(){
        String timeFrom = traficLog.getTimeFrom();
        String timeTo = traficLog.getTimeTo();

        ArrayList<Message> allMessages = new ArrayList<>();
        ArrayList<Message> traficInfo = mainChatFrame.getTraficInfo();
        for(Message m : traficInfo){
            int sent = Integer.parseInt(String.valueOf(m.getTimeSent()));
            int received = Integer.parseInt(String.valueOf(m.getTimeReceived()));
            if(sent>=Integer.parseInt(timeFrom) && received<=Integer.parseInt(timeTo)){
                allMessages.add(m);
            }
        }
        try{
            String filename = System.getProperty("user.dir") + "/files/traficLog/trafikLog.dat";
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos =  new ObjectOutputStream(fos);
            oos.writeObject(allMessages);
            oos.flush();
            oos.close();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Det gick inte");
            e.printStackTrace();
        }
        traficLog.showTraficLog(allMessages);
    }

    //Metod som sparar alla kontakter man lagt till och skriver dem på fil
    private void saveContacts() {
        ArrayList<User> contactList = new ArrayList<>();
        User[] contacts = mainChatFrame.getContacts();
        for(User u : contacts){
            contactList.add(u);
        }
        try{
            String filename = System.getProperty("user.dir") + "/P3/src/files/" + user.getUsername() + "_Contacts.dat";
            FileOutputStream fos = new FileOutputStream(filename); //här är det nått som händer
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

    //Metod som returnerar alla meddelandena som en ArrayList med Messages
    public ArrayList<Message> getTraficLog() {
        ArrayList<Message> allMessages;
        try{
            String filename = System.getProperty("user.dir") + "/P3/src/files/traficLog.dat";
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            allMessages = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
            return allMessages;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }
}
