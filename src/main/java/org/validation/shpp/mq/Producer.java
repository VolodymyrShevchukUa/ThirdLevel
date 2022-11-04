package org.validation.shpp.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Producer {




        public static void main(String[] args) {
            new ProducerThread("tcp://3.121.239.250:61616", "queue1").start();
        }

        static class ProducerThread extends Thread {
            String brokerUrl;
            String destinationUrl;

            public ProducerThread(String brokerUrl, String destinationUrl) {
                this.brokerUrl = brokerUrl;
                this.destinationUrl = destinationUrl;
            }

            @Override
            public void run() {
                ActiveMQConnectionFactory connectionFactory;
                Connection conn;
                Session session;

                try {
                    // 1. Создаем фабрику соединений
                    connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
                    // 2. Создаем объект подключения md
                    conn = connectionFactory.createConnection();
                    conn.start();
                    // 3. Создаем сеанс
                    // Первый параметр: открывать ли транзакцию. true: открыть транзакцию, второй параметр игнорируется.
                    // Второй параметр: имеет смысл, когда первый параметр имеет значение false. Режим ответа на сообщение. 1. Автоответ 2. Ручной ответ. Автоответчик означает, что клиент удаляет сообщение после его получения. Получение сообщения не означает, что оно используется правильно. Если мы хотим убедиться, что сообщение было правильно обработано, мы должны вручную.
                    session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
                    // 4. Создаем цель отправки точка-точка
                    Destination destination = session.createQueue(destinationUrl);
                    // 5. Создание сообщения производителя
                    MessageProducer producer = session.createProducer(destination);
                    // Устанавливаем режим производителя, есть два варианта: Persistence / Non-persistent
                    producer.setDeliveryMode(DeliveryMode.PERSISTENT);
                    // 6. Создаем текстовое сообщение
                    String text = "Hello world!";
                    TextMessage message = session.createTextMessage(text);
                    for (int i = 0; i < 1; i++) {
                        // 7, отправляем сообщение
                        producer.send(message);
                    }
                    // 8. Закрываем соединение
                    session.close();
                    conn.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

