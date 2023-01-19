package com.example.farmmanagementsystem;

public class Plot {

    private Integer id;
    private String number;
    private Float area;
    private String cropping;
    private Integer year;

    //  constractor
    public Plot(int id, String number, float area, String cropping, int year) {
        this.id = id;
        this.number = number;
        this.area = area;
        this.cropping = cropping;
        this.year = year;
    }

    public Integer getId(){return id;}

    public String getNumber(){return number;}

    public Float getArea(){return area;}

    public String getCropping(){return cropping;}

    public Integer getYear(){return year;}
}
