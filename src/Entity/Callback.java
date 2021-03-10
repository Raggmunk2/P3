package Entity;

public interface Callback {
    void updateListView(Message[] messages);
    void updateListView(User[] users);
}