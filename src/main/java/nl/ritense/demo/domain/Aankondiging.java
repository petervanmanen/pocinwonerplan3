package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Aankondiging.
 */
@Entity
@Table(name = "aankondiging")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Aankondiging implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "beschrijving")
    private String beschrijving;

    @Column(name = "categorie")
    private String categorie;

    @Column(name = "datum")
    private String datum;

    @Column(name = "naam")
    private String naam;

    @Column(name = "type")
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "betreftZaak",
            "mondtuitGunning",
            "procesleiderMedewerker",
            "betreftKwalificaties",
            "betreftOfferteaanvraags",
            "mondtuitAankondigings",
            "betreftOffertes",
            "betreftInschrijvings",
        },
        allowSetters = true
    )
    private Aanbesteding mondtuitAanbesteding;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Aankondiging id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeschrijving() {
        return this.beschrijving;
    }

    public Aankondiging beschrijving(String beschrijving) {
        this.setBeschrijving(beschrijving);
        return this;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public String getCategorie() {
        return this.categorie;
    }

    public Aankondiging categorie(String categorie) {
        this.setCategorie(categorie);
        return this;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDatum() {
        return this.datum;
    }

    public Aankondiging datum(String datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getNaam() {
        return this.naam;
    }

    public Aankondiging naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getType() {
        return this.type;
    }

    public Aankondiging type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Aanbesteding getMondtuitAanbesteding() {
        return this.mondtuitAanbesteding;
    }

    public void setMondtuitAanbesteding(Aanbesteding aanbesteding) {
        this.mondtuitAanbesteding = aanbesteding;
    }

    public Aankondiging mondtuitAanbesteding(Aanbesteding aanbesteding) {
        this.setMondtuitAanbesteding(aanbesteding);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aankondiging)) {
            return false;
        }
        return getId() != null && getId().equals(((Aankondiging) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Aankondiging{" +
            "id=" + getId() +
            ", beschrijving='" + getBeschrijving() + "'" +
            ", categorie='" + getCategorie() + "'" +
            ", datum='" + getDatum() + "'" +
            ", naam='" + getNaam() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
