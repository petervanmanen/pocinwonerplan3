package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Stelling.
 */
@Entity
@Table(name = "stelling")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Stelling implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "inhoud")
    private String inhoud;

    @Column(name = "stellingcode")
    private String stellingcode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftStelling")
    @JsonIgnoreProperties(value = { "heeftStelling", "staatopDoos" }, allowSetters = true)
    private Set<Magazijnlocatie> heeftMagazijnlocaties = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftStelling")
    @JsonIgnoreProperties(value = { "heeftPlanks", "heeftStelling", "istevindeninVindplaats" }, allowSetters = true)
    private Set<Kast> heeftKasts = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftStellings", "istevindeninVindplaats" }, allowSetters = true)
    private Depot heeftDepot;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "istevindeninStelling")
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

    public Stelling id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInhoud() {
        return this.inhoud;
    }

    public Stelling inhoud(String inhoud) {
        this.setInhoud(inhoud);
        return this;
    }

    public void setInhoud(String inhoud) {
        this.inhoud = inhoud;
    }

    public String getStellingcode() {
        return this.stellingcode;
    }

    public Stelling stellingcode(String stellingcode) {
        this.setStellingcode(stellingcode);
        return this;
    }

    public void setStellingcode(String stellingcode) {
        this.stellingcode = stellingcode;
    }

    public Set<Magazijnlocatie> getHeeftMagazijnlocaties() {
        return this.heeftMagazijnlocaties;
    }

    public void setHeeftMagazijnlocaties(Set<Magazijnlocatie> magazijnlocaties) {
        if (this.heeftMagazijnlocaties != null) {
            this.heeftMagazijnlocaties.forEach(i -> i.setHeeftStelling(null));
        }
        if (magazijnlocaties != null) {
            magazijnlocaties.forEach(i -> i.setHeeftStelling(this));
        }
        this.heeftMagazijnlocaties = magazijnlocaties;
    }

    public Stelling heeftMagazijnlocaties(Set<Magazijnlocatie> magazijnlocaties) {
        this.setHeeftMagazijnlocaties(magazijnlocaties);
        return this;
    }

    public Stelling addHeeftMagazijnlocatie(Magazijnlocatie magazijnlocatie) {
        this.heeftMagazijnlocaties.add(magazijnlocatie);
        magazijnlocatie.setHeeftStelling(this);
        return this;
    }

    public Stelling removeHeeftMagazijnlocatie(Magazijnlocatie magazijnlocatie) {
        this.heeftMagazijnlocaties.remove(magazijnlocatie);
        magazijnlocatie.setHeeftStelling(null);
        return this;
    }

    public Set<Kast> getHeeftKasts() {
        return this.heeftKasts;
    }

    public void setHeeftKasts(Set<Kast> kasts) {
        if (this.heeftKasts != null) {
            this.heeftKasts.forEach(i -> i.setHeeftStelling(null));
        }
        if (kasts != null) {
            kasts.forEach(i -> i.setHeeftStelling(this));
        }
        this.heeftKasts = kasts;
    }

    public Stelling heeftKasts(Set<Kast> kasts) {
        this.setHeeftKasts(kasts);
        return this;
    }

    public Stelling addHeeftKast(Kast kast) {
        this.heeftKasts.add(kast);
        kast.setHeeftStelling(this);
        return this;
    }

    public Stelling removeHeeftKast(Kast kast) {
        this.heeftKasts.remove(kast);
        kast.setHeeftStelling(null);
        return this;
    }

    public Depot getHeeftDepot() {
        return this.heeftDepot;
    }

    public void setHeeftDepot(Depot depot) {
        this.heeftDepot = depot;
    }

    public Stelling heeftDepot(Depot depot) {
        this.setHeeftDepot(depot);
        return this;
    }

    public Set<Vindplaats> getIstevindeninVindplaats() {
        return this.istevindeninVindplaats;
    }

    public void setIstevindeninVindplaats(Set<Vindplaats> vindplaats) {
        if (this.istevindeninVindplaats != null) {
            this.istevindeninVindplaats.forEach(i -> i.setIstevindeninStelling(null));
        }
        if (vindplaats != null) {
            vindplaats.forEach(i -> i.setIstevindeninStelling(this));
        }
        this.istevindeninVindplaats = vindplaats;
    }

    public Stelling istevindeninVindplaats(Set<Vindplaats> vindplaats) {
        this.setIstevindeninVindplaats(vindplaats);
        return this;
    }

    public Stelling addIstevindeninVindplaats(Vindplaats vindplaats) {
        this.istevindeninVindplaats.add(vindplaats);
        vindplaats.setIstevindeninStelling(this);
        return this;
    }

    public Stelling removeIstevindeninVindplaats(Vindplaats vindplaats) {
        this.istevindeninVindplaats.remove(vindplaats);
        vindplaats.setIstevindeninStelling(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stelling)) {
            return false;
        }
        return getId() != null && getId().equals(((Stelling) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Stelling{" +
            "id=" + getId() +
            ", inhoud='" + getInhoud() + "'" +
            ", stellingcode='" + getStellingcode() + "'" +
            "}";
    }
}
