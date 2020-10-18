package com.company.settings;

import java.util.HashMap;

final public class Text {

    private static HashMap<String, String> data = new HashMap<>();

    public static String get(String key) {
        return data.get(key);
    }

    public static String[] getMonths() {
        String[] months = new String[12];
        months[0] = get("JANUARY");
        months[1] = get("FEBRUARY");
        return months;
        //return data.values().toArray();
    }

    public static  void init() {
        data.put("PROGRAM_NAME", "Домашняя бухгалтерия");
        data.put("MENU_FILE", "Файл");
        data.put("MENU_EDIT", "Правка");
        data.put("MENU_VIEW", "Вид");
        data.put("MENU_HELP", "Помощь");

        data.put("JANUARY", "Январь");
        data.put("FEBRUARY", "Февраль");
        data.put("MARCH", "Март");
        data.put("APRIL", "Апрель");
        data.put("MAY", "Май");
        data.put("JUNE", "Июнь");
        data.put("JULY", "юль");
        data.put("AUGUST", "Август");
        data.put("SEPTEMBER", "Сентябр");
        data.put("OCTOBER", "Октябр");
        data.put("NOVOMBER", "Ноябр");
        data.put("DECEMBER", "Декабр");

        data.put("ERROR_TITLE_EMPTY", "Вы не ввели название");
        //todo ...
    }


}
