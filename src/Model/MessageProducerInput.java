package Model;

public class MessageProducerInput {
    private Buffer<MessageProducer> producerBuffer;



    public MessageProducerInput(Buffer<MessageProducer> producerBuffer) {
        this.producerBuffer=producerBuffer;
    }

    public void addMessageProducer(MessageProducer messageProducer) {
        producerBuffer.put(messageProducer);

    }
}
