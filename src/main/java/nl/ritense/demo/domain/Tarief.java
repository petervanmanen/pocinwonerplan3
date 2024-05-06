package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Tarief.
 */
@Entity
@Table(name = "tarief")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tarief implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bedrag", precision = 21, scale = 2)
    private BigDecimal bedrag;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "eenheid")
    private String eenheid;

    @Column(name = "wet")
    private String wet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftContracts",
            "leverdeprestatieLeverings",
            "voertwerkuitconformWerkbons",
            "contractantContracts",
            "heeftInschrijvings",
            "biedtaanKandidaats",
            "heeftKwalificaties",
            "gekwalificeerdCategories",
            "leverancierProducts",
            "ingedienddoorDeclaraties",
            "levertvoorzieningToewijzings",
            "heeftTariefs",
            "uitvoerderMeldings",
            "heeftleverancierApplicaties",
            "heeftleverancierServers",
            "crediteurFactuurs",
            "verplichtingaanInkooporders",
            "gerichtaanUitnodigings",
            "geleverdviaMedewerkers",
            "gerichtaanOfferteaanvraags",
            "ingedienddoorOffertes",
        },
        allowSetters = true
    )
    private Leverancier heeftLeverancier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "bevatTariefs",
            "bovenliggendContract",
            "betreftInkooporder",
            "heeftLeverancier",
            "contractantLeverancier",
            "bovenliggendContract2s",
        },
        allowSetters = true
    )
    private Contract bevatContract;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "heeftTariefs", "valtbinnenVoorzieningsoort", "betreftReserverings", "voorzieningLeverings" },
        allowSetters = true
    )
    private Voorziening heeftVoorziening;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tarief id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBedrag() {
        return this.bedrag;
    }

    public Tarief bedrag(BigDecimal bedrag) {
        this.setBedrag(bedrag);
        return this;
    }

    public void setBedrag(BigDecimal bedrag) {
        this.bedrag = bedrag;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Tarief datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Tarief datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public String getEenheid() {
        return this.eenheid;
    }

    public Tarief eenheid(String eenheid) {
        this.setEenheid(eenheid);
        return this;
    }

    public void setEenheid(String eenheid) {
        this.eenheid = eenheid;
    }

    public String getWet() {
        return this.wet;
    }

    public Tarief wet(String wet) {
        this.setWet(wet);
        return this;
    }

    public void setWet(String wet) {
        this.wet = wet;
    }

    public Leverancier getHeeftLeverancier() {
        return this.heeftLeverancier;
    }

    public void setHeeftLeverancier(Leverancier leverancier) {
        this.heeftLeverancier = leverancier;
    }

    public Tarief heeftLeverancier(Leverancier leverancier) {
        this.setHeeftLeverancier(leverancier);
        return this;
    }

    public Contract getBevatContract() {
        return this.bevatContract;
    }

    public void setBevatContract(Contract contract) {
        this.bevatContract = contract;
    }

    public Tarief bevatContract(Contract contract) {
        this.setBevatContract(contract);
        return this;
    }

    public Voorziening getHeeftVoorziening() {
        return this.heeftVoorziening;
    }

    public void setHeeftVoorziening(Voorziening voorziening) {
        this.heeftVoorziening = voorziening;
    }

    public Tarief heeftVoorziening(Voorziening voorziening) {
        this.setHeeftVoorziening(voorziening);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tarief)) {
            return false;
        }
        return getId() != null && getId().equals(((Tarief) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tarief{" +
            "id=" + getId() +
            ", bedrag=" + getBedrag() +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", eenheid='" + getEenheid() + "'" +
            ", wet='" + getWet() + "'" +
            "}";
    }
}
