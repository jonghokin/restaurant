package com.hoya.restaurant.entity;

import java.io.Serializable;
import java.util.Objects;

public class CartId implements Serializable {
    private String uuid;
    private String menuUuid;

    public CartId() {
    }

    public CartId(String uuid, String menuUuid) {
        this.uuid = uuid;
        this.menuUuid = menuUuid;
    }

    // equals()와 hashCode() 메서드를 구현해야 합니다.
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CartId cartId = (CartId) o;
        return Objects.equals(uuid, cartId.uuid) &&
                Objects.equals(menuUuid, cartId.menuUuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, menuUuid);
    }
}
