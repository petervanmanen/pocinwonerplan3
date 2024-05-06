package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Omgevingswaarderegel.
 */
@Entity
@Table(name = "omgevingswaarderegel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Omgevingswaarderegel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "groep")
    private String groep;

    @Column(name = "naam")
    private String naam;

    @ManyToMany(fetch = FetchType.LAZY)
    @NotNull
    @JoinTable(
        name = "rel_omgevingswaarderegel__beschrijft_omgevingsnorm",
        joinColumns = @JoinColumn(name = "omgevingswaarderegel_id"),
        inverseJoinColumns = @JoinColumn(name = "beschrijft_omgevingsnorm_id")
    )
    @JsonIgnoreProperties(value = { "beschrijftOmgevingswaarderegels" }, allowSetters = true)
    private Set<Omgevingsnorm> beschrijftOmgevingsnorms = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @NotNull
    @JoinTable(
        name = "rel_omgevingswaarderegel__beschrijft_omgevingswaarde",
        joinColumns = @JoinColumn(name = "omgevingswaarderegel_id"),
        inverseJoinColumns = @JoinColumn(name = "beschrijft_omgevingswaarde_id")
    )
    @JsonIgnoreProperties(value = { "beschrijftOmgevingswaarderegels" }, allowSetters = true)
    private Set<Omgevingswaarde> beschrijftOmgevingswaardes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Omgevingswaarderegel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroep() {
        return this.groep;
    }

    public Omgevingswaarderegel groep(String groep) {
        this.setGroep(groep);
        return this;
    }

    public void setGroep(String groep) {
        this.groep = groep;
    }

    public String getNaam() {
        return this.naam;
    }

    public Omgevingswaarderegel naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Set<Omgevingsnorm> getBeschrijftOmgevingsnorms() {
        return this.beschrijftOmgevingsnorms;
    }

    public void setBeschrijftOmgevingsnorms(Set<Omgevingsnorm> omgevingsnorms) {
        this.beschrijftOmgevingsnorms = omgevingsnorms;
    }

    public Omgevingswaarderegel beschrijftOmgevingsnorms(Set<Omgevingsnorm> omgevingsnorms) {
        this.setBeschrijftOmgevingsnorms(omgevingsnorms);
        return this;
    }

    public Omgevingswaarderegel addBeschrijftOmgevingsnorm(Omgevingsnorm omgevingsnorm) {
        this.beschrijftOmgevingsnorms.add(omgevingsnorm);
        return this;
    }

    public Omgevingswaarderegel removeBeschrijftOmgevingsnorm(Omgevingsnorm omgevingsnorm) {
        this.beschrijftOmgevingsnorms.remove(omgevingsnorm);
        return this;
    }

    public Set<Omgevingswaarde> getBeschrijftOmgevingswaardes() {
        return this.beschrijftOmgevingswaardes;
    }

    public void setBeschrijftOmgevingswaardes(Set<Omgevingswaarde> omgevingswaardes) {
        this.beschrijftOmgevingswaardes = omgevingswaardes;
    }

    public Omgevingswaarderegel beschrijftOmgevingswaardes(Set<Omgevingswaarde> omgevingswaardes) {
        this.setBeschrijftOmgevingswaardes(omgevingswaardes);
        return this;
    }

    public Omgevingswaarderegel addBeschrijftOmgevingswaarde(Omgevingswaarde omgevingswaarde) {
        this.beschrijftOmgevingswaardes.add(omgevingswaarde);
        return this;
    }

    public Omgevingswaarderegel removeBeschrijftOmgevingswaarde(Omgevingswaarde omgevingswaarde) {
        this.beschrijftOmgevingswaardes.remove(omgevingswaarde);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Omgevingswaarderegel)) {
            return false;
        }
        return getId() != null && getId().equals(((Omgevingswaarderegel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Omgevingswaarderegel{" +
            "id=" + getId() +
            ", groep='" + getGroep() + "'" +
            ", naam='" + getNaam() + "'" +
            "}";
    }
}
