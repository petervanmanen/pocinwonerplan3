package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Medewerker.
 */
@Entity
@Table(name = "medewerker")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Medewerker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "achternaam")
    private String achternaam;

    @Column(name = "datumindienst")
    private LocalDate datumindienst;

    @Column(name = "datumuitdienst")
    private String datumuitdienst;

    @Column(name = "emailadres")
    private String emailadres;

    @Column(name = "extern")
    private String extern;

    @Column(name = "functie")
    private String functie;

    @Column(name = "geslachtsaanduiding")
    private String geslachtsaanduiding;

    @Column(name = "medewerkeridentificatie")
    private String medewerkeridentificatie;

    @Column(name = "medewerkertoelichting")
    private String medewerkertoelichting;

    @Column(name = "roepnaam")
    private String roepnaam;

    @Size(max = 20)
    @Column(name = "telefoonnummer", length = 20)
    private String telefoonnummer;

    @Size(max = 20)
    @Column(name = "voorletters", length = 20)
    private String voorletters;

    @Size(max = 10)
    @Column(name = "voorvoegselachternaam", length = 10)
    private String voorvoegselachternaam;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ingevoerddoorMedewerker")
    @JsonIgnoreProperties(value = { "betreftWegdeels", "ingevoerddoorMedewerker", "gewijzigddoorMedewerker" }, allowSetters = true)
    private Set<Stremming> ingevoerddoorStremmings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "gewijzigddoorMedewerker")
    @JsonIgnoreProperties(value = { "betreftWegdeels", "ingevoerddoorMedewerker", "gewijzigddoorMedewerker" }, allowSetters = true)
    private Set<Stremming> gewijzigddoorStremmings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "voertuitMedewerker")
    @JsonIgnoreProperties(value = { "heeftMeldings", "voertuitMedewerker", "binnenAreaals" }, allowSetters = true)
    private Set<Schouwronde> voertuitSchouwrondes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "aanvragerMedewerker")
    @JsonIgnoreProperties(
        value = {
            "heeftZaak",
            "heeftRapportagemoments",
            "heeftTaaks",
            "valtbinnenSector",
            "behandelaarMedewerker",
            "verstrekkerRechtspersoon",
            "heeftKostenplaats",
            "heeftDocument",
            "betreftSubsidieaanvraag",
            "betreftSubsidiebeschikking",
            "aanvragerRechtspersoon",
            "aanvragerMedewerker",
        },
        allowSetters = true
    )
    private Set<Subsidie> aanvragerSubsidies = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "isverantwoordelijkevoorMedewerker")
    @JsonIgnoreProperties(
        value = {
            "heeftProducttype",
            "heeftHeffinggrondslags",
            "heeftStatustypes",
            "betreftProduct",
            "heeftBedrijfsprocestype",
            "isverantwoordelijkevoorMedewerker",
            "startDiensts",
            "isvanZaaks",
            "isaanleidingvoorFormuliersoorts",
        },
        allowSetters = true
    )
    private Set<Zaaktype> isverantwoordelijkevoorZaaktypes = new HashSet<>();

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
    private Leverancier geleverdviaLeverancier;

    @JsonIgnoreProperties(
        value = { "isMedewerker", "doetKlantbeoordelings", "oefentuitZaaks", "heeftklantcontactenKlantcontacts" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "isMedewerker")
    private Betrokkene isBetrokkene;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "uitgevoerddoorMedewerker")
    @JsonIgnoreProperties(
        value = { "komtvoortuitNaheffing", "uitgevoerddoorMedewerker", "betreftVoertuig", "betreftParkeervlak" },
        allowSetters = true
    )
    private Set<Parkeerscan> uitgevoerddoorParkeerscans = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "melderMedewerker")
    @JsonIgnoreProperties(
        value = {
            "hoofdcategorieCategorie",
            "subcategorieCategorie",
            "betreftContainertype",
            "betreftFractie",
            "betreftLocatie",
            "melderMedewerker",
            "uitvoerderLeverancier",
            "uitvoerderMedewerker",
            "betreftBeheerobjects",
            "heeftSchouwronde",
        },
        allowSetters = true
    )
    private Set<Melding> melderMeldings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "uitvoerderMedewerker")
    @JsonIgnoreProperties(
        value = {
            "hoofdcategorieCategorie",
            "subcategorieCategorie",
            "betreftContainertype",
            "betreftFractie",
            "betreftLocatie",
            "melderMedewerker",
            "uitvoerderLeverancier",
            "uitvoerderMedewerker",
            "betreftBeheerobjects",
            "heeftSchouwronde",
        },
        allowSetters = true
    )
    private Set<Melding> uitvoerderMeldings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "auteurMedewerker")
    @JsonIgnoreProperties(value = { "auteurMedewerker", "heeftnotitiesApplicatie" }, allowSetters = true)
    private Set<Notitie> auteurNotities = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "behandelaarMedewerker")
    @JsonIgnoreProperties(
        value = {
            "heeftZaak",
            "heeftRapportagemoments",
            "heeftTaaks",
            "valtbinnenSector",
            "behandelaarMedewerker",
            "verstrekkerRechtspersoon",
            "heeftKostenplaats",
            "heeftDocument",
            "betreftSubsidieaanvraag",
            "betreftSubsidiebeschikking",
            "aanvragerRechtspersoon",
            "aanvragerMedewerker",
        },
        allowSetters = true
    )
    private Set<Subsidie> behandelaarSubsidies = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "procesleiderMedewerker")
    @JsonIgnoreProperties(
        value = {
            "betreftZaak",
            "mondtuitGunning",
            "procesleiderMedewerker",
            "betreftKwalificaties",
            "betreftOfferteaanvraags",
            "mondtuitAankondigings",
            "betreftOffertes",
            "betreftInschrijvings",
        },
        allowSetters = true
    )
    private Set<Aanbesteding> procesleiderAanbestedings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "inhuurMedewerker")
    @JsonIgnoreProperties(
        value = { "betreftInschrijving", "betreftKandidaat", "betreftOfferte", "inhuurMedewerker", "mondtuitAanbesteding" },
        allowSetters = true
    )
    private Set<Gunning> inhuurGunnings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "metMedewerker")
    @JsonIgnoreProperties(
        value = { "mondtuitinKlantcontact", "heeftAfspraakstatus", "metMedewerker", "heeftbetrekkingopZaak" },
        allowSetters = true
    )
    private Set<Balieafspraak> metBalieafspraaks = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "isgevoerddoorMedewerker")
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
    private Set<Klantcontact> isgevoerddoorKlantcontacts = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "rollenMedewerkers")
    @JsonIgnoreProperties(
        value = {
            "heeftversiesVersies",
            "bevatGegevens",
            "heeftherkomstBatches",
            "heeftnotitiesNotities",
            "heeftleverancierLeverancier",
            "heeftdocumentenDocuments",
            "rollenMedewerkers",
        },
        allowSetters = true
    )
    private Set<Applicatie> rollenApplicaties = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "afhandelendmedewerkerMedewerkers")
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
    private Set<Zaak> afhandelendmedewerkerZaaks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Medewerker id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAchternaam() {
        return this.achternaam;
    }

    public Medewerker achternaam(String achternaam) {
        this.setAchternaam(achternaam);
        return this;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public LocalDate getDatumindienst() {
        return this.datumindienst;
    }

    public Medewerker datumindienst(LocalDate datumindienst) {
        this.setDatumindienst(datumindienst);
        return this;
    }

    public void setDatumindienst(LocalDate datumindienst) {
        this.datumindienst = datumindienst;
    }

    public String getDatumuitdienst() {
        return this.datumuitdienst;
    }

    public Medewerker datumuitdienst(String datumuitdienst) {
        this.setDatumuitdienst(datumuitdienst);
        return this;
    }

    public void setDatumuitdienst(String datumuitdienst) {
        this.datumuitdienst = datumuitdienst;
    }

    public String getEmailadres() {
        return this.emailadres;
    }

    public Medewerker emailadres(String emailadres) {
        this.setEmailadres(emailadres);
        return this;
    }

    public void setEmailadres(String emailadres) {
        this.emailadres = emailadres;
    }

    public String getExtern() {
        return this.extern;
    }

    public Medewerker extern(String extern) {
        this.setExtern(extern);
        return this;
    }

    public void setExtern(String extern) {
        this.extern = extern;
    }

    public String getFunctie() {
        return this.functie;
    }

    public Medewerker functie(String functie) {
        this.setFunctie(functie);
        return this;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }

    public String getGeslachtsaanduiding() {
        return this.geslachtsaanduiding;
    }

    public Medewerker geslachtsaanduiding(String geslachtsaanduiding) {
        this.setGeslachtsaanduiding(geslachtsaanduiding);
        return this;
    }

    public void setGeslachtsaanduiding(String geslachtsaanduiding) {
        this.geslachtsaanduiding = geslachtsaanduiding;
    }

    public String getMedewerkeridentificatie() {
        return this.medewerkeridentificatie;
    }

    public Medewerker medewerkeridentificatie(String medewerkeridentificatie) {
        this.setMedewerkeridentificatie(medewerkeridentificatie);
        return this;
    }

    public void setMedewerkeridentificatie(String medewerkeridentificatie) {
        this.medewerkeridentificatie = medewerkeridentificatie;
    }

    public String getMedewerkertoelichting() {
        return this.medewerkertoelichting;
    }

    public Medewerker medewerkertoelichting(String medewerkertoelichting) {
        this.setMedewerkertoelichting(medewerkertoelichting);
        return this;
    }

    public void setMedewerkertoelichting(String medewerkertoelichting) {
        this.medewerkertoelichting = medewerkertoelichting;
    }

    public String getRoepnaam() {
        return this.roepnaam;
    }

    public Medewerker roepnaam(String roepnaam) {
        this.setRoepnaam(roepnaam);
        return this;
    }

    public void setRoepnaam(String roepnaam) {
        this.roepnaam = roepnaam;
    }

    public String getTelefoonnummer() {
        return this.telefoonnummer;
    }

    public Medewerker telefoonnummer(String telefoonnummer) {
        this.setTelefoonnummer(telefoonnummer);
        return this;
    }

    public void setTelefoonnummer(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public String getVoorletters() {
        return this.voorletters;
    }

    public Medewerker voorletters(String voorletters) {
        this.setVoorletters(voorletters);
        return this;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getVoorvoegselachternaam() {
        return this.voorvoegselachternaam;
    }

    public Medewerker voorvoegselachternaam(String voorvoegselachternaam) {
        this.setVoorvoegselachternaam(voorvoegselachternaam);
        return this;
    }

    public void setVoorvoegselachternaam(String voorvoegselachternaam) {
        this.voorvoegselachternaam = voorvoegselachternaam;
    }

    public Set<Stremming> getIngevoerddoorStremmings() {
        return this.ingevoerddoorStremmings;
    }

    public void setIngevoerddoorStremmings(Set<Stremming> stremmings) {
        if (this.ingevoerddoorStremmings != null) {
            this.ingevoerddoorStremmings.forEach(i -> i.setIngevoerddoorMedewerker(null));
        }
        if (stremmings != null) {
            stremmings.forEach(i -> i.setIngevoerddoorMedewerker(this));
        }
        this.ingevoerddoorStremmings = stremmings;
    }

    public Medewerker ingevoerddoorStremmings(Set<Stremming> stremmings) {
        this.setIngevoerddoorStremmings(stremmings);
        return this;
    }

    public Medewerker addIngevoerddoorStremming(Stremming stremming) {
        this.ingevoerddoorStremmings.add(stremming);
        stremming.setIngevoerddoorMedewerker(this);
        return this;
    }

    public Medewerker removeIngevoerddoorStremming(Stremming stremming) {
        this.ingevoerddoorStremmings.remove(stremming);
        stremming.setIngevoerddoorMedewerker(null);
        return this;
    }

    public Set<Stremming> getGewijzigddoorStremmings() {
        return this.gewijzigddoorStremmings;
    }

    public void setGewijzigddoorStremmings(Set<Stremming> stremmings) {
        if (this.gewijzigddoorStremmings != null) {
            this.gewijzigddoorStremmings.forEach(i -> i.setGewijzigddoorMedewerker(null));
        }
        if (stremmings != null) {
            stremmings.forEach(i -> i.setGewijzigddoorMedewerker(this));
        }
        this.gewijzigddoorStremmings = stremmings;
    }

    public Medewerker gewijzigddoorStremmings(Set<Stremming> stremmings) {
        this.setGewijzigddoorStremmings(stremmings);
        return this;
    }

    public Medewerker addGewijzigddoorStremming(Stremming stremming) {
        this.gewijzigddoorStremmings.add(stremming);
        stremming.setGewijzigddoorMedewerker(this);
        return this;
    }

    public Medewerker removeGewijzigddoorStremming(Stremming stremming) {
        this.gewijzigddoorStremmings.remove(stremming);
        stremming.setGewijzigddoorMedewerker(null);
        return this;
    }

    public Set<Schouwronde> getVoertuitSchouwrondes() {
        return this.voertuitSchouwrondes;
    }

    public void setVoertuitSchouwrondes(Set<Schouwronde> schouwrondes) {
        if (this.voertuitSchouwrondes != null) {
            this.voertuitSchouwrondes.forEach(i -> i.setVoertuitMedewerker(null));
        }
        if (schouwrondes != null) {
            schouwrondes.forEach(i -> i.setVoertuitMedewerker(this));
        }
        this.voertuitSchouwrondes = schouwrondes;
    }

    public Medewerker voertuitSchouwrondes(Set<Schouwronde> schouwrondes) {
        this.setVoertuitSchouwrondes(schouwrondes);
        return this;
    }

    public Medewerker addVoertuitSchouwronde(Schouwronde schouwronde) {
        this.voertuitSchouwrondes.add(schouwronde);
        schouwronde.setVoertuitMedewerker(this);
        return this;
    }

    public Medewerker removeVoertuitSchouwronde(Schouwronde schouwronde) {
        this.voertuitSchouwrondes.remove(schouwronde);
        schouwronde.setVoertuitMedewerker(null);
        return this;
    }

    public Set<Subsidie> getAanvragerSubsidies() {
        return this.aanvragerSubsidies;
    }

    public void setAanvragerSubsidies(Set<Subsidie> subsidies) {
        if (this.aanvragerSubsidies != null) {
            this.aanvragerSubsidies.forEach(i -> i.setAanvragerMedewerker(null));
        }
        if (subsidies != null) {
            subsidies.forEach(i -> i.setAanvragerMedewerker(this));
        }
        this.aanvragerSubsidies = subsidies;
    }

    public Medewerker aanvragerSubsidies(Set<Subsidie> subsidies) {
        this.setAanvragerSubsidies(subsidies);
        return this;
    }

    public Medewerker addAanvragerSubsidie(Subsidie subsidie) {
        this.aanvragerSubsidies.add(subsidie);
        subsidie.setAanvragerMedewerker(this);
        return this;
    }

    public Medewerker removeAanvragerSubsidie(Subsidie subsidie) {
        this.aanvragerSubsidies.remove(subsidie);
        subsidie.setAanvragerMedewerker(null);
        return this;
    }

    public Set<Zaaktype> getIsverantwoordelijkevoorZaaktypes() {
        return this.isverantwoordelijkevoorZaaktypes;
    }

    public void setIsverantwoordelijkevoorZaaktypes(Set<Zaaktype> zaaktypes) {
        if (this.isverantwoordelijkevoorZaaktypes != null) {
            this.isverantwoordelijkevoorZaaktypes.forEach(i -> i.setIsverantwoordelijkevoorMedewerker(null));
        }
        if (zaaktypes != null) {
            zaaktypes.forEach(i -> i.setIsverantwoordelijkevoorMedewerker(this));
        }
        this.isverantwoordelijkevoorZaaktypes = zaaktypes;
    }

    public Medewerker isverantwoordelijkevoorZaaktypes(Set<Zaaktype> zaaktypes) {
        this.setIsverantwoordelijkevoorZaaktypes(zaaktypes);
        return this;
    }

    public Medewerker addIsverantwoordelijkevoorZaaktype(Zaaktype zaaktype) {
        this.isverantwoordelijkevoorZaaktypes.add(zaaktype);
        zaaktype.setIsverantwoordelijkevoorMedewerker(this);
        return this;
    }

    public Medewerker removeIsverantwoordelijkevoorZaaktype(Zaaktype zaaktype) {
        this.isverantwoordelijkevoorZaaktypes.remove(zaaktype);
        zaaktype.setIsverantwoordelijkevoorMedewerker(null);
        return this;
    }

    public Leverancier getGeleverdviaLeverancier() {
        return this.geleverdviaLeverancier;
    }

    public void setGeleverdviaLeverancier(Leverancier leverancier) {
        this.geleverdviaLeverancier = leverancier;
    }

    public Medewerker geleverdviaLeverancier(Leverancier leverancier) {
        this.setGeleverdviaLeverancier(leverancier);
        return this;
    }

    public Betrokkene getIsBetrokkene() {
        return this.isBetrokkene;
    }

    public void setIsBetrokkene(Betrokkene betrokkene) {
        if (this.isBetrokkene != null) {
            this.isBetrokkene.setIsMedewerker(null);
        }
        if (betrokkene != null) {
            betrokkene.setIsMedewerker(this);
        }
        this.isBetrokkene = betrokkene;
    }

    public Medewerker isBetrokkene(Betrokkene betrokkene) {
        this.setIsBetrokkene(betrokkene);
        return this;
    }

    public Set<Parkeerscan> getUitgevoerddoorParkeerscans() {
        return this.uitgevoerddoorParkeerscans;
    }

    public void setUitgevoerddoorParkeerscans(Set<Parkeerscan> parkeerscans) {
        if (this.uitgevoerddoorParkeerscans != null) {
            this.uitgevoerddoorParkeerscans.forEach(i -> i.setUitgevoerddoorMedewerker(null));
        }
        if (parkeerscans != null) {
            parkeerscans.forEach(i -> i.setUitgevoerddoorMedewerker(this));
        }
        this.uitgevoerddoorParkeerscans = parkeerscans;
    }

    public Medewerker uitgevoerddoorParkeerscans(Set<Parkeerscan> parkeerscans) {
        this.setUitgevoerddoorParkeerscans(parkeerscans);
        return this;
    }

    public Medewerker addUitgevoerddoorParkeerscan(Parkeerscan parkeerscan) {
        this.uitgevoerddoorParkeerscans.add(parkeerscan);
        parkeerscan.setUitgevoerddoorMedewerker(this);
        return this;
    }

    public Medewerker removeUitgevoerddoorParkeerscan(Parkeerscan parkeerscan) {
        this.uitgevoerddoorParkeerscans.remove(parkeerscan);
        parkeerscan.setUitgevoerddoorMedewerker(null);
        return this;
    }

    public Set<Melding> getMelderMeldings() {
        return this.melderMeldings;
    }

    public void setMelderMeldings(Set<Melding> meldings) {
        if (this.melderMeldings != null) {
            this.melderMeldings.forEach(i -> i.setMelderMedewerker(null));
        }
        if (meldings != null) {
            meldings.forEach(i -> i.setMelderMedewerker(this));
        }
        this.melderMeldings = meldings;
    }

    public Medewerker melderMeldings(Set<Melding> meldings) {
        this.setMelderMeldings(meldings);
        return this;
    }

    public Medewerker addMelderMelding(Melding melding) {
        this.melderMeldings.add(melding);
        melding.setMelderMedewerker(this);
        return this;
    }

    public Medewerker removeMelderMelding(Melding melding) {
        this.melderMeldings.remove(melding);
        melding.setMelderMedewerker(null);
        return this;
    }

    public Set<Melding> getUitvoerderMeldings() {
        return this.uitvoerderMeldings;
    }

    public void setUitvoerderMeldings(Set<Melding> meldings) {
        if (this.uitvoerderMeldings != null) {
            this.uitvoerderMeldings.forEach(i -> i.setUitvoerderMedewerker(null));
        }
        if (meldings != null) {
            meldings.forEach(i -> i.setUitvoerderMedewerker(this));
        }
        this.uitvoerderMeldings = meldings;
    }

    public Medewerker uitvoerderMeldings(Set<Melding> meldings) {
        this.setUitvoerderMeldings(meldings);
        return this;
    }

    public Medewerker addUitvoerderMelding(Melding melding) {
        this.uitvoerderMeldings.add(melding);
        melding.setUitvoerderMedewerker(this);
        return this;
    }

    public Medewerker removeUitvoerderMelding(Melding melding) {
        this.uitvoerderMeldings.remove(melding);
        melding.setUitvoerderMedewerker(null);
        return this;
    }

    public Set<Notitie> getAuteurNotities() {
        return this.auteurNotities;
    }

    public void setAuteurNotities(Set<Notitie> notities) {
        if (this.auteurNotities != null) {
            this.auteurNotities.forEach(i -> i.setAuteurMedewerker(null));
        }
        if (notities != null) {
            notities.forEach(i -> i.setAuteurMedewerker(this));
        }
        this.auteurNotities = notities;
    }

    public Medewerker auteurNotities(Set<Notitie> notities) {
        this.setAuteurNotities(notities);
        return this;
    }

    public Medewerker addAuteurNotitie(Notitie notitie) {
        this.auteurNotities.add(notitie);
        notitie.setAuteurMedewerker(this);
        return this;
    }

    public Medewerker removeAuteurNotitie(Notitie notitie) {
        this.auteurNotities.remove(notitie);
        notitie.setAuteurMedewerker(null);
        return this;
    }

    public Set<Subsidie> getBehandelaarSubsidies() {
        return this.behandelaarSubsidies;
    }

    public void setBehandelaarSubsidies(Set<Subsidie> subsidies) {
        if (this.behandelaarSubsidies != null) {
            this.behandelaarSubsidies.forEach(i -> i.setBehandelaarMedewerker(null));
        }
        if (subsidies != null) {
            subsidies.forEach(i -> i.setBehandelaarMedewerker(this));
        }
        this.behandelaarSubsidies = subsidies;
    }

    public Medewerker behandelaarSubsidies(Set<Subsidie> subsidies) {
        this.setBehandelaarSubsidies(subsidies);
        return this;
    }

    public Medewerker addBehandelaarSubsidie(Subsidie subsidie) {
        this.behandelaarSubsidies.add(subsidie);
        subsidie.setBehandelaarMedewerker(this);
        return this;
    }

    public Medewerker removeBehandelaarSubsidie(Subsidie subsidie) {
        this.behandelaarSubsidies.remove(subsidie);
        subsidie.setBehandelaarMedewerker(null);
        return this;
    }

    public Set<Aanbesteding> getProcesleiderAanbestedings() {
        return this.procesleiderAanbestedings;
    }

    public void setProcesleiderAanbestedings(Set<Aanbesteding> aanbestedings) {
        if (this.procesleiderAanbestedings != null) {
            this.procesleiderAanbestedings.forEach(i -> i.setProcesleiderMedewerker(null));
        }
        if (aanbestedings != null) {
            aanbestedings.forEach(i -> i.setProcesleiderMedewerker(this));
        }
        this.procesleiderAanbestedings = aanbestedings;
    }

    public Medewerker procesleiderAanbestedings(Set<Aanbesteding> aanbestedings) {
        this.setProcesleiderAanbestedings(aanbestedings);
        return this;
    }

    public Medewerker addProcesleiderAanbesteding(Aanbesteding aanbesteding) {
        this.procesleiderAanbestedings.add(aanbesteding);
        aanbesteding.setProcesleiderMedewerker(this);
        return this;
    }

    public Medewerker removeProcesleiderAanbesteding(Aanbesteding aanbesteding) {
        this.procesleiderAanbestedings.remove(aanbesteding);
        aanbesteding.setProcesleiderMedewerker(null);
        return this;
    }

    public Set<Gunning> getInhuurGunnings() {
        return this.inhuurGunnings;
    }

    public void setInhuurGunnings(Set<Gunning> gunnings) {
        if (this.inhuurGunnings != null) {
            this.inhuurGunnings.forEach(i -> i.setInhuurMedewerker(null));
        }
        if (gunnings != null) {
            gunnings.forEach(i -> i.setInhuurMedewerker(this));
        }
        this.inhuurGunnings = gunnings;
    }

    public Medewerker inhuurGunnings(Set<Gunning> gunnings) {
        this.setInhuurGunnings(gunnings);
        return this;
    }

    public Medewerker addInhuurGunning(Gunning gunning) {
        this.inhuurGunnings.add(gunning);
        gunning.setInhuurMedewerker(this);
        return this;
    }

    public Medewerker removeInhuurGunning(Gunning gunning) {
        this.inhuurGunnings.remove(gunning);
        gunning.setInhuurMedewerker(null);
        return this;
    }

    public Set<Balieafspraak> getMetBalieafspraaks() {
        return this.metBalieafspraaks;
    }

    public void setMetBalieafspraaks(Set<Balieafspraak> balieafspraaks) {
        if (this.metBalieafspraaks != null) {
            this.metBalieafspraaks.forEach(i -> i.setMetMedewerker(null));
        }
        if (balieafspraaks != null) {
            balieafspraaks.forEach(i -> i.setMetMedewerker(this));
        }
        this.metBalieafspraaks = balieafspraaks;
    }

    public Medewerker metBalieafspraaks(Set<Balieafspraak> balieafspraaks) {
        this.setMetBalieafspraaks(balieafspraaks);
        return this;
    }

    public Medewerker addMetBalieafspraak(Balieafspraak balieafspraak) {
        this.metBalieafspraaks.add(balieafspraak);
        balieafspraak.setMetMedewerker(this);
        return this;
    }

    public Medewerker removeMetBalieafspraak(Balieafspraak balieafspraak) {
        this.metBalieafspraaks.remove(balieafspraak);
        balieafspraak.setMetMedewerker(null);
        return this;
    }

    public Set<Klantcontact> getIsgevoerddoorKlantcontacts() {
        return this.isgevoerddoorKlantcontacts;
    }

    public void setIsgevoerddoorKlantcontacts(Set<Klantcontact> klantcontacts) {
        if (this.isgevoerddoorKlantcontacts != null) {
            this.isgevoerddoorKlantcontacts.forEach(i -> i.setIsgevoerddoorMedewerker(null));
        }
        if (klantcontacts != null) {
            klantcontacts.forEach(i -> i.setIsgevoerddoorMedewerker(this));
        }
        this.isgevoerddoorKlantcontacts = klantcontacts;
    }

    public Medewerker isgevoerddoorKlantcontacts(Set<Klantcontact> klantcontacts) {
        this.setIsgevoerddoorKlantcontacts(klantcontacts);
        return this;
    }

    public Medewerker addIsgevoerddoorKlantcontact(Klantcontact klantcontact) {
        this.isgevoerddoorKlantcontacts.add(klantcontact);
        klantcontact.setIsgevoerddoorMedewerker(this);
        return this;
    }

    public Medewerker removeIsgevoerddoorKlantcontact(Klantcontact klantcontact) {
        this.isgevoerddoorKlantcontacts.remove(klantcontact);
        klantcontact.setIsgevoerddoorMedewerker(null);
        return this;
    }

    public Set<Applicatie> getRollenApplicaties() {
        return this.rollenApplicaties;
    }

    public void setRollenApplicaties(Set<Applicatie> applicaties) {
        if (this.rollenApplicaties != null) {
            this.rollenApplicaties.forEach(i -> i.removeRollenMedewerker(this));
        }
        if (applicaties != null) {
            applicaties.forEach(i -> i.addRollenMedewerker(this));
        }
        this.rollenApplicaties = applicaties;
    }

    public Medewerker rollenApplicaties(Set<Applicatie> applicaties) {
        this.setRollenApplicaties(applicaties);
        return this;
    }

    public Medewerker addRollenApplicatie(Applicatie applicatie) {
        this.rollenApplicaties.add(applicatie);
        applicatie.getRollenMedewerkers().add(this);
        return this;
    }

    public Medewerker removeRollenApplicatie(Applicatie applicatie) {
        this.rollenApplicaties.remove(applicatie);
        applicatie.getRollenMedewerkers().remove(this);
        return this;
    }

    public Set<Zaak> getAfhandelendmedewerkerZaaks() {
        return this.afhandelendmedewerkerZaaks;
    }

    public void setAfhandelendmedewerkerZaaks(Set<Zaak> zaaks) {
        if (this.afhandelendmedewerkerZaaks != null) {
            this.afhandelendmedewerkerZaaks.forEach(i -> i.removeAfhandelendmedewerkerMedewerker(this));
        }
        if (zaaks != null) {
            zaaks.forEach(i -> i.addAfhandelendmedewerkerMedewerker(this));
        }
        this.afhandelendmedewerkerZaaks = zaaks;
    }

    public Medewerker afhandelendmedewerkerZaaks(Set<Zaak> zaaks) {
        this.setAfhandelendmedewerkerZaaks(zaaks);
        return this;
    }

    public Medewerker addAfhandelendmedewerkerZaak(Zaak zaak) {
        this.afhandelendmedewerkerZaaks.add(zaak);
        zaak.getAfhandelendmedewerkerMedewerkers().add(this);
        return this;
    }

    public Medewerker removeAfhandelendmedewerkerZaak(Zaak zaak) {
        this.afhandelendmedewerkerZaaks.remove(zaak);
        zaak.getAfhandelendmedewerkerMedewerkers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Medewerker)) {
            return false;
        }
        return getId() != null && getId().equals(((Medewerker) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Medewerker{" +
            "id=" + getId() +
            ", achternaam='" + getAchternaam() + "'" +
            ", datumindienst='" + getDatumindienst() + "'" +
            ", datumuitdienst='" + getDatumuitdienst() + "'" +
            ", emailadres='" + getEmailadres() + "'" +
            ", extern='" + getExtern() + "'" +
            ", functie='" + getFunctie() + "'" +
            ", geslachtsaanduiding='" + getGeslachtsaanduiding() + "'" +
            ", medewerkeridentificatie='" + getMedewerkeridentificatie() + "'" +
            ", medewerkertoelichting='" + getMedewerkertoelichting() + "'" +
            ", roepnaam='" + getRoepnaam() + "'" +
            ", telefoonnummer='" + getTelefoonnummer() + "'" +
            ", voorletters='" + getVoorletters() + "'" +
            ", voorvoegselachternaam='" + getVoorvoegselachternaam() + "'" +
            "}";
    }
}
