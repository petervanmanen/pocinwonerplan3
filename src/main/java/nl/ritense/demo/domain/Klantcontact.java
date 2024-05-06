package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Klantcontact.
 */
@Entity
@Table(name = "klantcontact")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Klantcontact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "eindtijd")
    private String eindtijd;

    @Size(max = 20)
    @Column(name = "kanaal", length = 20)
    private String kanaal;

    @Column(name = "notitie")
    private String notitie;

    @Column(name = "starttijd")
    private String starttijd;

    @Column(name = "tijdsduur")
    private String tijdsduur;

    @Column(name = "toelichting")
    private String toelichting;

    @Column(name = "wachttijdtotaal")
    private String wachttijdtotaal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "isMedewerker", "doetKlantbeoordelings", "oefentuitZaaks", "heeftklantcontactenKlantcontacts" },
        allowSetters = true
    )
    private Betrokkene heeftklantcontactenBetrokkene;

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
    private Medewerker isgevoerddoorMedewerker;

    @JsonIgnoreProperties(
        value = { "mondtuitinKlantcontact", "heeftAfspraakstatus", "metMedewerker", "heeftbetrekkingopZaak" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "mondtuitinKlantcontact")
    private Balieafspraak mondtuitinBalieafspraak;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftKlantcontacts", "heeftTelefoontjes" }, allowSetters = true)
    private Telefoononderwerp heeftTelefoononderwerp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "mondtuitinKlantcontacts", "heeftTelefoonstatus", "heeftTelefoononderwerp" }, allowSetters = true)
    private Telefoontje mondtuitinTelefoontje;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Klantcontact id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEindtijd() {
        return this.eindtijd;
    }

    public Klantcontact eindtijd(String eindtijd) {
        this.setEindtijd(eindtijd);
        return this;
    }

    public void setEindtijd(String eindtijd) {
        this.eindtijd = eindtijd;
    }

    public String getKanaal() {
        return this.kanaal;
    }

    public Klantcontact kanaal(String kanaal) {
        this.setKanaal(kanaal);
        return this;
    }

    public void setKanaal(String kanaal) {
        this.kanaal = kanaal;
    }

    public String getNotitie() {
        return this.notitie;
    }

    public Klantcontact notitie(String notitie) {
        this.setNotitie(notitie);
        return this;
    }

    public void setNotitie(String notitie) {
        this.notitie = notitie;
    }

    public String getStarttijd() {
        return this.starttijd;
    }

    public Klantcontact starttijd(String starttijd) {
        this.setStarttijd(starttijd);
        return this;
    }

    public void setStarttijd(String starttijd) {
        this.starttijd = starttijd;
    }

    public String getTijdsduur() {
        return this.tijdsduur;
    }

    public Klantcontact tijdsduur(String tijdsduur) {
        this.setTijdsduur(tijdsduur);
        return this;
    }

    public void setTijdsduur(String tijdsduur) {
        this.tijdsduur = tijdsduur;
    }

    public String getToelichting() {
        return this.toelichting;
    }

    public Klantcontact toelichting(String toelichting) {
        this.setToelichting(toelichting);
        return this;
    }

    public void setToelichting(String toelichting) {
        this.toelichting = toelichting;
    }

    public String getWachttijdtotaal() {
        return this.wachttijdtotaal;
    }

    public Klantcontact wachttijdtotaal(String wachttijdtotaal) {
        this.setWachttijdtotaal(wachttijdtotaal);
        return this;
    }

    public void setWachttijdtotaal(String wachttijdtotaal) {
        this.wachttijdtotaal = wachttijdtotaal;
    }

    public Betrokkene getHeeftklantcontactenBetrokkene() {
        return this.heeftklantcontactenBetrokkene;
    }

    public void setHeeftklantcontactenBetrokkene(Betrokkene betrokkene) {
        this.heeftklantcontactenBetrokkene = betrokkene;
    }

    public Klantcontact heeftklantcontactenBetrokkene(Betrokkene betrokkene) {
        this.setHeeftklantcontactenBetrokkene(betrokkene);
        return this;
    }

    public Zaak getHeeftbetrekkingopZaak() {
        return this.heeftbetrekkingopZaak;
    }

    public void setHeeftbetrekkingopZaak(Zaak zaak) {
        this.heeftbetrekkingopZaak = zaak;
    }

    public Klantcontact heeftbetrekkingopZaak(Zaak zaak) {
        this.setHeeftbetrekkingopZaak(zaak);
        return this;
    }

    public Medewerker getIsgevoerddoorMedewerker() {
        return this.isgevoerddoorMedewerker;
    }

    public void setIsgevoerddoorMedewerker(Medewerker medewerker) {
        this.isgevoerddoorMedewerker = medewerker;
    }

    public Klantcontact isgevoerddoorMedewerker(Medewerker medewerker) {
        this.setIsgevoerddoorMedewerker(medewerker);
        return this;
    }

    public Balieafspraak getMondtuitinBalieafspraak() {
        return this.mondtuitinBalieafspraak;
    }

    public void setMondtuitinBalieafspraak(Balieafspraak balieafspraak) {
        if (this.mondtuitinBalieafspraak != null) {
            this.mondtuitinBalieafspraak.setMondtuitinKlantcontact(null);
        }
        if (balieafspraak != null) {
            balieafspraak.setMondtuitinKlantcontact(this);
        }
        this.mondtuitinBalieafspraak = balieafspraak;
    }

    public Klantcontact mondtuitinBalieafspraak(Balieafspraak balieafspraak) {
        this.setMondtuitinBalieafspraak(balieafspraak);
        return this;
    }

    public Telefoononderwerp getHeeftTelefoononderwerp() {
        return this.heeftTelefoononderwerp;
    }

    public void setHeeftTelefoononderwerp(Telefoononderwerp telefoononderwerp) {
        this.heeftTelefoononderwerp = telefoononderwerp;
    }

    public Klantcontact heeftTelefoononderwerp(Telefoononderwerp telefoononderwerp) {
        this.setHeeftTelefoononderwerp(telefoononderwerp);
        return this;
    }

    public Telefoontje getMondtuitinTelefoontje() {
        return this.mondtuitinTelefoontje;
    }

    public void setMondtuitinTelefoontje(Telefoontje telefoontje) {
        this.mondtuitinTelefoontje = telefoontje;
    }

    public Klantcontact mondtuitinTelefoontje(Telefoontje telefoontje) {
        this.setMondtuitinTelefoontje(telefoontje);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Klantcontact)) {
            return false;
        }
        return getId() != null && getId().equals(((Klantcontact) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Klantcontact{" +
            "id=" + getId() +
            ", eindtijd='" + getEindtijd() + "'" +
            ", kanaal='" + getKanaal() + "'" +
            ", notitie='" + getNotitie() + "'" +
            ", starttijd='" + getStarttijd() + "'" +
            ", tijdsduur='" + getTijdsduur() + "'" +
            ", toelichting='" + getToelichting() + "'" +
            ", wachttijdtotaal='" + getWachttijdtotaal() + "'" +
            "}";
    }
}
