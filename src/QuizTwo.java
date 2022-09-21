//Добавьте вашей викторине графический интерфейс на ваш вкус.

import java.awt.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.*;
import javax.swing.*;


public class QuizTwo extends JFrame{
    public JPanel panel = new JPanel();  //создаем панель содержимого
    public  JTextArea textQ = new JTextArea(); //создаем поле для вывода вопросов
    public ArrayList<String> answer = new ArrayList<>(); //создадим списки для добавления вопросов и ответов из JSON
    public ArrayList<String> question = new ArrayList<>();
    public JTextField field = new JTextField(); // создаем поле для ввода ответа
    public int count = 0; //поле для количества правильных ответов
    public JButton button = new JButton("Ответ");
    public int num = 0; //поле для количества вопросов

      public QuizTwo ()  throws IOException, URISyntaxException, InterruptedException {
          super("Quiz");
          int n = 8;
          HttpRequest request = HttpRequest.newBuilder().uri(new URI("https://jservice.io/api/random?count=" + n)).GET().build(); //создаем запрос
          HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()); //отправляем запрос и получаем ответ
          String json = response.body();
          JSONArray array = new JSONArray(json); //из полученных вопросов создаем массив JSON
          for (int i = 0; i < 8; i++) { //проходимся по массиву
              JSONObject obj = array.getJSONObject(i); //получаем вопрос и ответ
              answer.add((String) obj.get("answer")); //добавляем вопросы и ответы в списки
              question.add((String) obj.get("question"));
          }
          textQ.setText(question.get(num)); //выводим первый вопрос
          button.addActionListener(e -> { //добавляем слушателя событий
              nextQuestion();
          });

          //устанавливаем размеры для компонентов
          textQ.setPreferredSize(new Dimension(500, 50));
          field.setPreferredSize(new Dimension(200, 30));
          button.setPreferredSize(new Dimension(100, 40));

          //указываем шрифт, стиль и размер текста для поля с вопросом
          textQ.setFont(new Font("Arial", 0, 14));

          //задаем перенос строк для поля с вопросом
          textQ.setLineWrap(true);

          //указываем менеджера расположения компонентов и добавляем компоненты на панель
          panel.setLayout(new FlowLayout(FlowLayout.CENTER));
          panel.setBackground(Color.orange);
          panel.add(textQ);
          panel.add(field);
          panel.add(button);
          getContentPane().add(panel);
          setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
          setSize(600, 400);
          setVisible(true);

      }
      //метод для отображения следующих вопросов
    public  void nextQuestion() {
        String ans = field.getText(); //получаем введенный ответ
        if (ans.equals(answer.get(num))) { //если введенный ответ равен правильному ответу
            count++; //увеличиваем счетчик
        }
        field.setText(""); //делаем поле ввода ответа пустым
        textQ.setText(question.get(num + 1)); //выводим следующий вопрос
        num++; //увеличиваем счетчик для вопросов
        if (num == 7) {
            JOptionPane.showMessageDialog(null, "Верно ответили  на " + count + " вопросов");
        }
      }

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
          new QuizTwo();

    }
}



