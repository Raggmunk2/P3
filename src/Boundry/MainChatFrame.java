package Boundry;

import Controller.Controller;
import Entity.*;
import Entity.Message;
import Entity.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;



public class MainChatFrame{
    private JFrame frame;
    private TextBoxWest textBoxWest;
    private OnlineUserEast onlineUserEast;
    private ButtonPanelSouth buttonPanelSouth;
    private ContactListCenter contactListCenter;
    private Controller controller;

    public MainChatFrame(Controller controller){
        this.controller=controller;
        setUpFrame();
    }

    //Här sätter vi upp framen för själva chatten
    private void setUpFrame() {
        this.buttonPanelSouth=new ButtonPanelSouth(controller);
        this.textBoxWest= new TextBoxWest(controller);
        this.onlineUserEast=new OnlineUserEast(controller);
        this.contactListCenter= new ContactListCenter(controller);
        frame = new JFrame("ChatBox");
        frame.setSize(1000,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(buttonPanelSouth, BorderLayout.SOUTH);
        frame.add(textBoxWest, BorderLayout.WEST);
        frame.add(onlineUserEast,BorderLayout.EAST);
        frame.add(contactListCenter);
        frame.setVisible(true);
    }

    //Metod som uppdaterar meddelandena och skickar vidare till TextBoxWest
    public void updateListView(Message[] messages) {
        textBoxWest.updateListView(messages);
    }

    //Metod som visar vilka som är Online skickas visare till OnlineUserEast
    public void showUserOnline(User[] users) {
        onlineUserEast.showUserOnline(users);
    }

    //Metod som ger den User som användaren väljer
    public Object getSelectedUser() {
        User user = onlineUserEast.getSelectedUser();
        return user;
    }

    //Metod som lägger till en kontakt i ContactListCenter
    public void addContactToList(User[] contacts) {
        contactListCenter.addContactToList(contacts);
    }

    //Metod som returnerar en Array av Users från ContactListCenter
    public User[] getContacts() {
        User[] contacts = contactListCenter.getListElements();
        return contacts;
    }

    //Metod som returnerar en User-array från de som användaren väljer att klickar på/markera
    public User[] getSelectedReceivers() {
        List onlineReceivers = onlineUserEast.getSelectedElements();
        List contactReceivers = contactListCenter.getSelectedElements();
        ArrayList<User> receivers = new ArrayList<>();
        for(Object o : onlineReceivers){ //Går igenom alla som är online och lägger de i arrayen
            receivers.add((User)o);
        }
        for(Object o: contactReceivers){ //går igenom alla tillagda kontakter och lägger de i arrayen
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

    //Metod som gör fönstret not visible vid neskoppling
    public void close() {
        frame.setVisible(false);
    }

    //Metod som returnerar en ArrayList med Message som den hämtar den från TextBoxWest
    public ArrayList<Message> getTraficInfo() {
        ArrayList<Message> infoString = textBoxWest.getTraficInfo();
        return infoString;
    }
}
