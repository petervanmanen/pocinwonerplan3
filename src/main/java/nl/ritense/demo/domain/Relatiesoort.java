package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Relatiesoort.
 */
@Entity
@Table(name = "relatiesoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Relatiesoort implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "issoortRelatiesoort")
    @JsonIgnoreProperties(value = { "issoortRelatiesoort", "maaktonderdeelvanHuishoudens", "heeftrelatieClients" }, allowSetters = true)
    private Set<Relatie> issoortRelaties = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Relatiesoort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Relatie> getIssoortRelaties() {
        return this.issoortRelaties;
    }

    public void setIssoortRelaties(Set<Relatie> relaties) {
        if (this.issoortRelaties != null) {
            this.issoortRelaties.forEach(i -> i.setIssoortRelatiesoort(null));
        }
        if (relaties != null) {
            relaties.forEach(i -> i.setIssoortRelatiesoort(this));
        }
        this.issoortRelaties = relaties;
    }

    public Relatiesoort issoortRelaties(Set<Relatie> relaties) {
        this.setIssoortRelaties(relaties);
        return this;
    }

    public Relatiesoort addIssoortRelatie(Relatie relatie) {
        this.issoortRelaties.add(relatie);
        relatie.setIssoortRelatiesoort(this);
        return this;
    }

    public Relatiesoort removeIssoortRelatie(Relatie relatie) {
        this.issoortRelaties.remove(relatie);
        relatie.setIssoortRelatiesoort(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Relatiesoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Relatiesoort) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Relatiesoort{" +
            "id=" + getId() +
            "}";
    }
}
