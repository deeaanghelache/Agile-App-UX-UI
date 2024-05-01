package com.unibuc.appbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unibuc.appbackend.enums.TaskAssignedStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@AllArgsConstructor
public class TaskAssigned {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    @NotNull
    private UUID taskAssignedId;

    @Column(columnDefinition = "varchar(1000)")
    @NotNull
    private String description;

    @Column(columnDefinition = "varchar(15)")
    @NotNull
    @Enumerated(EnumType.STRING)
    private TaskAssignedStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    @JsonIgnore
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "projectId", nullable = false)
    @JsonIgnore
    private Project project;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sprint_id", referencedColumnName = "sprintId", nullable = false)
    @JsonIgnore
    @NotNull
    private Sprint sprint;

    @OneToMany(mappedBy = "taskAssigned", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private Set<Subtask> subtasks;

    public TaskAssigned(UUID taskAssignedId, String description, TaskAssignedStatus status) {
        this.taskAssignedId = taskAssignedId;
        this.description = description;
        this.status = status;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        TaskAssigned that = (TaskAssigned) o;
        return getTaskAssignedId() != null && Objects.equals(getTaskAssignedId(), that.getTaskAssignedId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
