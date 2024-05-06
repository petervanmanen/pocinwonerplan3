package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Zaak.
 */
@Entity
@Table(name = "zaak")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Zaak implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "empty")
    private String empty;

    @Column(name = "archiefnominatie")
    private String archiefnominatie;

    @Column(name = "datumeinde")
    private String datumeinde;

    @Column(name = "datumeindegepland")
    private String datumeindegepland;

    @Column(name = "datumeindeuiterlijkeafdoening")
    private String datumeindeuiterlijkeafdoening;

    @Column(name = "datumlaatstebetaling")
    private String datumlaatstebetaling;

    @Column(name = "datumpublicatie")
    private String datumpublicatie;

    @Column(name = "datumregistratie")
    private String datumregistratie;

    @Column(name = "datumstart")
    private String datumstart;

    @Column(name = "datumvernietigingdossier")
    private String datumvernietigingdossier;

    @Column(name = "document")
    private String document;

    @Column(name = "duurverlenging")
    private String duurverlenging;

    @Column(name = "indicatiebetaling")
    private String indicatiebetaling;

    @Column(name = "indicatiedeelzaken")
    private String indicatiedeelzaken;

    @Column(name = "indicatieopschorting")
    private String indicatieopschorting;

    @Size(max = 100)
    @Column(name = "leges", length = 100)
    private String leges;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "omschrijvingresultaat")
    private String omschrijvingresultaat;

    @Column(name = "redenopschorting")
    private String redenopschorting;

    @Column(name = "redenverlenging")
    private String redenverlenging;

    @Column(name = "toelichting")
    private String toelichting;

    @Column(name = "toelichtingresultaat")
    private String toelichtingresultaat;

    @Column(name = "vertrouwelijkheid")
    private String vertrouwelijkheid;

    @Column(name = "zaakidentificatie")
    private String zaakidentificatie;

    @Column(name = "zaakniveau")
    private String zaakniveau;

    @JsonIgnoreProperties(value = { "heeftproductZaak", "heeftZaaktype", "heeftBedrijfsprocestype" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Producttype heeftproductProducttype;

    @JsonIgnoreProperties(value = { "heeftKlantbeoordelingredens", "heeftZaak", "doetBetrokkene" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Klantbeoordeling heeftKlantbeoordeling;

    @JsonIgnoreProperties(value = { "heeftgrondslagHeffinggrondslag", "heeftZaak" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Heffing heeftHeffing;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftbetalingZaak")
    @JsonIgnoreProperties(
        value = { "komtvooropBankafschriftregel", "vanBankrekening", "naarBankrekening", "heeftbetalingZaak" },
        allowSetters = true
    )
    private Set<Betaling> heeftbetalingBetalings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftZaak")
    @JsonIgnoreProperties(value = { "isvanStatustype", "heeftZaak" }, allowSetters = true)
    private Set<Status> heeftStatuses = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftuitstroomredenUitstroomreden",
            "heeftresultaatResultaat",
            "heeftArcheologiebesluits",
            "heeftBorings",
            "heeftPuts",
            "heeftProjectlocaties",
            "heeftProjectactiviteits",
            "soortprojectProjectsoort",
            "wordtbegrensddoorLocaties",
            "heeftKostenplaats",
            "hoortbijVindplaats",
            "heeftprojectTraject",
            "betreftZaaks",
        },
        allowSetters = true
    )
    private Project betreftProject;

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Zaaktype isvanZaaktype;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_zaak__kent_document",
        joinColumns = @JoinColumn(name = "zaak_id"),
        inverseJoinColumns = @JoinColumn(name = "kent_document_id")
    )
    @JsonIgnoreProperties(
        value = {
            "heeftkenmerkIdentificatiekenmerk",
            "isvanDocumenttype",
            "isvastgelegdinVerkeersbesluit",
            "isvastgelegdinBesluit",
            "inspectierapportBinnenlocatie",
            "heeftRapportagemoment",
            "heeftSubsidies",
            "heeftdocumentenApplicaties",
            "kanvastgelegdzijnalsBesluits",
            "kentZaaks",
        },
        allowSetters = true
    )
    private Set<Document> kentDocuments = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_zaak__afhandelendmedewerker_medewerker",
        joinColumns = @JoinColumn(name = "zaak_id"),
        inverseJoinColumns = @JoinColumn(name = "afhandelendmedewerker_medewerker_id")
    )
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
    private Set<Medewerker> afhandelendmedewerkerMedewerkers = new HashSet<>();

    @JsonIgnoreProperties(
        value = {
            "leidttotZaak",
            "bevatSpecificaties",
            "betrefteerderverzoekVerzoek",
            "betreftProjectactiviteits",
            "betreftProjectlocaties",
            "betreftActiviteits",
            "betreftLocaties",
            "heeftalsverantwoordelijkeInitiatiefnemer",
            "betrefteerderverzoekVerzoek2s",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "leidttotZaak")
    private Verzoek leidttotVerzoek;

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
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "heeftZaak")
    private Subsidie heeftSubsidie;

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
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "betreftZaak")
    private Aanbesteding betreftAanbesteding;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftbetrekkingopZaak")
    @JsonIgnoreProperties(
        value = { "mondtuitinKlantcontact", "heeftAfspraakstatus", "metMedewerker", "heeftbetrekkingopZaak" },
        allowSetters = true
    )
    private Set<Balieafspraak> heeftbetrekkingopBalieafspraaks = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "isuitkomstvanZaak")
    @JsonIgnoreProperties(
        value = { "isvastgelegdinDocument", "isuitkomstvanZaak", "isvanBesluittype", "kanvastgelegdzijnalsDocuments" },
        allowSetters = true
    )
    private Set<Besluit> isuitkomstvanBesluits = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftbetrekkingopZaak")
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
    private Set<Klantcontact> heeftbetrekkingopKlantcontacts = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "heeftZaaks")
    @JsonIgnoreProperties(value = { "heeftZaaks" }, allowSetters = true)
    private Set<Grondslag> heeftGrondslags = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "uitgevoerdbinnenZaaks")
    @JsonIgnoreProperties(value = { "uitgevoerdbinnenZaaks" }, allowSetters = true)
    private Set<Bedrijfsproces> uitgevoerdbinnenBedrijfsproces = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "oefentuitZaaks")
    @JsonIgnoreProperties(
        value = { "isMedewerker", "doetKlantbeoordelings", "oefentuitZaaks", "heeftklantcontactenKlantcontacts" },
        allowSetters = true
    )
    private Set<Betrokkene> oefentuitBetrokkenes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Zaak id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpty() {
        return this.empty;
    }

    public Zaak empty(String empty) {
        this.setEmpty(empty);
        return this;
    }

    public void setEmpty(String empty) {
        this.empty = empty;
    }

    public String getArchiefnominatie() {
        return this.archiefnominatie;
    }

    public Zaak archiefnominatie(String archiefnominatie) {
        this.setArchiefnominatie(archiefnominatie);
        return this;
    }

    public void setArchiefnominatie(String archiefnominatie) {
        this.archiefnominatie = archiefnominatie;
    }

    public String getDatumeinde() {
        return this.datumeinde;
    }

    public Zaak datumeinde(String datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(String datumeinde) {
        this.datumeinde = datumeinde;
    }

    public String getDatumeindegepland() {
        return this.datumeindegepland;
    }

    public Zaak datumeindegepland(String datumeindegepland) {
        this.setDatumeindegepland(datumeindegepland);
        return this;
    }

    public void setDatumeindegepland(String datumeindegepland) {
        this.datumeindegepland = datumeindegepland;
    }

    public String getDatumeindeuiterlijkeafdoening() {
        return this.datumeindeuiterlijkeafdoening;
    }

    public Zaak datumeindeuiterlijkeafdoening(String datumeindeuiterlijkeafdoening) {
        this.setDatumeindeuiterlijkeafdoening(datumeindeuiterlijkeafdoening);
        return this;
    }

    public void setDatumeindeuiterlijkeafdoening(String datumeindeuiterlijkeafdoening) {
        this.datumeindeuiterlijkeafdoening = datumeindeuiterlijkeafdoening;
    }

    public String getDatumlaatstebetaling() {
        return this.datumlaatstebetaling;
    }

    public Zaak datumlaatstebetaling(String datumlaatstebetaling) {
        this.setDatumlaatstebetaling(datumlaatstebetaling);
        return this;
    }

    public void setDatumlaatstebetaling(String datumlaatstebetaling) {
        this.datumlaatstebetaling = datumlaatstebetaling;
    }

    public String getDatumpublicatie() {
        return this.datumpublicatie;
    }

    public Zaak datumpublicatie(String datumpublicatie) {
        this.setDatumpublicatie(datumpublicatie);
        return this;
    }

    public void setDatumpublicatie(String datumpublicatie) {
        this.datumpublicatie = datumpublicatie;
    }

    public String getDatumregistratie() {
        return this.datumregistratie;
    }

    public Zaak datumregistratie(String datumregistratie) {
        this.setDatumregistratie(datumregistratie);
        return this;
    }

    public void setDatumregistratie(String datumregistratie) {
        this.datumregistratie = datumregistratie;
    }

    public String getDatumstart() {
        return this.datumstart;
    }

    public Zaak datumstart(String datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(String datumstart) {
        this.datumstart = datumstart;
    }

    public String getDatumvernietigingdossier() {
        return this.datumvernietigingdossier;
    }

    public Zaak datumvernietigingdossier(String datumvernietigingdossier) {
        this.setDatumvernietigingdossier(datumvernietigingdossier);
        return this;
    }

    public void setDatumvernietigingdossier(String datumvernietigingdossier) {
        this.datumvernietigingdossier = datumvernietigingdossier;
    }

    public String getDocument() {
        return this.document;
    }

    public Zaak document(String document) {
        this.setDocument(document);
        return this;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getDuurverlenging() {
        return this.duurverlenging;
    }

    public Zaak duurverlenging(String duurverlenging) {
        this.setDuurverlenging(duurverlenging);
        return this;
    }

    public void setDuurverlenging(String duurverlenging) {
        this.duurverlenging = duurverlenging;
    }

    public String getIndicatiebetaling() {
        return this.indicatiebetaling;
    }

    public Zaak indicatiebetaling(String indicatiebetaling) {
        this.setIndicatiebetaling(indicatiebetaling);
        return this;
    }

    public void setIndicatiebetaling(String indicatiebetaling) {
        this.indicatiebetaling = indicatiebetaling;
    }

    public String getIndicatiedeelzaken() {
        return this.indicatiedeelzaken;
    }

    public Zaak indicatiedeelzaken(String indicatiedeelzaken) {
        this.setIndicatiedeelzaken(indicatiedeelzaken);
        return this;
    }

    public void setIndicatiedeelzaken(String indicatiedeelzaken) {
        this.indicatiedeelzaken = indicatiedeelzaken;
    }

    public String getIndicatieopschorting() {
        return this.indicatieopschorting;
    }

    public Zaak indicatieopschorting(String indicatieopschorting) {
        this.setIndicatieopschorting(indicatieopschorting);
        return this;
    }

    public void setIndicatieopschorting(String indicatieopschorting) {
        this.indicatieopschorting = indicatieopschorting;
    }

    public String getLeges() {
        return this.leges;
    }

    public Zaak leges(String leges) {
        this.setLeges(leges);
        return this;
    }

    public void setLeges(String leges) {
        this.leges = leges;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Zaak omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOmschrijvingresultaat() {
        return this.omschrijvingresultaat;
    }

    public Zaak omschrijvingresultaat(String omschrijvingresultaat) {
        this.setOmschrijvingresultaat(omschrijvingresultaat);
        return this;
    }

    public void setOmschrijvingresultaat(String omschrijvingresultaat) {
        this.omschrijvingresultaat = omschrijvingresultaat;
    }

    public String getRedenopschorting() {
        return this.redenopschorting;
    }

    public Zaak redenopschorting(String redenopschorting) {
        this.setRedenopschorting(redenopschorting);
        return this;
    }

    public void setRedenopschorting(String redenopschorting) {
        this.redenopschorting = redenopschorting;
    }

    public String getRedenverlenging() {
        return this.redenverlenging;
    }

    public Zaak redenverlenging(String redenverlenging) {
        this.setRedenverlenging(redenverlenging);
        return this;
    }

    public void setRedenverlenging(String redenverlenging) {
        this.redenverlenging = redenverlenging;
    }

    public String getToelichting() {
        return this.toelichting;
    }

    public Zaak toelichting(String toelichting) {
        this.setToelichting(toelichting);
        return this;
    }

    public void setToelichting(String toelichting) {
        this.toelichting = toelichting;
    }

    public String getToelichtingresultaat() {
        return this.toelichtingresultaat;
    }

    public Zaak toelichtingresultaat(String toelichtingresultaat) {
        this.setToelichtingresultaat(toelichtingresultaat);
        return this;
    }

    public void setToelichtingresultaat(String toelichtingresultaat) {
        this.toelichtingresultaat = toelichtingresultaat;
    }

    public String getVertrouwelijkheid() {
        return this.vertrouwelijkheid;
    }

    public Zaak vertrouwelijkheid(String vertrouwelijkheid) {
        this.setVertrouwelijkheid(vertrouwelijkheid);
        return this;
    }

    public void setVertrouwelijkheid(String vertrouwelijkheid) {
        this.vertrouwelijkheid = vertrouwelijkheid;
    }

    public String getZaakidentificatie() {
        return this.zaakidentificatie;
    }

    public Zaak zaakidentificatie(String zaakidentificatie) {
        this.setZaakidentificatie(zaakidentificatie);
        return this;
    }

    public void setZaakidentificatie(String zaakidentificatie) {
        this.zaakidentificatie = zaakidentificatie;
    }

    public String getZaakniveau() {
        return this.zaakniveau;
    }

    public Zaak zaakniveau(String zaakniveau) {
        this.setZaakniveau(zaakniveau);
        return this;
    }

    public void setZaakniveau(String zaakniveau) {
        this.zaakniveau = zaakniveau;
    }

    public Producttype getHeeftproductProducttype() {
        return this.heeftproductProducttype;
    }

    public void setHeeftproductProducttype(Producttype producttype) {
        this.heeftproductProducttype = producttype;
    }

    public Zaak heeftproductProducttype(Producttype producttype) {
        this.setHeeftproductProducttype(producttype);
        return this;
    }

    public Klantbeoordeling getHeeftKlantbeoordeling() {
        return this.heeftKlantbeoordeling;
    }

    public void setHeeftKlantbeoordeling(Klantbeoordeling klantbeoordeling) {
        this.heeftKlantbeoordeling = klantbeoordeling;
    }

    public Zaak heeftKlantbeoordeling(Klantbeoordeling klantbeoordeling) {
        this.setHeeftKlantbeoordeling(klantbeoordeling);
        return this;
    }

    public Heffing getHeeftHeffing() {
        return this.heeftHeffing;
    }

    public void setHeeftHeffing(Heffing heffing) {
        this.heeftHeffing = heffing;
    }

    public Zaak heeftHeffing(Heffing heffing) {
        this.setHeeftHeffing(heffing);
        return this;
    }

    public Set<Betaling> getHeeftbetalingBetalings() {
        return this.heeftbetalingBetalings;
    }

    public void setHeeftbetalingBetalings(Set<Betaling> betalings) {
        if (this.heeftbetalingBetalings != null) {
            this.heeftbetalingBetalings.forEach(i -> i.setHeeftbetalingZaak(null));
        }
        if (betalings != null) {
            betalings.forEach(i -> i.setHeeftbetalingZaak(this));
        }
        this.heeftbetalingBetalings = betalings;
    }

    public Zaak heeftbetalingBetalings(Set<Betaling> betalings) {
        this.setHeeftbetalingBetalings(betalings);
        return this;
    }

    public Zaak addHeeftbetalingBetaling(Betaling betaling) {
        this.heeftbetalingBetalings.add(betaling);
        betaling.setHeeftbetalingZaak(this);
        return this;
    }

    public Zaak removeHeeftbetalingBetaling(Betaling betaling) {
        this.heeftbetalingBetalings.remove(betaling);
        betaling.setHeeftbetalingZaak(null);
        return this;
    }

    public Set<Status> getHeeftStatuses() {
        return this.heeftStatuses;
    }

    public void setHeeftStatuses(Set<Status> statuses) {
        if (this.heeftStatuses != null) {
            this.heeftStatuses.forEach(i -> i.setHeeftZaak(null));
        }
        if (statuses != null) {
            statuses.forEach(i -> i.setHeeftZaak(this));
        }
        this.heeftStatuses = statuses;
    }

    public Zaak heeftStatuses(Set<Status> statuses) {
        this.setHeeftStatuses(statuses);
        return this;
    }

    public Zaak addHeeftStatus(Status status) {
        this.heeftStatuses.add(status);
        status.setHeeftZaak(this);
        return this;
    }

    public Zaak removeHeeftStatus(Status status) {
        this.heeftStatuses.remove(status);
        status.setHeeftZaak(null);
        return this;
    }

    public Project getBetreftProject() {
        return this.betreftProject;
    }

    public void setBetreftProject(Project project) {
        this.betreftProject = project;
    }

    public Zaak betreftProject(Project project) {
        this.setBetreftProject(project);
        return this;
    }

    public Zaaktype getIsvanZaaktype() {
        return this.isvanZaaktype;
    }

    public void setIsvanZaaktype(Zaaktype zaaktype) {
        this.isvanZaaktype = zaaktype;
    }

    public Zaak isvanZaaktype(Zaaktype zaaktype) {
        this.setIsvanZaaktype(zaaktype);
        return this;
    }

    public Set<Document> getKentDocuments() {
        return this.kentDocuments;
    }

    public void setKentDocuments(Set<Document> documents) {
        this.kentDocuments = documents;
    }

    public Zaak kentDocuments(Set<Document> documents) {
        this.setKentDocuments(documents);
        return this;
    }

    public Zaak addKentDocument(Document document) {
        this.kentDocuments.add(document);
        return this;
    }

    public Zaak removeKentDocument(Document document) {
        this.kentDocuments.remove(document);
        return this;
    }

    public Set<Medewerker> getAfhandelendmedewerkerMedewerkers() {
        return this.afhandelendmedewerkerMedewerkers;
    }

    public void setAfhandelendmedewerkerMedewerkers(Set<Medewerker> medewerkers) {
        this.afhandelendmedewerkerMedewerkers = medewerkers;
    }

    public Zaak afhandelendmedewerkerMedewerkers(Set<Medewerker> medewerkers) {
        this.setAfhandelendmedewerkerMedewerkers(medewerkers);
        return this;
    }

    public Zaak addAfhandelendmedewerkerMedewerker(Medewerker medewerker) {
        this.afhandelendmedewerkerMedewerkers.add(medewerker);
        return this;
    }

    public Zaak removeAfhandelendmedewerkerMedewerker(Medewerker medewerker) {
        this.afhandelendmedewerkerMedewerkers.remove(medewerker);
        return this;
    }

    public Verzoek getLeidttotVerzoek() {
        return this.leidttotVerzoek;
    }

    public void setLeidttotVerzoek(Verzoek verzoek) {
        if (this.leidttotVerzoek != null) {
            this.leidttotVerzoek.setLeidttotZaak(null);
        }
        if (verzoek != null) {
            verzoek.setLeidttotZaak(this);
        }
        this.leidttotVerzoek = verzoek;
    }

    public Zaak leidttotVerzoek(Verzoek verzoek) {
        this.setLeidttotVerzoek(verzoek);
        return this;
    }

    public Subsidie getHeeftSubsidie() {
        return this.heeftSubsidie;
    }

    public void setHeeftSubsidie(Subsidie subsidie) {
        if (this.heeftSubsidie != null) {
            this.heeftSubsidie.setHeeftZaak(null);
        }
        if (subsidie != null) {
            subsidie.setHeeftZaak(this);
        }
        this.heeftSubsidie = subsidie;
    }

    public Zaak heeftSubsidie(Subsidie subsidie) {
        this.setHeeftSubsidie(subsidie);
        return this;
    }

    public Aanbesteding getBetreftAanbesteding() {
        return this.betreftAanbesteding;
    }

    public void setBetreftAanbesteding(Aanbesteding aanbesteding) {
        if (this.betreftAanbesteding != null) {
            this.betreftAanbesteding.setBetreftZaak(null);
        }
        if (aanbesteding != null) {
            aanbesteding.setBetreftZaak(this);
        }
        this.betreftAanbesteding = aanbesteding;
    }

    public Zaak betreftAanbesteding(Aanbesteding aanbesteding) {
        this.setBetreftAanbesteding(aanbesteding);
        return this;
    }

    public Set<Balieafspraak> getHeeftbetrekkingopBalieafspraaks() {
        return this.heeftbetrekkingopBalieafspraaks;
    }

    public void setHeeftbetrekkingopBalieafspraaks(Set<Balieafspraak> balieafspraaks) {
        if (this.heeftbetrekkingopBalieafspraaks != null) {
            this.heeftbetrekkingopBalieafspraaks.forEach(i -> i.setHeeftbetrekkingopZaak(null));
        }
        if (balieafspraaks != null) {
            balieafspraaks.forEach(i -> i.setHeeftbetrekkingopZaak(this));
        }
        this.heeftbetrekkingopBalieafspraaks = balieafspraaks;
    }

    public Zaak heeftbetrekkingopBalieafspraaks(Set<Balieafspraak> balieafspraaks) {
        this.setHeeftbetrekkingopBalieafspraaks(balieafspraaks);
        return this;
    }

    public Zaak addHeeftbetrekkingopBalieafspraak(Balieafspraak balieafspraak) {
        this.heeftbetrekkingopBalieafspraaks.add(balieafspraak);
        balieafspraak.setHeeftbetrekkingopZaak(this);
        return this;
    }

    public Zaak removeHeeftbetrekkingopBalieafspraak(Balieafspraak balieafspraak) {
        this.heeftbetrekkingopBalieafspraaks.remove(balieafspraak);
        balieafspraak.setHeeftbetrekkingopZaak(null);
        return this;
    }

    public Set<Besluit> getIsuitkomstvanBesluits() {
        return this.isuitkomstvanBesluits;
    }

    public void setIsuitkomstvanBesluits(Set<Besluit> besluits) {
        if (this.isuitkomstvanBesluits != null) {
            this.isuitkomstvanBesluits.forEach(i -> i.setIsuitkomstvanZaak(null));
        }
        if (besluits != null) {
            besluits.forEach(i -> i.setIsuitkomstvanZaak(this));
        }
        this.isuitkomstvanBesluits = besluits;
    }

    public Zaak isuitkomstvanBesluits(Set<Besluit> besluits) {
        this.setIsuitkomstvanBesluits(besluits);
        return this;
    }

    public Zaak addIsuitkomstvanBesluit(Besluit besluit) {
        this.isuitkomstvanBesluits.add(besluit);
        besluit.setIsuitkomstvanZaak(this);
        return this;
    }

    public Zaak removeIsuitkomstvanBesluit(Besluit besluit) {
        this.isuitkomstvanBesluits.remove(besluit);
        besluit.setIsuitkomstvanZaak(null);
        return this;
    }

    public Set<Klantcontact> getHeeftbetrekkingopKlantcontacts() {
        return this.heeftbetrekkingopKlantcontacts;
    }

    public void setHeeftbetrekkingopKlantcontacts(Set<Klantcontact> klantcontacts) {
        if (this.heeftbetrekkingopKlantcontacts != null) {
            this.heeftbetrekkingopKlantcontacts.forEach(i -> i.setHeeftbetrekkingopZaak(null));
        }
        if (klantcontacts != null) {
            klantcontacts.forEach(i -> i.setHeeftbetrekkingopZaak(this));
        }
        this.heeftbetrekkingopKlantcontacts = klantcontacts;
    }

    public Zaak heeftbetrekkingopKlantcontacts(Set<Klantcontact> klantcontacts) {
        this.setHeeftbetrekkingopKlantcontacts(klantcontacts);
        return this;
    }

    public Zaak addHeeftbetrekkingopKlantcontact(Klantcontact klantcontact) {
        this.heeftbetrekkingopKlantcontacts.add(klantcontact);
        klantcontact.setHeeftbetrekkingopZaak(this);
        return this;
    }

    public Zaak removeHeeftbetrekkingopKlantcontact(Klantcontact klantcontact) {
        this.heeftbetrekkingopKlantcontacts.remove(klantcontact);
        klantcontact.setHeeftbetrekkingopZaak(null);
        return this;
    }

    public Set<Grondslag> getHeeftGrondslags() {
        return this.heeftGrondslags;
    }

    public void setHeeftGrondslags(Set<Grondslag> grondslags) {
        if (this.heeftGrondslags != null) {
            this.heeftGrondslags.forEach(i -> i.removeHeeftZaak(this));
        }
        if (grondslags != null) {
            grondslags.forEach(i -> i.addHeeftZaak(this));
        }
        this.heeftGrondslags = grondslags;
    }

    public Zaak heeftGrondslags(Set<Grondslag> grondslags) {
        this.setHeeftGrondslags(grondslags);
        return this;
    }

    public Zaak addHeeftGrondslag(Grondslag grondslag) {
        this.heeftGrondslags.add(grondslag);
        grondslag.getHeeftZaaks().add(this);
        return this;
    }

    public Zaak removeHeeftGrondslag(Grondslag grondslag) {
        this.heeftGrondslags.remove(grondslag);
        grondslag.getHeeftZaaks().remove(this);
        return this;
    }

    public Set<Bedrijfsproces> getUitgevoerdbinnenBedrijfsproces() {
        return this.uitgevoerdbinnenBedrijfsproces;
    }

    public void setUitgevoerdbinnenBedrijfsproces(Set<Bedrijfsproces> bedrijfsproces) {
        if (this.uitgevoerdbinnenBedrijfsproces != null) {
            this.uitgevoerdbinnenBedrijfsproces.forEach(i -> i.removeUitgevoerdbinnenZaak(this));
        }
        if (bedrijfsproces != null) {
            bedrijfsproces.forEach(i -> i.addUitgevoerdbinnenZaak(this));
        }
        this.uitgevoerdbinnenBedrijfsproces = bedrijfsproces;
    }

    public Zaak uitgevoerdbinnenBedrijfsproces(Set<Bedrijfsproces> bedrijfsproces) {
        this.setUitgevoerdbinnenBedrijfsproces(bedrijfsproces);
        return this;
    }

    public Zaak addUitgevoerdbinnenBedrijfsproces(Bedrijfsproces bedrijfsproces) {
        this.uitgevoerdbinnenBedrijfsproces.add(bedrijfsproces);
        bedrijfsproces.getUitgevoerdbinnenZaaks().add(this);
        return this;
    }

    public Zaak removeUitgevoerdbinnenBedrijfsproces(Bedrijfsproces bedrijfsproces) {
        this.uitgevoerdbinnenBedrijfsproces.remove(bedrijfsproces);
        bedrijfsproces.getUitgevoerdbinnenZaaks().remove(this);
        return this;
    }

    public Set<Betrokkene> getOefentuitBetrokkenes() {
        return this.oefentuitBetrokkenes;
    }

    public void setOefentuitBetrokkenes(Set<Betrokkene> betrokkenes) {
        if (this.oefentuitBetrokkenes != null) {
            this.oefentuitBetrokkenes.forEach(i -> i.removeOefentuitZaak(this));
        }
        if (betrokkenes != null) {
            betrokkenes.forEach(i -> i.addOefentuitZaak(this));
        }
        this.oefentuitBetrokkenes = betrokkenes;
    }

    public Zaak oefentuitBetrokkenes(Set<Betrokkene> betrokkenes) {
        this.setOefentuitBetrokkenes(betrokkenes);
        return this;
    }

    public Zaak addOefentuitBetrokkene(Betrokkene betrokkene) {
        this.oefentuitBetrokkenes.add(betrokkene);
        betrokkene.getOefentuitZaaks().add(this);
        return this;
    }

    public Zaak removeOefentuitBetrokkene(Betrokkene betrokkene) {
        this.oefentuitBetrokkenes.remove(betrokkene);
        betrokkene.getOefentuitZaaks().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Zaak)) {
            return false;
        }
        return getId() != null && getId().equals(((Zaak) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Zaak{" +
            "id=" + getId() +
            ", empty='" + getEmpty() + "'" +
            ", archiefnominatie='" + getArchiefnominatie() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumeindegepland='" + getDatumeindegepland() + "'" +
            ", datumeindeuiterlijkeafdoening='" + getDatumeindeuiterlijkeafdoening() + "'" +
            ", datumlaatstebetaling='" + getDatumlaatstebetaling() + "'" +
            ", datumpublicatie='" + getDatumpublicatie() + "'" +
            ", datumregistratie='" + getDatumregistratie() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", datumvernietigingdossier='" + getDatumvernietigingdossier() + "'" +
            ", document='" + getDocument() + "'" +
            ", duurverlenging='" + getDuurverlenging() + "'" +
            ", indicatiebetaling='" + getIndicatiebetaling() + "'" +
            ", indicatiedeelzaken='" + getIndicatiedeelzaken() + "'" +
            ", indicatieopschorting='" + getIndicatieopschorting() + "'" +
            ", leges='" + getLeges() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", omschrijvingresultaat='" + getOmschrijvingresultaat() + "'" +
            ", redenopschorting='" + getRedenopschorting() + "'" +
            ", redenverlenging='" + getRedenverlenging() + "'" +
            ", toelichting='" + getToelichting() + "'" +
            ", toelichtingresultaat='" + getToelichtingresultaat() + "'" +
            ", vertrouwelijkheid='" + getVertrouwelijkheid() + "'" +
            ", zaakidentificatie='" + getZaakidentificatie() + "'" +
            ", zaakniveau='" + getZaakniveau() + "'" +
            "}";
    }
}
