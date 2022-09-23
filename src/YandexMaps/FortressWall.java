/*Напишите программу с использованием графического интерфейса, которая отобразит на карте Крепостную смоленскую стену.
 Добавьте метку на любые 5 башен на ваш вкус*/

package YandexMaps;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;


public class FortressWall extends JFrame{
    private JPanel panel;
    private JLabel label;
    private JButton button;


    public FortressWall()  {
        super("Смоленская крепостная стена");
        button.addActionListener((e) -> {
            try {
                Image img = ImageIO.read(new URL("https://static-maps.yandex.ru/1.x/?ll=32.041296,54.781810&&spn=0.003,0.003&l=map&" +
                        "pt=32.041901,54.780564,pmvvm1~32.040087,54.781271,pmb~32.043756,54.780070,pm2dgm3~32.045687,54.779619,flag~32.041665,54.780866,pm2blywm5"));
                this.label.setIcon(new ImageIcon(img));
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
        new FortressWall();
    }
}


