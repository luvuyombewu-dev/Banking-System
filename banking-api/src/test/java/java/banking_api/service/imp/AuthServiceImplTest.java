package banking_api.service.impl;

import banking_api.dto.AuthResponse;
import banking_api.dto.LoginRequest;
import banking_api.dto.RegisterRequest;
import banking_api.model.Account;
import banking_api.model.User;
import banking_api.repository.AccountRepository;
import banking_api.repository.UserRepository;
import banking_api.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void register_shouldCreateUserAccountAndReturnAuthResponse() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("Luvuyo");
        request.setLastName("Mbewu");
        request.setEmail("luvuyo@test.com");
        request.setPassword("password123");

        when(passwordEncoder.encode("password123")).thenReturn("encoded-password");
        when(jwtService.generateToken("luvuyo@test.com")).thenReturn("jwt-token");

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(11L);
            return user;
        });

        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> {
            Account account = invocation.getArgument(0);
            account.setId(8L);
            return account;
        });

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals(11L, response.getUserId());
        assertEquals("Luvuyo", response.getFirstName());
        assertEquals("Mbewu", response.getLastName());
        assertEquals("luvuyo@test.com", response.getEmail());
        assertEquals("ROLE_USER", response.getRole());

        verify(userRepository, times(1)).save(any(User.class));
        verify(accountRepository, times(1)).save(any(Account.class));
        verify(jwtService, times(1)).generateToken("luvuyo@test.com");
    }

    @Test
    void login_shouldReturnAuthResponse_whenCredentialsAreValid() {
        LoginRequest request = new LoginRequest();
        request.setEmail("luvuyo@test.com");
        request.setPassword("password123");

        User user = new User();
        user.setId(11L);
        user.setFirstName("Luvuyo");
        user.setLastName("Mbewu");
        user.setEmail("luvuyo@test.com");
        user.setPassword("encoded-password");
        user.setRole("ROLE_USER");

        when(userRepository.findByEmail("luvuyo@test.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "encoded-password")).thenReturn(true);
        when(jwtService.generateToken("luvuyo@test.com")).thenReturn("jwt-token");

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals(11L, response.getUserId());
        assertEquals("Luvuyo", response.getFirstName());
        assertEquals("Mbewu", response.getLastName());
        assertEquals("luvuyo@test.com", response.getEmail());
        assertEquals("ROLE_USER", response.getRole());
    }

    @Test
    void login_shouldThrow_whenPasswordIsInvalid() {
        LoginRequest request = new LoginRequest();
        request.setEmail("luvuyo@test.com");
        request.setPassword("wrong-password");

        User user = new User();
        user.setPassword("encoded-password");

        when(userRepository.findByEmail("luvuyo@test.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong-password", "encoded-password")).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> authService.login(request));

        assertEquals("Invalid credentials", ex.getMessage());
        verify(jwtService, never()).generateToken(anyString());
    }
}