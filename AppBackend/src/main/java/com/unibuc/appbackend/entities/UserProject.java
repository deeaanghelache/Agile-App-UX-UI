package com.unibuc.appbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unibuc.appbackend.embeddedIds.UserProjectEmbeddedId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class UserProject {
    @EmbeddedId
    @NotNull
    private UserProjectEmbeddedId userProjectEmbeddedId;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("projectId")
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        UserProject that = (UserProject) o;
        return getUserProjectEmbeddedId() != null && Objects.equals(getUserProjectEmbeddedId(), that.getUserProjectEmbeddedId());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(userProjectEmbeddedId);
    }
}
