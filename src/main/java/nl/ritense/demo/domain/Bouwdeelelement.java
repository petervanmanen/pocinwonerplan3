package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Bouwdeelelement.
 */
@Entity
@Table(name = "bouwdeelelement")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bouwdeelelement implements Serializable {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "bestaatuitBouwdeelelements", "bestaatuitVastgoedobject", "betreftWerkbons" }, allowSetters = true)
    private Bouwdeel bestaatuitBouwdeel;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "betreftBouwdeelelements")
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

    public Bouwdeelelement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Bouwdeelelement code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Bouwdeelelement omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Bouwdeel getBestaatuitBouwdeel() {
        return this.bestaatuitBouwdeel;
    }

    public void setBestaatuitBouwdeel(Bouwdeel bouwdeel) {
        this.bestaatuitBouwdeel = bouwdeel;
    }

    public Bouwdeelelement bestaatuitBouwdeel(Bouwdeel bouwdeel) {
        this.setBestaatuitBouwdeel(bouwdeel);
        return this;
    }

    public Set<Werkbon> getBetreftWerkbons() {
        return this.betreftWerkbons;
    }

    public void setBetreftWerkbons(Set<Werkbon> werkbons) {
        if (this.betreftWerkbons != null) {
            this.betreftWerkbons.forEach(i -> i.removeBetreftBouwdeelelement(this));
        }
        if (werkbons != null) {
            werkbons.forEach(i -> i.addBetreftBouwdeelelement(this));
        }
        this.betreftWerkbons = werkbons;
    }

    public Bouwdeelelement betreftWerkbons(Set<Werkbon> werkbons) {
        this.setBetreftWerkbons(werkbons);
        return this;
    }

    public Bouwdeelelement addBetreftWerkbon(Werkbon werkbon) {
        this.betreftWerkbons.add(werkbon);
        werkbon.getBetreftBouwdeelelements().add(this);
        return this;
    }

    public Bouwdeelelement removeBetreftWerkbon(Werkbon werkbon) {
        this.betreftWerkbons.remove(werkbon);
        werkbon.getBetreftBouwdeelelements().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bouwdeelelement)) {
            return false;
        }
        return getId() != null && getId().equals(((Bouwdeelelement) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bouwdeelelement{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
