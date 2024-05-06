package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Parkeerrecht.
 */
@Entity
@Table(name = "parkeerrecht")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Parkeerrecht implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aanmaaktijd")
    private String aanmaaktijd;

    @Column(name = "bedragaankoop", precision = 21, scale = 2)
    private BigDecimal bedragaankoop;

    @Column(name = "bedragbtw", precision = 21, scale = 2)
    private BigDecimal bedragbtw;

    @Column(name = "datumtijdeinde")
    private String datumtijdeinde;

    @Column(name = "datumtijdstart")
    private String datumtijdstart;

    @Column(name = "productnaam")
    private String productnaam;

    @Column(name = "productomschrijving")
    private String productomschrijving;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "leverancierParkeerrechts" }, allowSetters = true)
    private Belprovider leverancierBelprovider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "betreftParkeerrechts", "betreftParkeerscans" }, allowSetters = true)
    private Voertuig betreftVoertuig;

    @JsonIgnoreProperties(
        value = { "resulteertParkeerrecht", "houderRechtspersoon", "soortProductgroep", "soortProductsoort" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "resulteertParkeerrecht")
    private Parkeervergunning resulteertParkeervergunning;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Parkeerrecht id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAanmaaktijd() {
        return this.aanmaaktijd;
    }

    public Parkeerrecht aanmaaktijd(String aanmaaktijd) {
        this.setAanmaaktijd(aanmaaktijd);
        return this;
    }

    public void setAanmaaktijd(String aanmaaktijd) {
        this.aanmaaktijd = aanmaaktijd;
    }

    public BigDecimal getBedragaankoop() {
        return this.bedragaankoop;
    }

    public Parkeerrecht bedragaankoop(BigDecimal bedragaankoop) {
        this.setBedragaankoop(bedragaankoop);
        return this;
    }

    public void setBedragaankoop(BigDecimal bedragaankoop) {
        this.bedragaankoop = bedragaankoop;
    }

    public BigDecimal getBedragbtw() {
        return this.bedragbtw;
    }

    public Parkeerrecht bedragbtw(BigDecimal bedragbtw) {
        this.setBedragbtw(bedragbtw);
        return this;
    }

    public void setBedragbtw(BigDecimal bedragbtw) {
        this.bedragbtw = bedragbtw;
    }

    public String getDatumtijdeinde() {
        return this.datumtijdeinde;
    }

    public Parkeerrecht datumtijdeinde(String datumtijdeinde) {
        this.setDatumtijdeinde(datumtijdeinde);
        return this;
    }

    public void setDatumtijdeinde(String datumtijdeinde) {
        this.datumtijdeinde = datumtijdeinde;
    }

    public String getDatumtijdstart() {
        return this.datumtijdstart;
    }

    public Parkeerrecht datumtijdstart(String datumtijdstart) {
        this.setDatumtijdstart(datumtijdstart);
        return this;
    }

    public void setDatumtijdstart(String datumtijdstart) {
        this.datumtijdstart = datumtijdstart;
    }

    public String getProductnaam() {
        return this.productnaam;
    }

    public Parkeerrecht productnaam(String productnaam) {
        this.setProductnaam(productnaam);
        return this;
    }

    public void setProductnaam(String productnaam) {
        this.productnaam = productnaam;
    }

    public String getProductomschrijving() {
        return this.productomschrijving;
    }

    public Parkeerrecht productomschrijving(String productomschrijving) {
        this.setProductomschrijving(productomschrijving);
        return this;
    }

    public void setProductomschrijving(String productomschrijving) {
        this.productomschrijving = productomschrijving;
    }

    public Belprovider getLeverancierBelprovider() {
        return this.leverancierBelprovider;
    }

    public void setLeverancierBelprovider(Belprovider belprovider) {
        this.leverancierBelprovider = belprovider;
    }

    public Parkeerrecht leverancierBelprovider(Belprovider belprovider) {
        this.setLeverancierBelprovider(belprovider);
        return this;
    }

    public Voertuig getBetreftVoertuig() {
        return this.betreftVoertuig;
    }

    public void setBetreftVoertuig(Voertuig voertuig) {
        this.betreftVoertuig = voertuig;
    }

    public Parkeerrecht betreftVoertuig(Voertuig voertuig) {
        this.setBetreftVoertuig(voertuig);
        return this;
    }

    public Parkeervergunning getResulteertParkeervergunning() {
        return this.resulteertParkeervergunning;
    }

    public void setResulteertParkeervergunning(Parkeervergunning parkeervergunning) {
        if (this.resulteertParkeervergunning != null) {
            this.resulteertParkeervergunning.setResulteertParkeerrecht(null);
        }
        if (parkeervergunning != null) {
            parkeervergunning.setResulteertParkeerrecht(this);
        }
        this.resulteertParkeervergunning = parkeervergunning;
    }

    public Parkeerrecht resulteertParkeervergunning(Parkeervergunning parkeervergunning) {
        this.setResulteertParkeervergunning(parkeervergunning);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Parkeerrecht)) {
            return false;
        }
        return getId() != null && getId().equals(((Parkeerrecht) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Parkeerrecht{" +
            "id=" + getId() +
            ", aanmaaktijd='" + getAanmaaktijd() + "'" +
            ", bedragaankoop=" + getBedragaankoop() +
            ", bedragbtw=" + getBedragbtw() +
            ", datumtijdeinde='" + getDatumtijdeinde() + "'" +
            ", datumtijdstart='" + getDatumtijdstart() + "'" +
            ", productnaam='" + getProductnaam() + "'" +
            ", productomschrijving='" + getProductomschrijving() + "'" +
            "}";
    }
}
