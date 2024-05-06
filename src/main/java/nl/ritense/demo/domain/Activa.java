package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Activa.
 */
@Entity
@Table(name = "activa")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Activa implements Serializable {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "issoortActivas" }, allowSetters = true)
    private Activasoort issoortActivasoort;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "heeftActivas")
    @JsonIgnoreProperties(
        value = {
            "valtbinnenHoofdrekenings",
            "heeftSubrekenings",
            "heeftWerkorders",
            "heeftActivas",
            "heeftKostenplaats",
            "valtbinnenHoofdrekening2",
            "betreftBegrotingregels",
            "vanMutaties",
            "naarMutaties",
            "wordtgeschrevenopInkooporders",
        },
        allowSetters = true
    )
    private Set<Hoofdrekening> heeftHoofdrekenings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Activa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Activa naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Activa omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Activasoort getIssoortActivasoort() {
        return this.issoortActivasoort;
    }

    public void setIssoortActivasoort(Activasoort activasoort) {
        this.issoortActivasoort = activasoort;
    }

    public Activa issoortActivasoort(Activasoort activasoort) {
        this.setIssoortActivasoort(activasoort);
        return this;
    }

    public Set<Hoofdrekening> getHeeftHoofdrekenings() {
        return this.heeftHoofdrekenings;
    }

    public void setHeeftHoofdrekenings(Set<Hoofdrekening> hoofdrekenings) {
        if (this.heeftHoofdrekenings != null) {
            this.heeftHoofdrekenings.forEach(i -> i.removeHeeftActiva(this));
        }
        if (hoofdrekenings != null) {
            hoofdrekenings.forEach(i -> i.addHeeftActiva(this));
        }
        this.heeftHoofdrekenings = hoofdrekenings;
    }

    public Activa heeftHoofdrekenings(Set<Hoofdrekening> hoofdrekenings) {
        this.setHeeftHoofdrekenings(hoofdrekenings);
        return this;
    }

    public Activa addHeeftHoofdrekening(Hoofdrekening hoofdrekening) {
        this.heeftHoofdrekenings.add(hoofdrekening);
        hoofdrekening.getHeeftActivas().add(this);
        return this;
    }

    public Activa removeHeeftHoofdrekening(Hoofdrekening hoofdrekening) {
        this.heeftHoofdrekenings.remove(hoofdrekening);
        hoofdrekening.getHeeftActivas().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Activa)) {
            return false;
        }
        return getId() != null && getId().equals(((Activa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Activa{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
