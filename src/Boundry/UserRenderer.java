package Boundry;

import Entity.User;

import javax.swing.*;
import java.awt.*;
//Denna klassen bestämmer hur User-objekten i listan ska visas
public class UserRenderer extends JLabel implements ListCellRenderer<User> {

    public UserRenderer(){
        setOpaque(true);
    }
    @Override
    public Component getListCellRendererComponent(JList<? extends User> list, User user, int index, boolean isSelected, boolean cellHasFocus) {
        setIcon(user.getImage());
        setText(user.getUsername());

        if(isSelected){
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        return this;
    }
}
