/*Напишите консольное приложение, которое считывает два числа, которые пользователь вводит с клавиатуры - сначала день (от 1 до 31), а потом месяц (от 1 до 12). Затем выдайте Интересный факт про этот день на английском языке.
Используйте следующий API: http://numbersapi.com/.
Если пользователь ввёл несуществующую дату, то выведите ему ошибку. */


import java.util.Scanner;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DayInHistory {
    public static void main(String[] args) throws java.net.URISyntaxException, IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        int month = sc.nextInt(); //считываем месяц
        if (month < 1 || month > 12) {
            System.out.println("Некорректный номер месяца");
            month = sc.nextInt();
        }
        int day = sc.nextInt();
        if (month == 2) {
            if (day < 1 || day > 29) {
                System.out.println("Некорректный день месяца. Необходимо указать день от 1 до 29");
                day = sc.nextInt();
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day < 1 || day > 30) {
                System.out.println("Некорректный день месяца. Необходимо указать день от 1 до 30");
                day = sc.nextInt();
            }
        } else {
            if (day < 1 || day > 31) {
                System.out.println("Некорректный день месяца. Необходимо указать день от 1 до 31");
                day = sc.nextInt();
            }
        }
        HttpRequest request = HttpRequest.newBuilder().uri(new URI("http://numbersapi.com/" + month + "/" + day + "/date")).GET().build(); //создаем запрос
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()); //отправляем запрос и получаем ответ
        System.out.println(response.body());

        /*HttpRequest — это объект, представляющий запрос, который мы хотим отправить. Новые экземпляры можно создавать с помощью HttpRequest.Builder.
        Класс Builder предоставляет набор методов, которые можно использовать для настройки нашего запроса.
        При создании запроса указываем URL-адрес с помощью вызова метода uri(URI) для экземпляра Builder.
        Определяем HTTP-метод, для этого вызываем один из методов из Builder.
        Все запросы отправляются с помощью HttpClient , который можно создать с помощью метода HttpClient.newBuilder() или вызова HttpClient.newHttpClient() .
         */
    }
}



