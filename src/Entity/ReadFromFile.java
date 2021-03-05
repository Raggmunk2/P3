package Entity;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

public class ReadFromFile implements MessageProducer {
    private String text;
    private String image;
    private String filename;
    private Buffer<Message> messageBuffer = new Buffer<>();
    private ArrayList<Message> messageArray;
    private int currMessage;

    public ReadFromFile(String filename){
        this.filename=filename;
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"UTF-8"))){

            messageArray = new ArrayList<>();

            while (br.ready()){
                text = br.readLine();
                image = br.readLine();
                currMessage++;
                System.out.println(text + "\n" + image);
                messageBuffer.put(new Message(text, new ImageIcon(image)));
                messageArray.add(new Message(text, new ImageIcon(image)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int size() {
        return messageBuffer.size();
    }

    @Override
    public Message nextMessage(){
        return messageArray.get(currMessage);
    }

    public static void main(String[] args) {
        new ReadFromFile("files/Hej.txt");
    }
}
