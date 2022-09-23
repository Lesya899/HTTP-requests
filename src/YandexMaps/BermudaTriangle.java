//Напишите программу с использованием графического интерфейса, которая отобразит на карте Бермудский треугольник.

package YandexMaps;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class BermudaTriangle extends JFrame{
    private JPanel panel;
    private JLabel label;
    private JButton button;

    public BermudaTriangle()  {
        super("Бермудский треугольник");
        button.addActionListener((e) -> {
            try {
                //для отображения Бермудского треугольника используем многоугольник с заливкой: c:{цвет линии},f:{цвет заливки},w:{толщина},{вершины}
                Image img = ImageIO.read(new URL("https://static-maps.yandex.ru/1.x/?l=map&pl=c:cd5b45,f:2222DDC0,w:5,-80.290556,25.793333," +
                        "-66.057884,18.415753,-64.786408,32.293660,-80.290556,25.793333"));
                this.label.setIcon(new ImageIcon(img));
            } catch (IOException exp) {
                throw new RuntimeException(exp);
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
        new BermudaTriangle();
    }
}
