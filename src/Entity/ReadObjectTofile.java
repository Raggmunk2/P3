package Entity;

public class ReadObjectTofile implements MessageProducer{
    private String filename;
    private int size;
    private Message message;
    private Message[] messageArray;
    private int currMessage;
    @Override
    public int size() {
        return 0;
    }

    @Override
    public Message nextMessage() {
        return null;
    }
}
