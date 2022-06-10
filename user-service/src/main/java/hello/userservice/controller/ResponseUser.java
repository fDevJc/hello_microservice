package hello.userservice.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import hello.userservice.service.UserDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUser {
    private String email;
    private String name;
    private String userId;

    private List<ResponseOrder> orders;

    @Builder
    private ResponseUser(String email, String name, String userId) {
        this.email = email;
        this.name = name;
        this.userId = userId;
    }

    public static ResponseUser of(UserDto userDto) {
        return ResponseUser.builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .userId(userDto.getUserId())
                .build();
    }
}
