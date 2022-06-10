package hello.userservice.service;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    List<UserDto> getUsers();
    UserDto getUserById(Long id);
}
