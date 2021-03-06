package Boundry;

import Controller.Controller;
import Entity.Buffer;
import Entity.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class South extends JPanel implements ActionListener {
    private JTextField messageToWrite;
    private JButton sendButton;
    private Controller controller;
    private Buffer<Message> messageBuffer;
    private ChatBox chatBox;
    public South(Controller controller){
        this.controller=controller;
        setUp();

    }
    public void setUp(){
        setSize(new Dimension(100,500));
        messageToWrite = new JTextField();
        messageToWrite.setPreferredSize(new Dimension(300,20)); //415, 100
        messageToWrite.setBackground(Color.gray);


        sendButton = new JButton("Send");
        sendButton.addActionListener(this);

        add(sendButton,BorderLayout.EAST);

        add(messageToWrite, BorderLayout.WEST);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==sendButton) {
            String messageText = messageToWrite.getText();
            if(messageText != null && !messageText.isEmpty()) {
                controller.sendToServer(messageText);
            }
        }
    }

    public String getText() {
        String message = messageToWrite.getText();
       return message;

    }
}
