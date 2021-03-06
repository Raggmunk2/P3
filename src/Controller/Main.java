package Controller;


import Entity.*;
import Boundry.LogInWindow;

public class Main {
    public static void main(String[] args) {
        Buffer<Message> messageBuffer = new Buffer<Message>();
        MessageManager messageManager = new MessageManager(messageBuffer);
        new Server(messageManager,2341);
        messageManager.start();
        Controller controller = new Controller(messageBuffer,messageManager);
        new LogInWindow(controller);

    }
}
