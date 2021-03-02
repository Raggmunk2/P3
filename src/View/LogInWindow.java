package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogInWindow implements ActionListener {
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private JTextField userText;
    private JPasswordField passwordText;
    private JLabel userLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    private JLabel success = new JLabel("");
    private Controller controller;

    public LogInWindow(){

        setUp();
    }
    public void setUp(){
        frame.setSize(350,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Log in");
        frame.add(panel);

        userLabel = new JLabel("Username");
        panel.setLayout(null);
        userLabel.setBounds(10,20,80,25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10,60,90,35);
        panel.add(passwordLabel);

        passwordText = new JPasswordField();
        passwordText.setBounds(100,60,165,25);
        panel.add(passwordText);

        loginButton = new JButton("Login");
        loginButton.setBounds(130,100,100,25);
        loginButton.addActionListener(this);
        panel.add(loginButton);


        success.setBounds(125,130,300,25);
        panel.add(success);

        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("klickad");
        String user = userText.getText();
        String password = passwordText.getText();

        if(user.equals("Linn") && password.equals("lösenord")){ //ska fixas i HashMap ist
            success.setText("Login successful!");

            //öppna kopplingen till servern
        }

    }
}
