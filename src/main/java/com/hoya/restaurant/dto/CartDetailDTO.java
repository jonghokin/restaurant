package com.hoya.restaurant.dto;

import lombok.Data;

@Data
public class CartDetailDTO {
    private String menuName;
    private int quantity;
    private int menuTotalPrice;

    public CartDetailDTO(String menuName, int quantity, int menuTotalPrice) {
        this.menuName = menuName;
        this.quantity = quantity;
        this.menuTotalPrice = menuTotalPrice;
    }
}
