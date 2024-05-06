package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Omgevingsnorm.
 */
@Entity
@Table(name = "omgevingsnorm")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Omgevingsnorm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omgevingsnormgroep")
    private String omgevingsnormgroep;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "beschrijftOmgevingsnorms")
    @JsonIgnoreProperties(value = { "beschrijftOmgevingsnorms", "beschrijftOmgevingswaardes" }, allowSetters = true)
    private Set<Omgevingswaarderegel> beschrijftOmgevingswaarderegels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Omgevingsnorm id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Omgevingsnorm naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmgevingsnormgroep() {
        return this.omgevingsnormgroep;
    }

    public Omgevingsnorm omgevingsnormgroep(String omgevingsnormgroep) {
        this.setOmgevingsnormgroep(omgevingsnormgroep);
        return this;
    }

    public void setOmgevingsnormgroep(String omgevingsnormgroep) {
        this.omgevingsnormgroep = omgevingsnormgroep;
    }

    public Set<Omgevingswaarderegel> getBeschrijftOmgevingswaarderegels() {
        return this.beschrijftOmgevingswaarderegels;
    }

    public void setBeschrijftOmgevingswaarderegels(Set<Omgevingswaarderegel> omgevingswaarderegels) {
        if (this.beschrijftOmgevingswaarderegels != null) {
            this.beschrijftOmgevingswaarderegels.forEach(i -> i.removeBeschrijftOmgevingsnorm(this));
        }
        if (omgevingswaarderegels != null) {
            omgevingswaarderegels.forEach(i -> i.addBeschrijftOmgevingsnorm(this));
        }
        this.beschrijftOmgevingswaarderegels = omgevingswaarderegels;
    }

    public Omgevingsnorm beschrijftOmgevingswaarderegels(Set<Omgevingswaarderegel> omgevingswaarderegels) {
        this.setBeschrijftOmgevingswaarderegels(omgevingswaarderegels);
        return this;
    }

    public Omgevingsnorm addBeschrijftOmgevingswaarderegel(Omgevingswaarderegel omgevingswaarderegel) {
        this.beschrijftOmgevingswaarderegels.add(omgevingswaarderegel);
        omgevingswaarderegel.getBeschrijftOmgevingsnorms().add(this);
        return this;
    }

    public Omgevingsnorm removeBeschrijftOmgevingswaarderegel(Omgevingswaarderegel omgevingswaarderegel) {
        this.beschrijftOmgevingswaarderegels.remove(omgevingswaarderegel);
        omgevingswaarderegel.getBeschrijftOmgevingsnorms().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Omgevingsnorm)) {
            return false;
        }
        return getId() != null && getId().equals(((Omgevingsnorm) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Omgevingsnorm{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omgevingsnormgroep='" + getOmgevingsnormgroep() + "'" +
            "}";
    }
}
