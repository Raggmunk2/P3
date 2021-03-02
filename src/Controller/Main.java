package Controller;

import Model.*;
import View.LogInWindow;

import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        Buffer<Message> messageBuffer = new Buffer<Message>();
        Buffer<MessageProducer> producerBuffer	= new Buffer<MessageProducer>();
        MessageProducerInput ipManager = new MessageProducerInput(producerBuffer);
        ipManager.addMessageProducer(new ReadFromFile("files/Hej.txt"));
        Socket socket = new Socket();
        //int port = socket.getLocalPort();
        //System.out.println(port);
        new LogInWindow();
        new Server(ipManager,2341);
        new Client("127.0.0.1",2341);
    }
}
