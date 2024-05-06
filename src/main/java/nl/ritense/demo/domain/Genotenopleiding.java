package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Genotenopleiding.
 */
@Entity
@Table(name = "genotenopleiding")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Genotenopleiding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "datumtoewijzing")
    private LocalDate datumtoewijzing;

    @Column(name = "prijs", precision = 21, scale = 2)
    private BigDecimal prijs;

    @Column(name = "verrekenen")
    private String verrekenen;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Genotenopleiding id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Genotenopleiding datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Genotenopleiding datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public LocalDate getDatumtoewijzing() {
        return this.datumtoewijzing;
    }

    public Genotenopleiding datumtoewijzing(LocalDate datumtoewijzing) {
        this.setDatumtoewijzing(datumtoewijzing);
        return this;
    }

    public void setDatumtoewijzing(LocalDate datumtoewijzing) {
        this.datumtoewijzing = datumtoewijzing;
    }

    public BigDecimal getPrijs() {
        return this.prijs;
    }

    public Genotenopleiding prijs(BigDecimal prijs) {
        this.setPrijs(prijs);
        return this;
    }

    public void setPrijs(BigDecimal prijs) {
        this.prijs = prijs;
    }

    public String getVerrekenen() {
        return this.verrekenen;
    }

    public Genotenopleiding verrekenen(String verrekenen) {
        this.setVerrekenen(verrekenen);
        return this;
    }

    public void setVerrekenen(String verrekenen) {
        this.verrekenen = verrekenen;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Genotenopleiding)) {
            return false;
        }
        return getId() != null && getId().equals(((Genotenopleiding) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Genotenopleiding{" +
            "id=" + getId() +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", datumtoewijzing='" + getDatumtoewijzing() + "'" +
            ", prijs=" + getPrijs() +
            ", verrekenen='" + getVerrekenen() + "'" +
            "}";
    }
}
