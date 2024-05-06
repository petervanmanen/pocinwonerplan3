package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Financielesituatie.
 */
@Entity
@Table(name = "financielesituatie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Financielesituatie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumvastgesteld")
    private String datumvastgesteld;

    @Column(name = "schuld")
    private String schuld;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Financielesituatie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatumvastgesteld() {
        return this.datumvastgesteld;
    }

    public Financielesituatie datumvastgesteld(String datumvastgesteld) {
        this.setDatumvastgesteld(datumvastgesteld);
        return this;
    }

    public void setDatumvastgesteld(String datumvastgesteld) {
        this.datumvastgesteld = datumvastgesteld;
    }

    public String getSchuld() {
        return this.schuld;
    }

    public Financielesituatie schuld(String schuld) {
        this.setSchuld(schuld);
        return this;
    }

    public void setSchuld(String schuld) {
        this.schuld = schuld;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Financielesituatie)) {
            return false;
        }
        return getId() != null && getId().equals(((Financielesituatie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Financielesituatie{" +
            "id=" + getId() +
            ", datumvastgesteld='" + getDatumvastgesteld() + "'" +
            ", schuld='" + getSchuld() + "'" +
            "}";
    }
}
