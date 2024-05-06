package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Containertype.
 */
@Entity
@Table(name = "containertype")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Containertype implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "soortContainertype")
    @JsonIgnoreProperties(
        value = { "heeftVulgraadmetings", "geschiktvoorFractie", "soortContainertype", "heeftLocatie", "gelostOphaalmoments" },
        allowSetters = true
    )
    private Set<Container> soortContainers = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftContainertype")
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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "geschiktvoorContainertypes")
    @JsonIgnoreProperties(value = { "geschiktvoorContainertypes", "uitgevoerdmetRits" }, allowSetters = true)
    private Set<Vuilniswagen> geschiktvoorVuilniswagens = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Containertype id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Containertype naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Containertype omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Container> getSoortContainers() {
        return this.soortContainers;
    }

    public void setSoortContainers(Set<Container> containers) {
        if (this.soortContainers != null) {
            this.soortContainers.forEach(i -> i.setSoortContainertype(null));
        }
        if (containers != null) {
            containers.forEach(i -> i.setSoortContainertype(this));
        }
        this.soortContainers = containers;
    }

    public Containertype soortContainers(Set<Container> containers) {
        this.setSoortContainers(containers);
        return this;
    }

    public Containertype addSoortContainer(Container container) {
        this.soortContainers.add(container);
        container.setSoortContainertype(this);
        return this;
    }

    public Containertype removeSoortContainer(Container container) {
        this.soortContainers.remove(container);
        container.setSoortContainertype(null);
        return this;
    }

    public Set<Melding> getBetreftMeldings() {
        return this.betreftMeldings;
    }

    public void setBetreftMeldings(Set<Melding> meldings) {
        if (this.betreftMeldings != null) {
            this.betreftMeldings.forEach(i -> i.setBetreftContainertype(null));
        }
        if (meldings != null) {
            meldings.forEach(i -> i.setBetreftContainertype(this));
        }
        this.betreftMeldings = meldings;
    }

    public Containertype betreftMeldings(Set<Melding> meldings) {
        this.setBetreftMeldings(meldings);
        return this;
    }

    public Containertype addBetreftMelding(Melding melding) {
        this.betreftMeldings.add(melding);
        melding.setBetreftContainertype(this);
        return this;
    }

    public Containertype removeBetreftMelding(Melding melding) {
        this.betreftMeldings.remove(melding);
        melding.setBetreftContainertype(null);
        return this;
    }

    public Set<Vuilniswagen> getGeschiktvoorVuilniswagens() {
        return this.geschiktvoorVuilniswagens;
    }

    public void setGeschiktvoorVuilniswagens(Set<Vuilniswagen> vuilniswagens) {
        if (this.geschiktvoorVuilniswagens != null) {
            this.geschiktvoorVuilniswagens.forEach(i -> i.removeGeschiktvoorContainertype(this));
        }
        if (vuilniswagens != null) {
            vuilniswagens.forEach(i -> i.addGeschiktvoorContainertype(this));
        }
        this.geschiktvoorVuilniswagens = vuilniswagens;
    }

    public Containertype geschiktvoorVuilniswagens(Set<Vuilniswagen> vuilniswagens) {
        this.setGeschiktvoorVuilniswagens(vuilniswagens);
        return this;
    }

    public Containertype addGeschiktvoorVuilniswagen(Vuilniswagen vuilniswagen) {
        this.geschiktvoorVuilniswagens.add(vuilniswagen);
        vuilniswagen.getGeschiktvoorContainertypes().add(this);
        return this;
    }

    public Containertype removeGeschiktvoorVuilniswagen(Vuilniswagen vuilniswagen) {
        this.geschiktvoorVuilniswagens.remove(vuilniswagen);
        vuilniswagen.getGeschiktvoorContainertypes().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Containertype)) {
            return false;
        }
        return getId() != null && getId().equals(((Containertype) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Containertype{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
