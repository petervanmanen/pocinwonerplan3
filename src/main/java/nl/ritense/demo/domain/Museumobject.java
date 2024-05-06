package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Museumobject.
 */
@Entity
@Table(name = "museumobject")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Museumobject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "afmeting")
    private String afmeting;

    @Column(name = "bezittot")
    private LocalDate bezittot;

    @Column(name = "bezitvanaf")
    private LocalDate bezitvanaf;

    @Column(name = "medium")
    private String medium;

    @Column(name = "verkrijging")
    private String verkrijging;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "isbedoeldvoorTentoonstellings", "betreftMuseumobjects", "isLeners" }, allowSetters = true)
    private Bruikleen betreftBruikleen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "locatieMuseumobjects" }, allowSetters = true)
    private Standplaats locatieStandplaats;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_museumobject__heeft_belanghebbende",
        joinColumns = @JoinColumn(name = "museumobject_id"),
        inverseJoinColumns = @JoinColumn(name = "heeft_belanghebbende_id")
    )
    @JsonIgnoreProperties(value = { "heeftMuseumobjects" }, allowSetters = true)
    private Set<Belanghebbende> heeftBelanghebbendes = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_museumobject__onderdeel_tentoonstelling",
        joinColumns = @JoinColumn(name = "museumobject_id"),
        inverseJoinColumns = @JoinColumn(name = "onderdeel_tentoonstelling_id")
    )
    @JsonIgnoreProperties(
        value = { "voorRondleidings", "isbedoeldvoorBruikleens", "onderdeelMuseumobjects", "steltsamenSamenstellers" },
        allowSetters = true
    )
    private Set<Tentoonstelling> onderdeelTentoonstellings = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "bevatMuseumobjects")
    @JsonIgnoreProperties(value = { "bevatMuseumobjects" }, allowSetters = true)
    private Set<Collectie> bevatCollecties = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "betreftMuseumobjects")
    @JsonIgnoreProperties(value = { "betreftMuseumobjects" }, allowSetters = true)
    private Set<Incident> betreftIncidents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Museumobject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAfmeting() {
        return this.afmeting;
    }

    public Museumobject afmeting(String afmeting) {
        this.setAfmeting(afmeting);
        return this;
    }

    public void setAfmeting(String afmeting) {
        this.afmeting = afmeting;
    }

    public LocalDate getBezittot() {
        return this.bezittot;
    }

    public Museumobject bezittot(LocalDate bezittot) {
        this.setBezittot(bezittot);
        return this;
    }

    public void setBezittot(LocalDate bezittot) {
        this.bezittot = bezittot;
    }

    public LocalDate getBezitvanaf() {
        return this.bezitvanaf;
    }

    public Museumobject bezitvanaf(LocalDate bezitvanaf) {
        this.setBezitvanaf(bezitvanaf);
        return this;
    }

    public void setBezitvanaf(LocalDate bezitvanaf) {
        this.bezitvanaf = bezitvanaf;
    }

    public String getMedium() {
        return this.medium;
    }

    public Museumobject medium(String medium) {
        this.setMedium(medium);
        return this;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getVerkrijging() {
        return this.verkrijging;
    }

    public Museumobject verkrijging(String verkrijging) {
        this.setVerkrijging(verkrijging);
        return this;
    }

    public void setVerkrijging(String verkrijging) {
        this.verkrijging = verkrijging;
    }

    public Bruikleen getBetreftBruikleen() {
        return this.betreftBruikleen;
    }

    public void setBetreftBruikleen(Bruikleen bruikleen) {
        this.betreftBruikleen = bruikleen;
    }

    public Museumobject betreftBruikleen(Bruikleen bruikleen) {
        this.setBetreftBruikleen(bruikleen);
        return this;
    }

    public Standplaats getLocatieStandplaats() {
        return this.locatieStandplaats;
    }

    public void setLocatieStandplaats(Standplaats standplaats) {
        this.locatieStandplaats = standplaats;
    }

    public Museumobject locatieStandplaats(Standplaats standplaats) {
        this.setLocatieStandplaats(standplaats);
        return this;
    }

    public Set<Belanghebbende> getHeeftBelanghebbendes() {
        return this.heeftBelanghebbendes;
    }

    public void setHeeftBelanghebbendes(Set<Belanghebbende> belanghebbendes) {
        this.heeftBelanghebbendes = belanghebbendes;
    }

    public Museumobject heeftBelanghebbendes(Set<Belanghebbende> belanghebbendes) {
        this.setHeeftBelanghebbendes(belanghebbendes);
        return this;
    }

    public Museumobject addHeeftBelanghebbende(Belanghebbende belanghebbende) {
        this.heeftBelanghebbendes.add(belanghebbende);
        return this;
    }

    public Museumobject removeHeeftBelanghebbende(Belanghebbende belanghebbende) {
        this.heeftBelanghebbendes.remove(belanghebbende);
        return this;
    }

    public Set<Tentoonstelling> getOnderdeelTentoonstellings() {
        return this.onderdeelTentoonstellings;
    }

    public void setOnderdeelTentoonstellings(Set<Tentoonstelling> tentoonstellings) {
        this.onderdeelTentoonstellings = tentoonstellings;
    }

    public Museumobject onderdeelTentoonstellings(Set<Tentoonstelling> tentoonstellings) {
        this.setOnderdeelTentoonstellings(tentoonstellings);
        return this;
    }

    public Museumobject addOnderdeelTentoonstelling(Tentoonstelling tentoonstelling) {
        this.onderdeelTentoonstellings.add(tentoonstelling);
        return this;
    }

    public Museumobject removeOnderdeelTentoonstelling(Tentoonstelling tentoonstelling) {
        this.onderdeelTentoonstellings.remove(tentoonstelling);
        return this;
    }

    public Set<Collectie> getBevatCollecties() {
        return this.bevatCollecties;
    }

    public void setBevatCollecties(Set<Collectie> collecties) {
        if (this.bevatCollecties != null) {
            this.bevatCollecties.forEach(i -> i.removeBevatMuseumobject(this));
        }
        if (collecties != null) {
            collecties.forEach(i -> i.addBevatMuseumobject(this));
        }
        this.bevatCollecties = collecties;
    }

    public Museumobject bevatCollecties(Set<Collectie> collecties) {
        this.setBevatCollecties(collecties);
        return this;
    }

    public Museumobject addBevatCollectie(Collectie collectie) {
        this.bevatCollecties.add(collectie);
        collectie.getBevatMuseumobjects().add(this);
        return this;
    }

    public Museumobject removeBevatCollectie(Collectie collectie) {
        this.bevatCollecties.remove(collectie);
        collectie.getBevatMuseumobjects().remove(this);
        return this;
    }

    public Set<Incident> getBetreftIncidents() {
        return this.betreftIncidents;
    }

    public void setBetreftIncidents(Set<Incident> incidents) {
        if (this.betreftIncidents != null) {
            this.betreftIncidents.forEach(i -> i.removeBetreftMuseumobject(this));
        }
        if (incidents != null) {
            incidents.forEach(i -> i.addBetreftMuseumobject(this));
        }
        this.betreftIncidents = incidents;
    }

    public Museumobject betreftIncidents(Set<Incident> incidents) {
        this.setBetreftIncidents(incidents);
        return this;
    }

    public Museumobject addBetreftIncident(Incident incident) {
        this.betreftIncidents.add(incident);
        incident.getBetreftMuseumobjects().add(this);
        return this;
    }

    public Museumobject removeBetreftIncident(Incident incident) {
        this.betreftIncidents.remove(incident);
        incident.getBetreftMuseumobjects().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Museumobject)) {
            return false;
        }
        return getId() != null && getId().equals(((Museumobject) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Museumobject{" +
            "id=" + getId() +
            ", afmeting='" + getAfmeting() + "'" +
            ", bezittot='" + getBezittot() + "'" +
            ", bezitvanaf='" + getBezitvanaf() + "'" +
            ", medium='" + getMedium() + "'" +
            ", verkrijging='" + getVerkrijging() + "'" +
            "}";
    }
}
