package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Nadaanvullingbrp.
 */
@Entity
@Table(name = "nadaanvullingbrp")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Nadaanvullingbrp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "opmerkingen")
    private String opmerkingen;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Nadaanvullingbrp id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpmerkingen() {
        return this.opmerkingen;
    }

    public Nadaanvullingbrp opmerkingen(String opmerkingen) {
        this.setOpmerkingen(opmerkingen);
        return this;
    }

    public void setOpmerkingen(String opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nadaanvullingbrp)) {
            return false;
        }
        return getId() != null && getId().equals(((Nadaanvullingbrp) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nadaanvullingbrp{" +
            "id=" + getId() +
            ", opmerkingen='" + getOpmerkingen() + "'" +
            "}";
    }
}
