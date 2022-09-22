package YandexMaps;



import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;


public class Hermitage extends JFrame{
    private JPanel panel;
    private JLabel label;
    private JButton button;

    public Hermitage() {
        super("Карта");
        button.addActionListener(e -> {
            try {
                /* Через параметры ll+spn задается протяженность области показа относительно центра карты.
                   Параметр ll задает долготу и широту центра карты (в градусах), а spn — ее протяженность (в градусах).
                   Параметр l определяет тип карты(map -  схема)*/
                Image img = ImageIO.read(new URL("https://static-maps.yandex.ru/1.x/?ll=30.3140793,59.9397392&&spn=0.002,0.002&l=map"));
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

        new Hermitage();
    }
}



