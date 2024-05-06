package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Budgetuitputting.
 */
@Entity
@Table(name = "budgetuitputting")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Budgetuitputting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datum")
    private LocalDate datum;

    @Column(name = "uitgenutbedrag", precision = 21, scale = 2)
    private BigDecimal uitgenutbedrag;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Budgetuitputting id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public Budgetuitputting datum(LocalDate datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public BigDecimal getUitgenutbedrag() {
        return this.uitgenutbedrag;
    }

    public Budgetuitputting uitgenutbedrag(BigDecimal uitgenutbedrag) {
        this.setUitgenutbedrag(uitgenutbedrag);
        return this;
    }

    public void setUitgenutbedrag(BigDecimal uitgenutbedrag) {
        this.uitgenutbedrag = uitgenutbedrag;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Budgetuitputting)) {
            return false;
        }
        return getId() != null && getId().equals(((Budgetuitputting) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Budgetuitputting{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", uitgenutbedrag=" + getUitgenutbedrag() +
            "}";
    }
}
