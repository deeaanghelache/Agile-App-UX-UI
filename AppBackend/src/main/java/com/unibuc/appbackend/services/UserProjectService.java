package com.unibuc.appbackend.services;

import com.unibuc.appbackend.embeddedIds.UserProjectEmbeddedId;
import com.unibuc.appbackend.entities.Project;
import com.unibuc.appbackend.entities.User;
import com.unibuc.appbackend.entities.UserProject;
import com.unibuc.appbackend.interfaces.UserProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserProjectService {

    private UserProjectRepository userProjectRepository;
    private UserService userService;
    private ProjectService projectService;

    public UserProjectService(UserProjectRepository userProjectRepository, UserService userService, ProjectService projectService) {
        this.userProjectRepository = userProjectRepository;
        this.userService = userService;
        this.projectService = projectService;
    }

    public UserProject create(UUID userId, UUID projectId) {
        UserProject userProject = new UserProject();
        UserProjectEmbeddedId userProjectEmbeddedId = new UserProjectEmbeddedId(projectId, userId);

        Project project = projectService.getProjectById(projectId);
        User user = userService.getUserById(userId);
        userProject.setUserProjectEmbeddedId(userProjectEmbeddedId);
        userProject.setUser(user);
        userProject.setProject(project);

        return userProjectRepository.save(userProject);
    }

    public List<Project> getProjectsForGivenUser(UUID id) {
        List<UserProject> userProjects = userProjectRepository.getAllProjects(id);

        return userProjects.stream().map(UserProject::getProject).toList();
    }
}
