package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Omgevingsvergunning.
 */
@Entity
@Table(name = "omgevingsvergunning")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Omgevingsvergunning implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "bestaatuitGebouws",
            "binnenprogrammaProgramma",
            "isprojectleidervanProjectleider",
            "betrekkingopOmgevingsvergunnings",
            "heeftProjectontwikkelaars",
        },
        allowSetters = true
    )
    private Plan betrekkingopPlan;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Omgevingsvergunning id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Plan getBetrekkingopPlan() {
        return this.betrekkingopPlan;
    }

    public void setBetrekkingopPlan(Plan plan) {
        this.betrekkingopPlan = plan;
    }

    public Omgevingsvergunning betrekkingopPlan(Plan plan) {
        this.setBetrekkingopPlan(plan);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Omgevingsvergunning)) {
            return false;
        }
        return getId() != null && getId().equals(((Omgevingsvergunning) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Omgevingsvergunning{" +
            "id=" + getId() +
            "}";
    }
}
