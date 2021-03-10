package Boundry;

import Controller.Controller;
import Entity.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ContactListCenter extends JPanel {
    private Controller controller;
    private JList contacts;
    private JLabel contactsText;
    private User[] contactsList = new User[0];

    public ContactListCenter(Controller controller) {
        this.controller=controller;
        setUp();
    }

    private void setUp() {
        contacts = new JList();
        contacts.setPreferredSize(new Dimension(200,400));
        contacts.setBackground(Color.lightGray);
        contacts.setCellRenderer(new UserRenderer());


        contactsText = new JLabel("Your contacts");
        contactsText.setBounds(5,5,100,20);
        this.add(contactsText);
        this.add(contacts);

    }

    public void addContactToList(User[] newContacts) {
        contacts.setListData(newContacts);
        contactsList= newContacts;
    }
    public User[] getListElements() {
        return contactsList;
    }

    public List getSelectedElements() {
        return  contacts.getSelectedValuesList();
    }
}
