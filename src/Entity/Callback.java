package Entity;

import java.util.List;

public interface Callback {
    void updateListView(Message[] messages);
    void updateListView(User[] users);
}