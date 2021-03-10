package Entity;


import Entity.Client;
import Entity.Message;
import Entity.User;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Server implements Runnable{
    private Thread server = new Thread(this);
    private MessageManager messageManager;
    private int port;
    private DateTimeFormatter dtf;
    private Client client;
    private ArrayList<User> users = new ArrayList<>();
    private HashMap<User,LinkedList<Message>> unsentMessages;

    public Server(MessageManager messageManager, int port){
        this.messageManager=messageManager;
        unsentMessages = new HashMap<>();
        this.port=port;
        server.start();
    }

    @Override
    public void run() {
        while (true){
            Socket socket = null;
            try(ServerSocket serverSocket = new ServerSocket(port)){
                socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message newUser = (Message)ois.readObject();
                users.add(newUser.getSender());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                new Connection(ois,oos,newUser.getSender());
                sendUsers(users);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }


        }
    }

    private void sendUsers(ArrayList<User> users) {
        User[] onlineUsers = new User[users.size()];
        for(int i =0;i< onlineUsers.length;i++){
            onlineUsers[i] = users.get(i);
        }
        Message updateOnline = new Message(new User("SERVER",null),onlineUsers,"updateOnline",null,null,null);
        messageManager.put(updateOnline);
    }
    private ArrayList<User> getUsers() {
        return (ArrayList<User>) users.clone();
    }

    private class Connection{
        private Buffer<Message> messageBuffer;
        private Sender sender;
        private Receiver receiver;
        private Socket socket;

        public Connection(ObjectInputStream ois, ObjectOutputStream oos, User connectedUser){
            this.socket=socket;
            messageBuffer = new Buffer<Message>();
            sender = new Sender(oos,messageBuffer,connectedUser);
            receiver = new Receiver(ois,messageBuffer);
            sender.start();
            receiver.start();
        }//konstruktor
    }//conn

    private class Sender extends Thread implements PropertyChangeListener {
        private ObjectOutputStream oos;
        private Buffer<Message> messageBuffer;
        private User connectedUser;

        public Sender(ObjectOutputStream oos,Buffer<Message> messageBuffer, User connectedUser){
            this.oos = oos;
            this.messageBuffer=messageBuffer;
            this.connectedUser=connectedUser;
            messageManager.addPropertyChangeListener(this);

        }//konstruktor

        public void run(){
            while(true) {
                try {
                    Message message = messageBuffer.get();
                    for(User u: message.getRecipients()){
                        if(u.getUsername().equals(connectedUser.getUsername())){
                            oos.writeObject(message);
                            oos.flush();
                            break;
                        }
                    }


                } catch (InterruptedException | IOException e) {
                    //e.printStackTrace();
                    break;
                }
            }
        }//run

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if(evt.getPropertyName().equals("New message")){
                Message message = (Message) evt.getNewValue();
                messageBuffer.put(message);
            }
        }
    }//sender

    private class Receiver extends Thread{
        private ObjectInputStream ois;
        private Buffer<Message> messageBuffer;

        public Receiver(ObjectInputStream ois, Buffer<Message> messageBuffer) {
            this.ois = ois;
            this.messageBuffer = messageBuffer;
        }

        public void run(){
            while (true){
                try {
                    Message message = (Message) ois.readObject();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy MM dd HH mm ");
                    LocalDateTime now = LocalDateTime.now();
                    message.setTimeSent(dtf.format(now));
                    if(message.getSender().getUsername().equals("CLIENT")){
                        break;
                    }
                    ArrayList<User> onlineUsers = getUsers();
                    for(User u : message.getRecipients()){
                        boolean on = false;
                        for(User online : onlineUsers) {
                            if (u.equals(online)){
                                on = true;
                            }
                            if(!on){
                                LinkedList<Message> unsent;
                                if(unsentMessages.containsKey(u)){
                                    unsent = unsentMessages.get(u);
                                    if(unsent == null){
                                        unsent = new LinkedList<>();
                                    }
                                }else{
                                    unsent = new LinkedList<>();
                                }
                                unsent.addLast(message);
                                unsentMessages.put(u,unsent);
                                System.out.println("Unsent message added");
                            }
                        }
                    }
                    messageManager.put(message);
                } catch (IOException | ClassNotFoundException e) {
                    //e.printStackTrace();
                    break;
                }
            }//while
        }//run

    }//receiver
}//server
