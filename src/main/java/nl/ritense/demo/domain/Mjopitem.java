package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Mjopitem.
 */
@Entity
@Table(name = "mjopitem")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Mjopitem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumopzeggingaanbieder")
    private LocalDate datumopzeggingaanbieder;

    @Column(name = "datumopzeggingontvanger")
    private LocalDate datumopzeggingontvanger;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "kosten", precision = 21, scale = 2)
    private BigDecimal kosten;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "opzegtermijnaanbieder")
    private String opzegtermijnaanbieder;

    @Column(name = "opzegtermijnontvanger")
    private String opzegtermijnontvanger;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Mjopitem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Mjopitem code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Mjopitem datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumopzeggingaanbieder() {
        return this.datumopzeggingaanbieder;
    }

    public Mjopitem datumopzeggingaanbieder(LocalDate datumopzeggingaanbieder) {
        this.setDatumopzeggingaanbieder(datumopzeggingaanbieder);
        return this;
    }

    public void setDatumopzeggingaanbieder(LocalDate datumopzeggingaanbieder) {
        this.datumopzeggingaanbieder = datumopzeggingaanbieder;
    }

    public LocalDate getDatumopzeggingontvanger() {
        return this.datumopzeggingontvanger;
    }

    public Mjopitem datumopzeggingontvanger(LocalDate datumopzeggingontvanger) {
        this.setDatumopzeggingontvanger(datumopzeggingontvanger);
        return this;
    }

    public void setDatumopzeggingontvanger(LocalDate datumopzeggingontvanger) {
        this.datumopzeggingontvanger = datumopzeggingontvanger;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Mjopitem datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public BigDecimal getKosten() {
        return this.kosten;
    }

    public Mjopitem kosten(BigDecimal kosten) {
        this.setKosten(kosten);
        return this;
    }

    public void setKosten(BigDecimal kosten) {
        this.kosten = kosten;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Mjopitem omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOpzegtermijnaanbieder() {
        return this.opzegtermijnaanbieder;
    }

    public Mjopitem opzegtermijnaanbieder(String opzegtermijnaanbieder) {
        this.setOpzegtermijnaanbieder(opzegtermijnaanbieder);
        return this;
    }

    public void setOpzegtermijnaanbieder(String opzegtermijnaanbieder) {
        this.opzegtermijnaanbieder = opzegtermijnaanbieder;
    }

    public String getOpzegtermijnontvanger() {
        return this.opzegtermijnontvanger;
    }

    public Mjopitem opzegtermijnontvanger(String opzegtermijnontvanger) {
        this.setOpzegtermijnontvanger(opzegtermijnontvanger);
        return this;
    }

    public void setOpzegtermijnontvanger(String opzegtermijnontvanger) {
        this.opzegtermijnontvanger = opzegtermijnontvanger;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mjopitem)) {
            return false;
        }
        return getId() != null && getId().equals(((Mjopitem) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Mjopitem{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumopzeggingaanbieder='" + getDatumopzeggingaanbieder() + "'" +
            ", datumopzeggingontvanger='" + getDatumopzeggingontvanger() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", kosten=" + getKosten() +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", opzegtermijnaanbieder='" + getOpzegtermijnaanbieder() + "'" +
            ", opzegtermijnontvanger='" + getOpzegtermijnontvanger() + "'" +
            "}";
    }
}
