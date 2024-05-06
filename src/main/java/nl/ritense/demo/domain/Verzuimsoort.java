package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Verzuimsoort.
 */
@Entity
@Table(name = "verzuimsoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Verzuimsoort implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "soortverzuimVerzuimsoort")
    @JsonIgnoreProperties(value = { "soortverzuimVerzuimsoort", "heeftverzuimWerknemer" }, allowSetters = true)
    private Set<Verzuim> soortverzuimVerzuims = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Verzuimsoort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Verzuimsoort naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Verzuimsoort omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Verzuim> getSoortverzuimVerzuims() {
        return this.soortverzuimVerzuims;
    }

    public void setSoortverzuimVerzuims(Set<Verzuim> verzuims) {
        if (this.soortverzuimVerzuims != null) {
            this.soortverzuimVerzuims.forEach(i -> i.setSoortverzuimVerzuimsoort(null));
        }
        if (verzuims != null) {
            verzuims.forEach(i -> i.setSoortverzuimVerzuimsoort(this));
        }
        this.soortverzuimVerzuims = verzuims;
    }

    public Verzuimsoort soortverzuimVerzuims(Set<Verzuim> verzuims) {
        this.setSoortverzuimVerzuims(verzuims);
        return this;
    }

    public Verzuimsoort addSoortverzuimVerzuim(Verzuim verzuim) {
        this.soortverzuimVerzuims.add(verzuim);
        verzuim.setSoortverzuimVerzuimsoort(this);
        return this;
    }

    public Verzuimsoort removeSoortverzuimVerzuim(Verzuim verzuim) {
        this.soortverzuimVerzuims.remove(verzuim);
        verzuim.setSoortverzuimVerzuimsoort(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Verzuimsoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Verzuimsoort) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Verzuimsoort{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
