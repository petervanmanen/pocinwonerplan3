package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Kadastralemutatie.
 */
@Entity
@Table(name = "kadastralemutatie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Kadastralemutatie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "betrokkenenKadastralemutaties")
    @JsonIgnoreProperties(
        value = {
            "projectleiderRapportagemoments",
            "aanvragerSubsidies",
            "heeftTenaamstellings",
            "betrokkenenKadastralemutaties",
            "isIndiener",
            "houderParkeervergunnings",
            "verstrekkerSubsidies",
            "projectleiderTaaks",
            "heeftVastgoedcontracts",
        },
        allowSetters = true
    )
    private Set<Rechtspersoon> betrokkenenRechtspersoons = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Kadastralemutatie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Rechtspersoon> getBetrokkenenRechtspersoons() {
        return this.betrokkenenRechtspersoons;
    }

    public void setBetrokkenenRechtspersoons(Set<Rechtspersoon> rechtspersoons) {
        if (this.betrokkenenRechtspersoons != null) {
            this.betrokkenenRechtspersoons.forEach(i -> i.removeBetrokkenenKadastralemutatie(this));
        }
        if (rechtspersoons != null) {
            rechtspersoons.forEach(i -> i.addBetrokkenenKadastralemutatie(this));
        }
        this.betrokkenenRechtspersoons = rechtspersoons;
    }

    public Kadastralemutatie betrokkenenRechtspersoons(Set<Rechtspersoon> rechtspersoons) {
        this.setBetrokkenenRechtspersoons(rechtspersoons);
        return this;
    }

    public Kadastralemutatie addBetrokkenenRechtspersoon(Rechtspersoon rechtspersoon) {
        this.betrokkenenRechtspersoons.add(rechtspersoon);
        rechtspersoon.getBetrokkenenKadastralemutaties().add(this);
        return this;
    }

    public Kadastralemutatie removeBetrokkenenRechtspersoon(Rechtspersoon rechtspersoon) {
        this.betrokkenenRechtspersoons.remove(rechtspersoon);
        rechtspersoon.getBetrokkenenKadastralemutaties().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Kadastralemutatie)) {
            return false;
        }
        return getId() != null && getId().equals(((Kadastralemutatie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Kadastralemutatie{" +
            "id=" + getId() +
            "}";
    }
}
