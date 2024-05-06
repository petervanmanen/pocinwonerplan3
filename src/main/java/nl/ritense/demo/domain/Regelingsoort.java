package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Regelingsoort.
 */
@Entity
@Table(name = "regelingsoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Regelingsoort implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "isregelingsoortRegelingsoort")
    @JsonIgnoreProperties(value = { "isregelingsoortRegelingsoort", "heeftregelingClient" }, allowSetters = true)
    private Set<Regeling> isregelingsoortRegelings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Regelingsoort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Regelingsoort naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Regelingsoort omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Regeling> getIsregelingsoortRegelings() {
        return this.isregelingsoortRegelings;
    }

    public void setIsregelingsoortRegelings(Set<Regeling> regelings) {
        if (this.isregelingsoortRegelings != null) {
            this.isregelingsoortRegelings.forEach(i -> i.setIsregelingsoortRegelingsoort(null));
        }
        if (regelings != null) {
            regelings.forEach(i -> i.setIsregelingsoortRegelingsoort(this));
        }
        this.isregelingsoortRegelings = regelings;
    }

    public Regelingsoort isregelingsoortRegelings(Set<Regeling> regelings) {
        this.setIsregelingsoortRegelings(regelings);
        return this;
    }

    public Regelingsoort addIsregelingsoortRegeling(Regeling regeling) {
        this.isregelingsoortRegelings.add(regeling);
        regeling.setIsregelingsoortRegelingsoort(this);
        return this;
    }

    public Regelingsoort removeIsregelingsoortRegeling(Regeling regeling) {
        this.isregelingsoortRegelings.remove(regeling);
        regeling.setIsregelingsoortRegelingsoort(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Regelingsoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Regelingsoort) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Regelingsoort{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
