package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Betaling.
 */
@Entity
@Table(name = "betaling")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Betaling implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bedrag", precision = 21, scale = 2)
    private BigDecimal bedrag;

    @Column(name = "datumtijd")
    private String datumtijd;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "valuta")
    private String valuta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "leidttotMutatie", "heeftBankafschrift", "komtvooropBetalings" }, allowSetters = true)
    private Bankafschriftregel komtvooropBankafschriftregel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftBankafschrifts", "vanBetalings", "naarBetalings" }, allowSetters = true)
    private Bankrekening vanBankrekening;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftBankafschrifts", "vanBetalings", "naarBetalings" }, allowSetters = true)
    private Bankrekening naarBankrekening;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftproductProducttype",
            "heeftKlantbeoordeling",
            "heeftHeffing",
            "heeftbetalingBetalings",
            "heeftStatuses",
            "betreftProject",
            "isvanZaaktype",
            "kentDocuments",
            "afhandelendmedewerkerMedewerkers",
            "leidttotVerzoek",
            "heeftSubsidie",
            "betreftAanbesteding",
            "heeftbetrekkingopBalieafspraaks",
            "isuitkomstvanBesluits",
            "heeftbetrekkingopKlantcontacts",
            "heeftGrondslags",
            "uitgevoerdbinnenBedrijfsproces",
            "oefentuitBetrokkenes",
        },
        allowSetters = true
    )
    private Zaak heeftbetalingZaak;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Betaling id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBedrag() {
        return this.bedrag;
    }

    public Betaling bedrag(BigDecimal bedrag) {
        this.setBedrag(bedrag);
        return this;
    }

    public void setBedrag(BigDecimal bedrag) {
        this.bedrag = bedrag;
    }

    public String getDatumtijd() {
        return this.datumtijd;
    }

    public Betaling datumtijd(String datumtijd) {
        this.setDatumtijd(datumtijd);
        return this;
    }

    public void setDatumtijd(String datumtijd) {
        this.datumtijd = datumtijd;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Betaling omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getValuta() {
        return this.valuta;
    }

    public Betaling valuta(String valuta) {
        this.setValuta(valuta);
        return this;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    public Bankafschriftregel getKomtvooropBankafschriftregel() {
        return this.komtvooropBankafschriftregel;
    }

    public void setKomtvooropBankafschriftregel(Bankafschriftregel bankafschriftregel) {
        this.komtvooropBankafschriftregel = bankafschriftregel;
    }

    public Betaling komtvooropBankafschriftregel(Bankafschriftregel bankafschriftregel) {
        this.setKomtvooropBankafschriftregel(bankafschriftregel);
        return this;
    }

    public Bankrekening getVanBankrekening() {
        return this.vanBankrekening;
    }

    public void setVanBankrekening(Bankrekening bankrekening) {
        this.vanBankrekening = bankrekening;
    }

    public Betaling vanBankrekening(Bankrekening bankrekening) {
        this.setVanBankrekening(bankrekening);
        return this;
    }

    public Bankrekening getNaarBankrekening() {
        return this.naarBankrekening;
    }

    public void setNaarBankrekening(Bankrekening bankrekening) {
        this.naarBankrekening = bankrekening;
    }

    public Betaling naarBankrekening(Bankrekening bankrekening) {
        this.setNaarBankrekening(bankrekening);
        return this;
    }

    public Zaak getHeeftbetalingZaak() {
        return this.heeftbetalingZaak;
    }

    public void setHeeftbetalingZaak(Zaak zaak) {
        this.heeftbetalingZaak = zaak;
    }

    public Betaling heeftbetalingZaak(Zaak zaak) {
        this.setHeeftbetalingZaak(zaak);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Betaling)) {
            return false;
        }
        return getId() != null && getId().equals(((Betaling) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Betaling{" +
            "id=" + getId() +
            ", bedrag=" + getBedrag() +
            ", datumtijd='" + getDatumtijd() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", valuta='" + getValuta() + "'" +
            "}";
    }
}
