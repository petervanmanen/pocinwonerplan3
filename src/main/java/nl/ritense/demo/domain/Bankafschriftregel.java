package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A Bankafschriftregel.
 */
@Entity
@Table(name = "bankafschriftregel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bankafschriftregel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bedrag", precision = 21, scale = 2)
    private BigDecimal bedrag;

    @Column(name = "bij")
    private Boolean bij;

    @Column(name = "datum")
    private String datum;

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
    @JsonIgnoreProperties(value = { "heeftBankafschriftregels", "heeftBankrekening" }, allowSetters = true)
    private Bankafschrift heeftBankafschrift;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "komtvooropBankafschriftregel")
    @JsonIgnoreProperties(
        value = { "komtvooropBankafschriftregel", "vanBankrekening", "naarBankrekening", "heeftbetalingZaak" },
        allowSetters = true
    )
    private Set<Betaling> komtvooropBetalings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bankafschriftregel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBedrag() {
        return this.bedrag;
    }

    public Bankafschriftregel bedrag(BigDecimal bedrag) {
        this.setBedrag(bedrag);
        return this;
    }

    public void setBedrag(BigDecimal bedrag) {
        this.bedrag = bedrag;
    }

    public Boolean getBij() {
        return this.bij;
    }

    public Bankafschriftregel bij(Boolean bij) {
        this.setBij(bij);
        return this;
    }

    public void setBij(Boolean bij) {
        this.bij = bij;
    }

    public String getDatum() {
        return this.datum;
    }

    public Bankafschriftregel datum(String datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getRekeningvan() {
        return this.rekeningvan;
    }

    public Bankafschriftregel rekeningvan(String rekeningvan) {
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

    public Bankafschriftregel leidttotMutatie(Mutatie mutatie) {
        this.setLeidttotMutatie(mutatie);
        return this;
    }

    public Bankafschrift getHeeftBankafschrift() {
        return this.heeftBankafschrift;
    }

    public void setHeeftBankafschrift(Bankafschrift bankafschrift) {
        this.heeftBankafschrift = bankafschrift;
    }

    public Bankafschriftregel heeftBankafschrift(Bankafschrift bankafschrift) {
        this.setHeeftBankafschrift(bankafschrift);
        return this;
    }

    public Set<Betaling> getKomtvooropBetalings() {
        return this.komtvooropBetalings;
    }

    public void setKomtvooropBetalings(Set<Betaling> betalings) {
        if (this.komtvooropBetalings != null) {
            this.komtvooropBetalings.forEach(i -> i.setKomtvooropBankafschriftregel(null));
        }
        if (betalings != null) {
            betalings.forEach(i -> i.setKomtvooropBankafschriftregel(this));
        }
        this.komtvooropBetalings = betalings;
    }

    public Bankafschriftregel komtvooropBetalings(Set<Betaling> betalings) {
        this.setKomtvooropBetalings(betalings);
        return this;
    }

    public Bankafschriftregel addKomtvooropBetaling(Betaling betaling) {
        this.komtvooropBetalings.add(betaling);
        betaling.setKomtvooropBankafschriftregel(this);
        return this;
    }

    public Bankafschriftregel removeKomtvooropBetaling(Betaling betaling) {
        this.komtvooropBetalings.remove(betaling);
        betaling.setKomtvooropBankafschriftregel(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bankafschriftregel)) {
            return false;
        }
        return getId() != null && getId().equals(((Bankafschriftregel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bankafschriftregel{" +
            "id=" + getId() +
            ", bedrag=" + getBedrag() +
            ", bij='" + getBij() + "'" +
            ", datum='" + getDatum() + "'" +
            ", rekeningvan='" + getRekeningvan() + "'" +
            "}";
    }
}
