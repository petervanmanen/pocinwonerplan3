package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Adresseerbaarobjectaanduiding.
 */
@Entity
@Table(name = "adresseerbaarobjectaanduiding")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Adresseerbaarobjectaanduiding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 100)
    @Column(name = "identificatie", length = 100)
    private String identificatie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Adresseerbaarobjectaanduiding id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificatie() {
        return this.identificatie;
    }

    public Adresseerbaarobjectaanduiding identificatie(String identificatie) {
        this.setIdentificatie(identificatie);
        return this;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Adresseerbaarobjectaanduiding)) {
            return false;
        }
        return getId() != null && getId().equals(((Adresseerbaarobjectaanduiding) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Adresseerbaarobjectaanduiding{" +
            "id=" + getId() +
            ", identificatie='" + getIdentificatie() + "'" +
            "}";
    }
}
