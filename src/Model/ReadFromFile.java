package Model;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ReadFromFile implements MessageProducer {
    private String text;
    private String image;
    private String filename;
    private Buffer<Message> messageBuffer;
    private ArrayList<Message> messageArray;
    private int size;
    private int currMessage;

    public ReadFromFile(String filename){
        this.filename=filename;
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"UTF-8"))){
            //size = Integer.parseInt(br.readLine());

            messageArray = new ArrayList<>();
            for(int i =0;i<messageArray.size();i++){
            //for(Message i : messageArray){
                text = br.readLine();
                image = br.readLine();
                currMessage++;
                System.out.println(text + "\n" + image);
                System.out.println(messageBuffer.size());
                //messageBuffer.put(new Message(text, new ImageIcon(image)));
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
}
