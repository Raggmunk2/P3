package Boundry;

import Controller.Controller;
import Entity.Message;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TextBoxWest extends JPanel{
    private Controller controller;
    private JLabel label;
    private JList messageBox;
    ArrayList<Message> infoString = new ArrayList<>();


    public TextBoxWest(Controller controller) {
        this.controller=controller;
        setUp();

    }
    //Metod som sätter upp GUI-komponenterna på vänstra sidan
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

    //Metod som uppdaterar meddelandena på GUI
    public void updateListView(Message[] messages) {
        messageBox.setListData(messages);
        updateTraficInfo(messages);
    }

    //Metod som uppdaterar trafik loggen med meddelanden
    public void updateTraficInfo(Message[] messages) {
        for(Message m : messages){
            infoString.add(m);
        }

    }

    //Metod som returnerar en ArrayList med Message till trafik loggen
    public ArrayList<Message> getTraficInfo() {
        return infoString;
    }
}
