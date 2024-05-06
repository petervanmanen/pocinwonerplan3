package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Punt.
 */
@Entity
@Table(name = "punt")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Punt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "punt")
    private String punt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Punt id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPunt() {
        return this.punt;
    }

    public Punt punt(String punt) {
        this.setPunt(punt);
        return this;
    }

    public void setPunt(String punt) {
        this.punt = punt;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Punt)) {
            return false;
        }
        return getId() != null && getId().equals(((Punt) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Punt{" +
            "id=" + getId() +
            ", punt='" + getPunt() + "'" +
            "}";
    }
}
