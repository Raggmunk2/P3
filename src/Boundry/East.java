package Boundry;

import Controller.Controller;
import Entity.User;

import javax.swing.*;
import java.awt.*;

public class East extends JPanel {
    private Controller controller;
    private JList onlineList;
    private JLabel onlineText;

    public East(Controller controller) {
        this.controller=controller;
        setUp();
    }


    private void setUp() {
        onlineList = new JList();
        onlineList.setPreferredSize(new Dimension(250,425));// här visas alla som är online
        onlineList.setBackground(Color.LIGHT_GRAY);
        onlineList.setCellRenderer(new UserRenderer());
        onlineList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);



        onlineText = new JLabel("Online now:");
        //Font font = new Font("Arial",Font.PLAIN,12);
        onlineText.setBounds(5,5,100,20);
        //onlineText.setFont(font);
        //this.add(onlineText);
        this.add(onlineList);
    }

    public void showUserOnline(User[] usersOnline) {
        onlineList.setListData(usersOnline);
    }

    public User getSelectedUser() {
        User user = (User) onlineList.getSelectedValue();
        System.out.println(user);
        System.out.println("Your succesfully added a person to you contacts");
       return user;
    }

}
