package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Veld.
 */
@Entity
@Table(name = "veld")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Veld implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_veld__heeft_belijning",
        joinColumns = @JoinColumn(name = "veld_id"),
        inverseJoinColumns = @JoinColumn(name = "heeft_belijning_id")
    )
    @JsonIgnoreProperties(value = { "heeftBinnenlocaties", "heeftVelds" }, allowSetters = true)
    private Set<Belijning> heeftBelijnings = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftVelds" }, allowSetters = true)
    private Sportpark heeftSportpark;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Veld id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Belijning> getHeeftBelijnings() {
        return this.heeftBelijnings;
    }

    public void setHeeftBelijnings(Set<Belijning> belijnings) {
        this.heeftBelijnings = belijnings;
    }

    public Veld heeftBelijnings(Set<Belijning> belijnings) {
        this.setHeeftBelijnings(belijnings);
        return this;
    }

    public Veld addHeeftBelijning(Belijning belijning) {
        this.heeftBelijnings.add(belijning);
        return this;
    }

    public Veld removeHeeftBelijning(Belijning belijning) {
        this.heeftBelijnings.remove(belijning);
        return this;
    }

    public Sportpark getHeeftSportpark() {
        return this.heeftSportpark;
    }

    public void setHeeftSportpark(Sportpark sportpark) {
        this.heeftSportpark = sportpark;
    }

    public Veld heeftSportpark(Sportpark sportpark) {
        this.setHeeftSportpark(sportpark);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Veld)) {
            return false;
        }
        return getId() != null && getId().equals(((Veld) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Veld{" +
            "id=" + getId() +
            "}";
    }
}
