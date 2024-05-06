package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Omgevingswaarde.
 */
@Entity
@Table(name = "omgevingswaarde")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Omgevingswaarde implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omgevingswaardegroep")
    private String omgevingswaardegroep;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "beschrijftOmgevingswaardes")
    @JsonIgnoreProperties(value = { "beschrijftOmgevingsnorms", "beschrijftOmgevingswaardes" }, allowSetters = true)
    private Set<Omgevingswaarderegel> beschrijftOmgevingswaarderegels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Omgevingswaarde id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Omgevingswaarde naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmgevingswaardegroep() {
        return this.omgevingswaardegroep;
    }

    public Omgevingswaarde omgevingswaardegroep(String omgevingswaardegroep) {
        this.setOmgevingswaardegroep(omgevingswaardegroep);
        return this;
    }

    public void setOmgevingswaardegroep(String omgevingswaardegroep) {
        this.omgevingswaardegroep = omgevingswaardegroep;
    }

    public Set<Omgevingswaarderegel> getBeschrijftOmgevingswaarderegels() {
        return this.beschrijftOmgevingswaarderegels;
    }

    public void setBeschrijftOmgevingswaarderegels(Set<Omgevingswaarderegel> omgevingswaarderegels) {
        if (this.beschrijftOmgevingswaarderegels != null) {
            this.beschrijftOmgevingswaarderegels.forEach(i -> i.removeBeschrijftOmgevingswaarde(this));
        }
        if (omgevingswaarderegels != null) {
            omgevingswaarderegels.forEach(i -> i.addBeschrijftOmgevingswaarde(this));
        }
        this.beschrijftOmgevingswaarderegels = omgevingswaarderegels;
    }

    public Omgevingswaarde beschrijftOmgevingswaarderegels(Set<Omgevingswaarderegel> omgevingswaarderegels) {
        this.setBeschrijftOmgevingswaarderegels(omgevingswaarderegels);
        return this;
    }

    public Omgevingswaarde addBeschrijftOmgevingswaarderegel(Omgevingswaarderegel omgevingswaarderegel) {
        this.beschrijftOmgevingswaarderegels.add(omgevingswaarderegel);
        omgevingswaarderegel.getBeschrijftOmgevingswaardes().add(this);
        return this;
    }

    public Omgevingswaarde removeBeschrijftOmgevingswaarderegel(Omgevingswaarderegel omgevingswaarderegel) {
        this.beschrijftOmgevingswaarderegels.remove(omgevingswaarderegel);
        omgevingswaarderegel.getBeschrijftOmgevingswaardes().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Omgevingswaarde)) {
            return false;
        }
        return getId() != null && getId().equals(((Omgevingswaarde) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Omgevingswaarde{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omgevingswaardegroep='" + getOmgevingswaardegroep() + "'" +
            "}";
    }
}
