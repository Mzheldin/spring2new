package producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import static java.lang.System.in;

public class Client {

    private static Scanner scanner = new Scanner(in);
    private static ConnectionFactory connectionFactory = new ConnectionFactory();

    public static void main(String[] args) {
    connectionFactory.setHost("localhost");
    while (true)
        initReading();
    }

    private static void initReading(){
        String input = null;
        while (scanner.hasNext()){
            input = scanner.nextLine();
            if (input.startsWith(Commands.CHANNEL.getValue())
                    || input.equals(Commands.EXIT.getValue()))
                break;
        }
        if (input == null || input.equals(Commands.EXIT.getValue())){
            scanner.close();
            System.exit(1);
        }
        if (input.startsWith(Commands.CHANNEL.getValue()))
            openChannel(input.substring(6));
    }

    private static void openChannel(String channelName){
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(channelName, BuiltinExchangeType.TOPIC);
            while (scanner.hasNext()){
                String input = scanner.nextLine();
                if (input.equals(Commands.LEAVE.getValue()) || input.equals(Commands.EXIT.getValue())){
                    channel.close();
                    connection.close();
                    break;
                }
                channel.basicPublish(channelName, channelName, null, input.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + channelName + "':'" + input + "'");
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    private enum Channels {
        CHANNEL_1("Channel_1"),
        CHANNEL_2("Channel_2"),
        CHANNEL_3("Channel_3");

        private String field;

        Channels(String channelName){
            this.field = channelName;
        }

        public String getField(){
            return field;
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
