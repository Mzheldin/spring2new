package consumer;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static java.lang.System.in;

public class Receiver {

    private static Scanner scanner = new Scanner(in);
    private static ConnectionFactory connectionFactory = new ConnectionFactory();

    public static void main(String[] args) throws Exception{
        connectionFactory.setHost("localhost");
        while (true)
            initReading();
    }

    private static void initReading() throws Exception{
        Channel channel = null;
        Connection connection = null;
        String input = null;
        while (scanner.hasNext()){
            input = scanner.nextLine();
            if (input.equals(Commands.EXIT.getValue()))
                break;
            if (input.startsWith(Commands.CHANNEL.getValue())){
                String channelName = input.substring(6);
                if (channel != null)
                    channel.close();
                if (connection != null)
                    connection.close();
                connection = connectionFactory.newConnection();
                channel = connection.createChannel();
                channel.exchangeDeclare(channelName, BuiltinExchangeType.TOPIC);
                String queueName = channel.queueDeclare().getQueue();

                channel.queueBind(queueName, channelName, channelName);

                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                    System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");

                };
                channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
                });
            }
        }
        if (input == null || input.equals(Commands.EXIT.getValue())){
            if (channel != null)
                channel.close();
            if (connection != null)
                connection.close();
            scanner.close();
            System.exit(1);
        }
    }

    private enum Commands {
        CHANNEL("/chch "),
        LEAVE("/leave"),
        EXIT("/exit");

        private String field;

        Commands(String command){
            this.field = command;
        }

        public String getValue(){
            return field;
        }
    }
}
