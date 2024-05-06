package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Sector.
 */
@Entity
@Table(name = "sector")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sector implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "valtbinnenSector")
    @JsonIgnoreProperties(
        value = {
            "heeftZaak",
            "heeftRapportagemoments",
            "heeftTaaks",
            "valtbinnenSector",
            "behandelaarMedewerker",
            "verstrekkerRechtspersoon",
            "heeftKostenplaats",
            "heeftDocument",
            "betreftSubsidieaanvraag",
            "betreftSubsidiebeschikking",
            "aanvragerRechtspersoon",
            "aanvragerMedewerker",
        },
        allowSetters = true
    )
    private Set<Subsidie> valtbinnenSubsidies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sector id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Sector naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Sector omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Subsidie> getValtbinnenSubsidies() {
        return this.valtbinnenSubsidies;
    }

    public void setValtbinnenSubsidies(Set<Subsidie> subsidies) {
        if (this.valtbinnenSubsidies != null) {
            this.valtbinnenSubsidies.forEach(i -> i.setValtbinnenSector(null));
        }
        if (subsidies != null) {
            subsidies.forEach(i -> i.setValtbinnenSector(this));
        }
        this.valtbinnenSubsidies = subsidies;
    }

    public Sector valtbinnenSubsidies(Set<Subsidie> subsidies) {
        this.setValtbinnenSubsidies(subsidies);
        return this;
    }

    public Sector addValtbinnenSubsidie(Subsidie subsidie) {
        this.valtbinnenSubsidies.add(subsidie);
        subsidie.setValtbinnenSector(this);
        return this;
    }

    public Sector removeValtbinnenSubsidie(Subsidie subsidie) {
        this.valtbinnenSubsidies.remove(subsidie);
        subsidie.setValtbinnenSector(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sector)) {
            return false;
        }
        return getId() != null && getId().equals(((Sector) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sector{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
