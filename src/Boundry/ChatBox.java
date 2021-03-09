package Boundry;

import Controller.Controller;
import Entity.Callback;
import Entity.Message;
import Entity.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
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

    public User[] getSelectedReceivers() {
        List onlineReceivers = east.getSelectedElements();
        List contactReceivers = middle.getSelectedElements();
        ArrayList<User> receivers = new ArrayList<>();
        for(Object o : onlineReceivers){
            receivers.add((User)o);
        }
        for(Object o: contactReceivers){
            User u = (User) o;
            if(!receivers.contains(u)){
                receivers.add(u);
            }
        }
        User[] allReceivers = new User[receivers.size()+1]; //Lägg till mej själv på sista platsen
        for(int i =0;i< receivers.size();i++){
            allReceivers[i] = receivers.get(i);
        }
        return allReceivers;
    }
}
