package Entity;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Server implements Runnable{
    private Thread server = new Thread(this);
    private MessageProducerInput mpInput;
    private MessageManager messageManager;
    private int port;
    private DateTimeFormatter dtf;

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
                new Connection(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private class Connection{
        private Buffer<Message> messageBuffer;
        private Sender sender;
        private Receiver receiver;
        private Socket socket;

        public Connection(Socket socket){
            this.socket=socket;
            messageBuffer = new Buffer<>();
            try{
                sender = new Sender(socket.getOutputStream(),messageBuffer);
                receiver = new Receiver(socket.getInputStream(),messageBuffer);
                sender.start();
                receiver.start();
            }catch (IOException e){}
        }//konstruktor
    }//conn
    private class Sender extends Thread implements PropertyChangeListener {
        private ObjectOutputStream oos;
        private Buffer<Message> messageBuffer;
        public Sender(OutputStream outputStream,Buffer<Message> messageBuffer){
            try {
                oos = new ObjectOutputStream(outputStream);
                this.messageBuffer=messageBuffer;
                messageManager.addPropertyChangeListener(this);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }//konstruktor
        public void run(){
            while(true){
                try{
                    Message message = messageBuffer.get();
                    oos.writeObject(message);
                    oos.flush();
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }//while
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

        public Receiver(InputStream inputStream, Buffer<Message> messageBuffer) {
           try{
               ois = new ObjectInputStream(inputStream);
               this.messageBuffer = messageBuffer;
           } catch (IOException e) {
               e.printStackTrace();
           }
        }

        public void run(){ //lagt in tid för när meddelandet tog emot
            while (true){
                try {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
                    LocalDateTime now = LocalDateTime.now();
                    String textMessage = ois.readUTF();
                    textMessage += ois.readUTF();
                    textMessage += dtf.format(now);
                    Message message = new Message(textMessage, null);
                    if(message != null){
                        messageManager.put(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }//while
        }//run
    }//receiver
    public static void main(String[] args) {
        Buffer<Message> buffer = new Buffer<>();
        MessageManager messageManager = new MessageManager(buffer);
        Server server = new Server(messageManager, 2341);
        messageManager.start();
    }
}//server
