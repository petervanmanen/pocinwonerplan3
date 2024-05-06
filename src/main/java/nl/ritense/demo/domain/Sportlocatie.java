package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Sportlocatie.
 */
@Entity
@Table(name = "sportlocatie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sportlocatie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "gebruiktSportlocaties")
    @JsonIgnoreProperties(
        value = {
            "heeftUitschrijvings",
            "kentOnderwijsloopbaans",
            "heeftOnderwijssoorts",
            "gebruiktSportlocaties",
            "betreftBeslissings",
            "heeftVerzuimmeldings",
            "heeftVrijstellings",
            "heeftInschrijvings",
        },
        allowSetters = true
    )
    private Set<School> gebruiktSchools = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "gebruiktSportlocaties")
    @JsonIgnoreProperties(value = { "oefentuitSports", "gebruiktSportlocaties" }, allowSetters = true)
    private Set<Sportvereniging> gebruiktSportverenigings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sportlocatie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Sportlocatie naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Set<School> getGebruiktSchools() {
        return this.gebruiktSchools;
    }

    public void setGebruiktSchools(Set<School> schools) {
        if (this.gebruiktSchools != null) {
            this.gebruiktSchools.forEach(i -> i.removeGebruiktSportlocatie(this));
        }
        if (schools != null) {
            schools.forEach(i -> i.addGebruiktSportlocatie(this));
        }
        this.gebruiktSchools = schools;
    }

    public Sportlocatie gebruiktSchools(Set<School> schools) {
        this.setGebruiktSchools(schools);
        return this;
    }

    public Sportlocatie addGebruiktSchool(School school) {
        this.gebruiktSchools.add(school);
        school.getGebruiktSportlocaties().add(this);
        return this;
    }

    public Sportlocatie removeGebruiktSchool(School school) {
        this.gebruiktSchools.remove(school);
        school.getGebruiktSportlocaties().remove(this);
        return this;
    }

    public Set<Sportvereniging> getGebruiktSportverenigings() {
        return this.gebruiktSportverenigings;
    }

    public void setGebruiktSportverenigings(Set<Sportvereniging> sportverenigings) {
        if (this.gebruiktSportverenigings != null) {
            this.gebruiktSportverenigings.forEach(i -> i.removeGebruiktSportlocatie(this));
        }
        if (sportverenigings != null) {
            sportverenigings.forEach(i -> i.addGebruiktSportlocatie(this));
        }
        this.gebruiktSportverenigings = sportverenigings;
    }

    public Sportlocatie gebruiktSportverenigings(Set<Sportvereniging> sportverenigings) {
        this.setGebruiktSportverenigings(sportverenigings);
        return this;
    }

    public Sportlocatie addGebruiktSportvereniging(Sportvereniging sportvereniging) {
        this.gebruiktSportverenigings.add(sportvereniging);
        sportvereniging.getGebruiktSportlocaties().add(this);
        return this;
    }

    public Sportlocatie removeGebruiktSportvereniging(Sportvereniging sportvereniging) {
        this.gebruiktSportverenigings.remove(sportvereniging);
        sportvereniging.getGebruiktSportlocaties().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sportlocatie)) {
            return false;
        }
        return getId() != null && getId().equals(((Sportlocatie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sportlocatie{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            "}";
    }
}
