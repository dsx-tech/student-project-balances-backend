package dsx.bcv.server.services.data_services;

import dsx.bcv.server.data.models.Role;
import dsx.bcv.server.data.models.User;
import dsx.bcv.server.data.repositories.UserRepository;
import dsx.bcv.server.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(
            UserRepository userRepository,
            RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public User register(User user) {
        Role userRole = roleService.findByName("USER");
        user.setRoles(Set.of(userRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User registeredUser = userRepository.save(user);
        log.info("register: User {} saved", registeredUser);
        return registeredUser;
    }

    public List<User> findAll() {
        var users = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        log.info("findAll: {} users founded", users.size());
        return users;
    }

    public User findByUsername(String username) {
        var user = userRepository.findByUsername(username).orElseThrow(NotFoundException::new);
        log.info("findByUsername: {} found by username {}", user, username);
        return user;
    }

    public User findById(long id) {
        var user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        log.info("findByUsername: {} found by id {}", user, id);
        return user;
    }

    @Transactional
    public void deleteById(long id) {
        userRepository.findById(id).orElseThrow(NotFoundException::new);
        userRepository.deleteById(id);
        log.info("delete: user with id {} successfully deleted", id);
    }

    public long count() {
        return userRepository.count();
    }
}
