package Entity;

import Controller.Controller;


import java.beans.PropertyChangeSupport;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;



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
        Message message = new Message(user1,null,messageText,user1.getImage());
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
                Message userMessage = new Message(user1,null,null,user1.getImage());
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
                    //e.printStackTrace();
                    break;
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
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
                        LocalDateTime timeSent = LocalDateTime.now();
                        message = (Message) ois.readObject();
                        if (message.getSender().getUsername().equals(user1.getUsername())) {
                            message.setUsername("You");
                        }
                        if (message.getSender().getUsername().equals("SERVER")) {
                            handleServerMessage(message);
                        }
                        else {
                            messageRegistrator.add(message);
                            for (Callback callback : callbacks) {
                                Message[] messages = new Message[messageRegistrator.size()];
                                for (int i = 0; i < messages.length; i++) {
                                    messages[i] = messageRegistrator.get(i);
                                }
                                callback.updateListView(messages);
                            }
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    //e.printStackTrace();
                    break;
                }
            } //while
            }//run

        private void handleServerMessage(Message message) {
            if(message.getMessageText().equals("updateOnline")){
                for(Callback c : callbacks){
                    c.updateListView(message.getRecipients());
                }
            }
        }
    }//receiver
}//client
