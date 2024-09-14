package com.hoya.restaurant.dto;

public class MenuCartRequestDTO {

    private String menuUuid;
    private String tableUuid;
    private int quantity;

    // Getters and Setters
    public String getMenuUuid() {
        return menuUuid;
    }

    public void setMenuUuid(String menuUuid) {
        this.menuUuid = menuUuid;
    }

    public String getTableUuid() {
        return tableUuid;
    }

    public void setTableUuid(String tableUuid) {
        this.tableUuid = tableUuid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
