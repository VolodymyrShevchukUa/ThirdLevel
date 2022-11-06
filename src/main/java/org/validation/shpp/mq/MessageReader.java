package org.validation.shpp.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.validation.shpp.Configurable;

import javax.jms.*;
import java.util.LinkedList;
import java.util.List;

public class MessageReader implements Configurable {

    private Session session;
    private Connection connection;
    private ActiveMQConnectionFactory connectionFactory;

    public MessageReader(){
        connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(URL);
        connectionFactory.setUserName(NAME);
        connectionFactory.setPassword(PASS);
    }

    public List<String> receiveMessage() {

        List<String> response = new LinkedList<>();
        openConnection();
        try {
            Destination destination = session.createQueue(QUEUE_NAME);
            MessageConsumer messageConsumer = session.createConsumer(destination);
            while (true) {
                TextMessage textMessage = (TextMessage) messageConsumer.receive(10000);
                if (!textMessage.getText().equals(POISON_PILL)) {
                    response.add(textMessage.getText());
                }else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return response;
    }

    private void closeConnection(){
        try {
            session.close();
            connection.close();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
    private void openConnection(){
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}

