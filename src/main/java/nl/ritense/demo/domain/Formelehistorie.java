package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Formelehistorie.
 */
@Entity
@Table(name = "formelehistorie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Formelehistorie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "tijdstipregistratiegegevens")
    private String tijdstipregistratiegegevens;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Formelehistorie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTijdstipregistratiegegevens() {
        return this.tijdstipregistratiegegevens;
    }

    public Formelehistorie tijdstipregistratiegegevens(String tijdstipregistratiegegevens) {
        this.setTijdstipregistratiegegevens(tijdstipregistratiegegevens);
        return this;
    }

    public void setTijdstipregistratiegegevens(String tijdstipregistratiegegevens) {
        this.tijdstipregistratiegegevens = tijdstipregistratiegegevens;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Formelehistorie)) {
            return false;
        }
        return getId() != null && getId().equals(((Formelehistorie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Formelehistorie{" +
            "id=" + getId() +
            ", tijdstipregistratiegegevens='" + getTijdstipregistratiegegevens() + "'" +
            "}";
    }
}
