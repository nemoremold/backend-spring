package org.latheild.project.service;

import org.latheild.project.api.dto.ProjectMemberOperationDTO;
import org.latheild.project.api.dto.ChangeOwnerDTO;
import org.latheild.project.api.dto.ProjectDTO;

import java.util.ArrayList;

public interface ProjectService {
    boolean checkProjectExistence(String projectId);

    ProjectDTO createProject(ProjectDTO projectDTO);

    ProjectDTO updateProjectInfo(ProjectDTO projectDTO);

    ProjectDTO changeProjectOwner(ChangeOwnerDTO changeOwnerDTO);

    void deleteProjectById(ProjectDTO projectDTO);

    ProjectDTO getProjectById(String id);

    ArrayList<ProjectDTO> getProjectsByOwnerId(String ownerId);

    ArrayList<ProjectDTO> adminGetAllProjects(String code);

    void adminDeleteProjectById(String id, String code);

    void adminDeleteProjectsByOwnerId(String ownerId, String code);

    void adminDeleteAllProjects(String code);

    void addProjectMember(ProjectMemberOperationDTO projectMemberOperationDTO);

    void removeProjectMember(ProjectMemberOperationDTO projectMemberOperationDTO);

    ArrayList<ProjectDTO> getAllProjectsByUserId(String userId);
}
