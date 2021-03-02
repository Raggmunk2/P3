package Model;

import java.util.HashMap;

public class Contacts {
    //private HashMap<User,Client> clients = ...
    // egna tillägg
    /*public synchronized put(User user,Client client) {
        //clients.put(user,client);
    }*/
    public synchronized Client get(User user) {
        return get(user);
    }
    // fler synchronized-metoder som behövs
}
