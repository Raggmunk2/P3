package Entity;

import Controller.Controller;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;


public class Client {
    private PropertyChangeSupport changeSupport;
    private String ip;
    private int port;
    private ArrayList<Message> messageRegistrator = new ArrayList<>();
    private Buffer<Message> messageBuffer = new Buffer<>();
    private Controller controller;
    private ArrayList<Callback> callbacks = new ArrayList<>();
    private User user1;

    public Client(){
        System.out.println("Client startad");
        changeSupport = new PropertyChangeSupport(this);
    }


    public void sendToServer(String messageText) {
        Message message = new Message(user1,null,messageText,null);
        messageBuffer.put(message);
    }

    public void setUser(User user1) {
        this.user1=user1;
    }

    public void connect(String ip, int port) {
        new Connection(ip,port);
    }

    public void addMessageListner(Callback callback) {
        callbacks.add(callback);
    }

    private class Connection{
        private Sender sender;
        private Receiver receiver;
        public Connection(String ip, int port){
            try {
                Socket socket = new Socket(ip, port);
                System.out.println("Connected");
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                Message userMessage = new Message(user1,null,null,null);
                System.out.println("Sending user");
                oos.writeObject(userMessage);
                oos.flush();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                sender = new Sender(oos);
                receiver = new Receiver(ois);
                sender.start();
                receiver.start();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    private class Sender extends Thread {
        private ObjectOutputStream oos;

        public Sender(ObjectOutputStream outputStream) {
            oos = outputStream;
        }

        public void run() {
            while (true) {
                try {
                    Message message = messageBuffer.get();
                    oos.writeObject(message);
                    oos.flush();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private class Receiver extends Thread {
        private ObjectInputStream ois;

        public Receiver(ObjectInputStream inputStream) {
            ois = inputStream;
        }

        public void run() {
            while (true) {
                try {

                    Message message;
                    while (!Thread.interrupted()) {
                        //lagt till tid då meddelandet skickades
                        /*DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
                        LocalDateTime now = LocalDateTime.now();
                        String textMessage = ois.readUTF();
                        textMessage += dtf.format(now);
                        textMessage += ois.readUTF();
                        message = new Message(textMessage, null);*/
                        //String username = ois.readUTF();
                        message = (Message) ois.readObject();
                        if (message != null) {
                            //changeSupport.firePropertyChange("New message", null, message);
                        }
                        //username = controller.getUsernameText();
                        if (message.getSender().getUsername().equals(user1.getUsername())) {
                            message.setUsername("You");
                        }
                        messageRegistrator.add(message);
                        for (Callback callback : callbacks) {
                            String[] infoStrings = new String[messageRegistrator.size()];
                            for (int i = 0; i < infoStrings.length; i++) {
                                infoStrings[i] = messageRegistrator.get(i).toString();
                            }
                            callback.updateListView(infoStrings);
                        }
                    }


                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } //while
            }//run
        }//receiver
}//client
