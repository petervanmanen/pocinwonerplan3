package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Startkwalificatie.
 */
@Entity
@Table(name = "startkwalificatie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Startkwalificatie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbehaald")
    private LocalDate datumbehaald;

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
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "heeftStartkwalificatie")
    private Leerling heeftLeerling;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Startkwalificatie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbehaald() {
        return this.datumbehaald;
    }

    public Startkwalificatie datumbehaald(LocalDate datumbehaald) {
        this.setDatumbehaald(datumbehaald);
        return this;
    }

    public void setDatumbehaald(LocalDate datumbehaald) {
        this.datumbehaald = datumbehaald;
    }

    public Leerling getHeeftLeerling() {
        return this.heeftLeerling;
    }

    public void setHeeftLeerling(Leerling leerling) {
        if (this.heeftLeerling != null) {
            this.heeftLeerling.setHeeftStartkwalificatie(null);
        }
        if (leerling != null) {
            leerling.setHeeftStartkwalificatie(this);
        }
        this.heeftLeerling = leerling;
    }

    public Startkwalificatie heeftLeerling(Leerling leerling) {
        this.setHeeftLeerling(leerling);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Startkwalificatie)) {
            return false;
        }
        return getId() != null && getId().equals(((Startkwalificatie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Startkwalificatie{" +
            "id=" + getId() +
            ", datumbehaald='" + getDatumbehaald() + "'" +
            "}";
    }
}
