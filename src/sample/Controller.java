package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import javax.swing.*;
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
    private Text stateText;

    @FXML
    private Text timeAnswer;

    @FXML
    private Button stopBtn;

    @FXML
    private Button pauseBtn;

    @FXML
    private Button nextBtn;

    int extracted_timer;
    int extracted_themes;
    private int timer;
    private Timer nt;
    private Iterator<String>iterator_themes;
    private boolean pause;


    @FXML
    public void initialize() {
        // При нажатии "СТАРТ" показываем элементы игры и запускаем таймер
        startBtn.setOnAction(e -> {
            // выносим первое число в переменную, в случае с count themes еще преобразуем в int
            extracted_timer = extractNumber(inputTimeAnswer.getText());
            extracted_themes = extractNumber(inputCountThemes.getText());

            if (extracted_timer <= 0 || extracted_themes <= 0) return; // скипаем старт если ничего небыло введено
            switchVisibleElements(); // показываем нужные и скрываем лишние элементы
            timer = extracted_timer;
            timeAnswer.setText(timerStringFormat());
            ThemesList.generateSampleThemesList(extracted_themes);
            iterator_themes = ThemesList.getSampleThemes().iterator();
            currentTheme.setText(iterator_themes.next());
            switchPauseState(false);
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
            if (pause) return; // пропускаем если стоит пауза
            timer -= 1;
            timeAnswer.setText(timerStringFormat());
            // при достижении нуля
            if (timer <= 0) goToNextTheme();
        });
        // при нажатии "ПАУЗА"
        pauseBtn.setOnAction(e -> switchPauseState(!pause));

        // при нажатии "ДАЛЬШЕ"
        nextBtn.setOnAction(e -> goToNextTheme());
    }

    private  void goToNextTheme() {
        if (iterator_themes.hasNext()) {
            currentTheme.setText(iterator_themes.next());
            timer = extracted_timer;
            timeAnswer.setText(timerStringFormat());
        } else {
            nt.stop();
            switchVisibleElements();
        }
    }

    private void switchPauseState(boolean state) {
        pause = state;
        if (pause) {
            timeAnswer.setText("");

            stateText.setText("Пауза");
            pauseBtn.setStyle("-fx-font-family: arial; -fx-font-size: 20; -fx-font-weight: bold; -fx-background-color: #FFAD00; " +
                            "-fx-border-color: #ED3939; " +
                            "-fx-border-width: 3;");

        } else {
            timeAnswer.setText(timerStringFormat());
            stateText.setText("Осталось:");
            pauseBtn.setStyle("-fx-font-family: arial; -fx-font-size: 20; -fx-font-weight: bold; -fx-background-color: #FFAD00; " +
                            "-fx-border-color: #FFAD00; " +
                            "-fx-border-width: 0;");
        }
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

    private int extractNumber(final String str) {

        if(str == null || str.isEmpty()) return -1;

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
        if (sb.toString().equals("")) return -1;
        else return Integer.parseInt(sb.toString());
    }

}
