package Model;

import java.io.Serializable;

public interface MessageProducer extends Serializable {
    public int size();
    public Message nextMessage() throws InterruptedException;

    default void info() {
        System.out.println("size="+size()+"]");
    }
}
