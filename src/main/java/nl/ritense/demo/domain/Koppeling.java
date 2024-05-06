package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Koppeling.
 */
@Entity
@Table(name = "koppeling")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Koppeling implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "beschrijving")
    private String beschrijving;

    @Column(name = "direct")
    private Boolean direct;

    @Column(name = "toelichting")
    private String toelichting;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Koppeling id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeschrijving() {
        return this.beschrijving;
    }

    public Koppeling beschrijving(String beschrijving) {
        this.setBeschrijving(beschrijving);
        return this;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public Boolean getDirect() {
        return this.direct;
    }

    public Koppeling direct(Boolean direct) {
        this.setDirect(direct);
        return this;
    }

    public void setDirect(Boolean direct) {
        this.direct = direct;
    }

    public String getToelichting() {
        return this.toelichting;
    }

    public Koppeling toelichting(String toelichting) {
        this.setToelichting(toelichting);
        return this;
    }

    public void setToelichting(String toelichting) {
        this.toelichting = toelichting;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Koppeling)) {
            return false;
        }
        return getId() != null && getId().equals(((Koppeling) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Koppeling{" +
            "id=" + getId() +
            ", beschrijving='" + getBeschrijving() + "'" +
            ", direct='" + getDirect() + "'" +
            ", toelichting='" + getToelichting() + "'" +
            "}";
    }
}
