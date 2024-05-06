package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Doorgeleidingom.
 */
@Entity
@Table(name = "doorgeleidingom")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Doorgeleidingom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "afdoening")
    private String afdoening;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Doorgeleidingom id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAfdoening() {
        return this.afdoening;
    }

    public Doorgeleidingom afdoening(String afdoening) {
        this.setAfdoening(afdoening);
        return this;
    }

    public void setAfdoening(String afdoening) {
        this.afdoening = afdoening;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Doorgeleidingom)) {
            return false;
        }
        return getId() != null && getId().equals(((Doorgeleidingom) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Doorgeleidingom{" +
            "id=" + getId() +
            ", afdoening='" + getAfdoening() + "'" +
            "}";
    }
}
