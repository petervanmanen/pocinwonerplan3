package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Verlofaanvraag.
 */
@Entity
@Table(name = "verlofaanvraag")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Verlofaanvraag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "datumtot")
    private LocalDate datumtot;

    @Column(name = "soortverlof")
    private String soortverlof;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Verlofaanvraag id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Verlofaanvraag datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public LocalDate getDatumtot() {
        return this.datumtot;
    }

    public Verlofaanvraag datumtot(LocalDate datumtot) {
        this.setDatumtot(datumtot);
        return this;
    }

    public void setDatumtot(LocalDate datumtot) {
        this.datumtot = datumtot;
    }

    public String getSoortverlof() {
        return this.soortverlof;
    }

    public Verlofaanvraag soortverlof(String soortverlof) {
        this.setSoortverlof(soortverlof);
        return this;
    }

    public void setSoortverlof(String soortverlof) {
        this.soortverlof = soortverlof;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Verlofaanvraag)) {
            return false;
        }
        return getId() != null && getId().equals(((Verlofaanvraag) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Verlofaanvraag{" +
            "id=" + getId() +
            ", datumstart='" + getDatumstart() + "'" +
            ", datumtot='" + getDatumtot() + "'" +
            ", soortverlof='" + getSoortverlof() + "'" +
            "}";
    }
}
