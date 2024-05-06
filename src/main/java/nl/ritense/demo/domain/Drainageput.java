package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Drainageput.
 */
@Entity
@Table(name = "drainageput")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Drainageput implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "risicogebied")
    private String risicogebied;

    @Column(name = "type")
    private String type;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Drainageput id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRisicogebied() {
        return this.risicogebied;
    }

    public Drainageput risicogebied(String risicogebied) {
        this.setRisicogebied(risicogebied);
        return this;
    }

    public void setRisicogebied(String risicogebied) {
        this.risicogebied = risicogebied;
    }

    public String getType() {
        return this.type;
    }

    public Drainageput type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Drainageput)) {
            return false;
        }
        return getId() != null && getId().equals(((Drainageput) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Drainageput{" +
            "id=" + getId() +
            ", risicogebied='" + getRisicogebied() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
