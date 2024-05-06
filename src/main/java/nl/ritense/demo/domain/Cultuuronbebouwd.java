package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Cultuuronbebouwd.
 */
@Entity
@Table(name = "cultuuronbebouwd")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cultuuronbebouwd implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "cultuurcodeonbebouwd")
    private String cultuurcodeonbebouwd;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cultuuronbebouwd id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCultuurcodeonbebouwd() {
        return this.cultuurcodeonbebouwd;
    }

    public Cultuuronbebouwd cultuurcodeonbebouwd(String cultuurcodeonbebouwd) {
        this.setCultuurcodeonbebouwd(cultuurcodeonbebouwd);
        return this;
    }

    public void setCultuurcodeonbebouwd(String cultuurcodeonbebouwd) {
        this.cultuurcodeonbebouwd = cultuurcodeonbebouwd;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cultuuronbebouwd)) {
            return false;
        }
        return getId() != null && getId().equals(((Cultuuronbebouwd) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cultuuronbebouwd{" +
            "id=" + getId() +
            ", cultuurcodeonbebouwd='" + getCultuurcodeonbebouwd() + "'" +
            "}";
    }
}
