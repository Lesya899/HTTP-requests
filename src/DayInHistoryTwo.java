//Добавьте графический интерфейс вашему проекту. Используйте в разработке JDataPicker


import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javax.swing.*;
import java.awt.*;


public class DayInHistoryTwo {
    public static void main(String[] args) {
        //создаем окно
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        //создаем панель содержимого
        Container container = frame.getContentPane();
        container.setLayout(new FlowLayout(FlowLayout.CENTER)); //менеджер последовательного расположения  с выравниванием компонентов по центру
        container.setBackground(Color.pink.brighter());
        // Создаем модель даты
        UtilDateModel model = new UtilDateModel(); //средство выбора даты, вернет выбранную дату как объект типа java.util.Date
        //создаем панель даты
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        //создаем компонент даты
        JDatePickerImpl picker = new JDatePickerImpl(datePanel);
        picker.setTextEditable(true);
        picker.setShowYearButtons(true);
        //Создаем поле для вывода информации
        JTextArea text = new JTextArea();
        text.setLineWrap(true); //задаем перенос строк
        //создаем кнопку
        JButton button = new JButton("Получить факт");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedMonth =  picker.getModel().getMonth(); //из выбранной даты получаем месяц
                int selectedDay = picker.getModel().getDay(); //из выбранной даты получаем день
                try {
                    HttpRequest request = HttpRequest.newBuilder().uri(new URI("http://numbersapi.com/" + selectedMonth + "/" + selectedDay + "/date")).GET().build();//создаем запрос
                    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()); //отправляем запрос и получаем ответ
                    text.setText(response.body());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException | URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        container.add(picker);
        container.add(button);
        container.add(text);
        frame.setVisible(true);
    }
}
