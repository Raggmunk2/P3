package Boundry;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class South extends JPanel implements ActionListener {
    private JTextField messageToWrite;
    private JButton sendButton;
    private Controller controller;
    private JButton addContacts;
    private JButton logOut;

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
        sendButton.setBounds(10,10,100,20);
        sendButton.addActionListener(this);

        addContacts = new JButton("Add contacts");
        addContacts.setBounds(15,15,100,20);
        addContacts.addActionListener(this);

        logOut = new JButton("Log out");
        logOut.setBounds(20,20,100,20);
        logOut.addActionListener(this);

        add(logOut);
        add(addContacts);
        add(sendButton);

        add(messageToWrite);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==sendButton) {
            String messageText = messageToWrite.getText();
            if(messageText != null && !messageText.isEmpty()) {
                controller.sendToServer(messageText);
            }
        }
        if(e.getSource()==addContacts){
            controller.addToContact();
        }
        if(e.getSource()== logOut){
            controller.logOut();
        }
    }

    public String getText() {
        String message = messageToWrite.getText();
       return message;

    }
}
