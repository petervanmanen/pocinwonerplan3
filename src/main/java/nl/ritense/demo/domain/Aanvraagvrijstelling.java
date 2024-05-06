package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Aanvraagvrijstelling.
 */
@Entity
@Table(name = "aanvraagvrijstelling")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Aanvraagvrijstelling implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "buitenlandseschoollocatie")
    private String buitenlandseschoollocatie;

    @Column(name = "datumaanvraag")
    private LocalDate datumaanvraag;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Aanvraagvrijstelling id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuitenlandseschoollocatie() {
        return this.buitenlandseschoollocatie;
    }

    public Aanvraagvrijstelling buitenlandseschoollocatie(String buitenlandseschoollocatie) {
        this.setBuitenlandseschoollocatie(buitenlandseschoollocatie);
        return this;
    }

    public void setBuitenlandseschoollocatie(String buitenlandseschoollocatie) {
        this.buitenlandseschoollocatie = buitenlandseschoollocatie;
    }

    public LocalDate getDatumaanvraag() {
        return this.datumaanvraag;
    }

    public Aanvraagvrijstelling datumaanvraag(LocalDate datumaanvraag) {
        this.setDatumaanvraag(datumaanvraag);
        return this;
    }

    public void setDatumaanvraag(LocalDate datumaanvraag) {
        this.datumaanvraag = datumaanvraag;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aanvraagvrijstelling)) {
            return false;
        }
        return getId() != null && getId().equals(((Aanvraagvrijstelling) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Aanvraagvrijstelling{" +
            "id=" + getId() +
            ", buitenlandseschoollocatie='" + getBuitenlandseschoollocatie() + "'" +
            ", datumaanvraag='" + getDatumaanvraag() + "'" +
            "}";
    }
}
