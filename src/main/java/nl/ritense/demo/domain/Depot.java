package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Depot.
 */
@Entity
@Table(name = "depot")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Depot implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftDepot")
    @JsonIgnoreProperties(value = { "heeftMagazijnlocaties", "heeftKasts", "heeftDepot", "istevindeninVindplaats" }, allowSetters = true)
    private Set<Stelling> heeftStellings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "istevindeninDepot")
    @JsonIgnoreProperties(
        value = {
            "hoortbijProject", "istevindeninDepot", "istevindeninKast", "istevindeninPlank", "istevindeninStelling", "heeftArchiefstuks",
        },
        allowSetters = true
    )
    private Set<Vindplaats> istevindeninVindplaats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Depot id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Depot naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Depot omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Stelling> getHeeftStellings() {
        return this.heeftStellings;
    }

    public void setHeeftStellings(Set<Stelling> stellings) {
        if (this.heeftStellings != null) {
            this.heeftStellings.forEach(i -> i.setHeeftDepot(null));
        }
        if (stellings != null) {
            stellings.forEach(i -> i.setHeeftDepot(this));
        }
        this.heeftStellings = stellings;
    }

    public Depot heeftStellings(Set<Stelling> stellings) {
        this.setHeeftStellings(stellings);
        return this;
    }

    public Depot addHeeftStelling(Stelling stelling) {
        this.heeftStellings.add(stelling);
        stelling.setHeeftDepot(this);
        return this;
    }

    public Depot removeHeeftStelling(Stelling stelling) {
        this.heeftStellings.remove(stelling);
        stelling.setHeeftDepot(null);
        return this;
    }

    public Set<Vindplaats> getIstevindeninVindplaats() {
        return this.istevindeninVindplaats;
    }

    public void setIstevindeninVindplaats(Set<Vindplaats> vindplaats) {
        if (this.istevindeninVindplaats != null) {
            this.istevindeninVindplaats.forEach(i -> i.setIstevindeninDepot(null));
        }
        if (vindplaats != null) {
            vindplaats.forEach(i -> i.setIstevindeninDepot(this));
        }
        this.istevindeninVindplaats = vindplaats;
    }

    public Depot istevindeninVindplaats(Set<Vindplaats> vindplaats) {
        this.setIstevindeninVindplaats(vindplaats);
        return this;
    }

    public Depot addIstevindeninVindplaats(Vindplaats vindplaats) {
        this.istevindeninVindplaats.add(vindplaats);
        vindplaats.setIstevindeninDepot(this);
        return this;
    }

    public Depot removeIstevindeninVindplaats(Vindplaats vindplaats) {
        this.istevindeninVindplaats.remove(vindplaats);
        vindplaats.setIstevindeninDepot(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Depot)) {
            return false;
        }
        return getId() != null && getId().equals(((Depot) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Depot{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
