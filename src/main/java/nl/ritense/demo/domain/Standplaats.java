package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Standplaats.
 */
@Entity
@Table(name = "standplaats")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Standplaats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "adres")
    private String adres;

    @Column(name = "beschrijving")
    private String beschrijving;

    @Column(name = "naaminstelling")
    private String naaminstelling;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "locatieStandplaats")
    @JsonIgnoreProperties(
        value = {
            "betreftBruikleen",
            "locatieStandplaats",
            "heeftBelanghebbendes",
            "onderdeelTentoonstellings",
            "bevatCollecties",
            "betreftIncidents",
        },
        allowSetters = true
    )
    private Set<Museumobject> locatieMuseumobjects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Standplaats id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdres() {
        return this.adres;
    }

    public Standplaats adres(String adres) {
        this.setAdres(adres);
        return this;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getBeschrijving() {
        return this.beschrijving;
    }

    public Standplaats beschrijving(String beschrijving) {
        this.setBeschrijving(beschrijving);
        return this;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public String getNaaminstelling() {
        return this.naaminstelling;
    }

    public Standplaats naaminstelling(String naaminstelling) {
        this.setNaaminstelling(naaminstelling);
        return this;
    }

    public void setNaaminstelling(String naaminstelling) {
        this.naaminstelling = naaminstelling;
    }

    public Set<Museumobject> getLocatieMuseumobjects() {
        return this.locatieMuseumobjects;
    }

    public void setLocatieMuseumobjects(Set<Museumobject> museumobjects) {
        if (this.locatieMuseumobjects != null) {
            this.locatieMuseumobjects.forEach(i -> i.setLocatieStandplaats(null));
        }
        if (museumobjects != null) {
            museumobjects.forEach(i -> i.setLocatieStandplaats(this));
        }
        this.locatieMuseumobjects = museumobjects;
    }

    public Standplaats locatieMuseumobjects(Set<Museumobject> museumobjects) {
        this.setLocatieMuseumobjects(museumobjects);
        return this;
    }

    public Standplaats addLocatieMuseumobject(Museumobject museumobject) {
        this.locatieMuseumobjects.add(museumobject);
        museumobject.setLocatieStandplaats(this);
        return this;
    }

    public Standplaats removeLocatieMuseumobject(Museumobject museumobject) {
        this.locatieMuseumobjects.remove(museumobject);
        museumobject.setLocatieStandplaats(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Standplaats)) {
            return false;
        }
        return getId() != null && getId().equals(((Standplaats) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Standplaats{" +
            "id=" + getId() +
            ", adres='" + getAdres() + "'" +
            ", beschrijving='" + getBeschrijving() + "'" +
            ", naaminstelling='" + getNaaminstelling() + "'" +
            "}";
    }
}
