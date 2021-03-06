package Boundry;

import Controller.Controller;
import Entity.User;

import javax.swing.*;
import java.awt.*;

public class East extends JPanel {
    private Controller controller;
    private JList onlineList;
    private JList contacts;
    private JLabel onlineText;
    private JLabel contactText;

    public East(Controller controller) {
        this.controller=controller;
        setUp();
    }

    private void setUp() {
        onlineList = new JList();
        //onlineList.setBounds(10,10,10,10);
        onlineList.setPreferredSize(new Dimension(250,220));// här visas alla som är online
        onlineList.setBackground(Color.LIGHT_GRAY);
        onlineList.setCellRenderer(new UserRenderer());
        this.add(onlineList);


        onlineText = new JLabel("Online now:");
        Font font = new Font("Arial",Font.PLAIN,12);
        onlineText.setBounds(10,300,100,20);
        onlineText.setFont(font);
        //this.add(onlineText);

        contacts = new JList();
        contacts.setBounds(310,10,300,220);
        //contacts.setPreferredSize(new Dimension(300,220));
        contacts.setBackground(Color.orange);
        //this.add(contacts);
    }

    public void showUserOnline(User[] usersOnline) {
        onlineList.setListData(usersOnline);
    }
}
