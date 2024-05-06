package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Fietsregistratie.
 */
@Entity
@Table(name = "fietsregistratie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Fietsregistratie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "gelabeld")
    private String gelabeld;

    @Column(name = "verwijderd")
    private String verwijderd;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Fietsregistratie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGelabeld() {
        return this.gelabeld;
    }

    public Fietsregistratie gelabeld(String gelabeld) {
        this.setGelabeld(gelabeld);
        return this;
    }

    public void setGelabeld(String gelabeld) {
        this.gelabeld = gelabeld;
    }

    public String getVerwijderd() {
        return this.verwijderd;
    }

    public Fietsregistratie verwijderd(String verwijderd) {
        this.setVerwijderd(verwijderd);
        return this;
    }

    public void setVerwijderd(String verwijderd) {
        this.verwijderd = verwijderd;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fietsregistratie)) {
            return false;
        }
        return getId() != null && getId().equals(((Fietsregistratie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fietsregistratie{" +
            "id=" + getId() +
            ", gelabeld='" + getGelabeld() + "'" +
            ", verwijderd='" + getVerwijderd() + "'" +
            "}";
    }
}
