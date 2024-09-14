package com.hoya.restaurant.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoya.restaurant.dto.MenuListDTO;
import com.hoya.restaurant.entity.Menu;
import com.hoya.restaurant.repository.MenuRepository;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    // 메뉴 리스트
    public List<MenuListDTO> getMenuList() {
        return menuRepository.findAll().stream()
                .map(menu -> new MenuListDTO(menu.getUuid(), menu.getName(), menu.getPrice(), menu.getDescription()))
                .collect(Collectors.toList());
    }

    // 메뉴 상세정보
    public Menu getMenuDetail(String uuid) {
        return menuRepository.findById(uuid).orElseThrow(() -> new IllegalArgumentException("존재하지 않은 메뉴입니다."));
    }
}
