package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Examen.
 */
@Entity
@Table(name = "examen")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Examen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @JsonIgnoreProperties(value = { "afgerondmetExamen" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Inburgeringstraject afgerondmetInburgeringstraject;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "emptyExamen")
    @JsonIgnoreProperties(value = { "emptyExamen" }, allowSetters = true)
    private Set<Examenonderdeel> emptyExamenonderdeels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Examen id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Inburgeringstraject getAfgerondmetInburgeringstraject() {
        return this.afgerondmetInburgeringstraject;
    }

    public void setAfgerondmetInburgeringstraject(Inburgeringstraject inburgeringstraject) {
        this.afgerondmetInburgeringstraject = inburgeringstraject;
    }

    public Examen afgerondmetInburgeringstraject(Inburgeringstraject inburgeringstraject) {
        this.setAfgerondmetInburgeringstraject(inburgeringstraject);
        return this;
    }

    public Set<Examenonderdeel> getEmptyExamenonderdeels() {
        return this.emptyExamenonderdeels;
    }

    public void setEmptyExamenonderdeels(Set<Examenonderdeel> examenonderdeels) {
        if (this.emptyExamenonderdeels != null) {
            this.emptyExamenonderdeels.forEach(i -> i.setEmptyExamen(null));
        }
        if (examenonderdeels != null) {
            examenonderdeels.forEach(i -> i.setEmptyExamen(this));
        }
        this.emptyExamenonderdeels = examenonderdeels;
    }

    public Examen emptyExamenonderdeels(Set<Examenonderdeel> examenonderdeels) {
        this.setEmptyExamenonderdeels(examenonderdeels);
        return this;
    }

    public Examen addEmptyExamenonderdeel(Examenonderdeel examenonderdeel) {
        this.emptyExamenonderdeels.add(examenonderdeel);
        examenonderdeel.setEmptyExamen(this);
        return this;
    }

    public Examen removeEmptyExamenonderdeel(Examenonderdeel examenonderdeel) {
        this.emptyExamenonderdeels.remove(examenonderdeel);
        examenonderdeel.setEmptyExamen(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Examen)) {
            return false;
        }
        return getId() != null && getId().equals(((Examen) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Examen{" +
            "id=" + getId() +
            "}";
    }
}
