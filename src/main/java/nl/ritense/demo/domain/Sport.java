package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Sport.
 */
@Entity
@Table(name = "sport")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "oefentuitSports")
    @JsonIgnoreProperties(value = { "oefentuitSports", "gebruiktSportlocaties" }, allowSetters = true)
    private Set<Sportvereniging> oefentuitSportverenigings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sport id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Sport naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Set<Sportvereniging> getOefentuitSportverenigings() {
        return this.oefentuitSportverenigings;
    }

    public void setOefentuitSportverenigings(Set<Sportvereniging> sportverenigings) {
        if (this.oefentuitSportverenigings != null) {
            this.oefentuitSportverenigings.forEach(i -> i.removeOefentuitSport(this));
        }
        if (sportverenigings != null) {
            sportverenigings.forEach(i -> i.addOefentuitSport(this));
        }
        this.oefentuitSportverenigings = sportverenigings;
    }

    public Sport oefentuitSportverenigings(Set<Sportvereniging> sportverenigings) {
        this.setOefentuitSportverenigings(sportverenigings);
        return this;
    }

    public Sport addOefentuitSportvereniging(Sportvereniging sportvereniging) {
        this.oefentuitSportverenigings.add(sportvereniging);
        sportvereniging.getOefentuitSports().add(this);
        return this;
    }

    public Sport removeOefentuitSportvereniging(Sportvereniging sportvereniging) {
        this.oefentuitSportverenigings.remove(sportvereniging);
        sportvereniging.getOefentuitSports().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sport)) {
            return false;
        }
        return getId() != null && getId().equals(((Sport) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sport{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            "}";
    }
}
