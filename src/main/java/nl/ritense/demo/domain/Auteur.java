package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Auteur.
 */
@Entity
@Table(name = "auteur")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Auteur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumgeboorte")
    private LocalDate datumgeboorte;

    @Column(name = "datumoverlijden")
    private LocalDate datumoverlijden;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Auteur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumgeboorte() {
        return this.datumgeboorte;
    }

    public Auteur datumgeboorte(LocalDate datumgeboorte) {
        this.setDatumgeboorte(datumgeboorte);
        return this;
    }

    public void setDatumgeboorte(LocalDate datumgeboorte) {
        this.datumgeboorte = datumgeboorte;
    }

    public LocalDate getDatumoverlijden() {
        return this.datumoverlijden;
    }

    public Auteur datumoverlijden(LocalDate datumoverlijden) {
        this.setDatumoverlijden(datumoverlijden);
        return this;
    }

    public void setDatumoverlijden(LocalDate datumoverlijden) {
        this.datumoverlijden = datumoverlijden;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Auteur)) {
            return false;
        }
        return getId() != null && getId().equals(((Auteur) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Auteur{" +
            "id=" + getId() +
            ", datumgeboorte='" + getDatumgeboorte() + "'" +
            ", datumoverlijden='" + getDatumoverlijden() + "'" +
            "}";
    }
}
