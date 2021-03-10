package Entity;

import Controller.Controller;
import Entity.Callback;
import Entity.Message;
import Entity.User;


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
    private Buffer<Message> messageBuffer = new Buffer<Message>();
    private Controller controller;
    private ArrayList<Callback> callbacks = new ArrayList<>();
    private User user1;

    //Konstruktor som läggen en PropertyChangeSupport till klassen
    public Client(){
        changeSupport = new PropertyChangeSupport(this);
    }


    //Metod som puttar ett Message till messageBuffern
    public void sendToServer(Message message) {
        messageBuffer.put(message);
    }

    //Metod som "settar" Usern
    public void setUser(User user1) {
        this.user1=user1;
    }

    //Metod som skapar en en instans av den inre klassen med ip och port
    public void connect(String ip, int port) {
        new Connection(ip,port);
    }

    //Metod för att lägga på en callback på parametern
    public void addMessageListner(Callback callback) {
        callbacks.add(callback);
    }

    //Metod för att skapa ett nytt meddelande som Servern-Receiver kollar "Proppertyn" på
    //och puttar sedan det till messageBuffern
    public void close() {
        Message closeMessage = new Message(new User("CLIENT",null),null,"closeConnection",null,null,null);
        messageBuffer.put(closeMessage);
    }

    //Inre klass
    private class Connection{
        private Sender sender;
        private Receiver receiver;

        //Konstruktor som ansluter, skriver objekt, och s
        public Connection(String ip, int port){
            try {
                Socket socket = new Socket(ip, port);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                Message userMessage = new Message(user1,null,null,null,null,user1.getImage());
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
                    if(message.getSender().getUsername().equals("CLIENT")){
                        break;
                    }
                } catch (IOException | InterruptedException e) {
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
                        message = (Message) ois.readObject();
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy MM dd HH mm ");
                        LocalDateTime now = LocalDateTime.now();
                        message.setTimeReceived(dtf.format(now));
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
