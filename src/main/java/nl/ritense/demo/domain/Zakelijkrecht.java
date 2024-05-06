package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Zakelijkrecht.
 */
@Entity
@Table(name = "zakelijkrecht")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Zakelijkrecht implements Serializable {

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

    @Column(name = "kosten", precision = 21, scale = 2)
    private BigDecimal kosten;

    @Column(name = "soort")
    private String soort;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Zakelijkrecht id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Zakelijkrecht datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Zakelijkrecht datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public BigDecimal getKosten() {
        return this.kosten;
    }

    public Zakelijkrecht kosten(BigDecimal kosten) {
        this.setKosten(kosten);
        return this;
    }

    public void setKosten(BigDecimal kosten) {
        this.kosten = kosten;
    }

    public String getSoort() {
        return this.soort;
    }

    public Zakelijkrecht soort(String soort) {
        this.setSoort(soort);
        return this;
    }

    public void setSoort(String soort) {
        this.soort = soort;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Zakelijkrecht)) {
            return false;
        }
        return getId() != null && getId().equals(((Zakelijkrecht) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Zakelijkrecht{" +
            "id=" + getId() +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", kosten=" + getKosten() +
            ", soort='" + getSoort() + "'" +
            "}";
    }
}
