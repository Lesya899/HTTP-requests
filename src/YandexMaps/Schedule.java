/*
Напишите консольное приложение, которое выводит на экран график работы существующей организации, название которой ввёл пользователь.
 Если организацию найти не удалось, то выведите на экран "404 not found".
 */

package YandexMaps;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Schedule {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine().replace(" ", "+"); //при вводе нескольких слов заменяем пробел на знак +
        HttpRequest request = HttpRequest.newBuilder().uri(new URI("https://search-maps.yandex.ru/v1/?text=" + str + "&type=biz&lang=ru_RU&apikey=00000000000")).GET().build(); //создаем запрос
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()); //отправляем запрос и получаем ответ
        JSONObject json = new JSONObject(response.body()); //преобразуем ответ в объект JSON, JSONObject — это неупорядоченный набор пар ключ-значение
        JSONArray array = (JSONArray) json.get("features"); //получаем объект features и преобразуем его массив JSON
        for (Object object : array) { //проходимся по массиву
            JSONObject jsonObj = ((JSONObject) object).getJSONObject("properties").getJSONObject("CompanyMetaData"); //получаем JSON объекты
            String sched = "";
            if (jsonObj.has("Hours")) { //если JSON содержит объект Hours
                JSONObject objHours = jsonObj.getJSONObject("Hours"); //получаем объект
                sched = (String) objHours.get("text"); //извлекаем график работы из объекта text
            }
            else {
                sched = "информация отсутствует";
            }
            System.out.println("адрес: " + jsonObj.get("address") + ", график работы: " + sched);
        }
        if (array.length() == 0) {
            System.out.println("404 not found");
            }
        }
    }


