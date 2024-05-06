package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Declaratiesoort.
 */
@Entity
@Table(name = "declaratiesoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Declaratiesoort implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "soortdeclaratieDeclaratiesoort")
    @JsonIgnoreProperties(
        value = { "ingedienddoorLeverancier", "soortdeclaratieDeclaratiesoort", "dientinWerknemer", "valtbinnenDeclaratieregels" },
        allowSetters = true
    )
    private Set<Declaratie> soortdeclaratieDeclaraties = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Declaratiesoort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Declaratiesoort naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Declaratiesoort omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Declaratie> getSoortdeclaratieDeclaraties() {
        return this.soortdeclaratieDeclaraties;
    }

    public void setSoortdeclaratieDeclaraties(Set<Declaratie> declaraties) {
        if (this.soortdeclaratieDeclaraties != null) {
            this.soortdeclaratieDeclaraties.forEach(i -> i.setSoortdeclaratieDeclaratiesoort(null));
        }
        if (declaraties != null) {
            declaraties.forEach(i -> i.setSoortdeclaratieDeclaratiesoort(this));
        }
        this.soortdeclaratieDeclaraties = declaraties;
    }

    public Declaratiesoort soortdeclaratieDeclaraties(Set<Declaratie> declaraties) {
        this.setSoortdeclaratieDeclaraties(declaraties);
        return this;
    }

    public Declaratiesoort addSoortdeclaratieDeclaratie(Declaratie declaratie) {
        this.soortdeclaratieDeclaraties.add(declaratie);
        declaratie.setSoortdeclaratieDeclaratiesoort(this);
        return this;
    }

    public Declaratiesoort removeSoortdeclaratieDeclaratie(Declaratie declaratie) {
        this.soortdeclaratieDeclaraties.remove(declaratie);
        declaratie.setSoortdeclaratieDeclaratiesoort(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Declaratiesoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Declaratiesoort) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Declaratiesoort{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
