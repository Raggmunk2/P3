package Controller;

import Boundry.ChatBox;
import Entity.*;
import Boundry.LogInWindow;

import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        Buffer<Message> messageBuffer = new Buffer<Message>();
        MessageManager messageManager = new MessageManager(messageBuffer);
        //Server server = new Server(messageManager,2341);
        //ChatBox chatBox = new ChatBox();
        Controller controller = new Controller();
        new LogInWindow();

        //Buffer<MessageProducer> producerBuffer	= new Buffer<MessageProducer>();
        //MessageProducerInput ipManager = new MessageProducerInput(producerBuffer);

        //ipManager.addMessageProducer(new ReadFromFile("files/Hej.txt"));
        Socket socket = new Socket();


        //new Client("127.0.0.1",2341);
    }
}
