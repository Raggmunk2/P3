package Boundry;



import Controller.Controller;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class LogInWindow implements ActionListener {
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private JTextField userText;
    private JTextField ipText;
    private JTextField portText;
    private JLabel userLabel;
    private JLabel serverLabel;
    private JButton logInButton;
    private JButton selectImage;
    private Controller controller;
    private File selectedFile;
    private ImageIcon imageIcon;


    public LogInWindow(Controller controller) {
        this.controller = controller;
        setUp();
    }

    public LogInWindow() {
        //används
    }

    public void setUp(){
        frame.setSize(350,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Log in");
        frame.add(panel);

        userLabel = new JLabel("User");
        panel.setLayout(null);
        userLabel.setBounds(10,20,80,25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        serverLabel = new JLabel("IP and Port");
        serverLabel.setBounds(10,60,90,35);
        panel.add(serverLabel);

        ipText = new JTextField("127.0.0.1");
        ipText.setBounds(100,60,100,25);
        panel.add(ipText);

        portText = new JTextField("2341");
        portText.setBounds(205, 60, 60, 25);
        panel.add(portText);

        logInButton = new JButton("Login");
        logInButton.setBounds(40,100,100,25);
        logInButton.addActionListener(this);
        panel.add(logInButton);

        selectImage = new JButton("Select image");
        selectImage.setBounds(160,100,110,25);
        selectImage.addActionListener(this);
        panel.add(selectImage);

        frame.setVisible(true);
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
                    imageIcon = new ImageIcon(String.valueOf(selectedFile));
                    Image image = imageIcon.getImage();
                    Image newImage = image.getScaledInstance(25,25, Image.SCALE_SMOOTH);
                    imageIcon = new ImageIcon(newImage);
                    String username = userText.getText();
                    controller.addNewUser(username, imageIcon);
                }else {
                    JOptionPane.showInternalMessageDialog(null,"You chose the wrong format!");

                }
            }
        }
        if(e.getSource()== logInButton){
            if(selectedFile == null) {
                sendError("You have to chose a profile picture!");
            }
            else {
                String user = userText.getText();
                String ip = ipText.getText();
                int port = Integer.parseInt(portText.getText());
                System.out.println(String.format("User: %s, ip: %s, port: %s", user, ip, port));
                controller.connect(user,imageIcon ,ip, port);
                controller.addNewUser(user,imageIcon);
                //controller.readContactsOnFile();
                frame.setVisible(false);
            }
        }
    }

    public String getUsername() {
        String username =  userText.getText();
        return username;
    }
    public String getIpText(){
        String ip = ipText.getText();
        return ip;
    }

    public void sendError(String message) {
        JOptionPane.showInternalMessageDialog(null,message);
    }

    public void sucess(String message) {
        JOptionPane.showInternalMessageDialog(null,message);
    }

}
