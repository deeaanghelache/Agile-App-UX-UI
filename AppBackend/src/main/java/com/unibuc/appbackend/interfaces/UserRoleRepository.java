package com.unibuc.appbackend.interfaces;

import com.unibuc.appbackend.embeddedIds.UserRoleEmbeddedId;
import com.unibuc.appbackend.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleEmbeddedId> {

    @Query(value = "SELECT user_role.user_id, user_role.role_id " +
            "FROM user_role JOIN role ON (user_role.role_id = user_role.role_id) " +
            "WHERE user_role.user_id = :userId", nativeQuery = true)
    List<UserRole> queryBy(@Param("userId") UUID userId);
}
