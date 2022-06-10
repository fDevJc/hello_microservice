package hello.userservice.service;

import hello.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        userDto.setEncryptedPwd(bCryptPasswordEncoder.encode(userDto.getPwd()));
        return UserDto.of(userRepository.save(userDto.toEntity()));
    }

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream()
                .map(user -> UserDto.of(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        return UserDto.of(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }
}
