//Напишите программу с графическим интерфейсом, которая позволила бы вам загружать случайное изображение котика, собачки или лисички.
// Для загрузки каждого из изображений есть отдельная кнопка.

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.*;
import javax.imageio.ImageIO;
import java.net.URL;

public class ImageUp extends JFrame {
    private JPanel panel;
    private JButton buttonCat;
    private JButton buttonFox;
    private JButton buttonDog;
    private JLabel label;

    public  ImageUp () {
        buttonCat.addActionListener(e -> {
            try {
                HttpRequest request = HttpRequest.newBuilder().uri(new URI("https://cataas.com/cat?json=true")).GET().build(); //создаем запрос
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()); //отправляем запрос и получаем ответ
                JSONObject obj = new JSONObject(response.body()); //преобразуем ответ в объект Json
                String newUrlCat = (String) obj.get("url"); //получаем URL
                Image img = ImageIO.read(new URL("https://cataas.com" + newUrlCat)).getScaledInstance(600, 600, Image.SCALE_DEFAULT); //getScaledInstance создает масштабированную версию  изображения
                label.setIcon(new ImageIcon(img));

            } catch (IOException | URISyntaxException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        buttonDog.addActionListener(e -> {
            try {
                HttpRequest request = HttpRequest.newBuilder().uri(new URI("https://dog.ceo/api/breeds/image/random")).GET().build();
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                JSONObject obj = new JSONObject(response.body());
                String newUrlDog = (String) obj.get("message");
                Image img = ImageIO.read(new URL(newUrlDog)).getScaledInstance(600, 600, Image.SCALE_DEFAULT);
                label.setIcon(new ImageIcon(img));

            } catch (IOException | URISyntaxException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        buttonFox.addActionListener(e -> {
            try {
                HttpRequest request = HttpRequest.newBuilder().uri(new URI("https://randomfox.ca/floof/")).GET().build();
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                JSONObject obj = new JSONObject(response.body());
                String newUrlFox = (String) obj.get("image");
                Image img = ImageIO.read(new URL(newUrlFox)).getScaledInstance(600, 600, Image.SCALE_DEFAULT);
                label.setIcon(new ImageIcon(img));

            } catch (IOException | URISyntaxException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        setPreferredSize(new Dimension(900,700));
        getContentPane().add(panel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public static void main(String[] args)  {
        new ImageUp();
    }
}




