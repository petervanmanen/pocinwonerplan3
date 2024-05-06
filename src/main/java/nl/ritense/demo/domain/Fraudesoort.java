package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Fraudesoort.
 */
@Entity
@Table(name = "fraudesoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Fraudesoort implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "isvansoortFraudesoort")
    @JsonIgnoreProperties(value = { "isvansoortFraudesoort", "heeftfraudegegevensClient" }, allowSetters = true)
    private Set<Fraudegegevens> isvansoortFraudegegevens = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Fraudesoort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Fraudesoort naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Set<Fraudegegevens> getIsvansoortFraudegegevens() {
        return this.isvansoortFraudegegevens;
    }

    public void setIsvansoortFraudegegevens(Set<Fraudegegevens> fraudegegevens) {
        if (this.isvansoortFraudegegevens != null) {
            this.isvansoortFraudegegevens.forEach(i -> i.setIsvansoortFraudesoort(null));
        }
        if (fraudegegevens != null) {
            fraudegegevens.forEach(i -> i.setIsvansoortFraudesoort(this));
        }
        this.isvansoortFraudegegevens = fraudegegevens;
    }

    public Fraudesoort isvansoortFraudegegevens(Set<Fraudegegevens> fraudegegevens) {
        this.setIsvansoortFraudegegevens(fraudegegevens);
        return this;
    }

    public Fraudesoort addIsvansoortFraudegegevens(Fraudegegevens fraudegegevens) {
        this.isvansoortFraudegegevens.add(fraudegegevens);
        fraudegegevens.setIsvansoortFraudesoort(this);
        return this;
    }

    public Fraudesoort removeIsvansoortFraudegegevens(Fraudegegevens fraudegegevens) {
        this.isvansoortFraudegegevens.remove(fraudegegevens);
        fraudegegevens.setIsvansoortFraudesoort(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fraudesoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Fraudesoort) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fraudesoort{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            "}";
    }
}
