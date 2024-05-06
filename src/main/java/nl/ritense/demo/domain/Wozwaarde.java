package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Wozwaarde.
 */
@Entity
@Table(name = "wozwaarde")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Wozwaarde implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumpeilingtoestand")
    private LocalDate datumpeilingtoestand;

    @Column(name = "datumwaardepeiling")
    private LocalDate datumwaardepeiling;

    @Column(name = "statusbeschikking")
    private String statusbeschikking;

    @Column(name = "vastgesteldewaarde")
    private String vastgesteldewaarde;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Wozwaarde id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumpeilingtoestand() {
        return this.datumpeilingtoestand;
    }

    public Wozwaarde datumpeilingtoestand(LocalDate datumpeilingtoestand) {
        this.setDatumpeilingtoestand(datumpeilingtoestand);
        return this;
    }

    public void setDatumpeilingtoestand(LocalDate datumpeilingtoestand) {
        this.datumpeilingtoestand = datumpeilingtoestand;
    }

    public LocalDate getDatumwaardepeiling() {
        return this.datumwaardepeiling;
    }

    public Wozwaarde datumwaardepeiling(LocalDate datumwaardepeiling) {
        this.setDatumwaardepeiling(datumwaardepeiling);
        return this;
    }

    public void setDatumwaardepeiling(LocalDate datumwaardepeiling) {
        this.datumwaardepeiling = datumwaardepeiling;
    }

    public String getStatusbeschikking() {
        return this.statusbeschikking;
    }

    public Wozwaarde statusbeschikking(String statusbeschikking) {
        this.setStatusbeschikking(statusbeschikking);
        return this;
    }

    public void setStatusbeschikking(String statusbeschikking) {
        this.statusbeschikking = statusbeschikking;
    }

    public String getVastgesteldewaarde() {
        return this.vastgesteldewaarde;
    }

    public Wozwaarde vastgesteldewaarde(String vastgesteldewaarde) {
        this.setVastgesteldewaarde(vastgesteldewaarde);
        return this;
    }

    public void setVastgesteldewaarde(String vastgesteldewaarde) {
        this.vastgesteldewaarde = vastgesteldewaarde;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wozwaarde)) {
            return false;
        }
        return getId() != null && getId().equals(((Wozwaarde) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Wozwaarde{" +
            "id=" + getId() +
            ", datumpeilingtoestand='" + getDatumpeilingtoestand() + "'" +
            ", datumwaardepeiling='" + getDatumwaardepeiling() + "'" +
            ", statusbeschikking='" + getStatusbeschikking() + "'" +
            ", vastgesteldewaarde='" + getVastgesteldewaarde() + "'" +
            "}";
    }
}
