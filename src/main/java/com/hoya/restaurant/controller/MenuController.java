package com.hoya.restaurant.controller;

import com.hoya.restaurant.dto.MenuListDTO;
import com.hoya.restaurant.entity.Menu;
import com.hoya.restaurant.service.MenuService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@Tag(name = "메뉴 관리")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping()
    @Operation(summary = "메뉴 리스트")
    public List<MenuListDTO> getMenuList() {
        return menuService.getMenuList();
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "메뉴 상세조회")
    public Menu getMenuDetail(@PathVariable String uuid) {
        return menuService.getMenuDetail(uuid);
    }
}
