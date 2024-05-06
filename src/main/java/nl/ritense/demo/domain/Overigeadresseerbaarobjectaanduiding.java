package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Overigeadresseerbaarobjectaanduiding.
 */
@Entity
@Table(name = "overigeadresseerbaarobjectaanduiding")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Overigeadresseerbaarobjectaanduiding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "identificatiecodeoverigadresseerbaarobjectaanduiding")
    private String identificatiecodeoverigadresseerbaarobjectaanduiding;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Overigeadresseerbaarobjectaanduiding id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificatiecodeoverigadresseerbaarobjectaanduiding() {
        return this.identificatiecodeoverigadresseerbaarobjectaanduiding;
    }

    public Overigeadresseerbaarobjectaanduiding identificatiecodeoverigadresseerbaarobjectaanduiding(
        String identificatiecodeoverigadresseerbaarobjectaanduiding
    ) {
        this.setIdentificatiecodeoverigadresseerbaarobjectaanduiding(identificatiecodeoverigadresseerbaarobjectaanduiding);
        return this;
    }

    public void setIdentificatiecodeoverigadresseerbaarobjectaanduiding(String identificatiecodeoverigadresseerbaarobjectaanduiding) {
        this.identificatiecodeoverigadresseerbaarobjectaanduiding = identificatiecodeoverigadresseerbaarobjectaanduiding;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Overigeadresseerbaarobjectaanduiding)) {
            return false;
        }
        return getId() != null && getId().equals(((Overigeadresseerbaarobjectaanduiding) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Overigeadresseerbaarobjectaanduiding{" +
            "id=" + getId() +
            ", identificatiecodeoverigadresseerbaarobjectaanduiding='" + getIdentificatiecodeoverigadresseerbaarobjectaanduiding() + "'" +
            "}";
    }
}
