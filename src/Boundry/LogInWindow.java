package Boundry;



import Controller.Controller;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class LogInWindow implements ActionListener {
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private JTextField userText;
    private JTextField ipText;
    private JTextField portText;
    private JLabel userLabel;
    private JLabel serverLabel;
    private JButton connectButton;
    private JButton newUser;
    private Controller controller;


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

        connectButton = new JButton("Login");
        connectButton.setBounds(50,100,100,25);
        connectButton.addActionListener(this);
        panel.add(connectButton);

        newUser = new JButton("New User");
        newUser.setBounds(180,100,100,25);
        newUser.addActionListener(this);
        panel.add(newUser);


        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==newUser){
            new NewUserWindow();
        }
        if(e.getSource()== connectButton){
            String user = userText.getText();
            String ip = ipText.getText();
            int port = Integer.parseInt(portText.getText());
            System.out.println(String.format("User: %s, ip: %s, port: %s", user, ip, port));
            controller.connect(user, ip, port);
            frame.setVisible(false);
        }
    }

    public String getUsername() {
        String username =  userText.getText();
        return username;
    }
    public String getPassword(){
        String password = ipText.getText();
        return password;
    }

    public void sendError(String message) {
        JOptionPane.showInternalMessageDialog(null,message);
    }

    public void sucess(String message) {
        JOptionPane.showInternalMessageDialog(null,message);
    }

}
