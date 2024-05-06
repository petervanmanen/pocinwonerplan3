package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Inkomensvoorzieningsoort.
 */
@Entity
@Table(name = "inkomensvoorzieningsoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Inkomensvoorzieningsoort implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @Column(name = "code", length = 20)
    private String code;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "regeling")
    private String regeling;

    @Size(max = 20)
    @Column(name = "regelingcode", length = 20)
    private String regelingcode;

    @Column(name = "vergoeding")
    private String vergoeding;

    @Size(max = 20)
    @Column(name = "vergoedingscode", length = 20)
    private String vergoedingscode;

    @Column(name = "wet")
    private String wet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "issoortvoorzieningInkomensvoorzieningsoort")
    @JsonIgnoreProperties(
        value = {
            "heeftvoorzieningClient",
            "emptyClientbegeleider",
            "issoortvoorzieningInkomensvoorzieningsoort",
            "voorzieningbijstandspartijClients",
        },
        allowSetters = true
    )
    private Set<Inkomensvoorziening> issoortvoorzieningInkomensvoorzienings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Inkomensvoorzieningsoort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Inkomensvoorzieningsoort code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNaam() {
        return this.naam;
    }

    public Inkomensvoorzieningsoort naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Inkomensvoorzieningsoort omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getRegeling() {
        return this.regeling;
    }

    public Inkomensvoorzieningsoort regeling(String regeling) {
        this.setRegeling(regeling);
        return this;
    }

    public void setRegeling(String regeling) {
        this.regeling = regeling;
    }

    public String getRegelingcode() {
        return this.regelingcode;
    }

    public Inkomensvoorzieningsoort regelingcode(String regelingcode) {
        this.setRegelingcode(regelingcode);
        return this;
    }

    public void setRegelingcode(String regelingcode) {
        this.regelingcode = regelingcode;
    }

    public String getVergoeding() {
        return this.vergoeding;
    }

    public Inkomensvoorzieningsoort vergoeding(String vergoeding) {
        this.setVergoeding(vergoeding);
        return this;
    }

    public void setVergoeding(String vergoeding) {
        this.vergoeding = vergoeding;
    }

    public String getVergoedingscode() {
        return this.vergoedingscode;
    }

    public Inkomensvoorzieningsoort vergoedingscode(String vergoedingscode) {
        this.setVergoedingscode(vergoedingscode);
        return this;
    }

    public void setVergoedingscode(String vergoedingscode) {
        this.vergoedingscode = vergoedingscode;
    }

    public String getWet() {
        return this.wet;
    }

    public Inkomensvoorzieningsoort wet(String wet) {
        this.setWet(wet);
        return this;
    }

    public void setWet(String wet) {
        this.wet = wet;
    }

    public Set<Inkomensvoorziening> getIssoortvoorzieningInkomensvoorzienings() {
        return this.issoortvoorzieningInkomensvoorzienings;
    }

    public void setIssoortvoorzieningInkomensvoorzienings(Set<Inkomensvoorziening> inkomensvoorzienings) {
        if (this.issoortvoorzieningInkomensvoorzienings != null) {
            this.issoortvoorzieningInkomensvoorzienings.forEach(i -> i.setIssoortvoorzieningInkomensvoorzieningsoort(null));
        }
        if (inkomensvoorzienings != null) {
            inkomensvoorzienings.forEach(i -> i.setIssoortvoorzieningInkomensvoorzieningsoort(this));
        }
        this.issoortvoorzieningInkomensvoorzienings = inkomensvoorzienings;
    }

    public Inkomensvoorzieningsoort issoortvoorzieningInkomensvoorzienings(Set<Inkomensvoorziening> inkomensvoorzienings) {
        this.setIssoortvoorzieningInkomensvoorzienings(inkomensvoorzienings);
        return this;
    }

    public Inkomensvoorzieningsoort addIssoortvoorzieningInkomensvoorziening(Inkomensvoorziening inkomensvoorziening) {
        this.issoortvoorzieningInkomensvoorzienings.add(inkomensvoorziening);
        inkomensvoorziening.setIssoortvoorzieningInkomensvoorzieningsoort(this);
        return this;
    }

    public Inkomensvoorzieningsoort removeIssoortvoorzieningInkomensvoorziening(Inkomensvoorziening inkomensvoorziening) {
        this.issoortvoorzieningInkomensvoorzienings.remove(inkomensvoorziening);
        inkomensvoorziening.setIssoortvoorzieningInkomensvoorzieningsoort(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inkomensvoorzieningsoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Inkomensvoorzieningsoort) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inkomensvoorzieningsoort{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", regeling='" + getRegeling() + "'" +
            ", regelingcode='" + getRegelingcode() + "'" +
            ", vergoeding='" + getVergoeding() + "'" +
            ", vergoedingscode='" + getVergoedingscode() + "'" +
            ", wet='" + getWet() + "'" +
            "}";
    }
}
