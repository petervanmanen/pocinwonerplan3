package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Index.
 */
@Entity
@Table(name = "index")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Index implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "indexnaam")
    private String indexnaam;

    @Column(name = "indexwaarde")
    private String indexwaarde;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Index id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndexnaam() {
        return this.indexnaam;
    }

    public Index indexnaam(String indexnaam) {
        this.setIndexnaam(indexnaam);
        return this;
    }

    public void setIndexnaam(String indexnaam) {
        this.indexnaam = indexnaam;
    }

    public String getIndexwaarde() {
        return this.indexwaarde;
    }

    public Index indexwaarde(String indexwaarde) {
        this.setIndexwaarde(indexwaarde);
        return this;
    }

    public void setIndexwaarde(String indexwaarde) {
        this.indexwaarde = indexwaarde;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Index)) {
            return false;
        }
        return getId() != null && getId().equals(((Index) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Index{" +
            "id=" + getId() +
            ", indexnaam='" + getIndexnaam() + "'" +
            ", indexwaarde='" + getIndexwaarde() + "'" +
            "}";
    }
}
