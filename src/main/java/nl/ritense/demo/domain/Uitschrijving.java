package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Uitschrijving.
 */
@Entity
@Table(name = "uitschrijving")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Uitschrijving implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datum")
    private LocalDate datum;

    @Column(name = "diplomabehaald")
    private Boolean diplomabehaald;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftStartkwalificatie",
            "heeftVerzuimmeldings",
            "heeftVrijstellings",
            "heeftInschrijvings",
            "heeftOnderwijsloopbaans",
            "heeftUitschrijvings",
            "betreftBeslissings",
        },
        allowSetters = true
    )
    private Leerling heeftLeerling;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftUitschrijvings",
            "kentOnderwijsloopbaans",
            "heeftOnderwijssoorts",
            "gebruiktSportlocaties",
            "betreftBeslissings",
            "heeftVerzuimmeldings",
            "heeftVrijstellings",
            "heeftInschrijvings",
        },
        allowSetters = true
    )
    private School heeftSchool;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Uitschrijving id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public Uitschrijving datum(LocalDate datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public Boolean getDiplomabehaald() {
        return this.diplomabehaald;
    }

    public Uitschrijving diplomabehaald(Boolean diplomabehaald) {
        this.setDiplomabehaald(diplomabehaald);
        return this;
    }

    public void setDiplomabehaald(Boolean diplomabehaald) {
        this.diplomabehaald = diplomabehaald;
    }

    public Leerling getHeeftLeerling() {
        return this.heeftLeerling;
    }

    public void setHeeftLeerling(Leerling leerling) {
        this.heeftLeerling = leerling;
    }

    public Uitschrijving heeftLeerling(Leerling leerling) {
        this.setHeeftLeerling(leerling);
        return this;
    }

    public School getHeeftSchool() {
        return this.heeftSchool;
    }

    public void setHeeftSchool(School school) {
        this.heeftSchool = school;
    }

    public Uitschrijving heeftSchool(School school) {
        this.setHeeftSchool(school);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Uitschrijving)) {
            return false;
        }
        return getId() != null && getId().equals(((Uitschrijving) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Uitschrijving{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", diplomabehaald='" + getDiplomabehaald() + "'" +
            "}";
    }
}
