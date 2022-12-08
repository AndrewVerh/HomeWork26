import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String... args) throws IOException {
        int port = 8989;

        //Предопределяем первый ответ серверу, которого пока нет
        String city = " ";

        //Реализуем сервер
        try (ServerSocket server = new ServerSocket(port);) { // стартуем сервер один(!) раз
            System.out.println("Добро пожаловать на игровой сервер «ГОРОДА»");
            System.out.println();
            System.out.println("Давайте напомним её правила. Игроки по очереди называют города,");
            System.out.println("главное правило - следующий город должен начинаться на ту же букву,");
            System.out.println("на которую заканчивался последний названный город.");
            System.out.println();

            while (true) { // в цикле(!) принимаем подключения
                try (Socket client = server.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                     PrintWriter out = new PrintWriter(client.getOutputStream(), true))
                {
                    System.out.println("Подключен клиент " + client.getPort());

                    //Отправка сообщения от сервера клиенту в зависимости от значения ответа от сервера:
                    // "???" если подключился первый клиент
                    //"город" если подключились последующие клиенты
                    out.println(city.equals(" ") ? "???" : city);

                    //Ожидание ответа от клиента
                    String answerFromClient = in.readLine();
                    System.out.println(answerFromClient);

                    //Проверка условия игры «ГОРОДА»:
                    //Если первый символ текущего города совпадает с последним символом предыдущего города, то
                    //отправляем "ОК", иначе - "НЕ ОК"
                    if (city.equals(" ") || answerFromClient.charAt(0) == city.charAt(city.length()-1)) {
                        out.println("OK");
                        out.println("Следующий игрок");
                        city = answerFromClient;
                    } else {
                        out.println("NOT OK");
                        out.println("Город должен начинаться с последней буквы предыдущего города");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}