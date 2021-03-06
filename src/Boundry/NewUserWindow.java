package Boundry;

import Controller.Controller;
import Entity.User;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class NewUserWindow implements ActionListener {
    private JFrame frame2;
    private JTextField newUserText;
    private JLabel newUserLabel;
    private JPanel panel2;
    private JButton ok;
    private JButton selectImage;
    private JLabel instructions = new JLabel("");
    private Controller controller;
    private LogInWindow logInWindow;
    private File selectedFile;

    public NewUserWindow(){
        makeNewUserWindow();
        this.controller=new Controller();
        this.logInWindow=new LogInWindow();
    }

    public void makeNewUserWindow() {
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
        instructions.setText("Please enter username & chose a profile picture!");
        panel2.add(instructions);

        frame2.add(panel2);
        frame2.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==selectImage){
            System.out.println("Select image");
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            int returnValue = jfc.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile = jfc.getSelectedFile();
                boolean fileCheck = controller.checkFileType(selectedFile);
                if(fileCheck){
                    ImageIcon imageIcon = new ImageIcon(String.valueOf(selectedFile));
                    String username = newUserText.getText();
                    controller.addNewUser(username, imageIcon);
                }else {
                    JOptionPane.showInternalMessageDialog(null,"You chose the wrong format!");

                }
            }

        }
        if(e.getSource()==ok){
            if(selectedFile == null){
                logInWindow.sendError("You have to chose a profile picture!");
            }else {
                String username = newUserText.getText();
                controller.compare(username);
                //jämför om användarnamnet redan finns & stäng rutan
                frame2.setVisible(false); //Vet inte hur jag ska få bort rutan så jag satte visable till false
            }
        }
    }

}
