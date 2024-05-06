package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Rioleringsgebied.
 */
@Entity
@Table(name = "rioleringsgebied")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Rioleringsgebied implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "rioleringsgebied")
    private String rioleringsgebied;

    @Column(name = "zuiveringsgebied")
    private String zuiveringsgebied;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Rioleringsgebied id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRioleringsgebied() {
        return this.rioleringsgebied;
    }

    public Rioleringsgebied rioleringsgebied(String rioleringsgebied) {
        this.setRioleringsgebied(rioleringsgebied);
        return this;
    }

    public void setRioleringsgebied(String rioleringsgebied) {
        this.rioleringsgebied = rioleringsgebied;
    }

    public String getZuiveringsgebied() {
        return this.zuiveringsgebied;
    }

    public Rioleringsgebied zuiveringsgebied(String zuiveringsgebied) {
        this.setZuiveringsgebied(zuiveringsgebied);
        return this;
    }

    public void setZuiveringsgebied(String zuiveringsgebied) {
        this.zuiveringsgebied = zuiveringsgebied;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rioleringsgebied)) {
            return false;
        }
        return getId() != null && getId().equals(((Rioleringsgebied) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rioleringsgebied{" +
            "id=" + getId() +
            ", rioleringsgebied='" + getRioleringsgebied() + "'" +
            ", zuiveringsgebied='" + getZuiveringsgebied() + "'" +
            "}";
    }
}
