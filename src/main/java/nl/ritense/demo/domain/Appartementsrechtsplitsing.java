package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Appartementsrechtsplitsing.
 */
@Entity
@Table(name = "appartementsrechtsplitsing")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Appartementsrechtsplitsing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ddentificatieappartementsrechtsplitsing")
    private String ddentificatieappartementsrechtsplitsing;

    @Column(name = "typesplitsing")
    private String typesplitsing;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Appartementsrechtsplitsing id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDdentificatieappartementsrechtsplitsing() {
        return this.ddentificatieappartementsrechtsplitsing;
    }

    public Appartementsrechtsplitsing ddentificatieappartementsrechtsplitsing(String ddentificatieappartementsrechtsplitsing) {
        this.setDdentificatieappartementsrechtsplitsing(ddentificatieappartementsrechtsplitsing);
        return this;
    }

    public void setDdentificatieappartementsrechtsplitsing(String ddentificatieappartementsrechtsplitsing) {
        this.ddentificatieappartementsrechtsplitsing = ddentificatieappartementsrechtsplitsing;
    }

    public String getTypesplitsing() {
        return this.typesplitsing;
    }

    public Appartementsrechtsplitsing typesplitsing(String typesplitsing) {
        this.setTypesplitsing(typesplitsing);
        return this;
    }

    public void setTypesplitsing(String typesplitsing) {
        this.typesplitsing = typesplitsing;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Appartementsrechtsplitsing)) {
            return false;
        }
        return getId() != null && getId().equals(((Appartementsrechtsplitsing) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Appartementsrechtsplitsing{" +
            "id=" + getId() +
            ", ddentificatieappartementsrechtsplitsing='" + getDdentificatieappartementsrechtsplitsing() + "'" +
            ", typesplitsing='" + getTypesplitsing() + "'" +
            "}";
    }
}
