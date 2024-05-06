package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Materielehistorie.
 */
@Entity
@Table(name = "materielehistorie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Materielehistorie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheidgegevens")
    private String datumbegingeldigheidgegevens;

    @Column(name = "datumeindegeldigheidgegevens")
    private String datumeindegeldigheidgegevens;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Materielehistorie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatumbegingeldigheidgegevens() {
        return this.datumbegingeldigheidgegevens;
    }

    public Materielehistorie datumbegingeldigheidgegevens(String datumbegingeldigheidgegevens) {
        this.setDatumbegingeldigheidgegevens(datumbegingeldigheidgegevens);
        return this;
    }

    public void setDatumbegingeldigheidgegevens(String datumbegingeldigheidgegevens) {
        this.datumbegingeldigheidgegevens = datumbegingeldigheidgegevens;
    }

    public String getDatumeindegeldigheidgegevens() {
        return this.datumeindegeldigheidgegevens;
    }

    public Materielehistorie datumeindegeldigheidgegevens(String datumeindegeldigheidgegevens) {
        this.setDatumeindegeldigheidgegevens(datumeindegeldigheidgegevens);
        return this;
    }

    public void setDatumeindegeldigheidgegevens(String datumeindegeldigheidgegevens) {
        this.datumeindegeldigheidgegevens = datumeindegeldigheidgegevens;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Materielehistorie)) {
            return false;
        }
        return getId() != null && getId().equals(((Materielehistorie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Materielehistorie{" +
            "id=" + getId() +
            ", datumbegingeldigheidgegevens='" + getDatumbegingeldigheidgegevens() + "'" +
            ", datumeindegeldigheidgegevens='" + getDatumeindegeldigheidgegevens() + "'" +
            "}";
    }
}
