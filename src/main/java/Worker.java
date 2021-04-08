import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Worker {
    public static void main(String[] args) throws Exception{
        ConnectionFactory connectionFactory = new ConnectionFactory();
        try (
                Connection connection = connectionFactory.newConnection();
                Channel canal = connection.createChannel();
        ) {
            String mensagem = String.join ("", args);
            mensagem += " Adriano Ney Nascimento do Amaral ";
            String NOME_FILA = "Adriano2";

            boolean duravel = true;
            //(queue, passive, durable, exclusive, autoDelete, arguments)
            canal.queueDeclare(NOME_FILA, duravel, false, false, null);

            //canal.basicPublish ("", "task_queue", MessageProperties.PERSISTENT_TEXT_PLAIN, mensagem.getBytes());
            // ​(exchange, routingKey, mandatory, immediate, props, byte[] body)
            canal.basicPublish ("", NOME_FILA, MessageProperties.PERSISTENT_TEXT_PLAIN, mensagem.getBytes ());
            canal.basicPublish ("", NOME_FILA, MessageProperties.PERSISTENT_TEXT_PLAIN, "Não".getBytes ());
            canal.basicPublish ("", NOME_FILA, MessageProperties.PERSISTENT_TEXT_PLAIN, "é".getBytes ());
            canal.basicPublish ("", NOME_FILA, MessageProperties.PERSISTENT_TEXT_PLAIN, "que".getBytes ());
            canal.basicPublish ("", NOME_FILA, MessageProperties.PERSISTENT_TEXT_PLAIN, "funciona".getBytes ());

            System.out.println ("[x] Enviado '" + mensagem + "'");
        }

    }
}
