package View;

import Controller.Controller;
import Model.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class South extends JPanel implements ActionListener {
    private JTextField messageToWrite;
    private JButton sendButton;
    private Controller controller;
    public South(Controller controller){
        this.controller=controller;
        setUp();

    }
    public void setUp(){
        messageToWrite = new JTextField();
        messageToWrite.setPreferredSize(new Dimension(415,100)); //415, 100
        messageToWrite.setBackground(Color.gray);
        /*JScrollPane scrollPane = new JScrollPane(messageToWrite);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(415,100));
        this.add(scrollPane);*/

        sendButton = new JButton("Send");
        sendButton.setBounds(25,25,10,60);
        sendButton.addActionListener(this);
        this.add(sendButton);

        this.add(messageToWrite);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public String getText() {
        String message = messageToWrite.getText();
       return message;

    }
}
