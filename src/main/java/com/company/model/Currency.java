package com.company.model;

import com.company.exception.ModelException;
import com.company.saveload.SaveData;
import com.company.settings.Format;

import java.util.Objects;

public class Currency extends Common {

    private String title;
    private String code;
    private double rate;
    private boolean isOn;
    private boolean isBase;

    public Currency() {
    }

    public Currency(String title, String code, double rate, boolean isOn, boolean isBase) throws ModelException {
        if (title.length() == 0) throw new ModelException(ModelException.TITLE_EMPTY);
        if (code.length() == 0) throw new ModelException(ModelException.CODE_EMPTY);
        if (rate <= 0) throw new ModelException(ModelException.RATE_INCORRECT);

        this.title = title;
        this.code = code;
        this.rate = rate;
        this.isOn = isOn;
        this.isBase = isBase;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public boolean isBase() {
        return isBase;
    }

    public void setBase(boolean base) {
        isBase = base;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "title='" + title + '\'' +
                ", code='" + code + '\'' +
                ", rate=" + Format.rate(rate) +
                ", isOne=" + isOn +
                ", isBase=" + isBase +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Objects.equals(code, currency.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String getValueForComboBox() {
        return title;
    }

    public double getRateByCurrency(Currency currency) {
        return rate / currency.rate;
    }

    @Override
    public void postAdd(SaveData sd){
        clearBase(sd);
    }

    @Override
    public void postEdit(SaveData sd) {
        clearBase(sd);
        for (Account a : sd.getAccounts()){
            if (a.getCurrency().equals(sd.getOldCommon())) a.setCurrency(this);
        }
    }

    private void clearBase(SaveData sd) {
        if (isBase){
            rate= 1;
            Currency old = (Currency) sd.getOldCommon();
            for (Currency c : sd.getCurrencies()){
                if (!this.equals(c)){
                    c.setBase(false);
                    if (old != null) c.setRate(c.rate / old.rate);
                }
            }
        }
    }

    @Override
    public void postRemove(SaveData sd) {
        super.postRemove(sd);
    }

}
