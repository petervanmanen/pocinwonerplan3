package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Putdeksel.
 */
@Entity
@Table(name = "putdeksel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Putdeksel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "diameter")
    private String diameter;

    @Column(name = "put")
    private String put;

    @Column(name = "type")
    private String type;

    @Column(name = "vorm")
    private String vorm;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Putdeksel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiameter() {
        return this.diameter;
    }

    public Putdeksel diameter(String diameter) {
        this.setDiameter(diameter);
        return this;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getPut() {
        return this.put;
    }

    public Putdeksel put(String put) {
        this.setPut(put);
        return this;
    }

    public void setPut(String put) {
        this.put = put;
    }

    public String getType() {
        return this.type;
    }

    public Putdeksel type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVorm() {
        return this.vorm;
    }

    public Putdeksel vorm(String vorm) {
        this.setVorm(vorm);
        return this;
    }

    public void setVorm(String vorm) {
        this.vorm = vorm;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Putdeksel)) {
            return false;
        }
        return getId() != null && getId().equals(((Putdeksel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Putdeksel{" +
            "id=" + getId() +
            ", diameter='" + getDiameter() + "'" +
            ", put='" + getPut() + "'" +
            ", type='" + getType() + "'" +
            ", vorm='" + getVorm() + "'" +
            "}";
    }
}
