package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Subproducttype.
 */
@Entity
@Table(name = "subproducttype")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Subproducttype implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "prioriteit")
    private String prioriteit;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Subproducttype id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Subproducttype omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getPrioriteit() {
        return this.prioriteit;
    }

    public Subproducttype prioriteit(String prioriteit) {
        this.setPrioriteit(prioriteit);
        return this;
    }

    public void setPrioriteit(String prioriteit) {
        this.prioriteit = prioriteit;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subproducttype)) {
            return false;
        }
        return getId() != null && getId().equals(((Subproducttype) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Subproducttype{" +
            "id=" + getId() +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", prioriteit='" + getPrioriteit() + "'" +
            "}";
    }
}
