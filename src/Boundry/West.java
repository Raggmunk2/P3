package Boundry;

import Controller.Controller;
import Entity.Message;

import javax.swing.*;
import java.awt.*;

public class West extends JPanel{
    private Controller controller;
    private JLabel label;
    private JList messageBox;


    public West(Controller controller) {
        this.controller=controller;
        setUp();
    }

    private void setUp() {

        messageBox = new JList();
        messageBox.setPreferredSize(new Dimension(400,425));// här visas alla meddelanden
        messageBox.setBackground(Color.LIGHT_GRAY);
        this.add(messageBox);
        messageBox.setCellRenderer(new MessageRenderer());

        label = new JLabel();
        JScrollPane scrollPane = new JScrollPane(messageBox);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(485,425));
        this.add(scrollPane);
    }

    public void updateListView(Message[] messages) {
        messageBox.setListData(messages);
    }
}
