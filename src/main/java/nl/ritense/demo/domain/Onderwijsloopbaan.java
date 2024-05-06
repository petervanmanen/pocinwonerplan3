package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Onderwijsloopbaan.
 */
@Entity
@Table(name = "onderwijsloopbaan")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Onderwijsloopbaan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "emptyOnderwijsloopbaan")
    @JsonIgnoreProperties(value = { "emptyOnderwijsloopbaan" }, allowSetters = true)
    private Set<Loopbaanstap> emptyLoopbaanstaps = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftStartkwalificatie",
            "heeftVerzuimmeldings",
            "heeftVrijstellings",
            "heeftInschrijvings",
            "heeftOnderwijsloopbaans",
            "heeftUitschrijvings",
            "betreftBeslissings",
        },
        allowSetters = true
    )
    private Leerling heeftLeerling;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "kentOnderwijsloopbaans")
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
    private Set<School> kentSchools = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Onderwijsloopbaan id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Loopbaanstap> getEmptyLoopbaanstaps() {
        return this.emptyLoopbaanstaps;
    }

    public void setEmptyLoopbaanstaps(Set<Loopbaanstap> loopbaanstaps) {
        if (this.emptyLoopbaanstaps != null) {
            this.emptyLoopbaanstaps.forEach(i -> i.setEmptyOnderwijsloopbaan(null));
        }
        if (loopbaanstaps != null) {
            loopbaanstaps.forEach(i -> i.setEmptyOnderwijsloopbaan(this));
        }
        this.emptyLoopbaanstaps = loopbaanstaps;
    }

    public Onderwijsloopbaan emptyLoopbaanstaps(Set<Loopbaanstap> loopbaanstaps) {
        this.setEmptyLoopbaanstaps(loopbaanstaps);
        return this;
    }

    public Onderwijsloopbaan addEmptyLoopbaanstap(Loopbaanstap loopbaanstap) {
        this.emptyLoopbaanstaps.add(loopbaanstap);
        loopbaanstap.setEmptyOnderwijsloopbaan(this);
        return this;
    }

    public Onderwijsloopbaan removeEmptyLoopbaanstap(Loopbaanstap loopbaanstap) {
        this.emptyLoopbaanstaps.remove(loopbaanstap);
        loopbaanstap.setEmptyOnderwijsloopbaan(null);
        return this;
    }

    public Leerling getHeeftLeerling() {
        return this.heeftLeerling;
    }

    public void setHeeftLeerling(Leerling leerling) {
        this.heeftLeerling = leerling;
    }

    public Onderwijsloopbaan heeftLeerling(Leerling leerling) {
        this.setHeeftLeerling(leerling);
        return this;
    }

    public Set<School> getKentSchools() {
        return this.kentSchools;
    }

    public void setKentSchools(Set<School> schools) {
        if (this.kentSchools != null) {
            this.kentSchools.forEach(i -> i.removeKentOnderwijsloopbaan(this));
        }
        if (schools != null) {
            schools.forEach(i -> i.addKentOnderwijsloopbaan(this));
        }
        this.kentSchools = schools;
    }

    public Onderwijsloopbaan kentSchools(Set<School> schools) {
        this.setKentSchools(schools);
        return this;
    }

    public Onderwijsloopbaan addKentSchool(School school) {
        this.kentSchools.add(school);
        school.getKentOnderwijsloopbaans().add(this);
        return this;
    }

    public Onderwijsloopbaan removeKentSchool(School school) {
        this.kentSchools.remove(school);
        school.getKentOnderwijsloopbaans().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Onderwijsloopbaan)) {
            return false;
        }
        return getId() != null && getId().equals(((Onderwijsloopbaan) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Onderwijsloopbaan{" +
            "id=" + getId() +
            "}";
    }
}
