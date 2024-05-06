package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Verlofsoort.
 */
@Entity
@Table(name = "verlofsoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Verlofsoort implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "soortverlofVerlofsoort")
    @JsonIgnoreProperties(value = { "soortverlofVerlofsoort", "heeftverlofWerknemer" }, allowSetters = true)
    private Set<Verlof> soortverlofVerlofs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Verlofsoort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Verlofsoort naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Verlofsoort omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Verlof> getSoortverlofVerlofs() {
        return this.soortverlofVerlofs;
    }

    public void setSoortverlofVerlofs(Set<Verlof> verlofs) {
        if (this.soortverlofVerlofs != null) {
            this.soortverlofVerlofs.forEach(i -> i.setSoortverlofVerlofsoort(null));
        }
        if (verlofs != null) {
            verlofs.forEach(i -> i.setSoortverlofVerlofsoort(this));
        }
        this.soortverlofVerlofs = verlofs;
    }

    public Verlofsoort soortverlofVerlofs(Set<Verlof> verlofs) {
        this.setSoortverlofVerlofs(verlofs);
        return this;
    }

    public Verlofsoort addSoortverlofVerlof(Verlof verlof) {
        this.soortverlofVerlofs.add(verlof);
        verlof.setSoortverlofVerlofsoort(this);
        return this;
    }

    public Verlofsoort removeSoortverlofVerlof(Verlof verlof) {
        this.soortverlofVerlofs.remove(verlof);
        verlof.setSoortverlofVerlofsoort(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Verlofsoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Verlofsoort) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Verlofsoort{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
