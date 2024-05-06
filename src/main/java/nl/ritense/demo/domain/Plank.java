package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Plank.
 */
@Entity
@Table(name = "plank")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Plank implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @Column(name = "planknummer", length = 20)
    private String planknummer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftPlanks", "heeftStelling", "istevindeninVindplaats" }, allowSetters = true)
    private Kast heeftKast;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "istevindeninPlank")
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

    public Plank id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanknummer() {
        return this.planknummer;
    }

    public Plank planknummer(String planknummer) {
        this.setPlanknummer(planknummer);
        return this;
    }

    public void setPlanknummer(String planknummer) {
        this.planknummer = planknummer;
    }

    public Kast getHeeftKast() {
        return this.heeftKast;
    }

    public void setHeeftKast(Kast kast) {
        this.heeftKast = kast;
    }

    public Plank heeftKast(Kast kast) {
        this.setHeeftKast(kast);
        return this;
    }

    public Set<Vindplaats> getIstevindeninVindplaats() {
        return this.istevindeninVindplaats;
    }

    public void setIstevindeninVindplaats(Set<Vindplaats> vindplaats) {
        if (this.istevindeninVindplaats != null) {
            this.istevindeninVindplaats.forEach(i -> i.setIstevindeninPlank(null));
        }
        if (vindplaats != null) {
            vindplaats.forEach(i -> i.setIstevindeninPlank(this));
        }
        this.istevindeninVindplaats = vindplaats;
    }

    public Plank istevindeninVindplaats(Set<Vindplaats> vindplaats) {
        this.setIstevindeninVindplaats(vindplaats);
        return this;
    }

    public Plank addIstevindeninVindplaats(Vindplaats vindplaats) {
        this.istevindeninVindplaats.add(vindplaats);
        vindplaats.setIstevindeninPlank(this);
        return this;
    }

    public Plank removeIstevindeninVindplaats(Vindplaats vindplaats) {
        this.istevindeninVindplaats.remove(vindplaats);
        vindplaats.setIstevindeninPlank(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Plank)) {
            return false;
        }
        return getId() != null && getId().equals(((Plank) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Plank{" +
            "id=" + getId() +
            ", planknummer='" + getPlanknummer() + "'" +
            "}";
    }
}
