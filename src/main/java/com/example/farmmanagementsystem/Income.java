package com.example.farmmanagementsystem;

public class Income {

    private Integer id;
    private Float value;
    private String date;
    private String name;
    private Float amount;
    private String type;
    private String classification;

    public Income(int id, Float value, String date, String name, Float amount, String type, String classification) {
        this.id = id;
        this.value = value;
        this.date = date;
        this.name = name;
        this.amount = amount;
        this.type = type;
        this.classification = classification;
    }

    public Integer getId(){return id;}

    public String getName(){return name;}

    public Float getValue(){return value;}

    public String getDate(){return date;}

    public Float getAmount(){return amount;}

    public String getType(){return type;}

    public String getClassification(){return classification;}
}
