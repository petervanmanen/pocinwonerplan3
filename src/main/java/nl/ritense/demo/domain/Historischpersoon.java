package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Historischpersoon.
 */
@Entity
@Table(name = "historischpersoon")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Historischpersoon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "beroep")
    private String beroep;

    @Column(name = "datumgeboorte")
    private LocalDate datumgeboorte;

    @Column(name = "datumoverlijden")
    private LocalDate datumoverlijden;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "publiektoegankelijk")
    private String publiektoegankelijk;

    @Column(name = "woondeop")
    private String woondeop;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Historischpersoon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeroep() {
        return this.beroep;
    }

    public Historischpersoon beroep(String beroep) {
        this.setBeroep(beroep);
        return this;
    }

    public void setBeroep(String beroep) {
        this.beroep = beroep;
    }

    public LocalDate getDatumgeboorte() {
        return this.datumgeboorte;
    }

    public Historischpersoon datumgeboorte(LocalDate datumgeboorte) {
        this.setDatumgeboorte(datumgeboorte);
        return this;
    }

    public void setDatumgeboorte(LocalDate datumgeboorte) {
        this.datumgeboorte = datumgeboorte;
    }

    public LocalDate getDatumoverlijden() {
        return this.datumoverlijden;
    }

    public Historischpersoon datumoverlijden(LocalDate datumoverlijden) {
        this.setDatumoverlijden(datumoverlijden);
        return this;
    }

    public void setDatumoverlijden(LocalDate datumoverlijden) {
        this.datumoverlijden = datumoverlijden;
    }

    public String getNaam() {
        return this.naam;
    }

    public Historischpersoon naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Historischpersoon omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getPubliektoegankelijk() {
        return this.publiektoegankelijk;
    }

    public Historischpersoon publiektoegankelijk(String publiektoegankelijk) {
        this.setPubliektoegankelijk(publiektoegankelijk);
        return this;
    }

    public void setPubliektoegankelijk(String publiektoegankelijk) {
        this.publiektoegankelijk = publiektoegankelijk;
    }

    public String getWoondeop() {
        return this.woondeop;
    }

    public Historischpersoon woondeop(String woondeop) {
        this.setWoondeop(woondeop);
        return this;
    }

    public void setWoondeop(String woondeop) {
        this.woondeop = woondeop;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Historischpersoon)) {
            return false;
        }
        return getId() != null && getId().equals(((Historischpersoon) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Historischpersoon{" +
            "id=" + getId() +
            ", beroep='" + getBeroep() + "'" +
            ", datumgeboorte='" + getDatumgeboorte() + "'" +
            ", datumoverlijden='" + getDatumoverlijden() + "'" +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", publiektoegankelijk='" + getPubliektoegankelijk() + "'" +
            ", woondeop='" + getWoondeop() + "'" +
            "}";
    }
}
