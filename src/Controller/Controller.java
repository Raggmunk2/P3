package Controller;

import Entity.*;
import Boundry.ChatBox;
import Boundry.East;
import Boundry.South;
import Boundry.West;

import java.io.File;

public class Controller {
    private ChatBox chatBox;
    private Server server;
    private Buffer buffer;
    private West west;
    private South south;
    private East east;
    private MessageManager messageManager;
    public Controller(ChatBox chatBox,Server server){
        this.chatBox=chatBox;
        this.server=server;

    }
    public Controller(){
        startServer();
    }
    public void setMessage(){
        west.showMessage();
    }
    public Message getMessage() throws InterruptedException {
        Message message = (Message) buffer.get();
        return message;
    }

    public void startServer() {
        new Server(messageManager,2341);
    }

    public void getClient() {

    }
    public boolean checkFileType(File file){
        boolean fileCheck = false;

        if (file != null) {
            if (file.getName().endsWith(".jpg")||
                    file.getName().endsWith(".tif") ||
                    file.getName().endsWith(".gif") ||
                    file.getName().endsWith(".jpg")||
                    file.getName().endsWith(".tiff")||
                    file.getName().endsWith(".png")){
                fileCheck = true;
            } else {
                fileCheck = false;
            }
        }
        return fileCheck;
    }
}
