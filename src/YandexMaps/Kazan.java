/*Напишите программу с использованием графического интерфейса. Ваша программа должна уметь масштабировать изображение Казани.
 Большую часть вашего окна должно занимать само изображение карты с центром в городе Казань. Ниже вашего изображения должен находиться
 горизонтальный слайдер, который позволил бы вам менять уровень приближения карты. */

package YandexMaps;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class Kazan extends JFrame{
    private JPanel panel;
    private JLabel label;
    private JSlider slider;


    public Kazan() {
        super("Казань");

        slider.addChangeListener(e -> {
            try {
                /* Через параметры ll+spn задается протяженность области показа относительно центра карты.
                   Параметр ll задает долготу и широту центра карты (в градусах), а z — уровень масштабирования карты(0-17).
                   Параметр l определяет тип карты(map -  схема)*/
                Image img = ImageIO.read(new URL("https://static-maps.yandex.ru/1.x/?ll=49.106414,55.796127&l=map&" +
                        "pt=49.106414,55.796127,pma&z=" + slider.getValue()));
                label.setIcon(new ImageIcon(img));

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        setPreferredSize(new Dimension(900, 700));
        getContentPane().add(panel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public static void main(String[] args) {
        new Kazan();
    }
}