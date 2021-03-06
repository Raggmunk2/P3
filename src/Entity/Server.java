package Entity;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Server implements Runnable{
    private Thread server = new Thread(this);
    private MessageManager messageManager;
    private int port;
    private DateTimeFormatter dtf;
    private Client client;
    private ArrayList<User> users = new ArrayList<>();

    public Server(MessageManager messageManager, int port){
        this.messageManager=messageManager;
        this.port=port;
        server.start();
    }

    @Override
    public void run() {
        while (true){
            Socket socket = null;
            try(ServerSocket serverSocket = new ServerSocket(port)){
                System.out.println("Servers startar");
                socket = serverSocket.accept();
                System.out.println("har conn");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message newUser = (Message)ois.readObject();
                users.add(newUser.getSender());
                System.out.println("user added");
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                new Connection(ois,oos);
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
        Message updateOnline = new Message(new User("SERVER",null),onlineUsers,"updateOnline",null);
        messageManager.put(updateOnline);
    }

    private class Connection{
        private Buffer<Message> messageBuffer;
        private Sender sender;
        private Receiver receiver;
        private Socket socket;

        public Connection(ObjectInputStream ois, ObjectOutputStream oos){
            this.socket=socket;
            messageBuffer = new Buffer<>();
            sender = new Sender(oos,messageBuffer);
            receiver = new Receiver(ois,messageBuffer);
            sender.start();
            receiver.start();
        }//konstruktor
    }//conn

    private class Sender extends Thread implements PropertyChangeListener {
        private ObjectOutputStream oos;
        private Buffer<Message> messageBuffer;

        public Sender(ObjectOutputStream oos,Buffer<Message> messageBuffer){
            this.oos = oos;
            this.messageBuffer=messageBuffer;
            messageManager.addPropertyChangeListener(this);

        }//konstruktor

        public void run(){
                try{
                    while(true) {
                        System.out.println("Är du ens här!");
                        Message message = messageBuffer.get();
                        oos.writeObject(message);
                        System.out.println("hej");
                        oos.flush();
                    }
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
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
                    System.out.println("waiting for messages");
                    Message message = (Message) ois.readObject();
                    System.out.println("message received");
                    if (message != null) {
                        messageManager.put(message);
                    } else {
                        System.out.println("NULL");
                    }
                   /* DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
                    LocalDateTime now = LocalDateTime.now();
                    String textMessage = ois.readUTF();
                    textMessage += ois.readUTF();
                    textMessage += dtf.format(now);
                    Message message = new Message(textMessage, null);
                    if(message != null){
                        messageManager.put(message);
                    }*/
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }//while
        }//run
    }//receiver
}//server
