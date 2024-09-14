package com.hoya.restaurant.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
    public String addMenuToCart(
            @Parameter(description = "메뉴, 테이블 및 수량 정보 리스트", required = true) @RequestBody List<MenuCartRequestDTO> menuCartRequests, // 입력받음
            HttpSession session) {
        try {
            // 각 Cart 항목에 동일하게 적용할 UUID 생성 (String으로 변환)
            String commonCartUuid = UUID.randomUUID().toString(); // UUID -> String 변환

            for (MenuCartRequestDTO request : menuCartRequests) {
                // 각 요청별로 메뉴를 장바구니에 추가
                cartService.addMenuToCart(request.getMenuUuid(), request.getTableUuid(), request.getQuantity(),
                        commonCartUuid, session);
            }
            return "장바구니에 추가하였습니다.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
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
