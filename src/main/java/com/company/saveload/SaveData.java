package com.company.saveload;

import com.company.exception.ModelException;
import com.company.model.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class SaveData {

    private static SaveData instance;

    private List<Article> articles = new ArrayList<>();
    private List<Currency> currencies = new ArrayList<>();
    private List<Account> accounts = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();
    private List<Transfer> transfers = new ArrayList<>();
    
    private final Filter filter;
    private Common oldCommon;
    private boolean isSaved = true;

    private SaveData() {
        load();
        this.filter = new Filter();
    }

    public void load() {
        SaveLoad.load(this);
        sort();
    }

    private void sort() {
        this.articles.sort((o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle()));
        this.accounts.sort((o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle()));
        this.transactions.sort((o1, o2) -> (o2.getDate().compareTo(o1.getDate())));
        this.transfers.sort((o1, o2) -> (o2.getDate().compareTo(o1.getDate())));
        this.currencies.sort((o1, o2) -> {
            if (o1.isBase()) return -1;
            if (o2.isBase()) return 1;
            if (o1.isOn() ^ o2.isOn()){
                if (o2.isOn()) return 1;
                else return -1;
            }
            return o1.getTitle().compareToIgnoreCase(o2.getTitle());
        });
    }

    public void save() {
        SaveLoad.save(this);
        isSaved = true;
    }

    public static SaveData getInstance(){
        if (instance == null) instance = new SaveData();
        return instance;
    }
    
    public Filter getFilter(){
        return filter;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Transfer> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<Transfer> transfers) {
        this.transfers = transfers;
    }

    public Currency getBaseCurrency() {
        for (Currency c: currencies) {
            if (c.isBase()) return c;
        }
        return new Currency();
    }

    public ArrayList<Currency> getEnableCurrencies(){
        ArrayList<Currency> list = new ArrayList<>();
        for (Currency c: currencies){
            if (c.isOn()) list.add(c);
        }
        return list;
    }

    public List<Transaction> getFilterTransactions() {
        ArrayList<Transaction> list = new ArrayList<>();
        for (Transaction t: transactions)
            if (filter.check(t.getDate())) list.add(t);
        return list;
    }

    public List<Transfer> getFilterTransfers() {
        ArrayList<Transfer> list = new ArrayList<>();
        for (Transfer t: transfers)
            if (filter.check(t.getDate())) list.add(t);
        return list;
    }

    public List<Transaction> getFilterTransactionsOnCount(int count) {
        return new ArrayList(transactions.subList(0, Math.min(count, transactions.size())));
    }

    public Common getOldCommon() {
        return oldCommon;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void add(Common c) throws ModelException {
        List ref = getRef(c);
        if (ref.contains(c)) throw new ModelException(ModelException.IS_EXIST);
        ref.add(c);
        c.postAdd(this);
        sort();
        isSaved = false;
    }

    public void edit(Common oldC, Common newC) throws ModelException {
        List ref = getRef(oldC);
        if (ref.contains(newC) && oldC != ref.get(ref.indexOf(newC))) throw new ModelException(ModelException.IS_EXIST);
        ref.set(ref.indexOf(oldC), newC);
        oldCommon = oldC;
        newC.postEdit(this);
        sort();
        isSaved = false;
    }

    public void remove(Common c){
        getRef(c).remove(c);
        c.postRemove(this);
        isSaved = false;
    }

    public void updateCurrencies() throws Exception {
        HashMap<String, Double> rates = RateCurrency.getRates(getBaseCurrency());
        for (Currency c: currencies) {
            c.setRate(rates.get(c.getCode()));
        }
        for (Account a: accounts){
            a.getCurrency().setRate(rates.get(a.getCurrency().getCode()));
        }
    }

    private List getRef(Common c) {
        if (c instanceof Account) return accounts;
        else if (c instanceof Article) return  articles;
        else if (c instanceof Currency) return  currencies;
        else if (c instanceof Transaction) return  transactions;
        else if (c instanceof Transfer) return  transfers;
        return null;
    }

    @Override
    public String toString() {
        return "SaveData{" +
                "articles=" + articles +
                ", currencies=" + currencies +
                ", accounts=" + accounts +
                ", transactions=" + transactions +
                ", transfers=" + transfers +
                '}';
    }

}
