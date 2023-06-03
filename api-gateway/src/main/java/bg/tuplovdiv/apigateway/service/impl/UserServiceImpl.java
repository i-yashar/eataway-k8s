package bg.tuplovdiv.apigateway.service.impl;

import bg.tuplovdiv.apigateway.exception.UserRoleNotFoundException;
import bg.tuplovdiv.apigateway.mapper.UserMapper;
import bg.tuplovdiv.apigateway.model.UserRoleEnum;
import bg.tuplovdiv.apigateway.model.entity.DeliveryDriverEntity;
import bg.tuplovdiv.apigateway.model.entity.UserEntity;
import bg.tuplovdiv.apigateway.model.entity.UserRole;
import bg.tuplovdiv.apigateway.repository.DeliveryDriverRepository;
import bg.tuplovdiv.apigateway.repository.UserRepository;
import bg.tuplovdiv.apigateway.repository.UserRoleRepository;
import bg.tuplovdiv.apigateway.security.user.impl.EatawayUser;
import bg.tuplovdiv.apigateway.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static bg.tuplovdiv.apigateway.model.UserRoleEnum.CUSTOMER;
import static bg.tuplovdiv.apigateway.model.UserRoleEnum.EMPLOYEE;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DeliveryDriverRepository deliveryDriverRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserMapper mapper;

    public UserServiceImpl(UserRepository userRepository, DeliveryDriverRepository deliveryDriverRepository, UserRoleRepository userRoleRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.deliveryDriverRepository = deliveryDriverRepository;
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

    @Override
    public Collection<EatawayUser> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public void registerUserAsEmployee(String userId) {
        UserEntity user = getUser(userId);

        user.setUserRoles(createUserRole(EMPLOYEE));

        userRepository.save(user);
        deliveryDriverRepository.save(createDeliveryDriver(user));
    }

    private DeliveryDriverEntity createDeliveryDriver(UserEntity user) {
        return new DeliveryDriverEntity()
                .setDeliveryDriverId(user.getUserId())
                .setFree(true)
                .setCurrentOrderId(null);
    }

    @Override
    public void deregisterUserAsEmployee(String userId) {
        UserEntity user = getUser(userId);

        user.setUserRoles(createUserRole(CUSTOMER));

        userRepository.save(user);
        deliveryDriverRepository.delete(getDeliveryDriver(userId));
    }

    private UserEntity getUser(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User with userId " + userId + " not found"));
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

    private DeliveryDriverEntity getDeliveryDriver(String userId) {
        return deliveryDriverRepository.findByDeliveryDriverId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Delivery driver not found."));
    }
}
