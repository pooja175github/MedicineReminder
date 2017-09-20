package com.journaldev.recyclerviewcardview.Utils.Reminders;

public class Info {
    String Key;
    String Value;

    public Info() {
    }

    public Info(String key, String value) {
        Key = key;
        Value = value;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
