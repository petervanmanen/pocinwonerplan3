package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Aanvraagofmelding.
 */
@Entity
@Table(name = "aanvraagofmelding")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Aanvraagofmelding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datum")
    private LocalDate datum;

    @Column(name = "opmerkingen")
    private String opmerkingen;

    @Column(name = "reden")
    private String reden;

    @Column(name = "soortverzuimofaanvraag")
    private String soortverzuimofaanvraag;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Aanvraagofmelding id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public Aanvraagofmelding datum(LocalDate datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getOpmerkingen() {
        return this.opmerkingen;
    }

    public Aanvraagofmelding opmerkingen(String opmerkingen) {
        this.setOpmerkingen(opmerkingen);
        return this;
    }

    public void setOpmerkingen(String opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public String getReden() {
        return this.reden;
    }

    public Aanvraagofmelding reden(String reden) {
        this.setReden(reden);
        return this;
    }

    public void setReden(String reden) {
        this.reden = reden;
    }

    public String getSoortverzuimofaanvraag() {
        return this.soortverzuimofaanvraag;
    }

    public Aanvraagofmelding soortverzuimofaanvraag(String soortverzuimofaanvraag) {
        this.setSoortverzuimofaanvraag(soortverzuimofaanvraag);
        return this;
    }

    public void setSoortverzuimofaanvraag(String soortverzuimofaanvraag) {
        this.soortverzuimofaanvraag = soortverzuimofaanvraag;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aanvraagofmelding)) {
            return false;
        }
        return getId() != null && getId().equals(((Aanvraagofmelding) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Aanvraagofmelding{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", opmerkingen='" + getOpmerkingen() + "'" +
            ", reden='" + getReden() + "'" +
            ", soortverzuimofaanvraag='" + getSoortverzuimofaanvraag() + "'" +
            "}";
    }
}
