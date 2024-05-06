package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Gebruikerrol.
 */
@Entity
@Table(name = "gebruikerrol")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Gebruikerrol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "rol")
    private String rol;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Gebruikerrol id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRol() {
        return this.rol;
    }

    public Gebruikerrol rol(String rol) {
        this.setRol(rol);
        return this;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gebruikerrol)) {
            return false;
        }
        return getId() != null && getId().equals(((Gebruikerrol) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Gebruikerrol{" +
            "id=" + getId() +
            ", rol='" + getRol() + "'" +
            "}";
    }
}
