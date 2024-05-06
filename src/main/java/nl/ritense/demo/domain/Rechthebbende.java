package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Rechthebbende.
 */
@Entity
@Table(name = "rechthebbende")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Rechthebbende implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftRechthebbende")
    @JsonIgnoreProperties(
        value = {
            "heeftRechthebbende", "valtbinnenArchiefcategories", "stamtuitPeriodes", "isonderdeelvanArchiefstuks", "hoortbijIndelings",
        },
        allowSetters = true
    )
    private Set<Archief> heeftArchiefs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Rechthebbende id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Archief> getHeeftArchiefs() {
        return this.heeftArchiefs;
    }

    public void setHeeftArchiefs(Set<Archief> archiefs) {
        if (this.heeftArchiefs != null) {
            this.heeftArchiefs.forEach(i -> i.setHeeftRechthebbende(null));
        }
        if (archiefs != null) {
            archiefs.forEach(i -> i.setHeeftRechthebbende(this));
        }
        this.heeftArchiefs = archiefs;
    }

    public Rechthebbende heeftArchiefs(Set<Archief> archiefs) {
        this.setHeeftArchiefs(archiefs);
        return this;
    }

    public Rechthebbende addHeeftArchief(Archief archief) {
        this.heeftArchiefs.add(archief);
        archief.setHeeftRechthebbende(this);
        return this;
    }

    public Rechthebbende removeHeeftArchief(Archief archief) {
        this.heeftArchiefs.remove(archief);
        archief.setHeeftRechthebbende(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rechthebbende)) {
            return false;
        }
        return getId() != null && getId().equals(((Rechthebbende) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rechthebbende{" +
            "id=" + getId() +
            "}";
    }
}
