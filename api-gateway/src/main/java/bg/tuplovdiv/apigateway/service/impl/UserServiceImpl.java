package bg.tuplovdiv.apigateway.service.impl;

import bg.tuplovdiv.apigateway.exception.UserRoleNotFoundException;
import bg.tuplovdiv.apigateway.mapper.UserMapper;
import bg.tuplovdiv.apigateway.model.UserRoleEnum;
import bg.tuplovdiv.apigateway.model.entity.UserEntity;
import bg.tuplovdiv.apigateway.model.entity.UserRole;
import bg.tuplovdiv.apigateway.repository.UserRepository;
import bg.tuplovdiv.apigateway.repository.UserRoleRepository;
import bg.tuplovdiv.apigateway.security.user.impl.EatawayUser;
import bg.tuplovdiv.apigateway.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static bg.tuplovdiv.apigateway.model.UserRoleEnum.CUSTOMER;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserMapper mapper;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.mapper = mapper;
    }

    @Override
    public void registerUser(EatawayUser user) {
        Optional<UserEntity> optUser = userRepository.findByUserId(user.getUserId());

        if (optUser.isPresent()) {
            return;
        }

        UserEntity userEntity = mapper.toEntity(user);
        userEntity.setUserRoles(createUserRole(CUSTOMER));

        userRepository.save(userEntity);
    }

    private Set<UserRole> createUserRole(UserRoleEnum... roles) {
        return Arrays.stream(roles)
                .map(this::getUserRole)
                .collect(Collectors.toSet());
    }

    private UserRole getUserRole(UserRoleEnum role) {
        return userRoleRepository.findByUserRole(role)
                .orElseThrow(() -> new UserRoleNotFoundException("User role " + role.name() + " is unknown"));
    }

    @Override
    public Collection<EatawayUser> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}
