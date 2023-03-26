package bg.tuplovdiv.apigateway.service.impl;

import bg.tuplovdiv.apigateway.mapper.UserMapper;
import bg.tuplovdiv.apigateway.model.entity.UserEntity;
import bg.tuplovdiv.apigateway.repository.UserRepository;
import bg.tuplovdiv.apigateway.security.EatawayUser;
import bg.tuplovdiv.apigateway.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public void registerUser(EatawayUser user) {
        Optional<UserEntity> optUser = userRepository.findByUserId(user.getUserId());

        if(optUser.isPresent()) {
            return;
        }

        userRepository.save(mapper.toEntity(user));
    }
}
