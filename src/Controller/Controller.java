package Controller;

import Model.Buffer;
import Model.Message;
import Model.MessageProducerInput;
import Model.Server;
import View.ChatBox;
import View.East;
import View.South;
import View.West;

public class Controller {
    private ChatBox chatBox;
    private Server server;
    private Buffer buffer;
    private West west;
    private South south;
    private East east;
    public Controller(ChatBox chatBox,Server server){
        this.chatBox=chatBox;
        this.server=server;

    }
    public void setMessage(){
        west.showMessage();
    }
    public Message getMessage() throws InterruptedException {
        Message message = (Message) buffer.get();
        return message;
    }

    public void startServer(MessageProducerInput ipManager) {
        new Server(ipManager,2341);
        server.startServer();
    }
}
