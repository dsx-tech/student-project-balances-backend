package dsx.bcv.server.data.mocks;

import dsx.bcv.server.data.models.Role;
import dsx.bcv.server.data.models.User;
import dsx.bcv.server.services.data_services.RoleService;
import dsx.bcv.server.services.data_services.UserService;
import org.springframework.stereotype.Component;

@Component
public class MockUsers {

    private MockUsers(
            UserService userService,
            RoleService roleService
    ) {
        var userRole = new Role("USER");
        roleService.save(userRole);

        var user = new User(
                "username",
                "password",
                "email@gmail.com");

        if (userService.count() == 0) {
            userService.register(user);
        }
    }
}
