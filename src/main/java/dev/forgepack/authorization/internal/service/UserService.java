package dev.forgepack.authorization.internal.service;

import dev.forgepack.core.api.mapper.Mapper;
import dev.forgepack.core.api.repository.CrudRepository;
import dev.forgepack.core.internal.service.RestorableServiceImpl;
import dev.forgepack.validation.api.service.UniqueCheckableService;
import dev.forgepack.authorization.internal.model.User;
import dev.forgepack.authorization.internal.payload.UserRequest;
import dev.forgepack.authorization.internal.payload.UserResponse;
import dev.forgepack.authorization.internal.repository.UserRepository;
// import dev.forgepack.security.internal.utils.Information;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.UUID;

@Service
public class UserService extends RestorableServiceImpl<User, UserRequest, UserResponse> implements UniqueCheckableService {

    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(CrudRepository<User> repositoryGeneric, Mapper<User, UserRequest, UserResponse> mapperInterface, UserRepository userRepository) {
        super(User.class, repositoryGeneric, mapperInterface);
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByField(String field, Object value) {
        if ("username".equals(field)) {
            return userRepository.existsByUsernameIgnoreCase((String) value);
        }
        if ("email".equals(field)) {
            return userRepository.existsByEmailIgnoreCase((String) value);
        }
        else {
            throw new IllegalArgumentException("Unsupported field: " + field);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByFieldAndIdNot(String field, Object value, UUID id) {
        if ("username".equals(field)){
            return userRepository.existsByUsernameIgnoreCaseAndIdNot((String) value, id);
        }
        if ("email".equals(field)){
            return userRepository.existsByEmailIgnoreCaseAndIdNot((String) value, id);
        } else {
            throw new IllegalArgumentException("Unsupported field: " + field);
        }
    }
    public String generateSecurePassword() {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "!@#$%^&*()-_=+[]{}|;:,.<>?";

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        password.append(upper.charAt(random.nextInt(upper.length())));
        password.append(lower.charAt(random.nextInt(lower.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));
        password.append(special.charAt(random.nextInt(special.length())));
        String allChars = upper + lower + digits + special;
        for (int i = 4; i < 8; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }
        char[] chars = password.toString().toCharArray();
        for (int i = chars.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        return new String(chars);
    }
    public User isValidToChange(UUID id) {
        // String currentUser = Information.getCurrentUser().orElse("Unknown User");
        User user = userRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new EntityNotFoundException("Resource not found"));
        // User userCurrent = repositoryUser.findByUsername(currentUser).orElseThrow(() -> new EntityNotFoundException("Current user not found"));
        // if ((userCurrent.getUsername() != null && user.getUsername() != null &&
        //         userCurrent.getUsername().equals(user.getUsername())) ||
        //         userCurrent.getRole().stream().anyMatch(role -> role.getName().equals("ADMIN"))) {
        //     return user;
        // } else {
        //     log.warn("{} attempted unauthorized access to user with ID: {}", currentUser, id);
            throw new EntityNotFoundException("Resource not found");
        // }
    }
    public User isValidToChange(String username) {
        User user = userRepository.findByUsername(username.trim())
                .orElseThrow(() -> new EntityNotFoundException("Resource not found"));
        // String currentUsername = Information.getCurrentUser().orElse(null);
        // if (currentUsername == null) {
        //     return user;
        // }
        // User currentUser = repositoryUser.findByUsername(currentUsername)
        //         .orElseThrow(() -> new EntityNotFoundException("Current user not found"));
        // boolean isSameUser = currentUser.getUsername().equalsIgnoreCase(user.getUsername());
        // boolean isAdmin   = currentUser.getRole().stream().anyMatch(role -> role.getName().equals("ADMIN"));
        // if (isSameUser || isAdmin) {
        //     return user;
        // }
        // log.warn("{} attempted unauthorized access to user with username: {}", currentUsername, username);
        throw new EntityNotFoundException("Resource not found");
    }
}
