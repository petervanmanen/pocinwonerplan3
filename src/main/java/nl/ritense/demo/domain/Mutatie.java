package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Mutatie.
 */
@Entity
@Table(name = "mutatie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Mutatie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bedrag", precision = 21, scale = 2)
    private BigDecimal bedrag;

    @Column(name = "datum")
    private String datum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "valtbinnenHoofdrekenings",
            "heeftSubrekenings",
            "heeftWerkorders",
            "heeftActivas",
            "heeftKostenplaats",
            "valtbinnenHoofdrekening2",
            "betreftBegrotingregels",
            "vanMutaties",
            "naarMutaties",
            "wordtgeschrevenopInkooporders",
        },
        allowSetters = true
    )
    private Hoofdrekening vanHoofdrekening;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "valtbinnenHoofdrekenings",
            "heeftSubrekenings",
            "heeftWerkorders",
            "heeftActivas",
            "heeftKostenplaats",
            "valtbinnenHoofdrekening2",
            "betreftBegrotingregels",
            "vanMutaties",
            "naarMutaties",
            "wordtgeschrevenopInkooporders",
        },
        allowSetters = true
    )
    private Hoofdrekening naarHoofdrekening;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftVastgoedobjects",
            "heeftWerkorders",
            "heeftSubrekenings",
            "heeftInkooporders",
            "heeftTaakvelds",
            "heeftProgrammas",
            "heeftSubsidies",
            "heeftSubsidiecomponents",
            "betreftBegrotingregels",
            "schrijftopFactuurs",
            "heeftbetrekkingopMutaties",
            "heeftProducts",
            "heeftHoofdrekenings",
            "heeftProjects",
        },
        allowSetters = true
    )
    private Kostenplaats heeftbetrekkingopKostenplaats;

    @JsonIgnoreProperties(value = { "leidttotMutatie", "heeftBankafschrift", "komtvooropBetalings" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "leidttotMutatie")
    private Bankafschriftregel leidttotBankafschriftregel;

    @JsonIgnoreProperties(value = { "leidttotMutatie", "heeftBatch" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "leidttotMutatie")
    private Batchregel leidttotBatchregel;

    @JsonIgnoreProperties(value = { "leidttotMutatie", "heeftFactuur" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "leidttotMutatie")
    private Factuurregel leidttotFactuurregel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Mutatie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBedrag() {
        return this.bedrag;
    }

    public Mutatie bedrag(BigDecimal bedrag) {
        this.setBedrag(bedrag);
        return this;
    }

    public void setBedrag(BigDecimal bedrag) {
        this.bedrag = bedrag;
    }

    public String getDatum() {
        return this.datum;
    }

    public Mutatie datum(String datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public Hoofdrekening getVanHoofdrekening() {
        return this.vanHoofdrekening;
    }

    public void setVanHoofdrekening(Hoofdrekening hoofdrekening) {
        this.vanHoofdrekening = hoofdrekening;
    }

    public Mutatie vanHoofdrekening(Hoofdrekening hoofdrekening) {
        this.setVanHoofdrekening(hoofdrekening);
        return this;
    }

    public Hoofdrekening getNaarHoofdrekening() {
        return this.naarHoofdrekening;
    }

    public void setNaarHoofdrekening(Hoofdrekening hoofdrekening) {
        this.naarHoofdrekening = hoofdrekening;
    }

    public Mutatie naarHoofdrekening(Hoofdrekening hoofdrekening) {
        this.setNaarHoofdrekening(hoofdrekening);
        return this;
    }

    public Kostenplaats getHeeftbetrekkingopKostenplaats() {
        return this.heeftbetrekkingopKostenplaats;
    }

    public void setHeeftbetrekkingopKostenplaats(Kostenplaats kostenplaats) {
        this.heeftbetrekkingopKostenplaats = kostenplaats;
    }

    public Mutatie heeftbetrekkingopKostenplaats(Kostenplaats kostenplaats) {
        this.setHeeftbetrekkingopKostenplaats(kostenplaats);
        return this;
    }

    public Bankafschriftregel getLeidttotBankafschriftregel() {
        return this.leidttotBankafschriftregel;
    }

    public void setLeidttotBankafschriftregel(Bankafschriftregel bankafschriftregel) {
        if (this.leidttotBankafschriftregel != null) {
            this.leidttotBankafschriftregel.setLeidttotMutatie(null);
        }
        if (bankafschriftregel != null) {
            bankafschriftregel.setLeidttotMutatie(this);
        }
        this.leidttotBankafschriftregel = bankafschriftregel;
    }

    public Mutatie leidttotBankafschriftregel(Bankafschriftregel bankafschriftregel) {
        this.setLeidttotBankafschriftregel(bankafschriftregel);
        return this;
    }

    public Batchregel getLeidttotBatchregel() {
        return this.leidttotBatchregel;
    }

    public void setLeidttotBatchregel(Batchregel batchregel) {
        if (this.leidttotBatchregel != null) {
            this.leidttotBatchregel.setLeidttotMutatie(null);
        }
        if (batchregel != null) {
            batchregel.setLeidttotMutatie(this);
        }
        this.leidttotBatchregel = batchregel;
    }

    public Mutatie leidttotBatchregel(Batchregel batchregel) {
        this.setLeidttotBatchregel(batchregel);
        return this;
    }

    public Factuurregel getLeidttotFactuurregel() {
        return this.leidttotFactuurregel;
    }

    public void setLeidttotFactuurregel(Factuurregel factuurregel) {
        if (this.leidttotFactuurregel != null) {
            this.leidttotFactuurregel.setLeidttotMutatie(null);
        }
        if (factuurregel != null) {
            factuurregel.setLeidttotMutatie(this);
        }
        this.leidttotFactuurregel = factuurregel;
    }

    public Mutatie leidttotFactuurregel(Factuurregel factuurregel) {
        this.setLeidttotFactuurregel(factuurregel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mutatie)) {
            return false;
        }
        return getId() != null && getId().equals(((Mutatie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Mutatie{" +
            "id=" + getId() +
            ", bedrag=" + getBedrag() +
            ", datum='" + getDatum() + "'" +
            "}";
    }
}
