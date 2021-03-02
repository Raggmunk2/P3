package Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private MessageProducerInput mpInput;
    private int port;

    public Server(MessageProducerInput mpInput, int port){
        this.mpInput=mpInput;
        this.port=port;
        startServer();
    }
    public void startServer() {
        new Connection(port).start();
    }
    private class Connection extends Thread{
        private int port;

        public Connection(int port){
            this.port=port;
        }
        public void run(){
            Socket socket = null;
            try(ServerSocket serverSocket = new ServerSocket(port)){
                while(true){
                    try{
                        System.out.println("Servers startar");
                        socket = serverSocket.accept();
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        MessageProducer mp = (MessageProducer) ois.readObject();
                        mpInput.addMessageProducer(mp); //här anropas den metod som MessageProducerInput lägger till och tar emot objekten
                    } catch (IOException e) {
                        e.printStackTrace();
                        if(socket!=null){
                            try {
                                socket.close();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }//while
            }catch (IOException e){  }
        }//run
    }//conn
}
