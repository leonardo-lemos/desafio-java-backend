package dev.leonardolemos.desafiobackendwine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_id_generator")
    private Long id;

    @Override
    public boolean isNew() {
        return id == null;
    }

    @JsonIgnore
    public boolean getNew() {
        return id == null;
    }

}
