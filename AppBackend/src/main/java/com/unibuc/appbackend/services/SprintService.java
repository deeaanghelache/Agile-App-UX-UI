package com.unibuc.appbackend.services;

import com.unibuc.appbackend.entities.Sprint;
import com.unibuc.appbackend.exceptions.SprintNotFoundException;
import com.unibuc.appbackend.interfaces.SprintRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class SprintService {

    private SprintRepository sprintRepository;

    public SprintService(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    public Sprint create(Sprint sprint) {
        return sprintRepository.save(sprint);
    }

    public Sprint getSprintById(UUID uuid) {
        Optional<Sprint> sprint = sprintRepository.findById(uuid);
        if (sprint.isPresent()) {
            return sprint.get();
        } else {
            throw new SprintNotFoundException();
        }
    }

    public Sprint getById(UUID sprintId) {
        Optional<Sprint> sprint = sprintRepository.findById(sprintId);
        if (sprint.isPresent()) {
            return sprint.get();
        } else {
            throw new SprintNotFoundException();
        }
    }

    public void delete(UUID sprintId) {
        Optional<Sprint> sprint = sprintRepository.findById(sprintId);
        if (sprint.isPresent()) {
            sprintRepository.deleteById(sprintId);
        } else {
            throw new SprintNotFoundException();
        }
    }

    public Sprint update(UUID sprintId, LocalDate deadline) {
        Optional<Sprint> sprint = sprintRepository.findById(sprintId);
        if (sprint.isPresent()) {
            Sprint sprintToBeSaved = sprint.get();
            sprintToBeSaved.setDeadline(deadline);
            return sprintRepository.save(sprintToBeSaved);
        } else {
            throw new SprintNotFoundException();
        }
    }
}
