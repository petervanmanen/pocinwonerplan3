package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Uitlaatconstructie.
 */
@Entity
@Table(name = "uitlaatconstructie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Uitlaatconstructie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "waterobject")
    private String waterobject;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Uitlaatconstructie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public Uitlaatconstructie type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWaterobject() {
        return this.waterobject;
    }

    public Uitlaatconstructie waterobject(String waterobject) {
        this.setWaterobject(waterobject);
        return this;
    }

    public void setWaterobject(String waterobject) {
        this.waterobject = waterobject;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Uitlaatconstructie)) {
            return false;
        }
        return getId() != null && getId().equals(((Uitlaatconstructie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Uitlaatconstructie{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", waterobject='" + getWaterobject() + "'" +
            "}";
    }
}
