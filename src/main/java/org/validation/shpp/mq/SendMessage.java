package org.validation.shpp.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.validation.shpp.App;

import javax.jms.*;

public class SendMessage {

        public void sendMessage() {

            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();

            try {
                // Getting JMS connection from the server and starting it
                Connection connection = factory.createConnection();
                connection.start();

                // JMS messages are sent and received using a Session. We will
                // create here a non-transactional session object. If you want
                // to use transactions you should set the first parameter to 'true'
                Session session = connection.createSession(false,
                        Session.AUTO_ACKNOWLEDGE);

                Destination queue = session.createQueue("MYQUEUE");

                // MessageProducer is used for sending messages (as opposed
                // to MessageConsumer which is used for receiving them)
                MessageProducer producer = session.createProducer(queue);

                // We will send a small text message saying 'Hello World!'
                TextMessage message = session.createTextMessage("Hello World!\n");

                // Here we are sending the message!

                producer.send(message);
                System.out.println("SomethingMessage");
                session.close();
                connection.close();
            } catch (JMSException e){
                e.getStackTrace();
            }
        }
    }

