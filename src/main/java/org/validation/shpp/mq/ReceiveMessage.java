package org.validation.shpp.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ReceiveMessage {

        public void receiveMessage () {

            String url = "http://3.127.79.182:8161";

            // Getting JMS connection from the server and starting it
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();

            {
                try {
                    Connection connection = factory.createConnection();
                    connection.start();

                    // Creating session for seding messages
                    Session session = connection.createSession(false,
                            Session.AUTO_ACKNOWLEDGE);

                    // Getting the queue
                    Destination queue = session.createQueue("MYQUEUE");
                    // MessageConsumer is used for receiving (consuming) messages
                    MessageConsumer consumer = session.createConsumer(queue);
                    // Here we receive the message.
                    // By default this call is blocking, which means it will wait
                    // for a message to arrive on the queue.
                    Message message = consumer.receive(1000);

                    // There are many types of Message and TextMessage
                    // is just one of them. Producer sent us a TextMessage
                    // so we must cast to it to get access to its .getText()
                    // method.
                    if (message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        System.out.println("raz raz a");
                    }
                    session.close();
                    connection.close();

                } catch (JMSException e) {
                    e.getStackTrace();
                }
            }
        }
    }



