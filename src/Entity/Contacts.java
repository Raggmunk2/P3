package Entity;

import Controller.Controller;

import java.io.*;
import java.util.HashMap;

public class Contacts {
    private Controller controller;
    private HashMap<User,Client> clients = new HashMap<>();
    private HashMap<String,String> userPass = new HashMap<>();
    // egna tillägg
    /*public synchronized put(User user,Client client) {
        user =  user.getUser();
        client = client.getClient();
        clients.put(user,client);
    }*/
    public void readUserPass(String filename){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"UTF-8")); //"ISO-8859-1"));
            String[] parts;
            String username;
            String password;
            String str = br.readLine();
            while( str != null ) {
                parts = str.split( "," );
                username = parts[ 0 ];
                password = parts[ 1 ];
                userPass.put(parts[0],parts[1]); // (key=landets namn, value=Population-objekt) lagras i HashMappen
                str = br.readLine();
            }
            br.close();
        } catch( IOException e ) {
            e.printStackTrace();
        }
    }
    public void writeUserPass(String filename){
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"));
            String username= controller.getUsernameText();
            String password = controller.getPasswordText();
            bw.write(username);
            bw.write(password);
            bw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized Client get(User user) {
        return get(user);
    }
    // fler synchronized-metoder som behövs
}
