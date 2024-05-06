package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Resultaatsoort.
 */
@Entity
@Table(name = "resultaatsoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Resultaatsoort implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "soortresultaatResultaatsoort")
    @JsonIgnoreProperties(value = { "soortresultaatResultaatsoort", "heeftresultaatProject", "heeftresultaatTraject" }, allowSetters = true)
    private Set<Resultaat> soortresultaatResultaats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Resultaatsoort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Resultaatsoort naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Resultaatsoort omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Resultaat> getSoortresultaatResultaats() {
        return this.soortresultaatResultaats;
    }

    public void setSoortresultaatResultaats(Set<Resultaat> resultaats) {
        if (this.soortresultaatResultaats != null) {
            this.soortresultaatResultaats.forEach(i -> i.setSoortresultaatResultaatsoort(null));
        }
        if (resultaats != null) {
            resultaats.forEach(i -> i.setSoortresultaatResultaatsoort(this));
        }
        this.soortresultaatResultaats = resultaats;
    }

    public Resultaatsoort soortresultaatResultaats(Set<Resultaat> resultaats) {
        this.setSoortresultaatResultaats(resultaats);
        return this;
    }

    public Resultaatsoort addSoortresultaatResultaat(Resultaat resultaat) {
        this.soortresultaatResultaats.add(resultaat);
        resultaat.setSoortresultaatResultaatsoort(this);
        return this;
    }

    public Resultaatsoort removeSoortresultaatResultaat(Resultaat resultaat) {
        this.soortresultaatResultaats.remove(resultaat);
        resultaat.setSoortresultaatResultaatsoort(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resultaatsoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Resultaatsoort) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Resultaatsoort{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
