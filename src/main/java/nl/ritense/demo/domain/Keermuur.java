package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Keermuur.
 */
@Entity
@Table(name = "keermuur")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Keermuur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "belastingklassenieuw")
    private String belastingklassenieuw;

    @Column(name = "belastingklasseoud")
    private String belastingklasseoud;

    @Column(name = "type")
    private String type;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Keermuur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBelastingklassenieuw() {
        return this.belastingklassenieuw;
    }

    public Keermuur belastingklassenieuw(String belastingklassenieuw) {
        this.setBelastingklassenieuw(belastingklassenieuw);
        return this;
    }

    public void setBelastingklassenieuw(String belastingklassenieuw) {
        this.belastingklassenieuw = belastingklassenieuw;
    }

    public String getBelastingklasseoud() {
        return this.belastingklasseoud;
    }

    public Keermuur belastingklasseoud(String belastingklasseoud) {
        this.setBelastingklasseoud(belastingklasseoud);
        return this;
    }

    public void setBelastingklasseoud(String belastingklasseoud) {
        this.belastingklasseoud = belastingklasseoud;
    }

    public String getType() {
        return this.type;
    }

    public Keermuur type(String type) {
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
        if (!(o instanceof Keermuur)) {
            return false;
        }
        return getId() != null && getId().equals(((Keermuur) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Keermuur{" +
            "id=" + getId() +
            ", belastingklassenieuw='" + getBelastingklassenieuw() + "'" +
            ", belastingklasseoud='" + getBelastingklasseoud() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
