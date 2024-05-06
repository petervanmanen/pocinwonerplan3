package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Straatsectie.
 */
@Entity
@Table(name = "straatsectie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Straatsectie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "zonecode")
    private String zonecode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bevatStraatsectie")
    @JsonIgnoreProperties(value = { "bevatStraatsectie", "betreftParkeerscans" }, allowSetters = true)
    private Set<Parkeervlak> bevatParkeervlaks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Straatsectie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Straatsectie code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Straatsectie omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getZonecode() {
        return this.zonecode;
    }

    public Straatsectie zonecode(String zonecode) {
        this.setZonecode(zonecode);
        return this;
    }

    public void setZonecode(String zonecode) {
        this.zonecode = zonecode;
    }

    public Set<Parkeervlak> getBevatParkeervlaks() {
        return this.bevatParkeervlaks;
    }

    public void setBevatParkeervlaks(Set<Parkeervlak> parkeervlaks) {
        if (this.bevatParkeervlaks != null) {
            this.bevatParkeervlaks.forEach(i -> i.setBevatStraatsectie(null));
        }
        if (parkeervlaks != null) {
            parkeervlaks.forEach(i -> i.setBevatStraatsectie(this));
        }
        this.bevatParkeervlaks = parkeervlaks;
    }

    public Straatsectie bevatParkeervlaks(Set<Parkeervlak> parkeervlaks) {
        this.setBevatParkeervlaks(parkeervlaks);
        return this;
    }

    public Straatsectie addBevatParkeervlak(Parkeervlak parkeervlak) {
        this.bevatParkeervlaks.add(parkeervlak);
        parkeervlak.setBevatStraatsectie(this);
        return this;
    }

    public Straatsectie removeBevatParkeervlak(Parkeervlak parkeervlak) {
        this.bevatParkeervlaks.remove(parkeervlak);
        parkeervlak.setBevatStraatsectie(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Straatsectie)) {
            return false;
        }
        return getId() != null && getId().equals(((Straatsectie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Straatsectie{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", zonecode='" + getZonecode() + "'" +
            "}";
    }
}
