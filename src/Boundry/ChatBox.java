package Boundry;

import Controller.Controller;
import Entity.Callback;
import Entity.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ChatBox{
    private JFrame frame;
    private West west;
    private East east;
    private South south;
    private Controller controller;

    public ChatBox(Controller controller){
        this.controller=controller;
        setUpFrame();
    }

    private void setUpFrame() {
        this.south=new South(controller);
        this.west= new West(controller);
        this.east=new East(controller);
        frame = new JFrame("ChatBox");
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(south, BorderLayout.SOUTH);
        frame.add(west, BorderLayout.WEST);
        frame.add(east,BorderLayout.EAST);
        east.setBackground(Color.yellow); // ta bort sen
        west.setBackground(Color.blue);   // ta bort sen
        south.setBackground(Color.black); //ta bort sen
        frame.setVisible(true);
    }

    public void updateListView(Message[] messages) {
        west.updateListView(messages);
    }
    /*public void setMessageBox(Message message){
        try{
            message = controller.getMessage();
            west.showMessage(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

}
