package Boundry;

import Entity.Message;

import javax.swing.*;
import java.awt.*;
//Denna klassen bestämmer hur Message-objekten i listan ska visas

public class MessageRenderer extends JLabel implements ListCellRenderer<Message> {
    @Override
    public Component getListCellRendererComponent(JList<? extends Message> list, Message message, int index, boolean isSelected, boolean cellHasFocus) {
        setIcon(message.getProfilePic());
        setText(message.toString());
        return this;
    }
}
