package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Individueelkeuzebudget.
 */
@Entity
@Table(name = "individueelkeuzebudget")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Individueelkeuzebudget implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bedrag", precision = 21, scale = 2)
    private BigDecimal bedrag;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "datumtoekenning")
    private LocalDate datumtoekenning;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Individueelkeuzebudget id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBedrag() {
        return this.bedrag;
    }

    public Individueelkeuzebudget bedrag(BigDecimal bedrag) {
        this.setBedrag(bedrag);
        return this;
    }

    public void setBedrag(BigDecimal bedrag) {
        this.bedrag = bedrag;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Individueelkeuzebudget datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Individueelkeuzebudget datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public LocalDate getDatumtoekenning() {
        return this.datumtoekenning;
    }

    public Individueelkeuzebudget datumtoekenning(LocalDate datumtoekenning) {
        this.setDatumtoekenning(datumtoekenning);
        return this;
    }

    public void setDatumtoekenning(LocalDate datumtoekenning) {
        this.datumtoekenning = datumtoekenning;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Individueelkeuzebudget)) {
            return false;
        }
        return getId() != null && getId().equals(((Individueelkeuzebudget) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Individueelkeuzebudget{" +
            "id=" + getId() +
            ", bedrag=" + getBedrag() +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", datumtoekenning='" + getDatumtoekenning() + "'" +
            "}";
    }
}
