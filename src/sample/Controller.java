package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

public class Controller {

    @FXML
    private AnchorPane startPane;

    @FXML
    private Button startBtn;

    @FXML
    private TextField inputTimeAnswer;

    @FXML
    private TextField inputCountThemes;

    @FXML
    private Text currentTheme;

    @FXML
    private Text messageWish;

    @FXML
    private AnchorPane gamePane;

    @FXML
    private Text timeAnswer;

    @FXML
    private HBox gameButtons;

    @FXML
    private Button stopBtn;

    @FXML
    private Button pauseBtn;

    @FXML
    private Button nextBtn;

    private ArrayList<String> themes_list;
    String extracted_string;
    private int timer;
    private Timer nt;
    private Iterator iterator_themes;


    @FXML
    public void initialize() {
        // При нажатии "СТАРТ" показываем элементы игры и запускаем таймер
        startBtn.setOnAction(e -> {
            extracted_string = extractNumber(inputTimeAnswer.getText());
            if (extracted_string.equals("")) return; // скипаем старт если ничего небыло введено
            switchVisibleElements(); // показываем нужные и скрываем лишние элементы
            timer = Integer.parseInt(extracted_string);
            timeAnswer.setText(timerStringFormat());
            iterator_themes = themes_list.iterator();
            currentTheme.setText(iterator_themes.next().toString());
            nt.start();
        });
        // при нажатии "СТОП"
        stopBtn.setOnAction(e -> {
            switchVisibleElements(); // переключаем visiblы
            iterator_themes = null;
            nt.stop(); // останавливаем таймер
        });
        // инициализация таймера, каждую секунду уменьшаем timer и изменяем text у элемента отвечающего за отображение таймера
        nt = new Timer(1000, e -> {
            timer -= 1;
            timeAnswer.setText(timerStringFormat());
            // при достижении нуля
            if (timer <= 0) {
                if (iterator_themes.hasNext()) {
                    currentTheme.setText(iterator_themes.next().toString());
                    timer = Integer.parseInt(extracted_string);
                    timeAnswer.setText(timerStringFormat());
                } else {
                    nt.stop();
                }
            }
        });

        // добавляем темы в список
        String themes_str = "июнь,лето,зима,города,волейбол,вязание,личности,океан";
        themes_list = new ArrayList<>(Arrays.asList(themes_str.split(",")));
        iterator_themes = themes_list.iterator();
    }

    private String timerStringFormat() {
        return timer + " сек";
    }

    private void switchVisibleElements() {
        startPane.setVisible(!startPane.isVisible());
        gamePane.setVisible(!gamePane.isVisible());
        messageWish.setVisible(!messageWish.isVisible());
        currentTheme.setVisible(!currentTheme.isVisible());
    }

    private String extractNumber(final String str) {

        if(str == null || str.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        boolean found = false;
        for(char c : str.toCharArray()){
            if(Character.isDigit(c)){
                sb.append(c);
                found = true;
            } else if(found){
                // If we already found a digit before and this char is not a digit, stop looping
                break;
            }
        }

        return sb.toString();
    }

}
