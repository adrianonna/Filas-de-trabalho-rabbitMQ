import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class NewTask2 {
    public static void main(String[] args) throws Exception{
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection conexao = connectionFactory.newConnection();
        Channel canal = conexao.createChannel();

        String NOME_FILA = "Adriano2" + "";
        canal.queueDeclare(NOME_FILA, false, false, false, null);
        System.out.println ("[*] Aguardando mensagens. Para sair, pressione CTRL + C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String mensagem = new String (delivery.getBody (), "UTF-8");
            System.out.println ("[x] Recebido '" + mensagem + "'");

            int prefetchCount = 1;
            canal.basicQos(prefetchCount);

            try {
                doWork (mensagem);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println ("[x] Feito");
                canal.basicAck(delivery.getEnvelope(). getDeliveryTag(), false);
            }
        };

        boolean autoAck = false; // ack é feito aqui. Como está autoAck, enviará automaticamente
        canal.basicConsume (NOME_FILA, autoAck, deliverCallback, consumerTag -> {
            System.out.println("Cancelaram a fila: " + NOME_FILA);
        });
    }

    private static void doWork (String task) throws InterruptedException {
        for (char ch: task.toCharArray ()) {
            if (ch == '.') Thread.sleep (1000);
        }
    }

}
