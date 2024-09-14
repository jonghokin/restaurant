package com.hoya.restaurant.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoya.restaurant.dto.CartDetailDTO;
import com.hoya.restaurant.dto.CartDetailResponseDTO;
import com.hoya.restaurant.entity.Cart;
import com.hoya.restaurant.entity.Menu;
import com.hoya.restaurant.entity.TableInfo;
import com.hoya.restaurant.repository.CartRepository;
import com.hoya.restaurant.repository.MenuRepository;
import com.hoya.restaurant.repository.TableRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class CartService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private TableRepository tableRepository;

    // 장바구니 담기
    public void addMenuToCart(String menuUuid, String tableUuid, int quantity, String cartUuid, HttpSession session)
            throws Exception {
        String uid = (String) session.getAttribute("uid");

        if (uid == null) {
            throw new Exception("사용자 정보가 없습니다.");
        }

        Optional<Menu> menuOptional = menuRepository.findById(menuUuid);
        if (menuOptional.isEmpty()) {
            throw new Exception("존재하지 않는 메뉴입니다.");
        }

        Optional<TableInfo> tableOptional = tableRepository.findById(tableUuid);
        if (tableOptional.isEmpty()) {
            throw new Exception("존재하지 않는 테이블(식탁)입니다.");
        }

        Menu menu = menuOptional.get();
        TableInfo tableInfo = tableOptional.get();

        Cart cart = new Cart();
        cart.setUid(uid);
        cart.setMenuUuid(menu.getUuid());
        cart.setTableUuid(tableInfo.getUuid());
        cart.setQuantity(quantity);
        cart.setUuid(cartUuid);
        cart.setCreatedAt(LocalDateTime.now());

        cartRepository.save(cart);
    }

    // 장바구니 상세
    public CartDetailResponseDTO getCartDetail(String uuid) throws Exception {
        List<Cart> cartList = cartRepository.findAllByUuid(uuid);

        if (cartList.isEmpty()) {
            throw new Exception("해당 테이블에 대한 카트가 존재하지 않습니다.");
        }

        List<CartDetailDTO> cartMenus = new ArrayList<>();
        int totalCartPrice = 0;

        // 각 카트의 메뉴 정보와 가격 합산
        for (Cart cart : cartList) {
            Optional<Menu> menuOptional = menuRepository.findById(cart.getMenuUuid());

            if (menuOptional.isPresent()) {
                Menu menu = menuOptional.get();
                int menuTotalPrice = menu.getPrice() * cart.getQuantity();
                totalCartPrice += menuTotalPrice;

                CartDetailDTO cartMenuInfo = new CartDetailDTO(menu.getName(), cart.getQuantity(), menuTotalPrice);
                cartMenus.add(cartMenuInfo);
            } else {
                throw new Exception("존재하지 않는 메뉴가 포함되어 있습니다.");
            }
        }

        return new CartDetailResponseDTO(totalCartPrice, cartMenus);
    }

    // 메뉴 삭제
    public void deleteMenu(String menuUuid, String uuid) throws Exception {
        var cartOptional = cartRepository.findByMenuUuidAndUuid(menuUuid, uuid);

        if (cartOptional.isEmpty()) {
            throw new Exception("해당 메뉴가 장바구니에 존재하지 않습니다.");
        }

        cartRepository.delete(cartOptional.get());
    }

    // 메뉴 수량 수정
    public void updateMenuQuantity(String menuUuid, String uuid, int quantity) throws Exception {
        Optional<Cart> cartOptional = cartRepository.findByMenuUuidAndUuid(menuUuid, uuid);

        if (cartOptional.isEmpty()) {
            throw new Exception("해당 메뉴가 장바구니에 존재하지 않습니다.");
        }

        Cart cart = cartOptional.get();
        cart.setQuantity(quantity);

        cartRepository.save(cart);
    }
}
