package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Class1.
 */
@Entity
@Table(name = "class_1")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Class1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "waarde")
    private String waarde;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Class1 id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWaarde() {
        return this.waarde;
    }

    public Class1 waarde(String waarde) {
        this.setWaarde(waarde);
        return this;
    }

    public void setWaarde(String waarde) {
        this.waarde = waarde;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Class1)) {
            return false;
        }
        return getId() != null && getId().equals(((Class1) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Class1{" +
            "id=" + getId() +
            ", waarde='" + getWaarde() + "'" +
            "}";
    }
}
