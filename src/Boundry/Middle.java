package Boundry;

import Controller.Controller;
import Entity.User;

import javax.swing.*;
import java.awt.*;

public class Middle extends JPanel {
    private Controller controller;
    private JList contacts;
    private JLabel contactsText;

    public Middle(Controller controller) {
        this.controller=controller;
        setUp();
    }

    private void setUp() {
        contacts = new JList();
        //contacts.setBounds(10,10,200,200);
        contacts.setPreferredSize(new Dimension(200,400));
        contacts.setBackground(Color.gray);


        contactsText = new JLabel("Your contacts");
        contactsText.setBounds(5,5,100,20);
        this.add(contactsText);
        this.add(contacts);

    }

    public void addContactToList(User[] newContacts) {
        contacts.setListData(newContacts);
    }
}
