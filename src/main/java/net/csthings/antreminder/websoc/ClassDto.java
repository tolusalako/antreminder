package net.csthings.antreminder.websoc;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Entity
@Table(name = "class")
public @Data class ClassDto {

    @Id
    @GeneratedValue
    private UUID id;

    public String shortName;

    public String longName;

    public ClassDto() {
    }

    public ClassDto(UUID id, String shortName, String longName) {
        this.id = id;
        this.shortName = shortName;
        this.longName = longName;
    }

}
