package Boundry;

import Entity.Message;
import Entity.User;

import javax.swing.*;
import java.awt.*;

public class UserRenderer extends JLabel implements ListCellRenderer<User> {
    @Override
    public Component getListCellRendererComponent(JList<? extends User> list, User user, int index, boolean isSelected, boolean cellHasFocus) {
        setIcon(user.getImage());
        setText(user.getUsername());
        return this;
    }
}
