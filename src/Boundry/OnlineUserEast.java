package Boundry;

import Controller.Controller;
import Entity.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OnlineUserEast extends JPanel {
    private Controller controller;
    private JList onlineList;

    public OnlineUserEast(Controller controller) {
        this.controller=controller;
        setUp();
    }

    //Metod som sätter upp GUI komponenterna på högra sidan
    private void setUp() {
        onlineList = new JList();
        onlineList.setPreferredSize(new Dimension(250,425));// här visas alla som är online
        onlineList.setBackground(Color.LIGHT_GRAY);
        onlineList.setCellRenderer(new UserRenderer());

        this.add(onlineList);
    }

    //Metod som visar de som är online
    public void showUserOnline(User[] usersOnline) {
        onlineList.setListData(usersOnline);
    }

    //Metod som returnerar den User som användaren markerar i listan
    public User getSelectedUser() {
        User user = (User) onlineList.getSelectedValue();
        System.out.println(user);
       return user;
    }

    //Metod som ger den lista av Users som användaren har markerat
    public List getSelectedElements() {
        return onlineList.getSelectedValuesList();
    }
}
