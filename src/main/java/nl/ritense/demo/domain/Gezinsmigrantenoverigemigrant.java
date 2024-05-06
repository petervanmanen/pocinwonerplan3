package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Gezinsmigrantenoverigemigrant.
 */
@Entity
@Table(name = "gezinsmigrantenoverigemigrant")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Gezinsmigrantenoverigemigrant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Gezinsmigrantenoverigemigrant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gezinsmigrantenoverigemigrant)) {
            return false;
        }
        return getId() != null && getId().equals(((Gezinsmigrantenoverigemigrant) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Gezinsmigrantenoverigemigrant{" +
            "id=" + getId() +
            "}";
    }
}
