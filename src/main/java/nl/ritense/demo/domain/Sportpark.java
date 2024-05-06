package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Sportpark.
 */
@Entity
@Table(name = "sportpark")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sportpark implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftSportpark")
    @JsonIgnoreProperties(value = { "heeftBelijnings", "heeftSportpark" }, allowSetters = true)
    private Set<Veld> heeftVelds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sportpark id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Veld> getHeeftVelds() {
        return this.heeftVelds;
    }

    public void setHeeftVelds(Set<Veld> velds) {
        if (this.heeftVelds != null) {
            this.heeftVelds.forEach(i -> i.setHeeftSportpark(null));
        }
        if (velds != null) {
            velds.forEach(i -> i.setHeeftSportpark(this));
        }
        this.heeftVelds = velds;
    }

    public Sportpark heeftVelds(Set<Veld> velds) {
        this.setHeeftVelds(velds);
        return this;
    }

    public Sportpark addHeeftVeld(Veld veld) {
        this.heeftVelds.add(veld);
        veld.setHeeftSportpark(this);
        return this;
    }

    public Sportpark removeHeeftVeld(Veld veld) {
        this.heeftVelds.remove(veld);
        veld.setHeeftSportpark(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sportpark)) {
            return false;
        }
        return getId() != null && getId().equals(((Sportpark) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sportpark{" +
            "id=" + getId() +
            "}";
    }
}
