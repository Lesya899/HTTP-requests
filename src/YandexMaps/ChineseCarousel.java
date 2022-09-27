/*Напишите программу с использованием графического интерфейса. Ваша программа должна имитировать работу популярного
графического элемента в WEB разработке - Карусель. Найдите не менее 5 интересных месть в Китае и с помощью Яндекс.Карт
подгрузите их во время работы вашей программы. Затем выводите в главном окне вашей программы их поочерёдно с интервалом в 3 секунды.
Добавьте для пользователя кнопки "вперёд" и "назад" для того, чтобы можно было перелистывать ваши карты вручную.
 */

package YandexMaps;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public class ChineseCarousel extends JFrame {
    private JPanel panel;
    private JLabel label; //лейбл для изображения места
    private JButton buttonBack;
    private JButton buttonForward;
    private JLabel placeLabel; //лейбл для названия места
    public List<Places> interestingPlaces = new ArrayList<>();
    Timer timer;
    int index;

    public ChineseCarousel()  {
        super("Интересные места Китая");
        index = 0;

        //добавляем в список интересные места
        interestingPlaces.add(new Places("Храм Неба", "116.404305,39.880496&l=map&z=15"));
        interestingPlaces.add(new Places("Озеро Сиху", "120.142168,30.247485&l=map&z=15"));
        interestingPlaces.add(new Places("Запретный город", "116.391655,39.916416&l=map&z=16"));
        interestingPlaces.add(new Places("Набережная Вайтань", "121.485994,31.240743&l=map&z=17"));
        interestingPlaces.add(new Places("река Лицзян", "110.402312,25.163403&l=map&z=12"));

        // создаем объект Timer, указываем время задержки и слушателя действий
        timer = new Timer(3000, e -> {
            try {
                setImg(index);
                index++;
                if (index == interestingPlaces.size()) {
                    index = 0;
                }
                } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        timer.start(); //запускаем таймер

        //добавляем слушателя событий для кнопок
        buttonForward.addActionListener((e) -> {
            try {
                setImg(indexForward());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        buttonBack.addActionListener((e) -> {
            try {
                setImg(indexBack());
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

    //метод для вывода на экран изображения и названия места
    public void setImg(int index) throws IOException {
        placeLabel.setText(interestingPlaces.get(index).getNamePlace()); // из списка по индексу получаем название места и устанавливаем его для лейбла
        Image img = ImageIO.read(new URL("https://static-maps.yandex.ru/1.x/?ll=" + interestingPlaces.get(index).getCoordinates())); //из списка по индексу получаем координату и загружаем изображение
        label.setIcon(new ImageIcon(img));
    }

    //метод установки индекса для кнопки вперед
    public int indexForward(){
        if (index == interestingPlaces.size()-1) {
            index = 0;
            return index;
        }
        return index++;
    }

    //метод установки индекса для кнопки назад
    public int indexBack(){
        if (index == 0) {
            index = interestingPlaces.size() - 1;
            return index;
        }
        return index--;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new ChineseCarousel();

    }
}