import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String... args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        String host = "127.0.0.1";
        int port = 8989;

            try (Socket socket = new Socket(host, port);
                 PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                //Принимаем сообщение от сервера
                String answerFromServer = in.readLine();
                System.out.println(answerFromServer);

                //Вводим город
                String city = scanner.nextLine();
                //Отправляем серверу текущий город
                writer.println(city);

                //Принимаем ответ на наш город
                System.out.println(in.readLine());
                System.out.println(in.readLine());


            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
