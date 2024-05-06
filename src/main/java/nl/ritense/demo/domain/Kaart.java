package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Kaart.
 */
@Entity
@Table(name = "kaart")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Kaart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "kaart")
    private String kaart;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Kaart id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKaart() {
        return this.kaart;
    }

    public Kaart kaart(String kaart) {
        this.setKaart(kaart);
        return this;
    }

    public void setKaart(String kaart) {
        this.kaart = kaart;
    }

    public String getNaam() {
        return this.naam;
    }

    public Kaart naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Kaart omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Kaart)) {
            return false;
        }
        return getId() != null && getId().equals(((Kaart) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Kaart{" +
            "id=" + getId() +
            ", kaart='" + getKaart() + "'" +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
