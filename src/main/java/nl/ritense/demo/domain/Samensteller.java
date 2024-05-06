package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Samensteller.
 */
@Entity
@Table(name = "samensteller")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Samensteller implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "rol")
    private String rol;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_samensteller__steltsamen_tentoonstelling",
        joinColumns = @JoinColumn(name = "samensteller_id"),
        inverseJoinColumns = @JoinColumn(name = "steltsamen_tentoonstelling_id")
    )
    @JsonIgnoreProperties(
        value = { "voorRondleidings", "isbedoeldvoorBruikleens", "onderdeelMuseumobjects", "steltsamenSamenstellers" },
        allowSetters = true
    )
    private Set<Tentoonstelling> steltsamenTentoonstellings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Samensteller id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRol() {
        return this.rol;
    }

    public Samensteller rol(String rol) {
        this.setRol(rol);
        return this;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Set<Tentoonstelling> getSteltsamenTentoonstellings() {
        return this.steltsamenTentoonstellings;
    }

    public void setSteltsamenTentoonstellings(Set<Tentoonstelling> tentoonstellings) {
        this.steltsamenTentoonstellings = tentoonstellings;
    }

    public Samensteller steltsamenTentoonstellings(Set<Tentoonstelling> tentoonstellings) {
        this.setSteltsamenTentoonstellings(tentoonstellings);
        return this;
    }

    public Samensteller addSteltsamenTentoonstelling(Tentoonstelling tentoonstelling) {
        this.steltsamenTentoonstellings.add(tentoonstelling);
        return this;
    }

    public Samensteller removeSteltsamenTentoonstelling(Tentoonstelling tentoonstelling) {
        this.steltsamenTentoonstellings.remove(tentoonstelling);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Samensteller)) {
            return false;
        }
        return getId() != null && getId().equals(((Samensteller) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Samensteller{" +
            "id=" + getId() +
            ", rol='" + getRol() + "'" +
            "}";
    }
}
