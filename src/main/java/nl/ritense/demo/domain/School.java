package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A School.
 */
@Entity
@Table(name = "school")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class School implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftSchool")
    @JsonIgnoreProperties(value = { "heeftLeerling", "heeftSchool" }, allowSetters = true)
    private Set<Uitschrijving> heeftUitschrijvings = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @NotNull
    @JoinTable(
        name = "rel_school__kent_onderwijsloopbaan",
        joinColumns = @JoinColumn(name = "school_id"),
        inverseJoinColumns = @JoinColumn(name = "kent_onderwijsloopbaan_id")
    )
    @JsonIgnoreProperties(value = { "emptyLoopbaanstaps", "heeftLeerling", "kentSchools" }, allowSetters = true)
    private Set<Onderwijsloopbaan> kentOnderwijsloopbaans = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_school__heeft_onderwijssoort",
        joinColumns = @JoinColumn(name = "school_id"),
        inverseJoinColumns = @JoinColumn(name = "heeft_onderwijssoort_id")
    )
    @JsonIgnoreProperties(value = { "heeftSchools" }, allowSetters = true)
    private Set<Onderwijssoort> heeftOnderwijssoorts = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_school__gebruikt_sportlocatie",
        joinColumns = @JoinColumn(name = "school_id"),
        inverseJoinColumns = @JoinColumn(name = "gebruikt_sportlocatie_id")
    )
    @JsonIgnoreProperties(value = { "gebruiktSchools", "gebruiktSportverenigings" }, allowSetters = true)
    private Set<Sportlocatie> gebruiktSportlocaties = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftSchool")
    @JsonIgnoreProperties(value = { "betreftLeerling", "behandelaarLeerplichtambtenaar", "betreftSchool" }, allowSetters = true)
    private Set<Beslissing> betreftBeslissings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftSchool")
    @JsonIgnoreProperties(value = { "heeftSchool", "heeftLeerling" }, allowSetters = true)
    private Set<Verzuimmelding> heeftVerzuimmeldings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftSchool")
    @JsonIgnoreProperties(value = { "heeftSchool", "heeftLeerling" }, allowSetters = true)
    private Set<Vrijstelling> heeftVrijstellings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftSchool")
    @JsonIgnoreProperties(
        value = { "heeftSchool", "betreftAanbesteding", "betreftGunning", "heeftLeerling", "heeftLeverancier" },
        allowSetters = true
    )
    private Set<Inschrijving> heeftInschrijvings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public School id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public School naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Set<Uitschrijving> getHeeftUitschrijvings() {
        return this.heeftUitschrijvings;
    }

    public void setHeeftUitschrijvings(Set<Uitschrijving> uitschrijvings) {
        if (this.heeftUitschrijvings != null) {
            this.heeftUitschrijvings.forEach(i -> i.setHeeftSchool(null));
        }
        if (uitschrijvings != null) {
            uitschrijvings.forEach(i -> i.setHeeftSchool(this));
        }
        this.heeftUitschrijvings = uitschrijvings;
    }

    public School heeftUitschrijvings(Set<Uitschrijving> uitschrijvings) {
        this.setHeeftUitschrijvings(uitschrijvings);
        return this;
    }

    public School addHeeftUitschrijving(Uitschrijving uitschrijving) {
        this.heeftUitschrijvings.add(uitschrijving);
        uitschrijving.setHeeftSchool(this);
        return this;
    }

    public School removeHeeftUitschrijving(Uitschrijving uitschrijving) {
        this.heeftUitschrijvings.remove(uitschrijving);
        uitschrijving.setHeeftSchool(null);
        return this;
    }

    public Set<Onderwijsloopbaan> getKentOnderwijsloopbaans() {
        return this.kentOnderwijsloopbaans;
    }

    public void setKentOnderwijsloopbaans(Set<Onderwijsloopbaan> onderwijsloopbaans) {
        this.kentOnderwijsloopbaans = onderwijsloopbaans;
    }

    public School kentOnderwijsloopbaans(Set<Onderwijsloopbaan> onderwijsloopbaans) {
        this.setKentOnderwijsloopbaans(onderwijsloopbaans);
        return this;
    }

    public School addKentOnderwijsloopbaan(Onderwijsloopbaan onderwijsloopbaan) {
        this.kentOnderwijsloopbaans.add(onderwijsloopbaan);
        return this;
    }

    public School removeKentOnderwijsloopbaan(Onderwijsloopbaan onderwijsloopbaan) {
        this.kentOnderwijsloopbaans.remove(onderwijsloopbaan);
        return this;
    }

    public Set<Onderwijssoort> getHeeftOnderwijssoorts() {
        return this.heeftOnderwijssoorts;
    }

    public void setHeeftOnderwijssoorts(Set<Onderwijssoort> onderwijssoorts) {
        this.heeftOnderwijssoorts = onderwijssoorts;
    }

    public School heeftOnderwijssoorts(Set<Onderwijssoort> onderwijssoorts) {
        this.setHeeftOnderwijssoorts(onderwijssoorts);
        return this;
    }

    public School addHeeftOnderwijssoort(Onderwijssoort onderwijssoort) {
        this.heeftOnderwijssoorts.add(onderwijssoort);
        return this;
    }

    public School removeHeeftOnderwijssoort(Onderwijssoort onderwijssoort) {
        this.heeftOnderwijssoorts.remove(onderwijssoort);
        return this;
    }

    public Set<Sportlocatie> getGebruiktSportlocaties() {
        return this.gebruiktSportlocaties;
    }

    public void setGebruiktSportlocaties(Set<Sportlocatie> sportlocaties) {
        this.gebruiktSportlocaties = sportlocaties;
    }

    public School gebruiktSportlocaties(Set<Sportlocatie> sportlocaties) {
        this.setGebruiktSportlocaties(sportlocaties);
        return this;
    }

    public School addGebruiktSportlocatie(Sportlocatie sportlocatie) {
        this.gebruiktSportlocaties.add(sportlocatie);
        return this;
    }

    public School removeGebruiktSportlocatie(Sportlocatie sportlocatie) {
        this.gebruiktSportlocaties.remove(sportlocatie);
        return this;
    }

    public Set<Beslissing> getBetreftBeslissings() {
        return this.betreftBeslissings;
    }

    public void setBetreftBeslissings(Set<Beslissing> beslissings) {
        if (this.betreftBeslissings != null) {
            this.betreftBeslissings.forEach(i -> i.setBetreftSchool(null));
        }
        if (beslissings != null) {
            beslissings.forEach(i -> i.setBetreftSchool(this));
        }
        this.betreftBeslissings = beslissings;
    }

    public School betreftBeslissings(Set<Beslissing> beslissings) {
        this.setBetreftBeslissings(beslissings);
        return this;
    }

    public School addBetreftBeslissing(Beslissing beslissing) {
        this.betreftBeslissings.add(beslissing);
        beslissing.setBetreftSchool(this);
        return this;
    }

    public School removeBetreftBeslissing(Beslissing beslissing) {
        this.betreftBeslissings.remove(beslissing);
        beslissing.setBetreftSchool(null);
        return this;
    }

    public Set<Verzuimmelding> getHeeftVerzuimmeldings() {
        return this.heeftVerzuimmeldings;
    }

    public void setHeeftVerzuimmeldings(Set<Verzuimmelding> verzuimmeldings) {
        if (this.heeftVerzuimmeldings != null) {
            this.heeftVerzuimmeldings.forEach(i -> i.setHeeftSchool(null));
        }
        if (verzuimmeldings != null) {
            verzuimmeldings.forEach(i -> i.setHeeftSchool(this));
        }
        this.heeftVerzuimmeldings = verzuimmeldings;
    }

    public School heeftVerzuimmeldings(Set<Verzuimmelding> verzuimmeldings) {
        this.setHeeftVerzuimmeldings(verzuimmeldings);
        return this;
    }

    public School addHeeftVerzuimmelding(Verzuimmelding verzuimmelding) {
        this.heeftVerzuimmeldings.add(verzuimmelding);
        verzuimmelding.setHeeftSchool(this);
        return this;
    }

    public School removeHeeftVerzuimmelding(Verzuimmelding verzuimmelding) {
        this.heeftVerzuimmeldings.remove(verzuimmelding);
        verzuimmelding.setHeeftSchool(null);
        return this;
    }

    public Set<Vrijstelling> getHeeftVrijstellings() {
        return this.heeftVrijstellings;
    }

    public void setHeeftVrijstellings(Set<Vrijstelling> vrijstellings) {
        if (this.heeftVrijstellings != null) {
            this.heeftVrijstellings.forEach(i -> i.setHeeftSchool(null));
        }
        if (vrijstellings != null) {
            vrijstellings.forEach(i -> i.setHeeftSchool(this));
        }
        this.heeftVrijstellings = vrijstellings;
    }

    public School heeftVrijstellings(Set<Vrijstelling> vrijstellings) {
        this.setHeeftVrijstellings(vrijstellings);
        return this;
    }

    public School addHeeftVrijstelling(Vrijstelling vrijstelling) {
        this.heeftVrijstellings.add(vrijstelling);
        vrijstelling.setHeeftSchool(this);
        return this;
    }

    public School removeHeeftVrijstelling(Vrijstelling vrijstelling) {
        this.heeftVrijstellings.remove(vrijstelling);
        vrijstelling.setHeeftSchool(null);
        return this;
    }

    public Set<Inschrijving> getHeeftInschrijvings() {
        return this.heeftInschrijvings;
    }

    public void setHeeftInschrijvings(Set<Inschrijving> inschrijvings) {
        if (this.heeftInschrijvings != null) {
            this.heeftInschrijvings.forEach(i -> i.setHeeftSchool(null));
        }
        if (inschrijvings != null) {
            inschrijvings.forEach(i -> i.setHeeftSchool(this));
        }
        this.heeftInschrijvings = inschrijvings;
    }

    public School heeftInschrijvings(Set<Inschrijving> inschrijvings) {
        this.setHeeftInschrijvings(inschrijvings);
        return this;
    }

    public School addHeeftInschrijving(Inschrijving inschrijving) {
        this.heeftInschrijvings.add(inschrijving);
        inschrijving.setHeeftSchool(this);
        return this;
    }

    public School removeHeeftInschrijving(Inschrijving inschrijving) {
        this.heeftInschrijvings.remove(inschrijving);
        inschrijving.setHeeftSchool(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof School)) {
            return false;
        }
        return getId() != null && getId().equals(((School) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "School{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            "}";
    }
}
