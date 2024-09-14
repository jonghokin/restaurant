package com.hoya.restaurant.service;

import com.hoya.restaurant.entity.Cart;
import com.hoya.restaurant.entity.Menu;
import com.hoya.restaurant.entity.Order;
import com.hoya.restaurant.entity.OrderStatus;
import com.hoya.restaurant.repository.CartRepository;
import com.hoya.restaurant.repository.MenuRepository;
import com.hoya.restaurant.repository.OrderRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MenuRepository menuRepository;

    // 주문내역 확인
    public List<Order> getAllOrdersByUid(HttpSession session) {
        String uid = (String) session.getAttribute("uid");

        if (uid == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        List<Order> orders = orderRepository.findByUid(uid);

        if (orders.isEmpty()) {
            throw new IllegalArgumentException("해당 사용자의 주문 내역이 없습니다.");
        }

        return orders;
    }

    // 주문 상태 변경
    public void updateOrderStatus(String uuid, String status) {
        // 주문 조회
        Order order = orderRepository.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        try {
            OrderStatus newStatus = OrderStatus.valueOf(status.toLowerCase());
            order.setStatus(newStatus);
            orderRepository.save(order);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("'confirm', 'cancel', 'complete'만 입력가능합니다.");
        }
    }

    // 주문하기
    public Order orderRegist(String uuid, HttpSession session) {
        String uid = (String) session.getAttribute("uid");
        List<Cart> cartList = cartRepository.findAllByUuid(uuid);

        if (cartList.isEmpty()) {
            throw new IllegalArgumentException("장바구니가 비어있습니다.");
        }

        int totalPrice = calculateTotalPrice(cartList);

        Order order = new Order();
        Cart firstCart = cartList.get(0);
        order.setCartUuid(uuid);
        order.setTableUuid(firstCart.getTableUuid());
        order.setUid(uid);
        order.setTotalPrice(totalPrice);

        return orderRepository.save(order);
    }

    // 총 주문금액 합산
    private int calculateTotalPrice(List<Cart> cartList) {
        int totalPrice = 0;

        // 각 Cart 항목에 대해 메뉴의 가격과 수량을 계산
        for (Cart cart : cartList) {
            Optional<Menu> menuOptional = menuRepository.findByUuid(cart.getMenuUuid());
            if (menuOptional.isPresent()) {
                Menu menu = menuOptional.get();
                totalPrice += menu.getPrice() * cart.getQuantity();  // 메뉴 가격 * 수량
            } else {
                throw new IllegalArgumentException("존재하지 않는 메뉴입니다.");
            }
        }

        return totalPrice;
    }


}
