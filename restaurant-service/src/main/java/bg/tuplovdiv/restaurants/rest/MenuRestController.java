package bg.tuplovdiv.restaurants.rest;

import bg.tuplovdiv.restaurants.dto.MenuDTO;
import bg.tuplovdiv.restaurants.dto.page.PageDTO;
import bg.tuplovdiv.restaurants.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/restaurants/api/v1")
public class MenuRestController {

    private static final String ALL_MENUS_PATH = "/menus";
    private static final String MENU_PATH = ALL_MENUS_PATH + "/{menuId}";
    private static final String MENUS_PATH = "/restaurants/{restaurantId}/menus";

    private final MenuService menuService;

    public MenuRestController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping(MENU_PATH)
    public ResponseEntity<MenuDTO> getMenu(@PathVariable UUID menuId) {
        return ResponseEntity.ok(menuService.findMenuByMenuId(menuId));
    }

    @GetMapping(ALL_MENUS_PATH)
    public ResponseEntity<PageDTO<MenuDTO>> getAllMenus(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                        @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return ResponseEntity.ok(menuService.findAllMenus(page, size));
    }

    @GetMapping(MENUS_PATH)
    private ResponseEntity<PageDTO<MenuDTO>> getAllRestaurantMenus(@PathVariable UUID restaurantId,
                                                                   @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                                   @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return ResponseEntity.ok(menuService.findAllRestaurantMenus(restaurantId, page, size));
    }

    @PostMapping(MENUS_PATH)
    public ResponseEntity<Void> createMenu(@RequestBody MenuDTO menu) {
        UUID menuId = menuService.saveMenu(menu);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{menuId}")
                .buildAndExpand(menuId)
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
