package com.hoya.restaurant.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDetailResponseDTO {
    private int totalPrice;
    private List<CartDetailDTO> menuDetails;

    // 생성자
    public CartDetailResponseDTO(int totalPrice, List<CartDetailDTO> menuDetails) {
        this.totalPrice = totalPrice;
        this.menuDetails = menuDetails;
    }
}
