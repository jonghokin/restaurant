package com.hoya.restaurant.controller;

import com.hoya.restaurant.entity.Order;
import com.hoya.restaurant.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@Tag(name = "주문 관리")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/my")
    @Operation(summary = "나의 주문내역")
    public ResponseEntity<List<Order>> getUserOrders(HttpSession session) {
        List<Order> orders = orderService.getAllOrdersByUid(session);
        return ResponseEntity.ok(orders);
    }

    // 주문하기
    @PostMapping("/{uuid}")
    @Operation(summary = "주문하기")
    public ResponseEntity<Order> registerOrder(
            @PathVariable("uuid") String uuid,
            HttpSession session) {
        try {
            Order order = orderService.orderRegist(uuid, session);
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 주문 상태 변경 (admin만 가능)
    @PutMapping("/{uuid}/status")
    @Operation(summary = "주문 상태 변경(사장님만 가능)")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable("uuid") String uuid,
            @RequestParam("status") String status,
            HttpSession session) {

        String role = (String) session.getAttribute("role");

        if (role == null || !role.equals("admin")) {
            return ResponseEntity.status(403).body("주문 상태를 수정 할 수 있는 권한이 없습니다."); // 403 Forbidden
        }

        try {
            // 상태 변경 (cancel, complete 가능)
            orderService.updateOrderStatus(uuid, status);
            return ResponseEntity.ok("주문 상태가 " + status + "로 변경되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("잘못된 상태 변경 요청입니다."); // 400 Bad Request
        }
    }
}
