package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Bruikleen.
 */
@Entity
@Table(name = "bruikleen")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bruikleen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aanvraagdoor")
    private String aanvraagdoor;

    @Column(name = "datumaanvraag")
    private LocalDate datumaanvraag;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "toestemmingdoor")
    private String toestemmingdoor;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_bruikleen__isbedoeldvoor_tentoonstelling",
        joinColumns = @JoinColumn(name = "bruikleen_id"),
        inverseJoinColumns = @JoinColumn(name = "isbedoeldvoor_tentoonstelling_id")
    )
    @JsonIgnoreProperties(
        value = { "voorRondleidings", "isbedoeldvoorBruikleens", "onderdeelMuseumobjects", "steltsamenSamenstellers" },
        allowSetters = true
    )
    private Set<Tentoonstelling> isbedoeldvoorTentoonstellings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftBruikleen")
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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "isBruikleens")
    @JsonIgnoreProperties(value = { "isBruikleens" }, allowSetters = true)
    private Set<Lener> isLeners = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bruikleen id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAanvraagdoor() {
        return this.aanvraagdoor;
    }

    public Bruikleen aanvraagdoor(String aanvraagdoor) {
        this.setAanvraagdoor(aanvraagdoor);
        return this;
    }

    public void setAanvraagdoor(String aanvraagdoor) {
        this.aanvraagdoor = aanvraagdoor;
    }

    public LocalDate getDatumaanvraag() {
        return this.datumaanvraag;
    }

    public Bruikleen datumaanvraag(LocalDate datumaanvraag) {
        this.setDatumaanvraag(datumaanvraag);
        return this;
    }

    public void setDatumaanvraag(LocalDate datumaanvraag) {
        this.datumaanvraag = datumaanvraag;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Bruikleen datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Bruikleen datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public String getToestemmingdoor() {
        return this.toestemmingdoor;
    }

    public Bruikleen toestemmingdoor(String toestemmingdoor) {
        this.setToestemmingdoor(toestemmingdoor);
        return this;
    }

    public void setToestemmingdoor(String toestemmingdoor) {
        this.toestemmingdoor = toestemmingdoor;
    }

    public Set<Tentoonstelling> getIsbedoeldvoorTentoonstellings() {
        return this.isbedoeldvoorTentoonstellings;
    }

    public void setIsbedoeldvoorTentoonstellings(Set<Tentoonstelling> tentoonstellings) {
        this.isbedoeldvoorTentoonstellings = tentoonstellings;
    }

    public Bruikleen isbedoeldvoorTentoonstellings(Set<Tentoonstelling> tentoonstellings) {
        this.setIsbedoeldvoorTentoonstellings(tentoonstellings);
        return this;
    }

    public Bruikleen addIsbedoeldvoorTentoonstelling(Tentoonstelling tentoonstelling) {
        this.isbedoeldvoorTentoonstellings.add(tentoonstelling);
        return this;
    }

    public Bruikleen removeIsbedoeldvoorTentoonstelling(Tentoonstelling tentoonstelling) {
        this.isbedoeldvoorTentoonstellings.remove(tentoonstelling);
        return this;
    }

    public Set<Museumobject> getBetreftMuseumobjects() {
        return this.betreftMuseumobjects;
    }

    public void setBetreftMuseumobjects(Set<Museumobject> museumobjects) {
        if (this.betreftMuseumobjects != null) {
            this.betreftMuseumobjects.forEach(i -> i.setBetreftBruikleen(null));
        }
        if (museumobjects != null) {
            museumobjects.forEach(i -> i.setBetreftBruikleen(this));
        }
        this.betreftMuseumobjects = museumobjects;
    }

    public Bruikleen betreftMuseumobjects(Set<Museumobject> museumobjects) {
        this.setBetreftMuseumobjects(museumobjects);
        return this;
    }

    public Bruikleen addBetreftMuseumobject(Museumobject museumobject) {
        this.betreftMuseumobjects.add(museumobject);
        museumobject.setBetreftBruikleen(this);
        return this;
    }

    public Bruikleen removeBetreftMuseumobject(Museumobject museumobject) {
        this.betreftMuseumobjects.remove(museumobject);
        museumobject.setBetreftBruikleen(null);
        return this;
    }

    public Set<Lener> getIsLeners() {
        return this.isLeners;
    }

    public void setIsLeners(Set<Lener> leners) {
        if (this.isLeners != null) {
            this.isLeners.forEach(i -> i.removeIsBruikleen(this));
        }
        if (leners != null) {
            leners.forEach(i -> i.addIsBruikleen(this));
        }
        this.isLeners = leners;
    }

    public Bruikleen isLeners(Set<Lener> leners) {
        this.setIsLeners(leners);
        return this;
    }

    public Bruikleen addIsLener(Lener lener) {
        this.isLeners.add(lener);
        lener.getIsBruikleens().add(this);
        return this;
    }

    public Bruikleen removeIsLener(Lener lener) {
        this.isLeners.remove(lener);
        lener.getIsBruikleens().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bruikleen)) {
            return false;
        }
        return getId() != null && getId().equals(((Bruikleen) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bruikleen{" +
            "id=" + getId() +
            ", aanvraagdoor='" + getAanvraagdoor() + "'" +
            ", datumaanvraag='" + getDatumaanvraag() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", toestemmingdoor='" + getToestemmingdoor() + "'" +
            "}";
    }
}
