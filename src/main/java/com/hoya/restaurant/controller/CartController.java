package com.hoya.restaurant.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hoya.restaurant.dto.CartDetailResponseDTO;
import com.hoya.restaurant.dto.MenuCartRequestDTO;
import com.hoya.restaurant.service.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/cart")
@Tag(name = "장바구니 관리")
public class CartController {

    @Autowired
    private CartService cartService;

    // 장바구니에 메뉴 추가
    @PostMapping("/add")
    @Operation(summary = "장바구니에 메뉴 담기")
    public ResponseEntity<Map<String, Object>> addMenuToCart(
            @Parameter(description = "메뉴, 테이블 및 수량 정보 리스트", required = true) @RequestBody List<MenuCartRequestDTO> menuCartRequests, // 입력받음
            HttpSession session) {
        try {
            String commonCartUuid = UUID.randomUUID().toString();

            for (MenuCartRequestDTO request : menuCartRequests) {
                cartService.addMenuToCart(request.getMenuUuid(), request.getTableUuid(), request.getQuantity(),
                        commonCartUuid, session);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("cartUuid", commonCartUuid);
            response.put("message", "장바구니에 추가하였습니다.");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "장바구니 상세보기")
    public CartDetailResponseDTO getCartDetail(
            @Parameter(description = "cart테이블의 uuid", required = true) @PathVariable("uuid") String uuid) {
        try {
            return cartService.getCartDetail(uuid);
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    // 메뉴 삭제
    @DeleteMapping("/{uuid}/delete")
    @Operation(summary = "장바구니에서 메뉴 삭제")
    public String deleteMenuFromCart(
            @Parameter(description = "테이블의 UUID", required = true) @PathVariable("uuid") String uuid,

            @Parameter(description = "삭제할 메뉴의 UUID", required = true) @RequestParam("menuUuid") String menuUuid) {
        try {
            cartService.deleteMenu(menuUuid, uuid);
            return "메뉴가 장바구니에서 삭제되었습니다.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // 메뉴 수량 수정
    @PutMapping("/{uuid}/update")
    @Operation(summary = "장바구니에서 메뉴 수량 수정")
    public String updateMenuQuantity(
            @Parameter(description = "장바구니의 UUID", required = true) @PathVariable("uuid") String uuid,

            @Parameter(description = "수량을 수정할 메뉴의 UUID", required = true) @RequestParam("menuUuid") String menuUuid,

            @Parameter(description = "수정할 수량", required = true) @RequestParam("quantity") int quantity) {
        try {
            cartService.updateMenuQuantity(menuUuid, uuid, quantity);
            return "메뉴 수량이 수정되었습니다.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
