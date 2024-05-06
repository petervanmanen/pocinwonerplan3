package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Balieafspraak.
 */
@Entity
@Table(name = "balieafspraak")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Balieafspraak implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "eindtijdgepland")
    private String eindtijdgepland;

    @Column(name = "notitie")
    private String notitie;

    @Column(name = "starttijdgepland")
    private String starttijdgepland;

    @Column(name = "tijdaangemaakt")
    private String tijdaangemaakt;

    @Column(name = "tijdsduurgepland")
    private String tijdsduurgepland;

    @Column(name = "toelichting")
    private String toelichting;

    @Column(name = "wachttijdnastartafspraak")
    private String wachttijdnastartafspraak;

    @Column(name = "wachttijdtotaal")
    private String wachttijdtotaal;

    @Column(name = "wachttijdvoorstartafspraak")
    private String wachttijdvoorstartafspraak;

    @Column(name = "werkelijketijdsduur")
    private String werkelijketijdsduur;

    @JsonIgnoreProperties(
        value = {
            "heeftklantcontactenBetrokkene",
            "heeftbetrekkingopZaak",
            "isgevoerddoorMedewerker",
            "mondtuitinBalieafspraak",
            "heeftTelefoononderwerp",
            "mondtuitinTelefoontje",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Klantcontact mondtuitinKlantcontact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftBalieafspraaks" }, allowSetters = true)
    private Afspraakstatus heeftAfspraakstatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "ingevoerddoorStremmings",
            "gewijzigddoorStremmings",
            "voertuitSchouwrondes",
            "aanvragerSubsidies",
            "isverantwoordelijkevoorZaaktypes",
            "geleverdviaLeverancier",
            "isBetrokkene",
            "uitgevoerddoorParkeerscans",
            "melderMeldings",
            "uitvoerderMeldings",
            "auteurNotities",
            "behandelaarSubsidies",
            "procesleiderAanbestedings",
            "inhuurGunnings",
            "metBalieafspraaks",
            "isgevoerddoorKlantcontacts",
            "rollenApplicaties",
            "afhandelendmedewerkerZaaks",
        },
        allowSetters = true
    )
    private Medewerker metMedewerker;

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
    private Zaak heeftbetrekkingopZaak;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Balieafspraak id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEindtijdgepland() {
        return this.eindtijdgepland;
    }

    public Balieafspraak eindtijdgepland(String eindtijdgepland) {
        this.setEindtijdgepland(eindtijdgepland);
        return this;
    }

    public void setEindtijdgepland(String eindtijdgepland) {
        this.eindtijdgepland = eindtijdgepland;
    }

    public String getNotitie() {
        return this.notitie;
    }

    public Balieafspraak notitie(String notitie) {
        this.setNotitie(notitie);
        return this;
    }

    public void setNotitie(String notitie) {
        this.notitie = notitie;
    }

    public String getStarttijdgepland() {
        return this.starttijdgepland;
    }

    public Balieafspraak starttijdgepland(String starttijdgepland) {
        this.setStarttijdgepland(starttijdgepland);
        return this;
    }

    public void setStarttijdgepland(String starttijdgepland) {
        this.starttijdgepland = starttijdgepland;
    }

    public String getTijdaangemaakt() {
        return this.tijdaangemaakt;
    }

    public Balieafspraak tijdaangemaakt(String tijdaangemaakt) {
        this.setTijdaangemaakt(tijdaangemaakt);
        return this;
    }

    public void setTijdaangemaakt(String tijdaangemaakt) {
        this.tijdaangemaakt = tijdaangemaakt;
    }

    public String getTijdsduurgepland() {
        return this.tijdsduurgepland;
    }

    public Balieafspraak tijdsduurgepland(String tijdsduurgepland) {
        this.setTijdsduurgepland(tijdsduurgepland);
        return this;
    }

    public void setTijdsduurgepland(String tijdsduurgepland) {
        this.tijdsduurgepland = tijdsduurgepland;
    }

    public String getToelichting() {
        return this.toelichting;
    }

    public Balieafspraak toelichting(String toelichting) {
        this.setToelichting(toelichting);
        return this;
    }

    public void setToelichting(String toelichting) {
        this.toelichting = toelichting;
    }

    public String getWachttijdnastartafspraak() {
        return this.wachttijdnastartafspraak;
    }

    public Balieafspraak wachttijdnastartafspraak(String wachttijdnastartafspraak) {
        this.setWachttijdnastartafspraak(wachttijdnastartafspraak);
        return this;
    }

    public void setWachttijdnastartafspraak(String wachttijdnastartafspraak) {
        this.wachttijdnastartafspraak = wachttijdnastartafspraak;
    }

    public String getWachttijdtotaal() {
        return this.wachttijdtotaal;
    }

    public Balieafspraak wachttijdtotaal(String wachttijdtotaal) {
        this.setWachttijdtotaal(wachttijdtotaal);
        return this;
    }

    public void setWachttijdtotaal(String wachttijdtotaal) {
        this.wachttijdtotaal = wachttijdtotaal;
    }

    public String getWachttijdvoorstartafspraak() {
        return this.wachttijdvoorstartafspraak;
    }

    public Balieafspraak wachttijdvoorstartafspraak(String wachttijdvoorstartafspraak) {
        this.setWachttijdvoorstartafspraak(wachttijdvoorstartafspraak);
        return this;
    }

    public void setWachttijdvoorstartafspraak(String wachttijdvoorstartafspraak) {
        this.wachttijdvoorstartafspraak = wachttijdvoorstartafspraak;
    }

    public String getWerkelijketijdsduur() {
        return this.werkelijketijdsduur;
    }

    public Balieafspraak werkelijketijdsduur(String werkelijketijdsduur) {
        this.setWerkelijketijdsduur(werkelijketijdsduur);
        return this;
    }

    public void setWerkelijketijdsduur(String werkelijketijdsduur) {
        this.werkelijketijdsduur = werkelijketijdsduur;
    }

    public Klantcontact getMondtuitinKlantcontact() {
        return this.mondtuitinKlantcontact;
    }

    public void setMondtuitinKlantcontact(Klantcontact klantcontact) {
        this.mondtuitinKlantcontact = klantcontact;
    }

    public Balieafspraak mondtuitinKlantcontact(Klantcontact klantcontact) {
        this.setMondtuitinKlantcontact(klantcontact);
        return this;
    }

    public Afspraakstatus getHeeftAfspraakstatus() {
        return this.heeftAfspraakstatus;
    }

    public void setHeeftAfspraakstatus(Afspraakstatus afspraakstatus) {
        this.heeftAfspraakstatus = afspraakstatus;
    }

    public Balieafspraak heeftAfspraakstatus(Afspraakstatus afspraakstatus) {
        this.setHeeftAfspraakstatus(afspraakstatus);
        return this;
    }

    public Medewerker getMetMedewerker() {
        return this.metMedewerker;
    }

    public void setMetMedewerker(Medewerker medewerker) {
        this.metMedewerker = medewerker;
    }

    public Balieafspraak metMedewerker(Medewerker medewerker) {
        this.setMetMedewerker(medewerker);
        return this;
    }

    public Zaak getHeeftbetrekkingopZaak() {
        return this.heeftbetrekkingopZaak;
    }

    public void setHeeftbetrekkingopZaak(Zaak zaak) {
        this.heeftbetrekkingopZaak = zaak;
    }

    public Balieafspraak heeftbetrekkingopZaak(Zaak zaak) {
        this.setHeeftbetrekkingopZaak(zaak);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Balieafspraak)) {
            return false;
        }
        return getId() != null && getId().equals(((Balieafspraak) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Balieafspraak{" +
            "id=" + getId() +
            ", eindtijdgepland='" + getEindtijdgepland() + "'" +
            ", notitie='" + getNotitie() + "'" +
            ", starttijdgepland='" + getStarttijdgepland() + "'" +
            ", tijdaangemaakt='" + getTijdaangemaakt() + "'" +
            ", tijdsduurgepland='" + getTijdsduurgepland() + "'" +
            ", toelichting='" + getToelichting() + "'" +
            ", wachttijdnastartafspraak='" + getWachttijdnastartafspraak() + "'" +
            ", wachttijdtotaal='" + getWachttijdtotaal() + "'" +
            ", wachttijdvoorstartafspraak='" + getWachttijdvoorstartafspraak() + "'" +
            ", werkelijketijdsduur='" + getWerkelijketijdsduur() + "'" +
            "}";
    }
}
