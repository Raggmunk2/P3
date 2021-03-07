package Boundry;

import Controller.Controller;
import Entity.Callback;
import Entity.Message;
import Entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ChatBox{
    private JFrame frame;
    private West west;
    private East east;
    private South south;
    private Middle middle;
    private Controller controller;

    public ChatBox(Controller controller){
        this.controller=controller;
        setUpFrame();
    }

    private void setUpFrame() {
        this.south=new South(controller);
        this.west= new West(controller);
        this.east=new East(controller);
        this.middle= new Middle(controller);
        frame = new JFrame("ChatBox");
        frame.setSize(1000,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(south, BorderLayout.SOUTH);
        frame.add(west, BorderLayout.WEST);
        frame.add(east,BorderLayout.EAST);
        frame.add(middle);
        frame.setVisible(true);
    }

    public void updateListView(Message[] messages) {
        west.updateListView(messages);
    }

    public void showUserOnline(User[] users) {
        east.showUserOnline(users);
    }

    public Object getSelectedUser() {
        User user = east.getSelectedUser();
        return user;
    }

    public void addContactToList(User[] contacts) {
        middle.addContactToList(contacts);
    }

    public User[] getContacts() {
        User[] contacts = middle.getListElements();
        return contacts;
    }
}
