package Boundry;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ChatBox implements ActionListener {
    private JFrame frame;
    private West west;
    private East east;
    private South south;
    private Controller controller;

    public ChatBox(){
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
    public void setMessageBox(String message){
        try{
            controller.getMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ChatBox();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       String message =  south.getText();
        setMessageBox(message);
        //skicka meddelandet

    }
    public void getText(){

    }
}
