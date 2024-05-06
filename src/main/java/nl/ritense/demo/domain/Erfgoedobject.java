package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Erfgoedobject.
 */
@Entity
@Table(name = "erfgoedobject")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Erfgoedobject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "dateringtot")
    private String dateringtot;

    @Column(name = "dateringvanaf")
    private String dateringvanaf;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "titel")
    private String titel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Erfgoedobject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateringtot() {
        return this.dateringtot;
    }

    public Erfgoedobject dateringtot(String dateringtot) {
        this.setDateringtot(dateringtot);
        return this;
    }

    public void setDateringtot(String dateringtot) {
        this.dateringtot = dateringtot;
    }

    public String getDateringvanaf() {
        return this.dateringvanaf;
    }

    public Erfgoedobject dateringvanaf(String dateringvanaf) {
        this.setDateringvanaf(dateringvanaf);
        return this;
    }

    public void setDateringvanaf(String dateringvanaf) {
        this.dateringvanaf = dateringvanaf;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Erfgoedobject omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getTitel() {
        return this.titel;
    }

    public Erfgoedobject titel(String titel) {
        this.setTitel(titel);
        return this;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Erfgoedobject)) {
            return false;
        }
        return getId() != null && getId().equals(((Erfgoedobject) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Erfgoedobject{" +
            "id=" + getId() +
            ", dateringtot='" + getDateringtot() + "'" +
            ", dateringvanaf='" + getDateringvanaf() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", titel='" + getTitel() + "'" +
            "}";
    }
}
