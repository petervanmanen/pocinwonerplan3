package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Crowmelding.
 */
@Entity
@Table(name = "crowmelding")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Crowmelding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "kwaliteitsniveau")
    private String kwaliteitsniveau;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Crowmelding id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKwaliteitsniveau() {
        return this.kwaliteitsniveau;
    }

    public Crowmelding kwaliteitsniveau(String kwaliteitsniveau) {
        this.setKwaliteitsniveau(kwaliteitsniveau);
        return this;
    }

    public void setKwaliteitsniveau(String kwaliteitsniveau) {
        this.kwaliteitsniveau = kwaliteitsniveau;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Crowmelding)) {
            return false;
        }
        return getId() != null && getId().equals(((Crowmelding) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Crowmelding{" +
            "id=" + getId() +
            ", kwaliteitsniveau='" + getKwaliteitsniveau() + "'" +
            "}";
    }
}
