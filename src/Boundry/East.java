package Boundry;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;

public class East extends JPanel {
    private Controller controller;
    private JList onlineList;

    public East(Controller controller) {
        this.controller=controller;
        setUp();
    }

    private void setUp() {
        onlineList = new JList();
        onlineList.setPreferredSize(new Dimension(300,425));// här visas alla meddelanden
        onlineList.setBackground(Color.LIGHT_GRAY);
        this.add(onlineList);
    }
}
