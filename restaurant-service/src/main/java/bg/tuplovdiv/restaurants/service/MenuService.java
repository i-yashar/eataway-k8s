package bg.tuplovdiv.restaurants.service;

import bg.tuplovdiv.restaurants.dto.MenuDTO;
import bg.tuplovdiv.restaurants.dto.page.PageDTO;

import java.util.UUID;

public interface MenuService {
    PageDTO<MenuDTO> findAllMenus(int page, int size);
    UUID saveMenu(MenuDTO menuDTO);
}
