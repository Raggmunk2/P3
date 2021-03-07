package Boundry;

import Controller.Controller;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class TraficLog {
    private JFrame frame2;
    private JTextField answerTimeFrom;
    private JLabel timeFrom;
    private JLabel timeTo;
    private JTextField answerTimeTo;
    private JPanel panel2;
    private JLabel instructions = new JLabel("");
    private Controller controller;
    private JList textBox;

    public TraficLog(){
        makeWindow();
        this.controller=new Controller();

    }

    public void makeWindow() {
        frame2 = new JFrame();
        panel2 = new JPanel();
        panel2.setLayout(null);
        frame2.setSize(800,600);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setTitle("Trafic log");

        textBox = new JList();
        textBox.setBounds(20,60,750,490);
        panel2.add(textBox);


        timeFrom = new JLabel("Time from YYYY/MM/DD HH:MM");
        timeFrom.setBounds(10,30,300,20);
        panel2.add(timeFrom);

        answerTimeFrom = new JTextField(20);
        answerTimeFrom.setBounds(200,30,165,25);
        panel2.add(answerTimeFrom);

        timeTo = new JLabel("Time to YYYY/MM/DD HH:MM");
        timeTo.setBounds(380,30,300,20);
        panel2.add(timeTo);

        answerTimeTo = new JTextField(20);
        answerTimeTo.setBounds(555,30,165,25);
        panel2.add(answerTimeTo);


        //instruktions text
        instructions.setBounds(250,5,300,20);
        Font myFont = new Font("Arial", Font.PLAIN, 10);
        instructions.setFont(myFont);
        instructions.setText("Please enter timestamp for the trafic logger!");
        panel2.add(instructions);

        frame2.add(panel2);
        frame2.setVisible(true);
    }
    public void showTraficLog(String[] infoString){
        textBox.setListData(infoString);
    }

    public static void main(String[] args) {
        new TraficLog();
    }

}
