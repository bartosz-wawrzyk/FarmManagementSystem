package com.example.farmmanagementsystem;

public class Garage {

    private Integer id;
    private String name;
    private String model;
    private String type_vehical;
    private Integer year;
    private String date_review;
    private String number;

    //  constractor
    public Garage(int id, String name, String model, String type_vehical, int year, String date_review, String number) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.type_vehical = type_vehical;
        this.year = year;
        this.date_review = date_review;
        this.number = number;
    }

    public Integer getId(){return id;}

    public String getName(){return name;}

    public String getModel(){return model;}

    public String getType_vehical(){return type_vehical;}

    public Integer getYear(){return year;}

    public String getDate_review(){return date_review;}

    public String getNumber(){return number;}
}
