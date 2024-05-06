package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Afvalbak.
 */
@Entity
@Table(name = "afvalbak")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Afvalbak implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "typeplus")
    private String typeplus;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Afvalbak id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public Afvalbak type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeplus() {
        return this.typeplus;
    }

    public Afvalbak typeplus(String typeplus) {
        this.setTypeplus(typeplus);
        return this;
    }

    public void setTypeplus(String typeplus) {
        this.typeplus = typeplus;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Afvalbak)) {
            return false;
        }
        return getId() != null && getId().equals(((Afvalbak) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Afvalbak{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", typeplus='" + getTypeplus() + "'" +
            "}";
    }
}
