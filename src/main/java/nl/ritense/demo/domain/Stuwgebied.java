package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Stuwgebied.
 */
@Entity
@Table(name = "stuwgebied")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Stuwgebied implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bemalingsgebied")
    private String bemalingsgebied;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Stuwgebied id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBemalingsgebied() {
        return this.bemalingsgebied;
    }

    public Stuwgebied bemalingsgebied(String bemalingsgebied) {
        this.setBemalingsgebied(bemalingsgebied);
        return this;
    }

    public void setBemalingsgebied(String bemalingsgebied) {
        this.bemalingsgebied = bemalingsgebied;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stuwgebied)) {
            return false;
        }
        return getId() != null && getId().equals(((Stuwgebied) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Stuwgebied{" +
            "id=" + getId() +
            ", bemalingsgebied='" + getBemalingsgebied() + "'" +
            "}";
    }
}
