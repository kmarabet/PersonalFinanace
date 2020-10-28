package com.company;

import com.company.exception.ModelException;
import com.company.model.*;
import com.company.saveload.SaveData;
import com.company.settings.Format;
import com.company.settings.Settings;
import com.company.settings.Text;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonalFinance {

    public static void main(String[] args) throws Exception {
        init();

//        testModel();

        SaveData sd = SaveData.getInstance();
        sd.updateCurrencies();
        sd.save();
        System.out.println(sd.getCurrencies());

//        Currency c1 = new Currency("Рубль","RUB", 1, true, false);
//        Currency c5 = new Currency("Бел.Руб.","BYN", 1, true, true);
//        HashMap<String, Double>  rubRates = RateCurrency.getRates(c1);
//        System.out.println(rubRates);
//        HashMap<String, Double>  belrubRates = RateCurrency.getRates(c5);
//        System.out.println(belrubRates);

//        testModel();

        // write your code here
        System.out.println(Format.dateMonth(new Date()));
//        System.out.println(Text.get("PROGRAM_NAME"));
        System.out.println(Arrays.toString(Text.getMonths()));
    }

    private static void testModel() throws ModelException {

        Currency c1 = new Currency("Рубль","RUB", 1, true, false);
        Currency c2 = new Currency("Доллар","USD", 2.5, true, false);
        Currency c3 = new Currency("Евро","EUR", 3, true, false);
        Currency c4 = new Currency("Гривна","UAH", 1, false, false);
        Currency c5 = new Currency("Бел.Руб.","BYN", 1, true, true);

        Account ac1 = new Account("Кошелёк", c5, 1000);
        Account ac2 = new Account("Карта Visa", c2, 1000);
        Account ac3 = new Account("Карта MasterCard", c3, 1000);
        Account ac4 = new Account("Депозит в банке (ХАРАМ) RUB", c1, 1000);
        Account ac5 = new Account("Депозит в банке (ХАРАМ) USD", c2, 1000);

        Article art1 = new Article("Продукты");
        Article art2 = new Article("ЖКХ");
        Article art3 = new Article("Зарплата");
        Article art4 = new Article("Столовая");
        Article art5= new Article("Проыенты по депозиту (ХАРАМ)");

        ArrayList<Currency> currencies = new ArrayList<>();
        currencies.add(c1);
        currencies.add(c2);
        currencies.add(c3);
        currencies.add(c4);
        currencies.add(c5);

        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(ac1);
        accounts.add(ac2);
        accounts.add(ac3);
        accounts.add(ac4);
        accounts.add(ac5);

        ArrayList<Article> articles = new ArrayList<>();
        articles.add(art1);
        articles.add(art2);
        articles.add(art3);
        articles.add(art4);
        articles.add(art5);

        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(ac2, art3, 30000));
        transactions.add(new Transaction(ac2, art1, -1500, "На выходные"));
        transactions.add(new Transaction(ac1, art2, -5500, "Основная квартира"));
        transactions.add(new Transaction(ac1, art2, -4000, "Вторая квартира"));
        transactions.add(new Transaction(ac3, art5, 1000));
        transactions.add(new Transaction(ac2, art3, 25000, new Date((new Date()).getTime() - (long) 86400000 * 30)));//One month ago
        transactions.add(new Transaction(ac3, art3, 1000, new Date((new Date()).getTime() - (long) 86400000 * 30)));//One month ago

        for (int i = 0; i < 50; i++) {
            Article tempArticle;
            Account tempAccount;
            if (Math.random() < 0.5) tempArticle = art1;
            else tempArticle = art4;
            if (Math.random() < 0.5) tempAccount = ac1;
            else tempAccount = ac2;
            double tempAmount = Math.round(Math.random() * (-1000));//-10000 - 0
            Date tempDate = new Date((long) (new Date().getTime() - (long) 86400000 * 30 * Math.random()));
            transactions.add(new Transaction(tempAccount, tempArticle, tempAmount, tempDate));
        }

        ArrayList<Transfer> transfers = new ArrayList<>();
        transfers.add(new Transfer(ac2, ac1, 25000, 25000));
        transfers.add(new Transfer(ac2, ac3, 3000, 3000));
        transfers.add(new Transfer(ac2, ac4, 6000, 90));

        for (Account a : accounts){
            a.setAmountFromTransactionsAndTransfers(transactions, transfers);
        }

        SaveData sd = SaveData.getInstance();
        sd.setArticles(articles);
        sd.setAccounts(accounts);
        sd.setCurrencies(currencies);
        sd.setTransactions(transactions);
        sd.setTransfers(transfers);
        sd.save();
//        sd.load();
        System.out.println(sd);

    }

    private static void init() {
        try {
            Settings.init();
//            Settings.save();
            Text.init();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Consolas.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, Settings.FONT_CONSOLAS));
        } catch (FontFormatException | IOException e) {
            Logger.getLogger(PersonalFinance.class.getName()).log(Level.SEVERE, null, e);
//            e.printStackTrace();
        }
    }
}
