package com.unibuc.appbackend.services;

import com.unibuc.appbackend.embeddedIds.UserRoleEmbeddedId;
import com.unibuc.appbackend.entities.Role;
import com.unibuc.appbackend.entities.User;
import com.unibuc.appbackend.entities.UserRole;
import com.unibuc.appbackend.enums.RoleName;
import com.unibuc.appbackend.interfaces.UserRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserRoleService {

    private UserRoleRepository userRoleRepository;
    private RoleService roleService;

    public UserRoleService(UserRoleRepository userRoleRepository, RoleService roleService) {
        this.userRoleRepository = userRoleRepository;
        this.roleService = roleService;
    }

    public void addRoleForUser(User user) {
        Role role = roleService.getRoleByName(RoleName.USER);
        UserRoleEmbeddedId userRoleEmbeddedId = new UserRoleEmbeddedId(role.getRoleId(), user.getUserId());

        UserRole userRole = new UserRole(userRoleEmbeddedId, user, role);
        userRoleRepository.save(userRole);
    }

    public List<UserRole> getAllRolesForGivenUser(UUID userId){
        return userRoleRepository.queryBy(userId);
    }

    public Boolean checkAdminRoleForGivenUser(UUID userId) {
        var userRoles = getAllRolesForGivenUser(userId);

        for (var userRole : userRoles) {
            if (userRole.getRole().getRoleName().equals(RoleName.ADMIN)) {
                return true;
            }
        }
        return false;
    }

    public Boolean checkScrumMasterRoleForGivenUser(UUID userId) {
        var userRoles = getAllRolesForGivenUser(userId);

        for (var userRole : userRoles) {
            if (userRole.getRole().getRoleName().equals(RoleName.SCRUM_MASTER)) {
                return true;
            }
        }
        return false;
    }

    public UserRole addUserRole(UUID userId, String roleName) {
        RoleName roleNameEnum = switch (roleName.toLowerCase()) {
            case "admin" -> RoleName.ADMIN;
            case "user" -> RoleName.USER;
            case "scrummaster" -> RoleName.SCRUM_MASTER;
            default -> null;
        };

        Role role = roleService.getRoleByName(roleNameEnum);
        User user = new User(userId, null, null, null, null);
        UserRoleEmbeddedId userRoleEmbeddedId = new UserRoleEmbeddedId(role.getRoleId(), userId);

        UserRole userRole = new UserRole(userRoleEmbeddedId, user, role);
        return userRoleRepository.save(userRole);
    }
}
