package banking_api.service.impl;

import banking_api.exception.BadRequestException;
import banking_api.exception.ResourceNotFoundException;
import banking_api.model.PasswordResetToken;
import banking_api.model.User;
import banking_api.repository.PasswordResetTokenRepository;
import banking_api.repository.UserRepository;
import banking_api.service.PasswordResetService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetServiceImpl
        implements PasswordResetService {

    private final UserRepository userRepository;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final PasswordEncoder passwordEncoder;

    public PasswordResetServiceImpl(
            UserRepository userRepository,
            PasswordResetTokenRepository passwordResetTokenRepository,
            PasswordEncoder passwordEncoder
    ) {

        this.userRepository = userRepository;

        this.passwordResetTokenRepository =
                passwordResetTokenRepository;

        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void forgotPassword(String email) {

        User user =
                userRepository.findByEmail(
                        email.toLowerCase()
                ).orElseThrow(
                        () -> new ResourceNotFoundException(
                                "User not found"
                        )
                );

        passwordResetTokenRepository.deleteByUser(user);

        /*
         * Force Hibernate to execute the DELETE before
         * inserting the replacement token.
         */
        passwordResetTokenRepository.flush();

        PasswordResetToken resetToken =
                new PasswordResetToken();

        resetToken.setToken(
                UUID.randomUUID().toString()
        );

        resetToken.setUser(user);

        resetToken.setExpiryDate(
                LocalDateTime.now().plusMinutes(15)
        );

        passwordResetTokenRepository.save(
                resetToken
        );
    }

    @Override
    @Transactional
    public void resetPassword(
            String token,
            String newPassword
    ) {

        PasswordResetToken resetToken =
                passwordResetTokenRepository
                        .findByToken(token)
                        .orElseThrow(
                                () -> new BadRequestException(
                                        "Invalid or expired reset token"
                                )
                        );

        if (
                resetToken.getExpiryDate()
                        .isBefore(LocalDateTime.now())
        ) {

            passwordResetTokenRepository.delete(resetToken);

            throw new BadRequestException(
                    "Invalid or expired reset token"
            );
        }

        User user =
                resetToken.getUser();

        user.setPassword(
                passwordEncoder.encode(
                        newPassword
                )
        );

        userRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);
    }
}