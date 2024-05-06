package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Lijnengroep.
 */
@Entity
@Table(name = "lijnengroep")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Lijnengroep implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "omvatLijnengroep")
    @JsonIgnoreProperties(value = { "omvatLijnengroep" }, allowSetters = true)
    private Set<Lijn> omvatLijns = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Lijnengroep id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Lijn> getOmvatLijns() {
        return this.omvatLijns;
    }

    public void setOmvatLijns(Set<Lijn> lijns) {
        if (this.omvatLijns != null) {
            this.omvatLijns.forEach(i -> i.setOmvatLijnengroep(null));
        }
        if (lijns != null) {
            lijns.forEach(i -> i.setOmvatLijnengroep(this));
        }
        this.omvatLijns = lijns;
    }

    public Lijnengroep omvatLijns(Set<Lijn> lijns) {
        this.setOmvatLijns(lijns);
        return this;
    }

    public Lijnengroep addOmvatLijn(Lijn lijn) {
        this.omvatLijns.add(lijn);
        lijn.setOmvatLijnengroep(this);
        return this;
    }

    public Lijnengroep removeOmvatLijn(Lijn lijn) {
        this.omvatLijns.remove(lijn);
        lijn.setOmvatLijnengroep(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lijnengroep)) {
            return false;
        }
        return getId() != null && getId().equals(((Lijnengroep) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Lijnengroep{" +
            "id=" + getId() +
            "}";
    }
}
