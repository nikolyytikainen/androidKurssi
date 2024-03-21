package com.example.androidkurssi;

import androidx.datastore.rxjava2.RxDataStore;

import java.util.prefs.Preferences;
import androidx.datastore.preferences.core.Preferences;

public class DataStoreSingleton {
    RxDataStore<Preferences> datastore;
    private static final DataStoreSingleton ourInstance = new DataStoreSingleton();
    public static DataStoreSingleton getInstance() {
        return ourInstance;
    }
    private DataStoreSingleton() { }
    public void setDataStore(RxDataStore<Preferences> datastore) {
        this.datastore = datastore;
    }
    public RxDataStore<Preferences> getDataStore() {
        return datastore;
    }
}
