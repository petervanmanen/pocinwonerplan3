package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Incident.
 */
@Entity
@Table(name = "incident")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Incident implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datum")
    private LocalDate datum;

    @Column(name = "locatie")
    private String locatie;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_incident__betreft_museumobject",
        joinColumns = @JoinColumn(name = "incident_id"),
        inverseJoinColumns = @JoinColumn(name = "betreft_museumobject_id")
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
    private Set<Museumobject> betreftMuseumobjects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Incident id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public Incident datum(LocalDate datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getLocatie() {
        return this.locatie;
    }

    public Incident locatie(String locatie) {
        this.setLocatie(locatie);
        return this;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getNaam() {
        return this.naam;
    }

    public Incident naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Incident omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Museumobject> getBetreftMuseumobjects() {
        return this.betreftMuseumobjects;
    }

    public void setBetreftMuseumobjects(Set<Museumobject> museumobjects) {
        this.betreftMuseumobjects = museumobjects;
    }

    public Incident betreftMuseumobjects(Set<Museumobject> museumobjects) {
        this.setBetreftMuseumobjects(museumobjects);
        return this;
    }

    public Incident addBetreftMuseumobject(Museumobject museumobject) {
        this.betreftMuseumobjects.add(museumobject);
        return this;
    }

    public Incident removeBetreftMuseumobject(Museumobject museumobject) {
        this.betreftMuseumobjects.remove(museumobject);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Incident)) {
            return false;
        }
        return getId() != null && getId().equals(((Incident) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Incident{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", locatie='" + getLocatie() + "'" +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
