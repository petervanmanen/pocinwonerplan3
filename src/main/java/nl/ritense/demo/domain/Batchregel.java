package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Batchregel.
 */
@Entity
@Table(name = "batchregel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Batchregel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bedrag", precision = 21, scale = 2)
    private BigDecimal bedrag;

    @Column(name = "datumbetaling")
    private LocalDate datumbetaling;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "rekeningnaar")
    private String rekeningnaar;

    @Column(name = "rekeningvan")
    private String rekeningvan;

    @JsonIgnoreProperties(
        value = {
            "vanHoofdrekening",
            "naarHoofdrekening",
            "heeftbetrekkingopKostenplaats",
            "leidttotBankafschriftregel",
            "leidttotBatchregel",
            "leidttotFactuurregel",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Mutatie leidttotMutatie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftBatchregels", "heeftherkomstApplicatie" }, allowSetters = true)
    private Batch heeftBatch;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Batchregel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBedrag() {
        return this.bedrag;
    }

    public Batchregel bedrag(BigDecimal bedrag) {
        this.setBedrag(bedrag);
        return this;
    }

    public void setBedrag(BigDecimal bedrag) {
        this.bedrag = bedrag;
    }

    public LocalDate getDatumbetaling() {
        return this.datumbetaling;
    }

    public Batchregel datumbetaling(LocalDate datumbetaling) {
        this.setDatumbetaling(datumbetaling);
        return this;
    }

    public void setDatumbetaling(LocalDate datumbetaling) {
        this.datumbetaling = datumbetaling;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Batchregel omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getRekeningnaar() {
        return this.rekeningnaar;
    }

    public Batchregel rekeningnaar(String rekeningnaar) {
        this.setRekeningnaar(rekeningnaar);
        return this;
    }

    public void setRekeningnaar(String rekeningnaar) {
        this.rekeningnaar = rekeningnaar;
    }

    public String getRekeningvan() {
        return this.rekeningvan;
    }

    public Batchregel rekeningvan(String rekeningvan) {
        this.setRekeningvan(rekeningvan);
        return this;
    }

    public void setRekeningvan(String rekeningvan) {
        this.rekeningvan = rekeningvan;
    }

    public Mutatie getLeidttotMutatie() {
        return this.leidttotMutatie;
    }

    public void setLeidttotMutatie(Mutatie mutatie) {
        this.leidttotMutatie = mutatie;
    }

    public Batchregel leidttotMutatie(Mutatie mutatie) {
        this.setLeidttotMutatie(mutatie);
        return this;
    }

    public Batch getHeeftBatch() {
        return this.heeftBatch;
    }

    public void setHeeftBatch(Batch batch) {
        this.heeftBatch = batch;
    }

    public Batchregel heeftBatch(Batch batch) {
        this.setHeeftBatch(batch);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Batchregel)) {
            return false;
        }
        return getId() != null && getId().equals(((Batchregel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Batchregel{" +
            "id=" + getId() +
            ", bedrag=" + getBedrag() +
            ", datumbetaling='" + getDatumbetaling() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", rekeningnaar='" + getRekeningnaar() + "'" +
            ", rekeningvan='" + getRekeningvan() + "'" +
            "}";
    }
}
