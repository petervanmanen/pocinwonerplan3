package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Uitgever.
 */
@Entity
@Table(name = "uitgever")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Uitgever implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftUitgever")
    @JsonIgnoreProperties(
        value = {
            "isonderdeelvanArchief",
            "heeftUitgever",
            "heeftVindplaats",
            "heeftOrdeningsschemas",
            "stamtuitPeriodes",
            "valtbinnenIndeling",
            "voorAanvraags",
        },
        allowSetters = true
    )
    private Set<Archiefstuk> heeftArchiefstuks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Uitgever id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Archiefstuk> getHeeftArchiefstuks() {
        return this.heeftArchiefstuks;
    }

    public void setHeeftArchiefstuks(Set<Archiefstuk> archiefstuks) {
        if (this.heeftArchiefstuks != null) {
            this.heeftArchiefstuks.forEach(i -> i.setHeeftUitgever(null));
        }
        if (archiefstuks != null) {
            archiefstuks.forEach(i -> i.setHeeftUitgever(this));
        }
        this.heeftArchiefstuks = archiefstuks;
    }

    public Uitgever heeftArchiefstuks(Set<Archiefstuk> archiefstuks) {
        this.setHeeftArchiefstuks(archiefstuks);
        return this;
    }

    public Uitgever addHeeftArchiefstuk(Archiefstuk archiefstuk) {
        this.heeftArchiefstuks.add(archiefstuk);
        archiefstuk.setHeeftUitgever(this);
        return this;
    }

    public Uitgever removeHeeftArchiefstuk(Archiefstuk archiefstuk) {
        this.heeftArchiefstuks.remove(archiefstuk);
        archiefstuk.setHeeftUitgever(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Uitgever)) {
            return false;
        }
        return getId() != null && getId().equals(((Uitgever) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Uitgever{" +
            "id=" + getId() +
            "}";
    }
}
