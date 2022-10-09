/* Разработайте большой проект с использованием графического интерфейса и API Яндекс Карт.
В правой части проекта вы должны отображать карту, под который должен находится ползунок для масштабирования,
радиокнопки для выбора режима схема или спутник, и чекбокс для отображения слоя пробок. В левой части приложения разместите:
-текстовое поле для ввода запроса и кнопку для поиска;
-список, в который будут выводиться результаты поиска;
-текстовое поле, в котором будет хранится более детальная информация об объекте.
Логика проекта: при вводе запроса в поле поиска и после нажатия на кнопку поиск вы ищете географические объекты или организации.
В список вы отображаете все найденные результаты с обязательной пометкой "организация", если это организация. При клике на элемент
списка в окне детальная информация отображается максимально детальная информация об этом объекте - всё, что сможете достать. Учтите,
что для организация и топонимов вывод будет различный. Также, после щелчка по элементу списка справа должна загружаться картинка,
отображающая этот объект. Ползунок, радиокнопки и чек-бокс пробки должны работать соответственно очевидной логике.  */


package YandexMaps;

import org.json.JSONArray;
import org.json.JSONObject;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


public class BigProject extends JFrame {
    private JPanel panel;
    private JTextField textField;
    private JButton buttonSearch; //кнопка поиска
    private JLabel labelMap; // лейбл для вывода изображения карты
    private JSlider slider;
    private JTextArea detailInfoTextArea; //поле для вывода дополнительной информации
    private JRadioButton radioButtonScheme; //кнопка для выбора типа карты "Схема"
    private JCheckBox checkBoxTrafficJams; //чек-бокс для выбора "пробки"
    private JList jlistObj; //поле для вывода списка найденных географических объектов
    private JRadioButton radioButtonSatellite; //кнопка для выбора типа карты "Спутник"
    private ButtonGroup buttonGroup;
    String text;
    String coordinates; //переменная для координат
    private JSONArray array;

    private List<String> listGeographicalObjects; //список для названий географических объектов
    private final DefaultListModel<String> model = new DefaultListModel(); //модель, которая будет содержать элементы списка
    private List<String> listDetailInfo;//список для дополнительной информации
    private List<String> listCoordinates; //список для координат
    private String l = "&l=map";
    private String p = "";


    public BigProject() {
        super("Карта");
        setPreferredSize(new Dimension(1200, 700));
        getContentPane().add(panel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);


        //поиск географических объектов
        buttonSearch.addActionListener(e -> {
            text = textField.getText().replace(" ", "+");
            try {
                HttpRequest request = HttpRequest.newBuilder().uri(new URI("https://search-maps.yandex.ru/v1/?text=" + text + "&lang=ru_RU&apikey=65851351-e6d0-4346-89b9-7a8a61c441b1")).GET().build();
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()); //отправляем запрос и получаем ответ
                JSONObject json = new JSONObject(response.body()); //преобразуем ответ в объект JSON, JSONObject — это неупорядоченный набор пар ключ-значение
                array = (JSONArray) json.get("features"); //получаем объект features и преобразуем его массив JSON
                listGeographicalObjects = new ArrayList<>();
                if (array.length() == 0) {
                    listGeographicalObjects.add("404 not found");
                } else {
                    for (Object object : array) { //проходимся по массиву
                        JSONObject jsonObj = ((JSONObject) object).getJSONObject("properties");//получаем объект
                        if (jsonObj.has("CompanyMetaData")) { //если географический объект - организация
                            listGeographicalObjects.add(jsonObj.get("name") + " - организация");
                        } else if (jsonObj.has("GeocoderMetaData")){ //если географический объект - топоним
                            listGeographicalObjects.add(jsonObj.getString("name"));
                        }
                    }
                }
            } catch (URISyntaxException | IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            model.clear(); // очищаем модель
            jlistObj.setModel(model); //устанавливаем для Jlist модель
            for (int i = 0; i < listGeographicalObjects.size(); i++) { //проходимся по списку найденных географических объектов
                model.addElement(listGeographicalObjects.get(i)); //и добавляем их в модель
            }
        });

        //добавляем прослушиватель в список, чтобы получать уведомления каждый раз, когда происходит изменение выбора
        jlistObj.addListSelectionListener(e -> {
            detailInfoTextArea.setText("");
            if (jlistObj.getSelectedIndex() >= 0) { // если выбран элемент в списке jlistObj ( getSelectedIndex()возвращает значение -1, если элемент не выбран)
                int index = jlistObj.getSelectedIndex(); //получаем индекс выбранного элемента из списка jlistObj
                listCoordinates = new ArrayList<>();
                for (Object obj : array) { //проходимся по массиву JSON, получаем объекты и добавляем координаты в список
                    JSONArray arrayCoordinates = ((JSONObject) obj).getJSONObject("geometry").getJSONArray("coordinates");
                    listCoordinates.add(arrayCoordinates.get(0) + "," + arrayCoordinates.get(1));
                }
                coordinates = listCoordinates.get(index); //по индексу выбранного элемента  получаем координаты из списка координат
                detailsInfo(); //добавляем доп. информацию о географическом объекте в список
                detailInfoTextArea.setText(listDetailInfo.get(index)); //для выбранного элемента  выводим  доп. информацию
                try {
                    Image image = ImageIO.read(new URL("https://static-maps.yandex.ru/1.x/?ll=" + coordinates + "&pt=" + coordinates + ",pma&z=17" + l + p));
                    labelMap.setIcon(new ImageIcon(image));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //добавляем слушателя для слайдера
        slider.addChangeListener(e -> {
            try {
                Image image = ImageIO.read(new URL("https://static-maps.yandex.ru/1.x/?ll=" + coordinates + "&pt=" + coordinates + ",pma&z=" + slider.getValue() + l + p));
                labelMap.setIcon(new ImageIcon(image));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        //добавляем слушателя событий для кнопок выбора типа карты
        radioButtonScheme.addChangeListener(e -> {
            l = buttonGroup.getSelection().getActionCommand(); //получаем какая кнопка из группы выбрана и для нее команду действия
            try {
                    Image image = ImageIO.read(new URL("https://static-maps.yandex.ru/1.x/?ll=" + coordinates + "&pt=" + coordinates + ",pma&z=17" + l + p));
                    labelMap.setIcon(new ImageIcon(image));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
        });

        //добавляем чек-бокс для пробок
        checkBoxTrafficJams.addChangeListener(e -> {
            if (checkBoxTrafficJams.isSelected()) {
                p = ",trf";
            }else {
                p = "";
            }
            try {
                Image image = ImageIO.read(new URL("https://static-maps.yandex.ru/1.x/?ll=" + coordinates + "&pt=" + coordinates + ",pma&z=17" + l + p));
                labelMap.setIcon(new ImageIcon(image));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });
    }

    //метод добавляет дополнительную информацию об организации в список
    public void detailsInfo() {
        listDetailInfo = new ArrayList<>();
        String info = "";
        if (array.length() != 0) { //если массив JSON не пустой
            for (Object obj : array) { //проходимся по массиву
                JSONObject jsonObj = ((JSONObject) obj).getJSONObject("properties");//получаем объект "properties"
                if (jsonObj.has("CompanyMetaData")) { //если jsonObj содержит объект "CompanyMetaData"
                    JSONObject company = jsonObj.getJSONObject("CompanyMetaData");
                    info = listCoordinates.get(jlistObj.getSelectedIndex()).replace(",", ", ") + "\n";
                    info += company.getString("address") + "\n";
                    if (company.has("Phones")) {
                        JSONArray arr = (JSONArray) company.get("Phones"); // получаем массив элементов для поля Phones
                        JSONObject ph = (JSONObject) arr.get(0); // достаем из массива первый элемент
                        info += ph.get("formatted") + "\n";
                    } else {
                        info += "телефон отсутствует" + "\n";
                    }
                    if (company.has("Hours")) { //если JSON содержит объект Hours
                        JSONObject objHours = company.getJSONObject("Hours"); //получаем объект
                        info += objHours.get("text") + "\n"; //извлекаем график работы из объекта text
                    } else {
                        info += "информация о графике работы отсутствует";
                    }
                    listDetailInfo.add(info);
                } else { //если географический объект топоним
                    JSONObject geoMetaData = jsonObj.getJSONObject("GeocoderMetaData");
                    info = listCoordinates.get(jlistObj.getSelectedIndex()).replace(",", ", ") + "\n";
                    info +=  geoMetaData.get("text");
                    }
                    listDetailInfo.add(info);
            }
        }
    }

    public static void main(String[] args) {
        new BigProject();
    }
}
