package com.example.farmmanagementsystem;

public class Breeding {

    private Integer id;
    private String name;
    private Integer amount;
    private String category;

    public Breeding(int id, String name, int amount, String category) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.category = category;
    }

    public Integer getId(){return id;}

    public String getName(){return name;}

    public Integer getAmount(){return amount;}

    public String getCategory(){return category;}
}
