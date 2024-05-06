package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Filterput.
 */
@Entity
@Table(name = "filterput")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Filterput implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "drain")
    private String drain;

    @Column(name = "risicogebied")
    private String risicogebied;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Filterput id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDrain() {
        return this.drain;
    }

    public Filterput drain(String drain) {
        this.setDrain(drain);
        return this;
    }

    public void setDrain(String drain) {
        this.drain = drain;
    }

    public String getRisicogebied() {
        return this.risicogebied;
    }

    public Filterput risicogebied(String risicogebied) {
        this.setRisicogebied(risicogebied);
        return this;
    }

    public void setRisicogebied(String risicogebied) {
        this.risicogebied = risicogebied;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Filterput)) {
            return false;
        }
        return getId() != null && getId().equals(((Filterput) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Filterput{" +
            "id=" + getId() +
            ", drain='" + getDrain() + "'" +
            ", risicogebied='" + getRisicogebied() + "'" +
            "}";
    }
}
