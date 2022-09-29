/*Напишите консольное приложение, которое выводит на экран точный адрес любого ЗДАНИЯ, которое вводит пользователь
 (например: Кремль, Лувр, Большой Театр). Если здание найти не удалось, то выведите на экран "404 not found".
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

public class ExactAddress {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine().replace(" ", "+"); //при вводе нескольких слов заменяем пробел на знак +
        HttpRequest request = HttpRequest.newBuilder().uri(new URI("https://search-maps.yandex.ru/v1/?text=" + str + "&type=biz&lang=ru_RU&apikey=00000000000")).GET().build(); //создаем запрос
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()); //отправляем запрос и получаем ответ
        JSONObject json = new JSONObject(response.body()); //преобразуем ответ в объект JSON, JSONObject — это неупорядоченный набор пар ключ-значение
        JSONArray array = (JSONArray) json.get("features"); //получаем объект features и преобразуем его массив JSON
        for (Object object : array) { //проходимся по массиву
            JSONObject jsonObj = ((JSONObject) object).getJSONObject("properties").getJSONObject("CompanyMetaData"); //получаем JSON объекты
            System.out.println(jsonObj.get("address")); //извлекаем адресс
        }
        if (array.length() == 0) {
            System.out.println("404 not found");
        }
    }
}



/* ниже приведен ответ на запрос в формате JSON
{
  "type": "FeatureCollection",
  "properties": {
    "ResponseMetaData": {
      "SearchResponse": {
        "found": 1,
        "display": "multiple",
        "boundedBy": [
          [
            37.61753331,
            55.75054549
          ],
          [
            37.62022469,
            55.75230649
          ]
        ]
      },
      "SearchRequest": {
        "request": "кремль",
        "skip": 0,
        "results": 10,
        "boundedBy": [
          [
            37.048427,
            55.43644866
          ],
          [
            38.175903,
            56.04690174
          ]
        ]
      }
    }
  },
  "features": [
    {
      "type": "Feature",
      "geometry": {
        "type": "Point",
        "coordinates": [
          37.618879,
          55.751426
        ]
      },
      "properties": {
        "name": "Московский Кремль",
        "description": "Россия, Москва, Ивановская площадь",
        "boundedBy": [
          [
            37.617653,
            55.750717
          ],
          [
            37.619989,
            55.752278
          ]
        ],
        "CompanyMetaData": {
          "id": "1023322799",
          "name": "Московский Кремль",
          "address": "Россия, Москва, Ивановская площадь",
          "url": "https://www.kreml.ru/",
          "Phones": [
            {
              "type": "phone",
              "formatted": "+7 (495) 695-41-46"
            },
            {
              "type": "phone",
              "formatted": "+7 (495) 697-03-49"
            }
          ],
          "Categories": [
            {
              "class": "museum",
              "name": "Музей"
            },
            {
              "class": "landmark",
              "name": "Достопримечательность"
            }
          ],
          "Hours": {
            "text": "пн,вт,ср,пт,сб,вс 09:30–18:00",
            "Availabilities": [
              {
                "Intervals": [
                  {
                    "from": "09:30:00",
                    "to": "18:00:00"
                  }
                ],
                "Monday": true,
                "Tuesday": true,
                "Wednesday": true,
                "Friday": true,
                "Saturday": true,
                "Sunday": true
              }
            ]
          }
        }
      }
    }
  ]
}
 */






