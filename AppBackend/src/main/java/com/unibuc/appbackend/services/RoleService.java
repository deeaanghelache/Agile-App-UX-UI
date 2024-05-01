package com.unibuc.appbackend.services;

import com.unibuc.appbackend.entities.Role;
import com.unibuc.appbackend.enums.RoleName;
import com.unibuc.appbackend.exceptions.UserRoleNotFoundException;
import com.unibuc.appbackend.interfaces.RoleRepository;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleById(UUID id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            return role.get();
        } else {
            throw new UserRoleNotFoundException();
        }
    }

    public Role getRoleByName(RoleName name) {
        Optional<Role> role = roleRepository.findByRoleName(name);
        if (role.isPresent()) {
            return role.get();
        } else {
            throw new UserRoleNotFoundException();
        }
    }

    public Role create(String name) {
        Role role = new Role();

        switch (name.toLowerCase()) {
            case "user":
                role.setRoleName(RoleName.USER);
                break;
            case "admin":
                role.setRoleName(RoleName.ADMIN);
                break;
            case "scrummaster":
                role.setRoleName(RoleName.SCRUM_MASTER);
                break;
        }
        return roleRepository.save(role);
    }

    public void delete(UUID uuid) throws RoleNotFoundException {
        Optional<Role> role = roleRepository.findById(uuid);
        if (role.isPresent()) {
            roleRepository.deleteById(uuid);
        } else {
            throw new RoleNotFoundException();
        }
    }
}
