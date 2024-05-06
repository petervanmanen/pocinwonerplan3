package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Normwaarde.
 */
@Entity
@Table(name = "normwaarde")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Normwaarde implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "kwalitatievewaarde")
    private String kwalitatievewaarde;

    @Column(name = "kwantitatievewaardeeenheid")
    private String kwantitatievewaardeeenheid;

    @Column(name = "kwantitatievewaardeomvang")
    private String kwantitatievewaardeomvang;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_normwaarde__geldtvoor_locatie",
        joinColumns = @JoinColumn(name = "normwaarde_id"),
        inverseJoinColumns = @JoinColumn(name = "geldtvoor_locatie_id")
    )
    @JsonIgnoreProperties(
        value = {
            "heeftContainers",
            "betreftMeldings",
            "gestoptopOphaalmoments",
            "betreftProjectlocaties",
            "heeftlocatiePuts",
            "wordtbegrensddoorProjects",
            "betreftVerzoeks",
            "werkingsgebiedRegelteksts",
            "isverbondenmetActiviteits",
            "verwijstnaarGebiedsaanwijzings",
            "geldtvoorNormwaardes",
        },
        allowSetters = true
    )
    private Set<Locatie> geldtvoorLocaties = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "bevatNormwaardes" }, allowSetters = true)
    private Norm bevatNorm;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Normwaarde id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKwalitatievewaarde() {
        return this.kwalitatievewaarde;
    }

    public Normwaarde kwalitatievewaarde(String kwalitatievewaarde) {
        this.setKwalitatievewaarde(kwalitatievewaarde);
        return this;
    }

    public void setKwalitatievewaarde(String kwalitatievewaarde) {
        this.kwalitatievewaarde = kwalitatievewaarde;
    }

    public String getKwantitatievewaardeeenheid() {
        return this.kwantitatievewaardeeenheid;
    }

    public Normwaarde kwantitatievewaardeeenheid(String kwantitatievewaardeeenheid) {
        this.setKwantitatievewaardeeenheid(kwantitatievewaardeeenheid);
        return this;
    }

    public void setKwantitatievewaardeeenheid(String kwantitatievewaardeeenheid) {
        this.kwantitatievewaardeeenheid = kwantitatievewaardeeenheid;
    }

    public String getKwantitatievewaardeomvang() {
        return this.kwantitatievewaardeomvang;
    }

    public Normwaarde kwantitatievewaardeomvang(String kwantitatievewaardeomvang) {
        this.setKwantitatievewaardeomvang(kwantitatievewaardeomvang);
        return this;
    }

    public void setKwantitatievewaardeomvang(String kwantitatievewaardeomvang) {
        this.kwantitatievewaardeomvang = kwantitatievewaardeomvang;
    }

    public Set<Locatie> getGeldtvoorLocaties() {
        return this.geldtvoorLocaties;
    }

    public void setGeldtvoorLocaties(Set<Locatie> locaties) {
        this.geldtvoorLocaties = locaties;
    }

    public Normwaarde geldtvoorLocaties(Set<Locatie> locaties) {
        this.setGeldtvoorLocaties(locaties);
        return this;
    }

    public Normwaarde addGeldtvoorLocatie(Locatie locatie) {
        this.geldtvoorLocaties.add(locatie);
        return this;
    }

    public Normwaarde removeGeldtvoorLocatie(Locatie locatie) {
        this.geldtvoorLocaties.remove(locatie);
        return this;
    }

    public Norm getBevatNorm() {
        return this.bevatNorm;
    }

    public void setBevatNorm(Norm norm) {
        this.bevatNorm = norm;
    }

    public Normwaarde bevatNorm(Norm norm) {
        this.setBevatNorm(norm);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Normwaarde)) {
            return false;
        }
        return getId() != null && getId().equals(((Normwaarde) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Normwaarde{" +
            "id=" + getId() +
            ", kwalitatievewaarde='" + getKwalitatievewaarde() + "'" +
            ", kwantitatievewaardeeenheid='" + getKwantitatievewaardeeenheid() + "'" +
            ", kwantitatievewaardeomvang='" + getKwantitatievewaardeomvang() + "'" +
            "}";
    }
}
