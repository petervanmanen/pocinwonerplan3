package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Belijning.
 */
@Entity
@Table(name = "belijning")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Belijning implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "heeftBelijnings")
    @JsonIgnoreProperties(
        value = { "inspectierapportDocuments", "isgevestigdinVerblijfsobject", "bedientWijk", "heeftBelijnings", "heeftSportmateriaals" },
        allowSetters = true
    )
    private Set<Binnenlocatie> heeftBinnenlocaties = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "heeftBelijnings")
    @JsonIgnoreProperties(value = { "heeftBelijnings", "heeftSportpark" }, allowSetters = true)
    private Set<Veld> heeftVelds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Belijning id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Belijning naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Set<Binnenlocatie> getHeeftBinnenlocaties() {
        return this.heeftBinnenlocaties;
    }

    public void setHeeftBinnenlocaties(Set<Binnenlocatie> binnenlocaties) {
        if (this.heeftBinnenlocaties != null) {
            this.heeftBinnenlocaties.forEach(i -> i.removeHeeftBelijning(this));
        }
        if (binnenlocaties != null) {
            binnenlocaties.forEach(i -> i.addHeeftBelijning(this));
        }
        this.heeftBinnenlocaties = binnenlocaties;
    }

    public Belijning heeftBinnenlocaties(Set<Binnenlocatie> binnenlocaties) {
        this.setHeeftBinnenlocaties(binnenlocaties);
        return this;
    }

    public Belijning addHeeftBinnenlocatie(Binnenlocatie binnenlocatie) {
        this.heeftBinnenlocaties.add(binnenlocatie);
        binnenlocatie.getHeeftBelijnings().add(this);
        return this;
    }

    public Belijning removeHeeftBinnenlocatie(Binnenlocatie binnenlocatie) {
        this.heeftBinnenlocaties.remove(binnenlocatie);
        binnenlocatie.getHeeftBelijnings().remove(this);
        return this;
    }

    public Set<Veld> getHeeftVelds() {
        return this.heeftVelds;
    }

    public void setHeeftVelds(Set<Veld> velds) {
        if (this.heeftVelds != null) {
            this.heeftVelds.forEach(i -> i.removeHeeftBelijning(this));
        }
        if (velds != null) {
            velds.forEach(i -> i.addHeeftBelijning(this));
        }
        this.heeftVelds = velds;
    }

    public Belijning heeftVelds(Set<Veld> velds) {
        this.setHeeftVelds(velds);
        return this;
    }

    public Belijning addHeeftVeld(Veld veld) {
        this.heeftVelds.add(veld);
        veld.getHeeftBelijnings().add(this);
        return this;
    }

    public Belijning removeHeeftVeld(Veld veld) {
        this.heeftVelds.remove(veld);
        veld.getHeeftBelijnings().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Belijning)) {
            return false;
        }
        return getId() != null && getId().equals(((Belijning) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Belijning{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            "}";
    }
}
