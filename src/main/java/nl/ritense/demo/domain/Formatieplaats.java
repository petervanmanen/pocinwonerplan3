package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Formatieplaats.
 */
@Entity
@Table(name = "formatieplaats")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Formatieplaats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 100)
    @Column(name = "urenperweek", length = 100)
    private String urenperweek;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Formatieplaats id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrenperweek() {
        return this.urenperweek;
    }

    public Formatieplaats urenperweek(String urenperweek) {
        this.setUrenperweek(urenperweek);
        return this;
    }

    public void setUrenperweek(String urenperweek) {
        this.urenperweek = urenperweek;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Formatieplaats)) {
            return false;
        }
        return getId() != null && getId().equals(((Formatieplaats) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Formatieplaats{" +
            "id=" + getId() +
            ", urenperweek='" + getUrenperweek() + "'" +
            "}";
    }
}
