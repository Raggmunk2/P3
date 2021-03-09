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

    public void compare(String username) {

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

    }

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
        client.setUser(user1);
        client.connect(ip,port);
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
        String username = user.getUsername();
        writeContactOnFile(username);
    }
    private void writeContactOnFile(String username) {
        ArrayList<User> contactlist = user.getContactList();
        File file = new File ("src/files/"+username+"_Contacts.txt");
        boolean fileExists = checkFileExist(file);
        if(!fileExists) {
            JOptionPane.showInternalMessageDialog(null,"You need to chose a path to save you contacts");
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(file), "UTF-8")) ) {
                    for (User u : contactlist) {
                        bw.write(String.valueOf(u));
                        System.out.println(String.valueOf(u));
                        bw.newLine();
                        bw.flush();
                    }
                } catch(IOException e) {
                    System.err.println(e);
                }
            }
        }
    }
    /*den gamla
    private void writeContactOnFile(User[] contacts) {
        String username = user.getUsername();
        boolean fileExists = checkFileExist(username);
        if(fileExists) {
            try {
                OutputStreamWriter oos = new OutputStreamWriter(new FileOutputStream("src/files/"+username+"_Contacts.txt"), "UTF-8");
                for (User u : contacts) {
                    String readUsername = u.getUsername();
                    ImageIcon image = u.getImage();
                    oos.write(readUsername + ","+ image);
                    oos.flush();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

    private boolean checkFileExist(File file) {
        if(file.exists()) {
            return true;
        }
        else{
            return false;
        }
    }
    public void readContactsOnFile(String name,String image){
        StringBuffer res = new StringBuffer();
        String str;
        int line = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream("src/files/"+name+"_Contacts.txt"),"UTF-8"))) {
            str = br.readLine();
            System.out.println(str);
            while ( str!=null ) {
                //res.append(++line+". "+str+"\n");
                str = br.readLine();
                System.out.println(str);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println(e);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println(e);
            //Om detta händer så ska systemet gå vidare till att skapa en fil med det namnet
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
    /*den gamla
    public void readContactsOnFile(){
        try{
            String username = user.getUsername();
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("files/"+username+"_Contacts.txt"));
            String str = ois.readUTF();
            while(str != null){
                str.split(",");
                String readUsername = ois.readUTF();
                String image = ois.readUTF();
                ImageIcon imageIcon = new ImageIcon(image);
                System.out.println(readUsername + "\n" + imageIcon);

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void logOut() {
        //spara all trafik
        //system exit
        traficLog = new TraficLog();
        String[] infoString;//vart hämtar jag infon?!
        //traficLog.showTraficLog(infoString);
    }
}
