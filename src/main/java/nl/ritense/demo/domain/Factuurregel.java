package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Factuurregel.
 */
@Entity
@Table(name = "factuurregel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Factuurregel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aantal")
    private String aantal;

    @Column(name = "bedragbtw", precision = 21, scale = 2)
    private BigDecimal bedragbtw;

    @Column(name = "bedragexbtw", precision = 21, scale = 2)
    private BigDecimal bedragexbtw;

    @Column(name = "btwpercentage")
    private String btwpercentage;

    @Column(name = "nummer")
    private String nummer;

    @Column(name = "omschrijving")
    private String omschrijving;

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

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "heeftFactuurregels", "schrijftopKostenplaats", "gedektviaInkooporder", "crediteurLeverancier", "heeftDebiteur" },
        allowSetters = true
    )
    private Factuur heeftFactuur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Factuurregel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAantal() {
        return this.aantal;
    }

    public Factuurregel aantal(String aantal) {
        this.setAantal(aantal);
        return this;
    }

    public void setAantal(String aantal) {
        this.aantal = aantal;
    }

    public BigDecimal getBedragbtw() {
        return this.bedragbtw;
    }

    public Factuurregel bedragbtw(BigDecimal bedragbtw) {
        this.setBedragbtw(bedragbtw);
        return this;
    }

    public void setBedragbtw(BigDecimal bedragbtw) {
        this.bedragbtw = bedragbtw;
    }

    public BigDecimal getBedragexbtw() {
        return this.bedragexbtw;
    }

    public Factuurregel bedragexbtw(BigDecimal bedragexbtw) {
        this.setBedragexbtw(bedragexbtw);
        return this;
    }

    public void setBedragexbtw(BigDecimal bedragexbtw) {
        this.bedragexbtw = bedragexbtw;
    }

    public String getBtwpercentage() {
        return this.btwpercentage;
    }

    public Factuurregel btwpercentage(String btwpercentage) {
        this.setBtwpercentage(btwpercentage);
        return this;
    }

    public void setBtwpercentage(String btwpercentage) {
        this.btwpercentage = btwpercentage;
    }

    public String getNummer() {
        return this.nummer;
    }

    public Factuurregel nummer(String nummer) {
        this.setNummer(nummer);
        return this;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Factuurregel omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Mutatie getLeidttotMutatie() {
        return this.leidttotMutatie;
    }

    public void setLeidttotMutatie(Mutatie mutatie) {
        this.leidttotMutatie = mutatie;
    }

    public Factuurregel leidttotMutatie(Mutatie mutatie) {
        this.setLeidttotMutatie(mutatie);
        return this;
    }

    public Factuur getHeeftFactuur() {
        return this.heeftFactuur;
    }

    public void setHeeftFactuur(Factuur factuur) {
        this.heeftFactuur = factuur;
    }

    public Factuurregel heeftFactuur(Factuur factuur) {
        this.setHeeftFactuur(factuur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Factuurregel)) {
            return false;
        }
        return getId() != null && getId().equals(((Factuurregel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Factuurregel{" +
            "id=" + getId() +
            ", aantal='" + getAantal() + "'" +
            ", bedragbtw=" + getBedragbtw() +
            ", bedragexbtw=" + getBedragexbtw() +
            ", btwpercentage='" + getBtwpercentage() + "'" +
            ", nummer='" + getNummer() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
