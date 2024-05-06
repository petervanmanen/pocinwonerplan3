package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Examenonderdeel.
 */
@Entity
@Table(name = "examenonderdeel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Examenonderdeel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "afgerondmetInburgeringstraject", "emptyExamenonderdeels" }, allowSetters = true)
    private Examen emptyExamen;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Examenonderdeel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Examen getEmptyExamen() {
        return this.emptyExamen;
    }

    public void setEmptyExamen(Examen examen) {
        this.emptyExamen = examen;
    }

    public Examenonderdeel emptyExamen(Examen examen) {
        this.setEmptyExamen(examen);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Examenonderdeel)) {
            return false;
        }
        return getId() != null && getId().equals(((Examenonderdeel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Examenonderdeel{" +
            "id=" + getId() +
            "}";
    }
}
