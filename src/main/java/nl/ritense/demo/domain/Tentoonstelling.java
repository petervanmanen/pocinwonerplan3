package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Tentoonstelling.
 */
@Entity
@Table(name = "tentoonstelling")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tentoonstelling implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "subtitel")
    private String subtitel;

    @Column(name = "titel")
    private String titel;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "voorTentoonstelling")
    @JsonIgnoreProperties(value = { "voorTentoonstelling", "heeftActiviteits" }, allowSetters = true)
    private Set<Rondleiding> voorRondleidings = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "isbedoeldvoorTentoonstellings")
    @JsonIgnoreProperties(value = { "isbedoeldvoorTentoonstellings", "betreftMuseumobjects", "isLeners" }, allowSetters = true)
    private Set<Bruikleen> isbedoeldvoorBruikleens = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "onderdeelTentoonstellings")
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
    private Set<Museumobject> onderdeelMuseumobjects = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "steltsamenTentoonstellings")
    @JsonIgnoreProperties(value = { "steltsamenTentoonstellings" }, allowSetters = true)
    private Set<Samensteller> steltsamenSamenstellers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tentoonstelling id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Tentoonstelling datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Tentoonstelling datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Tentoonstelling omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getSubtitel() {
        return this.subtitel;
    }

    public Tentoonstelling subtitel(String subtitel) {
        this.setSubtitel(subtitel);
        return this;
    }

    public void setSubtitel(String subtitel) {
        this.subtitel = subtitel;
    }

    public String getTitel() {
        return this.titel;
    }

    public Tentoonstelling titel(String titel) {
        this.setTitel(titel);
        return this;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public Set<Rondleiding> getVoorRondleidings() {
        return this.voorRondleidings;
    }

    public void setVoorRondleidings(Set<Rondleiding> rondleidings) {
        if (this.voorRondleidings != null) {
            this.voorRondleidings.forEach(i -> i.setVoorTentoonstelling(null));
        }
        if (rondleidings != null) {
            rondleidings.forEach(i -> i.setVoorTentoonstelling(this));
        }
        this.voorRondleidings = rondleidings;
    }

    public Tentoonstelling voorRondleidings(Set<Rondleiding> rondleidings) {
        this.setVoorRondleidings(rondleidings);
        return this;
    }

    public Tentoonstelling addVoorRondleiding(Rondleiding rondleiding) {
        this.voorRondleidings.add(rondleiding);
        rondleiding.setVoorTentoonstelling(this);
        return this;
    }

    public Tentoonstelling removeVoorRondleiding(Rondleiding rondleiding) {
        this.voorRondleidings.remove(rondleiding);
        rondleiding.setVoorTentoonstelling(null);
        return this;
    }

    public Set<Bruikleen> getIsbedoeldvoorBruikleens() {
        return this.isbedoeldvoorBruikleens;
    }

    public void setIsbedoeldvoorBruikleens(Set<Bruikleen> bruikleens) {
        if (this.isbedoeldvoorBruikleens != null) {
            this.isbedoeldvoorBruikleens.forEach(i -> i.removeIsbedoeldvoorTentoonstelling(this));
        }
        if (bruikleens != null) {
            bruikleens.forEach(i -> i.addIsbedoeldvoorTentoonstelling(this));
        }
        this.isbedoeldvoorBruikleens = bruikleens;
    }

    public Tentoonstelling isbedoeldvoorBruikleens(Set<Bruikleen> bruikleens) {
        this.setIsbedoeldvoorBruikleens(bruikleens);
        return this;
    }

    public Tentoonstelling addIsbedoeldvoorBruikleen(Bruikleen bruikleen) {
        this.isbedoeldvoorBruikleens.add(bruikleen);
        bruikleen.getIsbedoeldvoorTentoonstellings().add(this);
        return this;
    }

    public Tentoonstelling removeIsbedoeldvoorBruikleen(Bruikleen bruikleen) {
        this.isbedoeldvoorBruikleens.remove(bruikleen);
        bruikleen.getIsbedoeldvoorTentoonstellings().remove(this);
        return this;
    }

    public Set<Museumobject> getOnderdeelMuseumobjects() {
        return this.onderdeelMuseumobjects;
    }

    public void setOnderdeelMuseumobjects(Set<Museumobject> museumobjects) {
        if (this.onderdeelMuseumobjects != null) {
            this.onderdeelMuseumobjects.forEach(i -> i.removeOnderdeelTentoonstelling(this));
        }
        if (museumobjects != null) {
            museumobjects.forEach(i -> i.addOnderdeelTentoonstelling(this));
        }
        this.onderdeelMuseumobjects = museumobjects;
    }

    public Tentoonstelling onderdeelMuseumobjects(Set<Museumobject> museumobjects) {
        this.setOnderdeelMuseumobjects(museumobjects);
        return this;
    }

    public Tentoonstelling addOnderdeelMuseumobject(Museumobject museumobject) {
        this.onderdeelMuseumobjects.add(museumobject);
        museumobject.getOnderdeelTentoonstellings().add(this);
        return this;
    }

    public Tentoonstelling removeOnderdeelMuseumobject(Museumobject museumobject) {
        this.onderdeelMuseumobjects.remove(museumobject);
        museumobject.getOnderdeelTentoonstellings().remove(this);
        return this;
    }

    public Set<Samensteller> getSteltsamenSamenstellers() {
        return this.steltsamenSamenstellers;
    }

    public void setSteltsamenSamenstellers(Set<Samensteller> samenstellers) {
        if (this.steltsamenSamenstellers != null) {
            this.steltsamenSamenstellers.forEach(i -> i.removeSteltsamenTentoonstelling(this));
        }
        if (samenstellers != null) {
            samenstellers.forEach(i -> i.addSteltsamenTentoonstelling(this));
        }
        this.steltsamenSamenstellers = samenstellers;
    }

    public Tentoonstelling steltsamenSamenstellers(Set<Samensteller> samenstellers) {
        this.setSteltsamenSamenstellers(samenstellers);
        return this;
    }

    public Tentoonstelling addSteltsamenSamensteller(Samensteller samensteller) {
        this.steltsamenSamenstellers.add(samensteller);
        samensteller.getSteltsamenTentoonstellings().add(this);
        return this;
    }

    public Tentoonstelling removeSteltsamenSamensteller(Samensteller samensteller) {
        this.steltsamenSamenstellers.remove(samensteller);
        samensteller.getSteltsamenTentoonstellings().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tentoonstelling)) {
            return false;
        }
        return getId() != null && getId().equals(((Tentoonstelling) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tentoonstelling{" +
            "id=" + getId() +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", subtitel='" + getSubtitel() + "'" +
            ", titel='" + getTitel() + "'" +
            "}";
    }
}
