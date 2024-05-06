package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Bouwdeel.
 */
@Entity
@Table(name = "bouwdeel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bouwdeel implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bestaatuitBouwdeel")
    @JsonIgnoreProperties(value = { "bestaatuitBouwdeel", "betreftWerkbons" }, allowSetters = true)
    private Set<Bouwdeelelement> bestaatuitBouwdeelelements = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "betreftPand",
            "bestaatuitBouwdeels",
            "betreftInspecties",
            "heeftVerblijfsobject",
            "heeftPand",
            "heeftKostenplaats",
            "betreftWerkbons",
        },
        allowSetters = true
    )
    private Vastgoedobject bestaatuitVastgoedobject;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "betreftBouwdeels")
    @JsonIgnoreProperties(
        value = {
            "betreftVastgoedobject", "betreftBouwdeels", "betreftBouwdeelelements", "hoortbijInkooporder", "voertwerkuitconformLeverancier",
        },
        allowSetters = true
    )
    private Set<Werkbon> betreftWerkbons = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bouwdeel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Bouwdeel code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Bouwdeel omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Bouwdeelelement> getBestaatuitBouwdeelelements() {
        return this.bestaatuitBouwdeelelements;
    }

    public void setBestaatuitBouwdeelelements(Set<Bouwdeelelement> bouwdeelelements) {
        if (this.bestaatuitBouwdeelelements != null) {
            this.bestaatuitBouwdeelelements.forEach(i -> i.setBestaatuitBouwdeel(null));
        }
        if (bouwdeelelements != null) {
            bouwdeelelements.forEach(i -> i.setBestaatuitBouwdeel(this));
        }
        this.bestaatuitBouwdeelelements = bouwdeelelements;
    }

    public Bouwdeel bestaatuitBouwdeelelements(Set<Bouwdeelelement> bouwdeelelements) {
        this.setBestaatuitBouwdeelelements(bouwdeelelements);
        return this;
    }

    public Bouwdeel addBestaatuitBouwdeelelement(Bouwdeelelement bouwdeelelement) {
        this.bestaatuitBouwdeelelements.add(bouwdeelelement);
        bouwdeelelement.setBestaatuitBouwdeel(this);
        return this;
    }

    public Bouwdeel removeBestaatuitBouwdeelelement(Bouwdeelelement bouwdeelelement) {
        this.bestaatuitBouwdeelelements.remove(bouwdeelelement);
        bouwdeelelement.setBestaatuitBouwdeel(null);
        return this;
    }

    public Vastgoedobject getBestaatuitVastgoedobject() {
        return this.bestaatuitVastgoedobject;
    }

    public void setBestaatuitVastgoedobject(Vastgoedobject vastgoedobject) {
        this.bestaatuitVastgoedobject = vastgoedobject;
    }

    public Bouwdeel bestaatuitVastgoedobject(Vastgoedobject vastgoedobject) {
        this.setBestaatuitVastgoedobject(vastgoedobject);
        return this;
    }

    public Set<Werkbon> getBetreftWerkbons() {
        return this.betreftWerkbons;
    }

    public void setBetreftWerkbons(Set<Werkbon> werkbons) {
        if (this.betreftWerkbons != null) {
            this.betreftWerkbons.forEach(i -> i.removeBetreftBouwdeel(this));
        }
        if (werkbons != null) {
            werkbons.forEach(i -> i.addBetreftBouwdeel(this));
        }
        this.betreftWerkbons = werkbons;
    }

    public Bouwdeel betreftWerkbons(Set<Werkbon> werkbons) {
        this.setBetreftWerkbons(werkbons);
        return this;
    }

    public Bouwdeel addBetreftWerkbon(Werkbon werkbon) {
        this.betreftWerkbons.add(werkbon);
        werkbon.getBetreftBouwdeels().add(this);
        return this;
    }

    public Bouwdeel removeBetreftWerkbon(Werkbon werkbon) {
        this.betreftWerkbons.remove(werkbon);
        werkbon.getBetreftBouwdeels().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bouwdeel)) {
            return false;
        }
        return getId() != null && getId().equals(((Bouwdeel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bouwdeel{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
