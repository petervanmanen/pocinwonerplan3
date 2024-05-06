package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Uitstroomredensoort.
 */
@Entity
@Table(name = "uitstroomredensoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Uitstroomredensoort implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "soortuitstroomredenUitstroomredensoort")
    @JsonIgnoreProperties(
        value = { "soortuitstroomredenUitstroomredensoort", "heeftuitstroomredenProject", "heeftuitstroomredenTraject" },
        allowSetters = true
    )
    private Set<Uitstroomreden> soortuitstroomredenUitstroomredens = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Uitstroomredensoort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Uitstroomredensoort naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Uitstroomredensoort omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Uitstroomreden> getSoortuitstroomredenUitstroomredens() {
        return this.soortuitstroomredenUitstroomredens;
    }

    public void setSoortuitstroomredenUitstroomredens(Set<Uitstroomreden> uitstroomredens) {
        if (this.soortuitstroomredenUitstroomredens != null) {
            this.soortuitstroomredenUitstroomredens.forEach(i -> i.setSoortuitstroomredenUitstroomredensoort(null));
        }
        if (uitstroomredens != null) {
            uitstroomredens.forEach(i -> i.setSoortuitstroomredenUitstroomredensoort(this));
        }
        this.soortuitstroomredenUitstroomredens = uitstroomredens;
    }

    public Uitstroomredensoort soortuitstroomredenUitstroomredens(Set<Uitstroomreden> uitstroomredens) {
        this.setSoortuitstroomredenUitstroomredens(uitstroomredens);
        return this;
    }

    public Uitstroomredensoort addSoortuitstroomredenUitstroomreden(Uitstroomreden uitstroomreden) {
        this.soortuitstroomredenUitstroomredens.add(uitstroomreden);
        uitstroomreden.setSoortuitstroomredenUitstroomredensoort(this);
        return this;
    }

    public Uitstroomredensoort removeSoortuitstroomredenUitstroomreden(Uitstroomreden uitstroomreden) {
        this.soortuitstroomredenUitstroomredens.remove(uitstroomreden);
        uitstroomreden.setSoortuitstroomredenUitstroomredensoort(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Uitstroomredensoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Uitstroomredensoort) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Uitstroomredensoort{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
