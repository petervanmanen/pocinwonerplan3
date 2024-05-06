package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Regelvooriedereen.
 */
@Entity
@Table(name = "regelvooriedereen")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Regelvooriedereen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "activiteitregelkwalificatie")
    private String activiteitregelkwalificatie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Regelvooriedereen id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActiviteitregelkwalificatie() {
        return this.activiteitregelkwalificatie;
    }

    public Regelvooriedereen activiteitregelkwalificatie(String activiteitregelkwalificatie) {
        this.setActiviteitregelkwalificatie(activiteitregelkwalificatie);
        return this;
    }

    public void setActiviteitregelkwalificatie(String activiteitregelkwalificatie) {
        this.activiteitregelkwalificatie = activiteitregelkwalificatie;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Regelvooriedereen)) {
            return false;
        }
        return getId() != null && getId().equals(((Regelvooriedereen) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Regelvooriedereen{" +
            "id=" + getId() +
            ", activiteitregelkwalificatie='" + getActiviteitregelkwalificatie() + "'" +
            "}";
    }
}
