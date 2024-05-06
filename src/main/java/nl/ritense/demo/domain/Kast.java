package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Kast.
 */
@Entity
@Table(name = "kast")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Kast implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @Column(name = "kastnummer", length = 20)
    private String kastnummer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftKast")
    @JsonIgnoreProperties(value = { "heeftKast", "istevindeninVindplaats" }, allowSetters = true)
    private Set<Plank> heeftPlanks = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftMagazijnlocaties", "heeftKasts", "heeftDepot", "istevindeninVindplaats" }, allowSetters = true)
    private Stelling heeftStelling;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "istevindeninKast")
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

    public Kast id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKastnummer() {
        return this.kastnummer;
    }

    public Kast kastnummer(String kastnummer) {
        this.setKastnummer(kastnummer);
        return this;
    }

    public void setKastnummer(String kastnummer) {
        this.kastnummer = kastnummer;
    }

    public Set<Plank> getHeeftPlanks() {
        return this.heeftPlanks;
    }

    public void setHeeftPlanks(Set<Plank> planks) {
        if (this.heeftPlanks != null) {
            this.heeftPlanks.forEach(i -> i.setHeeftKast(null));
        }
        if (planks != null) {
            planks.forEach(i -> i.setHeeftKast(this));
        }
        this.heeftPlanks = planks;
    }

    public Kast heeftPlanks(Set<Plank> planks) {
        this.setHeeftPlanks(planks);
        return this;
    }

    public Kast addHeeftPlank(Plank plank) {
        this.heeftPlanks.add(plank);
        plank.setHeeftKast(this);
        return this;
    }

    public Kast removeHeeftPlank(Plank plank) {
        this.heeftPlanks.remove(plank);
        plank.setHeeftKast(null);
        return this;
    }

    public Stelling getHeeftStelling() {
        return this.heeftStelling;
    }

    public void setHeeftStelling(Stelling stelling) {
        this.heeftStelling = stelling;
    }

    public Kast heeftStelling(Stelling stelling) {
        this.setHeeftStelling(stelling);
        return this;
    }

    public Set<Vindplaats> getIstevindeninVindplaats() {
        return this.istevindeninVindplaats;
    }

    public void setIstevindeninVindplaats(Set<Vindplaats> vindplaats) {
        if (this.istevindeninVindplaats != null) {
            this.istevindeninVindplaats.forEach(i -> i.setIstevindeninKast(null));
        }
        if (vindplaats != null) {
            vindplaats.forEach(i -> i.setIstevindeninKast(this));
        }
        this.istevindeninVindplaats = vindplaats;
    }

    public Kast istevindeninVindplaats(Set<Vindplaats> vindplaats) {
        this.setIstevindeninVindplaats(vindplaats);
        return this;
    }

    public Kast addIstevindeninVindplaats(Vindplaats vindplaats) {
        this.istevindeninVindplaats.add(vindplaats);
        vindplaats.setIstevindeninKast(this);
        return this;
    }

    public Kast removeIstevindeninVindplaats(Vindplaats vindplaats) {
        this.istevindeninVindplaats.remove(vindplaats);
        vindplaats.setIstevindeninKast(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Kast)) {
            return false;
        }
        return getId() != null && getId().equals(((Kast) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Kast{" +
            "id=" + getId() +
            ", kastnummer='" + getKastnummer() + "'" +
            "}";
    }
}
