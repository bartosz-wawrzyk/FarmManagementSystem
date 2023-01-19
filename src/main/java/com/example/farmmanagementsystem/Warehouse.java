package com.example.farmmanagementsystem;

public class Warehouse {
    private Integer id;
    private String name;
    private Float count;
    private String type;
    private Float value;
    private String date;
    private String category;

    public Warehouse(int id, String name, Float count, String type, Float value, String date, String category) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.type = type;
        this.value = value;
        this.date = date;
        this.category = category;
    }

    public Integer getId(){return id;}

    public String getName(){return name;}

    public Float getValue(){return value;}

    public String getDate(){return date;}

    public Float getCount(){return count;}

    public String getType(){return type;}

    public String getCategory(){return category;}
}
