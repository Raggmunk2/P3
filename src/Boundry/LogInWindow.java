package Boundry;

import Controller.Controller;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.FileChooserUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class LogInWindow implements ActionListener {
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private JTextField userText;
    private JPasswordField passwordText;
    private JLabel userLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    private JButton newUser;
    private JLabel success = new JLabel("");
    private Controller controller;

    private JFrame frame2;
    private JTextField newUserText;
    private JLabel newUserLabel;
    private JPanel panel2;
    private JTextField newPasswordtext;
    private JLabel newPasswordLabel;
    private JButton ok;
    private JButton selectImage;
    private JLabel instructions = new JLabel("");

    public LogInWindow(Controller controller){
        this.controller=controller;

    }
    public LogInWindow(){
        this.controller=controller;
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
        loginButton.setBounds(160,100,100,25);
        loginButton.addActionListener(this);
        panel.add(loginButton);

        newUser = new JButton("New User");
        newUser.setBounds(50,100,100,25);
        newUser.addActionListener(this);
        panel.add(newUser);


        success.setBounds(125,130,300,25);
        panel.add(success);

        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==selectImage){
            System.out.println("Select image");
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            int returnValue = jfc.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                boolean fileCheck = controller.checkFileType(selectedFile);
                if(fileCheck){
                    new LogInWindow();
                    frame2.setVisible(false); //Vet inte hur jag ska få bort rutan så jag satte visable till false
                }else {
                    JOptionPane.showInternalMessageDialog(null,"You chose the wrong format!");

                }
            }

        }

        if(e.getSource()==newUser){
            makeNewUserWindow();
            System.out.println("New user");
            //skapa ny användare

            if(e.getSource()==ok){
                //jämför om användarnamnet redan finns & stäng rutan
            }


        }
        if(e.getSource()==loginButton){
            System.out.println("klickad");
            String user = userText.getText();
            String password = passwordText.getText();

            if(user.equals("Linn") && password.equals("lösenord")){ //ska fixas i HashMap ist
                success.setText("Login successful!");
                controller.startServer();
                //öppna kopplingen till servern
            }
        }


    }

    private void makeNewUserWindow() {
        frame2 = new JFrame();
        panel2 = new JPanel();
        panel2.setLayout(null);
        frame2.setSize(350,200);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setTitle("New User");

        //lable för username
        newUserLabel = new JLabel("Username");
        newUserLabel.setBounds(10,30,60,20);
        panel2.add(newUserLabel);

        //textruta för username
        newUserText = new JTextField(20);
        newUserText.setBounds(100,30,165,25);
        panel2.add(newUserText);

        //lable password
        newPasswordLabel = new JLabel("New password");
        newPasswordLabel.setBounds(10,60,90,20);
        panel2.add(newPasswordLabel);

        //textruta för lösenord
        newPasswordtext = new JTextField(20);
        newPasswordtext.setBounds(100,60,165,25);
        panel2.add(newPasswordtext);


        //ok-knapp
        ok = new JButton("OK");
        ok.setBounds(170,100,100,25);
        ok.addActionListener(this);
        panel2.add(ok);

        //"välja bild"-knapp
        selectImage = new JButton("Select image");
        selectImage.setBounds(40,100,110,25);
        selectImage.addActionListener(this);
        panel2.add(selectImage);

        //instruktions text
        instructions.setBounds(10,5,300,20);
        Font myFont = new Font("Arial", Font.PLAIN, 10);
        instructions.setFont(myFont);
        instructions.setText("Please enter username, password & chose a profile picture!");
        panel2.add(instructions);

        frame2.add(panel2);
        frame2.setVisible(true);

    }

}
