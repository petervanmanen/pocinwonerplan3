package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Levering.
 */
@Entity
@Table(name = "levering")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Levering implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @Column(name = "code", length = 20)
    private String code;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "datumstop")
    private LocalDate datumstop;

    @Column(name = "eenheid")
    private String eenheid;

    @Column(name = "frequentie")
    private String frequentie;

    @Column(name = "omvang")
    private String omvang;

    @Column(name = "stopreden")
    private String stopreden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "toewijzingToewijzings",
            "emptyClient",
            "geeftafClientbegeleider",
            "isgebaseerdopBeperkings",
            "geleverdeprestatieLeverings",
            "isvoorDeclaratieregels",
        },
        allowSetters = true
    )
    private Beschikking geleverdeprestatieBeschikking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftParticipatiedossier",
            "heeftvoorzieningInkomensvoorziening",
            "heeftScores",
            "leverttegenprestatieTegenprestaties",
            "heeftparticipatietrajectTrajects",
            "heeftfraudegegevensFraudegegevens",
            "heeftregelingRegelings",
            "heefttrajectTrajects",
            "valtbinnendoelgroepDoelgroep",
            "heeftrelatieRelaties",
            "voorzieningbijstandspartijInkomensvoorzienings",
            "maaktonderdeeluitvanHuishoudens",
            "heefttaalniveauTaalniveaus",
            "emptyBeschikkings",
            "prestatievoorLeverings",
            "betreftDeclaratieregels",
            "ondersteuntclientClientbegeleiders",
        },
        allowSetters = true
    )
    private Client prestatievoorClient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "isopbasisvanDeclaratieregels", "levertvoorzieningLeverancier", "toewijzingBeschikking", "geleverdezorgLeverings" },
        allowSetters = true
    )
    private Toewijzing geleverdezorgToewijzing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "heeftTariefs", "valtbinnenVoorzieningsoort", "betreftReserverings", "voorzieningLeverings" },
        allowSetters = true
    )
    private Voorziening voorzieningVoorziening;

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
    private Leverancier leverdeprestatieLeverancier;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Levering id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Levering code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Levering datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public LocalDate getDatumstop() {
        return this.datumstop;
    }

    public Levering datumstop(LocalDate datumstop) {
        this.setDatumstop(datumstop);
        return this;
    }

    public void setDatumstop(LocalDate datumstop) {
        this.datumstop = datumstop;
    }

    public String getEenheid() {
        return this.eenheid;
    }

    public Levering eenheid(String eenheid) {
        this.setEenheid(eenheid);
        return this;
    }

    public void setEenheid(String eenheid) {
        this.eenheid = eenheid;
    }

    public String getFrequentie() {
        return this.frequentie;
    }

    public Levering frequentie(String frequentie) {
        this.setFrequentie(frequentie);
        return this;
    }

    public void setFrequentie(String frequentie) {
        this.frequentie = frequentie;
    }

    public String getOmvang() {
        return this.omvang;
    }

    public Levering omvang(String omvang) {
        this.setOmvang(omvang);
        return this;
    }

    public void setOmvang(String omvang) {
        this.omvang = omvang;
    }

    public String getStopreden() {
        return this.stopreden;
    }

    public Levering stopreden(String stopreden) {
        this.setStopreden(stopreden);
        return this;
    }

    public void setStopreden(String stopreden) {
        this.stopreden = stopreden;
    }

    public Beschikking getGeleverdeprestatieBeschikking() {
        return this.geleverdeprestatieBeschikking;
    }

    public void setGeleverdeprestatieBeschikking(Beschikking beschikking) {
        this.geleverdeprestatieBeschikking = beschikking;
    }

    public Levering geleverdeprestatieBeschikking(Beschikking beschikking) {
        this.setGeleverdeprestatieBeschikking(beschikking);
        return this;
    }

    public Client getPrestatievoorClient() {
        return this.prestatievoorClient;
    }

    public void setPrestatievoorClient(Client client) {
        this.prestatievoorClient = client;
    }

    public Levering prestatievoorClient(Client client) {
        this.setPrestatievoorClient(client);
        return this;
    }

    public Toewijzing getGeleverdezorgToewijzing() {
        return this.geleverdezorgToewijzing;
    }

    public void setGeleverdezorgToewijzing(Toewijzing toewijzing) {
        this.geleverdezorgToewijzing = toewijzing;
    }

    public Levering geleverdezorgToewijzing(Toewijzing toewijzing) {
        this.setGeleverdezorgToewijzing(toewijzing);
        return this;
    }

    public Voorziening getVoorzieningVoorziening() {
        return this.voorzieningVoorziening;
    }

    public void setVoorzieningVoorziening(Voorziening voorziening) {
        this.voorzieningVoorziening = voorziening;
    }

    public Levering voorzieningVoorziening(Voorziening voorziening) {
        this.setVoorzieningVoorziening(voorziening);
        return this;
    }

    public Leverancier getLeverdeprestatieLeverancier() {
        return this.leverdeprestatieLeverancier;
    }

    public void setLeverdeprestatieLeverancier(Leverancier leverancier) {
        this.leverdeprestatieLeverancier = leverancier;
    }

    public Levering leverdeprestatieLeverancier(Leverancier leverancier) {
        this.setLeverdeprestatieLeverancier(leverancier);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Levering)) {
            return false;
        }
        return getId() != null && getId().equals(((Levering) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Levering{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", datumstop='" + getDatumstop() + "'" +
            ", eenheid='" + getEenheid() + "'" +
            ", frequentie='" + getFrequentie() + "'" +
            ", omvang='" + getOmvang() + "'" +
            ", stopreden='" + getStopreden() + "'" +
            "}";
    }
}
