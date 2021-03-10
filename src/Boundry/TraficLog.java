package Boundry;

import Controller.Controller;
import Entity.Message;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class TraficLog implements ActionListener {
    private JFrame frame2;
    private JTextField answerTimeFrom;
    private JLabel timeFrom;
    private JLabel timeTo;
    private JTextField answerTimeTo;
    private JPanel panel2;
    private JLabel instructions = new JLabel("");
    private Controller controller;
    private MainChatFrame mainChatFrame;
    private JList textBox;
    private JButton ok;

    public TraficLog(){
        makeWindow();
        this.controller=new Controller();

    }

    //Metod som sätter upp GUI-komponenterna till ett fönster
    public void makeWindow() {
        frame2 = new JFrame();
        panel2 = new JPanel();
        panel2.setLayout(null);
        frame2.setSize(800,600);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setTitle("Trafic log");

        textBox = new JList();
        textBox.setBounds(20,60,750,490);
        textBox.setBackground(Color.blue);

        JScrollPane scrollPane = new JScrollPane(textBox);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(830,490));
        //textBox.add(scrollPane);


        timeFrom = new JLabel("Time from: yyyyMMddHHmm ");
        timeFrom.setBounds(10,30,300,20);
        panel2.add(timeFrom);

        answerTimeFrom = new JTextField(20);
        answerTimeFrom.setBounds(200,30,165,25);
        panel2.add(answerTimeFrom);

        timeTo = new JLabel("Time to: yyyyMMddHHmm ");
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

        ok = new JButton("OK");
        ok.setBounds(725,30,55,25);
        panel2.add(ok);

        panel2.add(textBox);
        frame2.add(panel2);
        frame2.setVisible(true);
    }


    public static void main(String[] args) {
        new TraficLog();
    }

    //Metod som hämtar från-datumet/tiden som användaren vill söka på
    public String getTimeFrom() {
        return answerTimeFrom.getText();
    }

    //Metod som hämtar till-datumet/tiden som användaren vill söka på
    public String getTimeTo() {
        return answerTimeTo.getText();
    }

    //Metod som visar trafikloggen i textrutan
    public void showTraficLog(ArrayList<Message> allMessages) {
        Object[] infoStrings = ((ArrayList) allMessages).toArray();
        textBox.setListData(infoStrings);
    }

    //ActionPreformed-metod som reagerar på OK-knappen
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(ok)){
            ArrayList<Message> allMesseges = controller.getTraficLog();
            showTraficLog(allMesseges);
        }
    }
}
