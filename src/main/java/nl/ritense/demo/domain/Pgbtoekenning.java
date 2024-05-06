package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Pgbtoekenning.
 */
@Entity
@Table(name = "pgbtoekenning")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Pgbtoekenning implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "budget", precision = 21, scale = 2)
    private BigDecimal budget;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumtoekenning")
    private LocalDate datumtoekenning;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pgbtoekenning id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBudget() {
        return this.budget;
    }

    public Pgbtoekenning budget(BigDecimal budget) {
        this.setBudget(budget);
        return this;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Pgbtoekenning datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumtoekenning() {
        return this.datumtoekenning;
    }

    public Pgbtoekenning datumtoekenning(LocalDate datumtoekenning) {
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
        if (!(o instanceof Pgbtoekenning)) {
            return false;
        }
        return getId() != null && getId().equals(((Pgbtoekenning) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pgbtoekenning{" +
            "id=" + getId() +
            ", budget=" + getBudget() +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumtoekenning='" + getDatumtoekenning() + "'" +
            "}";
    }
}
