package Entity;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Client {
    private PropertyChangeSupport changeSupport;
    private DateTimeFormatter dtf;


    public Client(String ip, int port){
        System.out.println("Client startad");
        changeSupport = new PropertyChangeSupport(this);
        new Connection(ip,port);
    }

    public void addClientListener(PropertyChangeListener listener){
        changeSupport.addPropertyChangeListener(listener);
    }

    private class Connection extends Thread{
        private String ip;
        private int port;
        public Connection(String ip, int port){
            this.ip=ip;
            this.port=port;
            start();
        }
        public void run(){
            ObjectInputStream ois;
            try{
                Socket socket = new Socket(ip,port);
                ois = new ObjectInputStream(socket.getInputStream());
                Message message;
                while(!Thread.interrupted()){
                    //lagt till tid då meddelandet skickades
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
                    LocalDateTime now = LocalDateTime.now();
                    String textMessage = ois.readUTF();
                    textMessage += ois.readUTF();
                    textMessage += dtf.format(now);
                    message = new Message(textMessage, null);
                    if(message != null){
                        changeSupport.firePropertyChange("New message", null, message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
