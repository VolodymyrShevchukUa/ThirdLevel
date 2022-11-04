package org.validation.shpp.mq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSProducer {
    // Имя пользователя подключения по умолчанию
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    // Пароль подключения по умолчанию
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    // адрес соединения по умолчанию
    private static final String BROKEURL = "tcp://3.121.239.250:8181/admin";
   //         "failover://tcp://3.121.239.250:8161";
    // Количество отправленных сообщений
    private static final int SENDNUM = 10;

    public static void main(String[] args) {
        // фабрика соединений
        ConnectionFactory connectionFactory = null;
        // соединяем
        Connection connection = null;
        // Разговор, поток для получения или отправки сообщений
        Session session = null;
        // назначение сообщения
        Destination destination = null;
        // Производитель сообщения
        MessageProducer messageProducer = null;

        // Создание фабрики соединений
        connectionFactory = new ActiveMQConnectionFactory(JMSProducer.USERNAME, JMSProducer.PASSWORD, JMSProducer.BROKEURL);

        try {
            // Получить соединение через фабрику соединений
            connection = connectionFactory.createConnection();
            // начать соединение
            connection.start();
            // Создать сессию


            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // Создать очередь сообщений
            destination = session.createQueue("queue1");
            // Создать производителя сообщения
            messageProducer = session.createProducer(destination);
            // отправить сообщение
            sendMessage(session, messageProducer);
            // совершаем транзакцию
            session.commit();
        } catch (
                Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

        /**
         * Имя метода: sendMessage
         * Функция: отправить сообщение
         * Описание:
         * Создано: typ
         * Время создания: 2018/8/14 21:47
         * Изменено:
         * Изменить описание:
         * Время модификации:
         */
        public static void sendMessage (Session session, MessageProducer messageProducer) throws Exception {
            for (int i = 0; i < JMSProducer.SENDNUM; i++) {
                TextMessage message = session.createTextMessage("Сообщение отправлено ActiveMQ" + i);
                System.out.println("Сообщение от ActiveMQ" + i);
                messageProducer.send(message);
            }
        }
    }


