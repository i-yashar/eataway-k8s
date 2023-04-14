package bg.tuplovdiv.restaurants.service.impl;

import bg.tuplovdiv.restaurants.dto.MenuDTO;
import bg.tuplovdiv.restaurants.dto.page.PageDTO;
import bg.tuplovdiv.restaurants.mapper.MenuMapper;
import bg.tuplovdiv.restaurants.model.entity.MenuEntity;
import bg.tuplovdiv.restaurants.repository.MenuRepository;
import bg.tuplovdiv.restaurants.service.MenuService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;

    private final MenuMapper mapper;

    public MenuServiceImpl(MenuRepository menuRepository, MenuMapper mapper) {
        this.menuRepository = menuRepository;
        this.mapper = mapper;
    }

    @Override
    public PageDTO<MenuDTO> findAllRestaurantMenus(UUID restaurantId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MenuEntity> menus = menuRepository.findAllByRestaurantExternalId(restaurantId, pageable);

        return getMenusPage(menus);
    }

    @Override
    public PageDTO<MenuDTO> findAllMenus(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MenuEntity> menus = menuRepository.findAll(pageable);

        return getMenusPage(menus);
    }

    private PageDTO<MenuDTO> getMenusPage(Page<MenuEntity> menus) {
        return new PageDTO<MenuDTO>()
                .setContent(mapToListOfDTOs(menus.getContent()))
                .setPageInfo(menus.getContent().size(), menus.hasNext());
    }

    private Collection<MenuDTO> mapToListOfDTOs(List<MenuEntity> content) {
        return content.stream()
                .map(mapper::toMenuDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UUID saveMenu(MenuDTO menuDTO) {
        MenuEntity menuEntity = mapper.toMenuEntity(menuDTO);

        return menuRepository.save(menuEntity).getExternalId();
    }
}
