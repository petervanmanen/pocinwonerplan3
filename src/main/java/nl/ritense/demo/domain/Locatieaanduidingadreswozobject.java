package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Locatieaanduidingadreswozobject.
 */
@Entity
@Table(name = "locatieaanduidingadreswozobject")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Locatieaanduidingadreswozobject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "locatieomschrijving")
    private String locatieomschrijving;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Locatieaanduidingadreswozobject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocatieomschrijving() {
        return this.locatieomschrijving;
    }

    public Locatieaanduidingadreswozobject locatieomschrijving(String locatieomschrijving) {
        this.setLocatieomschrijving(locatieomschrijving);
        return this;
    }

    public void setLocatieomschrijving(String locatieomschrijving) {
        this.locatieomschrijving = locatieomschrijving;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Locatieaanduidingadreswozobject)) {
            return false;
        }
        return getId() != null && getId().equals(((Locatieaanduidingadreswozobject) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Locatieaanduidingadreswozobject{" +
            "id=" + getId() +
            ", locatieomschrijving='" + getLocatieomschrijving() + "'" +
            "}";
    }
}
