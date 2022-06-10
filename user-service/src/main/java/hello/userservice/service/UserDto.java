package hello.userservice.service;

import hello.userservice.domain.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private String email;
    private String name;
    private String pwd;
    private String userId;
    private LocalDateTime createAt;
    private String encryptedPwd;

    @Builder
    private UserDto(String email, String name, String pwd, String userId) {
        this.email = email;
        this.name = name;
        this.pwd = pwd;
        this.userId = userId;
    }

    public static UserDto of(User userEntity) {
        return UserDto.builder()
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .userId(userEntity.getUserId())
                .build();
    }

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .email(email)
                .name(name)
                .encryptedPwd(encryptedPwd)
                .build();
    }
}
