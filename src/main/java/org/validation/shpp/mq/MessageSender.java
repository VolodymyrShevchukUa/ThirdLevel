package org.validation.shpp.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.validation.shpp.Config;

import javax.jms.*;
import java.util.List;

public class MessageSender {

    Config config;
    ActiveMQConnectionFactory connectionFactory;
    Connection connection;
    Session session;

    // Тут мабуть добре було б замутити сінглтон
    public MessageSender() {
        config = new Config();
        connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(config.getBROKE_URL());
        connectionFactory.setUserName(config.getNAME());
        connectionFactory.setPassword(config.getPASSWORD());
    }


    public void sendMessages(List<String> message) {
        openConnection();

        try {
            Destination queue = session.createQueue(config.getQUEUE());
            MessageProducer producer = session.createProducer(queue);
            for (String text : message) {
                TextMessage textMessage = session.createTextMessage(text);
                producer.send(textMessage);
            }
            producer.close();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }

    }
    private void closeConnection() {
        try {
            session.close();
            connection.close();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    private void openConnection() {
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

