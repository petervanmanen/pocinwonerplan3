package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Fractie.
 */
@Entity
@Table(name = "fractie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Fractie implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "geschiktvoorFractie")
    @JsonIgnoreProperties(
        value = { "heeftVulgraadmetings", "geschiktvoorFractie", "soortContainertype", "heeftLocatie", "gelostOphaalmoments" },
        allowSetters = true
    )
    private Set<Container> geschiktvoorContainers = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftFractie")
    @JsonIgnoreProperties(
        value = {
            "hoofdcategorieCategorie",
            "subcategorieCategorie",
            "betreftContainertype",
            "betreftFractie",
            "betreftLocatie",
            "melderMedewerker",
            "uitvoerderLeverancier",
            "uitvoerderMedewerker",
            "betreftBeheerobjects",
            "heeftSchouwronde",
        },
        allowSetters = true
    )
    private Set<Melding> betreftMeldings = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "inzamelpuntvanFracties")
    @JsonIgnoreProperties(value = { "inzamelpuntvanFracties", "bijStortings", "geldigvoorPas" }, allowSetters = true)
    private Set<Milieustraat> inzamelpuntvanMilieustraats = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "fractieFracties")
    @JsonIgnoreProperties(value = { "bijMilieustraat", "fractieFracties", "uitgevoerdestortingPas" }, allowSetters = true)
    private Set<Storting> fractieStortings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Fractie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Fractie naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Fractie omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Container> getGeschiktvoorContainers() {
        return this.geschiktvoorContainers;
    }

    public void setGeschiktvoorContainers(Set<Container> containers) {
        if (this.geschiktvoorContainers != null) {
            this.geschiktvoorContainers.forEach(i -> i.setGeschiktvoorFractie(null));
        }
        if (containers != null) {
            containers.forEach(i -> i.setGeschiktvoorFractie(this));
        }
        this.geschiktvoorContainers = containers;
    }

    public Fractie geschiktvoorContainers(Set<Container> containers) {
        this.setGeschiktvoorContainers(containers);
        return this;
    }

    public Fractie addGeschiktvoorContainer(Container container) {
        this.geschiktvoorContainers.add(container);
        container.setGeschiktvoorFractie(this);
        return this;
    }

    public Fractie removeGeschiktvoorContainer(Container container) {
        this.geschiktvoorContainers.remove(container);
        container.setGeschiktvoorFractie(null);
        return this;
    }

    public Set<Melding> getBetreftMeldings() {
        return this.betreftMeldings;
    }

    public void setBetreftMeldings(Set<Melding> meldings) {
        if (this.betreftMeldings != null) {
            this.betreftMeldings.forEach(i -> i.setBetreftFractie(null));
        }
        if (meldings != null) {
            meldings.forEach(i -> i.setBetreftFractie(this));
        }
        this.betreftMeldings = meldings;
    }

    public Fractie betreftMeldings(Set<Melding> meldings) {
        this.setBetreftMeldings(meldings);
        return this;
    }

    public Fractie addBetreftMelding(Melding melding) {
        this.betreftMeldings.add(melding);
        melding.setBetreftFractie(this);
        return this;
    }

    public Fractie removeBetreftMelding(Melding melding) {
        this.betreftMeldings.remove(melding);
        melding.setBetreftFractie(null);
        return this;
    }

    public Set<Milieustraat> getInzamelpuntvanMilieustraats() {
        return this.inzamelpuntvanMilieustraats;
    }

    public void setInzamelpuntvanMilieustraats(Set<Milieustraat> milieustraats) {
        if (this.inzamelpuntvanMilieustraats != null) {
            this.inzamelpuntvanMilieustraats.forEach(i -> i.removeInzamelpuntvanFractie(this));
        }
        if (milieustraats != null) {
            milieustraats.forEach(i -> i.addInzamelpuntvanFractie(this));
        }
        this.inzamelpuntvanMilieustraats = milieustraats;
    }

    public Fractie inzamelpuntvanMilieustraats(Set<Milieustraat> milieustraats) {
        this.setInzamelpuntvanMilieustraats(milieustraats);
        return this;
    }

    public Fractie addInzamelpuntvanMilieustraat(Milieustraat milieustraat) {
        this.inzamelpuntvanMilieustraats.add(milieustraat);
        milieustraat.getInzamelpuntvanFracties().add(this);
        return this;
    }

    public Fractie removeInzamelpuntvanMilieustraat(Milieustraat milieustraat) {
        this.inzamelpuntvanMilieustraats.remove(milieustraat);
        milieustraat.getInzamelpuntvanFracties().remove(this);
        return this;
    }

    public Set<Storting> getFractieStortings() {
        return this.fractieStortings;
    }

    public void setFractieStortings(Set<Storting> stortings) {
        if (this.fractieStortings != null) {
            this.fractieStortings.forEach(i -> i.removeFractieFractie(this));
        }
        if (stortings != null) {
            stortings.forEach(i -> i.addFractieFractie(this));
        }
        this.fractieStortings = stortings;
    }

    public Fractie fractieStortings(Set<Storting> stortings) {
        this.setFractieStortings(stortings);
        return this;
    }

    public Fractie addFractieStorting(Storting storting) {
        this.fractieStortings.add(storting);
        storting.getFractieFracties().add(this);
        return this;
    }

    public Fractie removeFractieStorting(Storting storting) {
        this.fractieStortings.remove(storting);
        storting.getFractieFracties().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fractie)) {
            return false;
        }
        return getId() != null && getId().equals(((Fractie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fractie{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
