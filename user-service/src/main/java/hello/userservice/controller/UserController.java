package hello.userservice.controller;

import hello.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final Environment env;

    @Value("${greeting.message}")
    String message;

    @GetMapping("/health_check")
    public String status() {
        String result = "It's working in UserService" +
                "\n , port(local.server.port) = " + env.getProperty("local.server.port") +
                "\n , port(server.port) = " + env.getProperty("server.port") +
                "\n , token.secret = " + env.getProperty("token.secret") +
                "\n , token.expiration time = " + env.getProperty("token.expiration_time");
        return result;
    }

    @GetMapping("/welcome")
    public String welcome() {
        return message;
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody RequestUser requestUser) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseUser.of(userService.createUser(requestUser.toDto())));
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers().stream()
                .map(userDto -> ResponseUser.of(userDto))
                .collect(Collectors.toList()));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok().body(ResponseUser.of(userService.getUserById(userId)));
    }
}
