package hello.userservice.service;

import feign.FeignException;
import hello.userservice.client.OrderServiceClient;
import hello.userservice.controller.ResponseOrder;
import hello.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Environment env;

    CircuitBreakerFactory circuitBreakerFactory;

    //    private final RestTemplate restTemplate;
    private final OrderServiceClient orderServiceClient;

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
        String orderUrl = String.format(env.getProperty("order_service.url"), id);

        //use rest template
//        ResponseEntity<List<ResponseOrder>> orderListResponse = restTemplate.exchange(orderUrl, HttpMethod.GET, null,
//                new ParameterizedTypeReference<List<ResponseOrder>>() {
//                }
//        );
//        List<ResponseOrder> responseOrders = orderListResponse.getBody();

        //use open feign
        List<ResponseOrder> responseOrders = null;

//        try {
//            responseOrders = orderServiceClient.getOrders(id);
//        } catch (FeignException e) {
//            log.error(e.getMessage());
//        }

        //error decoder를 이용하여 try 문장 삭제
//        responseOrders = orderServiceClient.getOrders(id);

        //use circuit breaker

        log.info("Before call orders microservice");
        CircuitBreaker circuitbreaker = circuitBreakerFactory.create("circuitbreaker");
        responseOrders = circuitbreaker.run(() -> orderServiceClient.getOrders(id), throwable -> new ArrayList<>());
        log.info("After call orders microservice");

        return UserDto.of(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")), responseOrders);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return UserDto.of(userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        hello.userservice.domain.User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(user.getEmail(), user.getEncryptedPwd(),
                true,
                true,
                true,
                true,
                new ArrayList<>());
    }
}
