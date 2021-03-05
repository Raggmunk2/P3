package Boundry;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {
    private JFrame frame;
    private West west;
    private East east;
    private South south;


    //west

    private JLabel label;
    private JList messageBox;

    //south
    private JTextField messageToWrite;
    private JButton sendButton;
    private Controller controller;

    //east
    private JList onlineList;

    public GUI(int width, int height){
        new JFrame("ChatBox");
        setPreferredSize(new Dimension(width,height));
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUpPanel();
        setVisible(true);
        setResizable(false);


    }

    private void setUpPanel() {
        this.south=new South(controller);
        this.west= new West(controller);
        this.east=new East(controller);
        //frame.add(south, BorderLayout.SOUTH);
        //frame.add(west, BorderLayout.WEST);
        //frame.add(east,BorderLayout.EAST);
        //east.setBackground(Color.yellow); // ta bort sen
        //west.setBackground(Color.blue);   // ta bort sen
        //south.setBackground(Color.black); //ta bort sen


        JPanel panel = new JPanel();


        //west
        messageBox = new JList();
        //messageBox.setPreferredSize(new Dimension(400,425));// här visas alla meddelanden
        messageBox.setBounds(50,50,485,425);
        messageBox.add(west,BorderLayout.WEST);
        messageBox.setBackground(Color.blue);
        panel.add(messageBox);

        label = new JLabel();
        JScrollPane scrollPane = new JScrollPane(messageBox);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(485,425));
        panel.add(scrollPane);

        //south
        messageToWrite = new JTextField();
        messageToWrite.setBounds(100,500,100,600);
        messageToWrite.setPreferredSize(new Dimension(100,100)); //415, 100
        messageToWrite.setBackground(Color.gray);


        sendButton = new JButton("Send");
        sendButton.setBounds(25,25,10,60);
        sendButton.addActionListener(this);

        panel.add(sendButton);

        panel.add(messageToWrite);

        //east
        onlineList = new JList();
        onlineList.setPreferredSize(new Dimension(300,425));// här visas alla kontakter
        onlineList.setBackground(Color.LIGHT_GRAY);
        panel.add(onlineList);
        add(panel);

    }
    public void setMessageBox(String message){
        try{
            controller.getMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new GUI(1000,800);
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
