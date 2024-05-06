package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Behandelsoort.
 */
@Entity
@Table(name = "behandelsoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Behandelsoort implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "isvansoortBehandelsoort")
    @JsonIgnoreProperties(value = { "isvansoortBehandelsoort" }, allowSetters = true)
    private Set<Behandeling> isvansoortBehandelings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Behandelsoort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Behandelsoort naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Behandelsoort omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Behandeling> getIsvansoortBehandelings() {
        return this.isvansoortBehandelings;
    }

    public void setIsvansoortBehandelings(Set<Behandeling> behandelings) {
        if (this.isvansoortBehandelings != null) {
            this.isvansoortBehandelings.forEach(i -> i.setIsvansoortBehandelsoort(null));
        }
        if (behandelings != null) {
            behandelings.forEach(i -> i.setIsvansoortBehandelsoort(this));
        }
        this.isvansoortBehandelings = behandelings;
    }

    public Behandelsoort isvansoortBehandelings(Set<Behandeling> behandelings) {
        this.setIsvansoortBehandelings(behandelings);
        return this;
    }

    public Behandelsoort addIsvansoortBehandeling(Behandeling behandeling) {
        this.isvansoortBehandelings.add(behandeling);
        behandeling.setIsvansoortBehandelsoort(this);
        return this;
    }

    public Behandelsoort removeIsvansoortBehandeling(Behandeling behandeling) {
        this.isvansoortBehandelings.remove(behandeling);
        behandeling.setIsvansoortBehandelsoort(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Behandelsoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Behandelsoort) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Behandelsoort{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
