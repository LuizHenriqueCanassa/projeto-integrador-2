package br.com.luizcanassa.projetintegrador2.domain.projections;

import java.time.LocalDateTime;

public interface UserProjection {

    Long getId();

    String getName();

    String getUsername();

    Boolean getActive();

    LocalDateTime getCreatedAt();
}
