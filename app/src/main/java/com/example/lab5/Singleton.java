package com.example.lab5;



import java.util.List;

//Класс Singleton нужен нам для того , чтобы передать наш массив данных из одного активити в другое

public class Singleton {
    private static Singleton singleObject;
    private List<Item> items;

    public static Singleton getInstance()
    {
        if(singleObject == null)
        {
            singleObject = new Singleton();
        }
        return singleObject;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }
}
