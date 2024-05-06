package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Zaal.
 */
@Entity
@Table(name = "zaal")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Zaal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "capaciteit")
    private String capaciteit;

    @Column(name = "naam")
    private String naam;

    @Column(name = "nummer")
    private String nummer;

    @Column(name = "omschrijving")
    private String omschrijving;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftZaal")
    @JsonIgnoreProperties(value = { "betreftVoorziening", "betreftZaal", "heeftActiviteit" }, allowSetters = true)
    private Set<Reservering> betreftReserverings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Zaal id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCapaciteit() {
        return this.capaciteit;
    }

    public Zaal capaciteit(String capaciteit) {
        this.setCapaciteit(capaciteit);
        return this;
    }

    public void setCapaciteit(String capaciteit) {
        this.capaciteit = capaciteit;
    }

    public String getNaam() {
        return this.naam;
    }

    public Zaal naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getNummer() {
        return this.nummer;
    }

    public Zaal nummer(String nummer) {
        this.setNummer(nummer);
        return this;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Zaal omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Reservering> getBetreftReserverings() {
        return this.betreftReserverings;
    }

    public void setBetreftReserverings(Set<Reservering> reserverings) {
        if (this.betreftReserverings != null) {
            this.betreftReserverings.forEach(i -> i.setBetreftZaal(null));
        }
        if (reserverings != null) {
            reserverings.forEach(i -> i.setBetreftZaal(this));
        }
        this.betreftReserverings = reserverings;
    }

    public Zaal betreftReserverings(Set<Reservering> reserverings) {
        this.setBetreftReserverings(reserverings);
        return this;
    }

    public Zaal addBetreftReservering(Reservering reservering) {
        this.betreftReserverings.add(reservering);
        reservering.setBetreftZaal(this);
        return this;
    }

    public Zaal removeBetreftReservering(Reservering reservering) {
        this.betreftReserverings.remove(reservering);
        reservering.setBetreftZaal(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Zaal)) {
            return false;
        }
        return getId() != null && getId().equals(((Zaal) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Zaal{" +
            "id=" + getId() +
            ", capaciteit='" + getCapaciteit() + "'" +
            ", naam='" + getNaam() + "'" +
            ", nummer='" + getNummer() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
