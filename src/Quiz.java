/* На основе API http://jservice.io создайте консольную викторину на 8 вопросов.
 Пользователю задаётся вопрос и предлагается ввести ответ. После того, как пользователь ответит на все вопросы ему сообщается, на сколько вопросов он ответил верно.
 */

import java.net.URISyntaxException;
import java.util.Scanner;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.*;


public class Quiz {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        int n = 8;
        int count = 0; //переменная для количества правильных ответов
        HttpRequest request = HttpRequest.newBuilder().uri(new URI("https://jservice.io/api/random?count=" + n)).GET().build(); //создаем запрос
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()); //отправляем запрос и получаем ответ
        String json = response.body();
        JSONArray array = new JSONArray(json); //из полученных вопросов создаем массив JSON
        for (int i = 0; i < n; i++) { //проходимся по массиву
            JSONObject obj = array.getJSONObject(i); //получаем вопрос и ответ
            String answer = (String) obj.get("answer"); //преобразуем в строку
            String question = (String) obj.get("question");
            System.out.println(question + "?"); //выводим на печать вопрос
            String ans = sc.nextLine(); //предлагаем ввести ответ
            if (ans.equals(answer)) { //если введенный ответ равен правильному ответу
                count++; //увеличиваем счетчик
            }
        }
        System.out.println("Верно ответили  на " + count + " вопросов");
    }
}



