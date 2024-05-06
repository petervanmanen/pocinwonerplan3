package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Bijzonderheidsoort.
 */
@Entity
@Table(name = "bijzonderheidsoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bijzonderheidsoort implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "isvansoortBijzonderheidsoort")
    @JsonIgnoreProperties(value = { "isvansoortBijzonderheidsoort" }, allowSetters = true)
    private Set<Bijzonderheid> isvansoortBijzonderheids = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bijzonderheidsoort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Bijzonderheidsoort naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Bijzonderheidsoort omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Bijzonderheid> getIsvansoortBijzonderheids() {
        return this.isvansoortBijzonderheids;
    }

    public void setIsvansoortBijzonderheids(Set<Bijzonderheid> bijzonderheids) {
        if (this.isvansoortBijzonderheids != null) {
            this.isvansoortBijzonderheids.forEach(i -> i.setIsvansoortBijzonderheidsoort(null));
        }
        if (bijzonderheids != null) {
            bijzonderheids.forEach(i -> i.setIsvansoortBijzonderheidsoort(this));
        }
        this.isvansoortBijzonderheids = bijzonderheids;
    }

    public Bijzonderheidsoort isvansoortBijzonderheids(Set<Bijzonderheid> bijzonderheids) {
        this.setIsvansoortBijzonderheids(bijzonderheids);
        return this;
    }

    public Bijzonderheidsoort addIsvansoortBijzonderheid(Bijzonderheid bijzonderheid) {
        this.isvansoortBijzonderheids.add(bijzonderheid);
        bijzonderheid.setIsvansoortBijzonderheidsoort(this);
        return this;
    }

    public Bijzonderheidsoort removeIsvansoortBijzonderheid(Bijzonderheid bijzonderheid) {
        this.isvansoortBijzonderheids.remove(bijzonderheid);
        bijzonderheid.setIsvansoortBijzonderheidsoort(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bijzonderheidsoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Bijzonderheidsoort) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bijzonderheidsoort{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
