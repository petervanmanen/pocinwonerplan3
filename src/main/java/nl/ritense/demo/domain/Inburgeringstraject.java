package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Inburgeringstraject.
 */
@Entity
@Table(name = "inburgeringstraject")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Inburgeringstraject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @JsonIgnoreProperties(value = { "afgerondmetInburgeringstraject", "emptyExamenonderdeels" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "afgerondmetInburgeringstraject")
    private Examen afgerondmetExamen;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Inburgeringstraject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Examen getAfgerondmetExamen() {
        return this.afgerondmetExamen;
    }

    public void setAfgerondmetExamen(Examen examen) {
        if (this.afgerondmetExamen != null) {
            this.afgerondmetExamen.setAfgerondmetInburgeringstraject(null);
        }
        if (examen != null) {
            examen.setAfgerondmetInburgeringstraject(this);
        }
        this.afgerondmetExamen = examen;
    }

    public Inburgeringstraject afgerondmetExamen(Examen examen) {
        this.setAfgerondmetExamen(examen);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inburgeringstraject)) {
            return false;
        }
        return getId() != null && getId().equals(((Inburgeringstraject) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inburgeringstraject{" +
            "id=" + getId() +
            "}";
    }
}
