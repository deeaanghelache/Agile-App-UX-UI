package com.unibuc.appbackend.services;

import com.unibuc.appbackend.entities.Sprint;
import com.unibuc.appbackend.exceptions.SprintNotFoundException;
import com.unibuc.appbackend.interfaces.SprintRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SprintServiceTest {

    @InjectMocks
    private SprintService sprintService;

    @Mock
    private SprintRepository sprintRepository;

    @Test
    void createSprint_shouldReturnCreatedSprint() {
        Sprint expectedSprint = new Sprint();
        expectedSprint.setSprintId(UUID.randomUUID());
        expectedSprint.setDeadline(LocalDate.now());

        when(sprintRepository.save(any(Sprint.class))).thenReturn(expectedSprint);

        Sprint result = sprintService.create(expectedSprint);

        assertNotNull(result);
        assertEquals(expectedSprint.getSprintId(), result.getSprintId());
        assertEquals(expectedSprint.getDeadline(), result.getDeadline());

        verify(sprintRepository).save(any(Sprint.class));
    }

    @Test
    void getSprintById_existingSprint_shouldReturnSprint() {
        UUID sprintId = UUID.randomUUID();
        Sprint expectedSprint = new Sprint(sprintId, LocalDate.now());

        when(sprintRepository.findById(sprintId)).thenReturn(Optional.of(expectedSprint));

        Sprint result = sprintService.getSprintById(sprintId);

        assertNotNull(result);
        assertEquals(expectedSprint.getSprintId(), result.getSprintId());
        assertEquals(expectedSprint.getDeadline(), result.getDeadline());

        verify(sprintRepository).findById(sprintId);
    }

    @Test
    void getSprintById_nonexistentSprint_shouldThrowSprintNotFoundException() {
        UUID nonExistentSprintId = UUID.randomUUID();

        when(sprintRepository.findById(nonExistentSprintId)).thenReturn(Optional.empty());

        assertThrows(SprintNotFoundException.class, () -> sprintService.getSprintById(nonExistentSprintId));

        verify(sprintRepository).findById(nonExistentSprintId);
    }

    @Test
    public void delete_foundSprint_shouldDelete() {
        UUID sprintId = UUID.randomUUID();
        Sprint sampleSprint = new Sprint(sprintId, LocalDate.now());

        when(sprintRepository.findById(sprintId)).thenReturn(Optional.of(sampleSprint));

        sprintService.delete(sprintId);

        verify(sprintRepository, times(1)).deleteById(sprintId);
    }

    @Test
    public void delete_nonexistentSprint_shouldThrowSprintNotFoundException() {
        UUID nonExistingSprintId = UUID.randomUUID();

        when(sprintRepository.findById(nonExistingSprintId)).thenReturn(Optional.empty());

        assertThrows(SprintNotFoundException.class, () -> sprintService.delete(nonExistingSprintId));

        verify(sprintRepository, times(0)).deleteById(nonExistingSprintId);
    }

    @Test
    public void updateSprint_foundSprint_shouldUpdate() {
        UUID sprintId = UUID.randomUUID();
        LocalDate newDeadline = LocalDate.now().plusDays(7);
        Sprint sampleSprint = new Sprint(sprintId, LocalDate.now());

        when(sprintRepository.findById(sprintId)).thenReturn(Optional.of(sampleSprint));
        when(sprintRepository.save(any(Sprint.class))).thenReturn(sampleSprint);

        Sprint updatedSprint = sprintService.update(sprintId, newDeadline);

        assertEquals(newDeadline, updatedSprint.getDeadline());

        verify(sprintRepository, times(1)).save(any(Sprint.class));
    }

    @Test
    public void update_nonexistingSprint_shouldThrowSprintNotFoundException() {
        UUID nonExistingSprintId = UUID.randomUUID();
        LocalDate newDeadline = LocalDate.now().plusDays(7);

        when(sprintRepository.findById(nonExistingSprintId)).thenReturn(Optional.empty());

        assertThrows(SprintNotFoundException.class, () -> sprintService.update(nonExistingSprintId, newDeadline));

        verify(sprintRepository, times(0)).save(any(Sprint.class));
    }
}
