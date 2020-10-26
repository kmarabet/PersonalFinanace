package com.company.model;

import com.company.saveload.SaveData;

abstract public class Common {

    public String getValueForComboBox() {
        return null;
    }

    public void postAdd(SaveData sd) {}
    public void postEdit(SaveData sd) {}
    public void postRemove(SaveData sd) {}

}
