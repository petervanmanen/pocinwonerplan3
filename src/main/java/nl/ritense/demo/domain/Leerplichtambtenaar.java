package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Leerplichtambtenaar.
 */
@Entity
@Table(name = "leerplichtambtenaar")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Leerplichtambtenaar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "behandelaarLeerplichtambtenaar")
    @JsonIgnoreProperties(value = { "betreftLeerling", "behandelaarLeerplichtambtenaar", "betreftSchool" }, allowSetters = true)
    private Set<Beslissing> behandelaarBeslissings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Leerplichtambtenaar id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Beslissing> getBehandelaarBeslissings() {
        return this.behandelaarBeslissings;
    }

    public void setBehandelaarBeslissings(Set<Beslissing> beslissings) {
        if (this.behandelaarBeslissings != null) {
            this.behandelaarBeslissings.forEach(i -> i.setBehandelaarLeerplichtambtenaar(null));
        }
        if (beslissings != null) {
            beslissings.forEach(i -> i.setBehandelaarLeerplichtambtenaar(this));
        }
        this.behandelaarBeslissings = beslissings;
    }

    public Leerplichtambtenaar behandelaarBeslissings(Set<Beslissing> beslissings) {
        this.setBehandelaarBeslissings(beslissings);
        return this;
    }

    public Leerplichtambtenaar addBehandelaarBeslissing(Beslissing beslissing) {
        this.behandelaarBeslissings.add(beslissing);
        beslissing.setBehandelaarLeerplichtambtenaar(this);
        return this;
    }

    public Leerplichtambtenaar removeBehandelaarBeslissing(Beslissing beslissing) {
        this.behandelaarBeslissings.remove(beslissing);
        beslissing.setBehandelaarLeerplichtambtenaar(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Leerplichtambtenaar)) {
            return false;
        }
        return getId() != null && getId().equals(((Leerplichtambtenaar) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Leerplichtambtenaar{" +
            "id=" + getId() +
            "}";
    }
}
