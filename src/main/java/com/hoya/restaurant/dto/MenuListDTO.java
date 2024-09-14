package com.hoya.restaurant.dto;

import lombok.Data;

@Data
public class MenuListDTO {
    private String uuid;
    private String name;
    private int price;
    private String description;

    public MenuListDTO(String uuid, String name, int price, String description){
        this.uuid = uuid;
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
