package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Onderwijssoort.
 */
@Entity
@Table(name = "onderwijssoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Onderwijssoort implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "onderwijstype")
    private String onderwijstype;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "heeftOnderwijssoorts")
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
    private Set<School> heeftSchools = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Onderwijssoort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Onderwijssoort omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOnderwijstype() {
        return this.onderwijstype;
    }

    public Onderwijssoort onderwijstype(String onderwijstype) {
        this.setOnderwijstype(onderwijstype);
        return this;
    }

    public void setOnderwijstype(String onderwijstype) {
        this.onderwijstype = onderwijstype;
    }

    public Set<School> getHeeftSchools() {
        return this.heeftSchools;
    }

    public void setHeeftSchools(Set<School> schools) {
        if (this.heeftSchools != null) {
            this.heeftSchools.forEach(i -> i.removeHeeftOnderwijssoort(this));
        }
        if (schools != null) {
            schools.forEach(i -> i.addHeeftOnderwijssoort(this));
        }
        this.heeftSchools = schools;
    }

    public Onderwijssoort heeftSchools(Set<School> schools) {
        this.setHeeftSchools(schools);
        return this;
    }

    public Onderwijssoort addHeeftSchool(School school) {
        this.heeftSchools.add(school);
        school.getHeeftOnderwijssoorts().add(this);
        return this;
    }

    public Onderwijssoort removeHeeftSchool(School school) {
        this.heeftSchools.remove(school);
        school.getHeeftOnderwijssoorts().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Onderwijssoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Onderwijssoort) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Onderwijssoort{" +
            "id=" + getId() +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", onderwijstype='" + getOnderwijstype() + "'" +
            "}";
    }
}
