package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ThemesList implements Iterable<String> {
    private ArrayList<String> list = new ArrayList<>(Arrays.asList(
            "июнь", "лето", "растение", "одуванчик", "авто", "программирование", "склад ума",
            "океан", "спорт", "медали", "чемпионат", "слуги", "преграды", "история",
            "песни", "анекдот", "руды", "преподаватель", "язык", "планка", "танкер"
    ));
    private ArrayList<String> sample_themes;
    private int count_themes;

//    public ThemesList(int size_sample) {
//        this.size_sample = size_sample;
//    }

    public void generateSampleThemesList(int count_themes) {
        if (count_themes > list.size()) count_themes = list.size();
        this.count_themes = count_themes;
        int i = 0;
        while (i < count_themes) {
            String word = list.get((int) (Math.random() * list.size()) + 1);
            if (!sample_themes.contains(word)) sample_themes.add(word);
            i++;
        }
    }

    @Override
    public Iterator<String> iterator() {
        return sample_themes.iterator();
    }
}
