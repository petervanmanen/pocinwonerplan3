package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Activasoort.
 */
@Entity
@Table(name = "activasoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Activasoort implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "issoortActivasoort")
    @JsonIgnoreProperties(value = { "issoortActivasoort", "heeftHoofdrekenings" }, allowSetters = true)
    private Set<Activa> issoortActivas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Activasoort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Activasoort naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Activasoort omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Activa> getIssoortActivas() {
        return this.issoortActivas;
    }

    public void setIssoortActivas(Set<Activa> activas) {
        if (this.issoortActivas != null) {
            this.issoortActivas.forEach(i -> i.setIssoortActivasoort(null));
        }
        if (activas != null) {
            activas.forEach(i -> i.setIssoortActivasoort(this));
        }
        this.issoortActivas = activas;
    }

    public Activasoort issoortActivas(Set<Activa> activas) {
        this.setIssoortActivas(activas);
        return this;
    }

    public Activasoort addIssoortActiva(Activa activa) {
        this.issoortActivas.add(activa);
        activa.setIssoortActivasoort(this);
        return this;
    }

    public Activasoort removeIssoortActiva(Activa activa) {
        this.issoortActivas.remove(activa);
        activa.setIssoortActivasoort(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Activasoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Activasoort) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Activasoort{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
