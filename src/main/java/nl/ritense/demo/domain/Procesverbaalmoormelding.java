package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Procesverbaalmoormelding.
 */
@Entity
@Table(name = "procesverbaalmoormelding")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Procesverbaalmoormelding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datum")
    private String datum;

    @Column(name = "goedkeuring")
    private Boolean goedkeuring;

    @Column(name = "opmerkingen")
    private String opmerkingen;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Procesverbaalmoormelding id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatum() {
        return this.datum;
    }

    public Procesverbaalmoormelding datum(String datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public Boolean getGoedkeuring() {
        return this.goedkeuring;
    }

    public Procesverbaalmoormelding goedkeuring(Boolean goedkeuring) {
        this.setGoedkeuring(goedkeuring);
        return this;
    }

    public void setGoedkeuring(Boolean goedkeuring) {
        this.goedkeuring = goedkeuring;
    }

    public String getOpmerkingen() {
        return this.opmerkingen;
    }

    public Procesverbaalmoormelding opmerkingen(String opmerkingen) {
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
        if (!(o instanceof Procesverbaalmoormelding)) {
            return false;
        }
        return getId() != null && getId().equals(((Procesverbaalmoormelding) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Procesverbaalmoormelding{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", goedkeuring='" + getGoedkeuring() + "'" +
            ", opmerkingen='" + getOpmerkingen() + "'" +
            "}";
    }
}
