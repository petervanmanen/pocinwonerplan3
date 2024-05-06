package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Voorzieningsoort.
 */
@Entity
@Table(name = "voorzieningsoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Voorzieningsoort implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "productcategorie")
    private String productcategorie;

    @Column(name = "productcategoriecode")
    private String productcategoriecode;

    @Size(max = 20)
    @Column(name = "productcode", length = 20)
    private String productcode;

    @Column(name = "wet")
    private String wet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "valtbinnenVoorzieningsoort")
    @JsonIgnoreProperties(
        value = { "heeftTariefs", "valtbinnenVoorzieningsoort", "betreftReserverings", "voorzieningLeverings" },
        allowSetters = true
    )
    private Set<Voorziening> valtbinnenVoorzienings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Voorzieningsoort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Voorzieningsoort code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNaam() {
        return this.naam;
    }

    public Voorzieningsoort naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Voorzieningsoort omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getProductcategorie() {
        return this.productcategorie;
    }

    public Voorzieningsoort productcategorie(String productcategorie) {
        this.setProductcategorie(productcategorie);
        return this;
    }

    public void setProductcategorie(String productcategorie) {
        this.productcategorie = productcategorie;
    }

    public String getProductcategoriecode() {
        return this.productcategoriecode;
    }

    public Voorzieningsoort productcategoriecode(String productcategoriecode) {
        this.setProductcategoriecode(productcategoriecode);
        return this;
    }

    public void setProductcategoriecode(String productcategoriecode) {
        this.productcategoriecode = productcategoriecode;
    }

    public String getProductcode() {
        return this.productcode;
    }

    public Voorzieningsoort productcode(String productcode) {
        this.setProductcode(productcode);
        return this;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getWet() {
        return this.wet;
    }

    public Voorzieningsoort wet(String wet) {
        this.setWet(wet);
        return this;
    }

    public void setWet(String wet) {
        this.wet = wet;
    }

    public Set<Voorziening> getValtbinnenVoorzienings() {
        return this.valtbinnenVoorzienings;
    }

    public void setValtbinnenVoorzienings(Set<Voorziening> voorzienings) {
        if (this.valtbinnenVoorzienings != null) {
            this.valtbinnenVoorzienings.forEach(i -> i.setValtbinnenVoorzieningsoort(null));
        }
        if (voorzienings != null) {
            voorzienings.forEach(i -> i.setValtbinnenVoorzieningsoort(this));
        }
        this.valtbinnenVoorzienings = voorzienings;
    }

    public Voorzieningsoort valtbinnenVoorzienings(Set<Voorziening> voorzienings) {
        this.setValtbinnenVoorzienings(voorzienings);
        return this;
    }

    public Voorzieningsoort addValtbinnenVoorziening(Voorziening voorziening) {
        this.valtbinnenVoorzienings.add(voorziening);
        voorziening.setValtbinnenVoorzieningsoort(this);
        return this;
    }

    public Voorzieningsoort removeValtbinnenVoorziening(Voorziening voorziening) {
        this.valtbinnenVoorzienings.remove(voorziening);
        voorziening.setValtbinnenVoorzieningsoort(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Voorzieningsoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Voorzieningsoort) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Voorzieningsoort{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", productcategorie='" + getProductcategorie() + "'" +
            ", productcategoriecode='" + getProductcategoriecode() + "'" +
            ", productcode='" + getProductcode() + "'" +
            ", wet='" + getWet() + "'" +
            "}";
    }
}
