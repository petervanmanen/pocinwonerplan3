package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Adresseerbaarobject.
 */
@Entity
@Table(name = "adresseerbaarobject")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Adresseerbaarobject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "identificatie")
    private String identificatie;

    @Column(name = "typeadresseerbaarobject")
    private String typeadresseerbaarobject;

    @Column(name = "versie")
    private String versie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Adresseerbaarobject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificatie() {
        return this.identificatie;
    }

    public Adresseerbaarobject identificatie(String identificatie) {
        this.setIdentificatie(identificatie);
        return this;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public String getTypeadresseerbaarobject() {
        return this.typeadresseerbaarobject;
    }

    public Adresseerbaarobject typeadresseerbaarobject(String typeadresseerbaarobject) {
        this.setTypeadresseerbaarobject(typeadresseerbaarobject);
        return this;
    }

    public void setTypeadresseerbaarobject(String typeadresseerbaarobject) {
        this.typeadresseerbaarobject = typeadresseerbaarobject;
    }

    public String getVersie() {
        return this.versie;
    }

    public Adresseerbaarobject versie(String versie) {
        this.setVersie(versie);
        return this;
    }

    public void setVersie(String versie) {
        this.versie = versie;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Adresseerbaarobject)) {
            return false;
        }
        return getId() != null && getId().equals(((Adresseerbaarobject) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Adresseerbaarobject{" +
            "id=" + getId() +
            ", identificatie='" + getIdentificatie() + "'" +
            ", typeadresseerbaarobject='" + getTypeadresseerbaarobject() + "'" +
            ", versie='" + getVersie() + "'" +
            "}";
    }
}
