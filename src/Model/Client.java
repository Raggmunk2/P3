package Model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Client {
    private PropertyChangeSupport changeSupport;


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
                    message = (Message) ois.readObject();
                    if(message != null){
                        changeSupport.firePropertyChange("New message", null, message);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
