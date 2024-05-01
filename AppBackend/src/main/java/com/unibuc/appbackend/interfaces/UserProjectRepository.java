package com.unibuc.appbackend.interfaces;

import com.unibuc.appbackend.embeddedIds.UserProjectEmbeddedId;
import com.unibuc.appbackend.entities.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserProjectRepository extends JpaRepository<UserProject, UserProjectEmbeddedId> {

    @Query(value = "SELECT user_project.user_id, user_project.project_id " +
            "FROM user_project JOIN project ON (user_project.project_id = project.project_id) " +
            "WHERE user_project.user_id = :userId", nativeQuery = true)
    List<UserProject> getAllProjects(@Param("userId") UUID userId);

}
