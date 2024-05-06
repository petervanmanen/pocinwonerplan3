package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Collectie.
 */
@Entity
@Table(name = "collectie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Collectie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_collectie__bevat_museumobject",
        joinColumns = @JoinColumn(name = "collectie_id"),
        inverseJoinColumns = @JoinColumn(name = "bevat_museumobject_id")
    )
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
    private Set<Museumobject> bevatMuseumobjects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Collectie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Collectie naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Collectie omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Museumobject> getBevatMuseumobjects() {
        return this.bevatMuseumobjects;
    }

    public void setBevatMuseumobjects(Set<Museumobject> museumobjects) {
        this.bevatMuseumobjects = museumobjects;
    }

    public Collectie bevatMuseumobjects(Set<Museumobject> museumobjects) {
        this.setBevatMuseumobjects(museumobjects);
        return this;
    }

    public Collectie addBevatMuseumobject(Museumobject museumobject) {
        this.bevatMuseumobjects.add(museumobject);
        return this;
    }

    public Collectie removeBevatMuseumobject(Museumobject museumobject) {
        this.bevatMuseumobjects.remove(museumobject);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Collectie)) {
            return false;
        }
        return getId() != null && getId().equals(((Collectie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Collectie{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
