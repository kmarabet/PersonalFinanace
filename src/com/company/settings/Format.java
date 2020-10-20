package com.company.settings;

import com.company.model.Currency;
import com.company.model.Filter;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Format {

    public static String amount(double amount){
        return String.format(Settings.FORMAT_AMOUNT, amount);
    }

    public static String amount(double amount, Currency currency){
        return amount(amount) + " " + currency.getCode();
    }

    public static String rate(double rate){
        return String.format(Settings.FORMAT_RATE, rate);
    }

    public static String rate(double rate, Currency currency){
        return rate(rate) + " " + currency.getCode();
    }

    public static String date(Date date){
        return dateFormat(date, Settings.FORMAT_DATE);
    }

    public static String dateMonth(Date date){
        return dateFormat(date, Settings.FORMAT_DATE_MONTH);
    }

    public static String dateYear(Date date){
        return dateFormat(date, Settings.FORMAT_DATE_YEAR);
    }

    public static double fromAmountToNumber(String amount){
        amount = amount.replaceAll(",",".");
        return Double.parseDouble(amount);
    }

    private static String dateFormat(Date date, String formatDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatDate, new MainDateFormatSymbols());
        return sdf.format(date);
    }

    private static String yesNo(boolean yes){
        if (yes) return Text.get("Yes");
        return Text.get("No");
    }

    public static String getTitleFilter(Filter filter){
        Date time = filter.getTo();
        switch (filter.getStep()) {
            case Filter.STEP_DAY:
                return date(time);
            case Filter.STEP_MONTH:
                return dateMonth(time);
            case Filter.STEP_YEAR:
                return dateYear(time);
        }
        return null;
    }

    private static class MainDateFormatSymbols extends DateFormatSymbols {
        @Override
        public String[] getMonths() {
//            return super.getMonths();
            return Text.getMonths();
        }
    }

}