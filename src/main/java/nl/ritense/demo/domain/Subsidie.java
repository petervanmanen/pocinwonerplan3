package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Subsidie.
 */
@Entity
@Table(name = "subsidie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Subsidie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "accountantscontrole")
    private String accountantscontrole;

    @Column(name = "cofinanciering", precision = 21, scale = 2)
    private BigDecimal cofinanciering;

    @Column(name = "datumbehandeltermijn")
    private LocalDate datumbehandeltermijn;

    @Column(name = "datumbewaartermijn")
    private LocalDate datumbewaartermijn;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "datumsubsidievaststelling")
    private LocalDate datumsubsidievaststelling;

    @Column(name = "datumverzendingeindeafrekening")
    private LocalDate datumverzendingeindeafrekening;

    @Column(name = "deadlineindiening")
    private LocalDate deadlineindiening;

    @Column(name = "doelstelling")
    private String doelstelling;

    @Column(name = "gerealiseerdeprojectkosten")
    private LocalDate gerealiseerdeprojectkosten;

    @Column(name = "hoogtesubsidie", precision = 21, scale = 2)
    private BigDecimal hoogtesubsidie;

    @Column(name = "niveau")
    private String niveau;

    @Column(name = "onderwerp")
    private String onderwerp;

    @Column(name = "ontvangenbedrag", precision = 21, scale = 2)
    private BigDecimal ontvangenbedrag;

    @Column(name = "opmerkingen")
    private String opmerkingen;

    @Column(name = "opmerkingenvoorschotten")
    private String opmerkingenvoorschotten;

    @Column(name = "prestatiesubsidie")
    private String prestatiesubsidie;

    @Column(name = "socialreturnbedrag", precision = 21, scale = 2)
    private BigDecimal socialreturnbedrag;

    @Column(name = "socialreturnnagekomen")
    private String socialreturnnagekomen;

    @Column(name = "socialreturnverplichting")
    private String socialreturnverplichting;

    @Column(name = "status")
    private String status;

    @Column(name = "subsidiebedrag", precision = 21, scale = 2)
    private BigDecimal subsidiebedrag;

    @Column(name = "subsidiesoort")
    private String subsidiesoort;

    @Column(name = "subsidievaststellingbedrag", precision = 21, scale = 2)
    private BigDecimal subsidievaststellingbedrag;

    @Column(name = "uitgaandesubsidie")
    private String uitgaandesubsidie;

    @Column(name = "verantwoordenop")
    private LocalDate verantwoordenop;

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Zaak heeftZaak;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftSubsidie")
    @JsonIgnoreProperties(value = { "heeftDocuments", "heeftSubsidie", "projectleiderRechtspersoon" }, allowSetters = true)
    private Set<Rapportagemoment> heeftRapportagemoments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftSubsidie")
    @JsonIgnoreProperties(value = { "projectleiderRechtspersoon", "heeftSubsidie" }, allowSetters = true)
    private Set<Taak> heeftTaaks = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "valtbinnenSubsidies" }, allowSetters = true)
    private Sector valtbinnenSector;

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
    private Medewerker behandelaarMedewerker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "projectleiderRapportagemoments",
            "aanvragerSubsidies",
            "heeftTenaamstellings",
            "betrokkenenKadastralemutaties",
            "isIndiener",
            "houderParkeervergunnings",
            "verstrekkerSubsidies",
            "projectleiderTaaks",
            "heeftVastgoedcontracts",
        },
        allowSetters = true
    )
    private Rechtspersoon verstrekkerRechtspersoon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftVastgoedobjects",
            "heeftWerkorders",
            "heeftSubrekenings",
            "heeftInkooporders",
            "heeftTaakvelds",
            "heeftProgrammas",
            "heeftSubsidies",
            "heeftSubsidiecomponents",
            "betreftBegrotingregels",
            "schrijftopFactuurs",
            "heeftbetrekkingopMutaties",
            "heeftProducts",
            "heeftHoofdrekenings",
            "heeftProjects",
        },
        allowSetters = true
    )
    private Kostenplaats heeftKostenplaats;

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Document heeftDocument;

    @JsonIgnoreProperties(value = { "betreftSubsidie", "mondtuitSubsidiebeschikking" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "betreftSubsidie")
    private Subsidieaanvraag betreftSubsidieaanvraag;

    @JsonIgnoreProperties(value = { "betreftSubsidie", "mondtuitSubsidieaanvraag" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "betreftSubsidie")
    private Subsidiebeschikking betreftSubsidiebeschikking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "projectleiderRapportagemoments",
            "aanvragerSubsidies",
            "heeftTenaamstellings",
            "betrokkenenKadastralemutaties",
            "isIndiener",
            "houderParkeervergunnings",
            "verstrekkerSubsidies",
            "projectleiderTaaks",
            "heeftVastgoedcontracts",
        },
        allowSetters = true
    )
    private Rechtspersoon aanvragerRechtspersoon;

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
    private Medewerker aanvragerMedewerker;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Subsidie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountantscontrole() {
        return this.accountantscontrole;
    }

    public Subsidie accountantscontrole(String accountantscontrole) {
        this.setAccountantscontrole(accountantscontrole);
        return this;
    }

    public void setAccountantscontrole(String accountantscontrole) {
        this.accountantscontrole = accountantscontrole;
    }

    public BigDecimal getCofinanciering() {
        return this.cofinanciering;
    }

    public Subsidie cofinanciering(BigDecimal cofinanciering) {
        this.setCofinanciering(cofinanciering);
        return this;
    }

    public void setCofinanciering(BigDecimal cofinanciering) {
        this.cofinanciering = cofinanciering;
    }

    public LocalDate getDatumbehandeltermijn() {
        return this.datumbehandeltermijn;
    }

    public Subsidie datumbehandeltermijn(LocalDate datumbehandeltermijn) {
        this.setDatumbehandeltermijn(datumbehandeltermijn);
        return this;
    }

    public void setDatumbehandeltermijn(LocalDate datumbehandeltermijn) {
        this.datumbehandeltermijn = datumbehandeltermijn;
    }

    public LocalDate getDatumbewaartermijn() {
        return this.datumbewaartermijn;
    }

    public Subsidie datumbewaartermijn(LocalDate datumbewaartermijn) {
        this.setDatumbewaartermijn(datumbewaartermijn);
        return this;
    }

    public void setDatumbewaartermijn(LocalDate datumbewaartermijn) {
        this.datumbewaartermijn = datumbewaartermijn;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Subsidie datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Subsidie datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public LocalDate getDatumsubsidievaststelling() {
        return this.datumsubsidievaststelling;
    }

    public Subsidie datumsubsidievaststelling(LocalDate datumsubsidievaststelling) {
        this.setDatumsubsidievaststelling(datumsubsidievaststelling);
        return this;
    }

    public void setDatumsubsidievaststelling(LocalDate datumsubsidievaststelling) {
        this.datumsubsidievaststelling = datumsubsidievaststelling;
    }

    public LocalDate getDatumverzendingeindeafrekening() {
        return this.datumverzendingeindeafrekening;
    }

    public Subsidie datumverzendingeindeafrekening(LocalDate datumverzendingeindeafrekening) {
        this.setDatumverzendingeindeafrekening(datumverzendingeindeafrekening);
        return this;
    }

    public void setDatumverzendingeindeafrekening(LocalDate datumverzendingeindeafrekening) {
        this.datumverzendingeindeafrekening = datumverzendingeindeafrekening;
    }

    public LocalDate getDeadlineindiening() {
        return this.deadlineindiening;
    }

    public Subsidie deadlineindiening(LocalDate deadlineindiening) {
        this.setDeadlineindiening(deadlineindiening);
        return this;
    }

    public void setDeadlineindiening(LocalDate deadlineindiening) {
        this.deadlineindiening = deadlineindiening;
    }

    public String getDoelstelling() {
        return this.doelstelling;
    }

    public Subsidie doelstelling(String doelstelling) {
        this.setDoelstelling(doelstelling);
        return this;
    }

    public void setDoelstelling(String doelstelling) {
        this.doelstelling = doelstelling;
    }

    public LocalDate getGerealiseerdeprojectkosten() {
        return this.gerealiseerdeprojectkosten;
    }

    public Subsidie gerealiseerdeprojectkosten(LocalDate gerealiseerdeprojectkosten) {
        this.setGerealiseerdeprojectkosten(gerealiseerdeprojectkosten);
        return this;
    }

    public void setGerealiseerdeprojectkosten(LocalDate gerealiseerdeprojectkosten) {
        this.gerealiseerdeprojectkosten = gerealiseerdeprojectkosten;
    }

    public BigDecimal getHoogtesubsidie() {
        return this.hoogtesubsidie;
    }

    public Subsidie hoogtesubsidie(BigDecimal hoogtesubsidie) {
        this.setHoogtesubsidie(hoogtesubsidie);
        return this;
    }

    public void setHoogtesubsidie(BigDecimal hoogtesubsidie) {
        this.hoogtesubsidie = hoogtesubsidie;
    }

    public String getNiveau() {
        return this.niveau;
    }

    public Subsidie niveau(String niveau) {
        this.setNiveau(niveau);
        return this;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getOnderwerp() {
        return this.onderwerp;
    }

    public Subsidie onderwerp(String onderwerp) {
        this.setOnderwerp(onderwerp);
        return this;
    }

    public void setOnderwerp(String onderwerp) {
        this.onderwerp = onderwerp;
    }

    public BigDecimal getOntvangenbedrag() {
        return this.ontvangenbedrag;
    }

    public Subsidie ontvangenbedrag(BigDecimal ontvangenbedrag) {
        this.setOntvangenbedrag(ontvangenbedrag);
        return this;
    }

    public void setOntvangenbedrag(BigDecimal ontvangenbedrag) {
        this.ontvangenbedrag = ontvangenbedrag;
    }

    public String getOpmerkingen() {
        return this.opmerkingen;
    }

    public Subsidie opmerkingen(String opmerkingen) {
        this.setOpmerkingen(opmerkingen);
        return this;
    }

    public void setOpmerkingen(String opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public String getOpmerkingenvoorschotten() {
        return this.opmerkingenvoorschotten;
    }

    public Subsidie opmerkingenvoorschotten(String opmerkingenvoorschotten) {
        this.setOpmerkingenvoorschotten(opmerkingenvoorschotten);
        return this;
    }

    public void setOpmerkingenvoorschotten(String opmerkingenvoorschotten) {
        this.opmerkingenvoorschotten = opmerkingenvoorschotten;
    }

    public String getPrestatiesubsidie() {
        return this.prestatiesubsidie;
    }

    public Subsidie prestatiesubsidie(String prestatiesubsidie) {
        this.setPrestatiesubsidie(prestatiesubsidie);
        return this;
    }

    public void setPrestatiesubsidie(String prestatiesubsidie) {
        this.prestatiesubsidie = prestatiesubsidie;
    }

    public BigDecimal getSocialreturnbedrag() {
        return this.socialreturnbedrag;
    }

    public Subsidie socialreturnbedrag(BigDecimal socialreturnbedrag) {
        this.setSocialreturnbedrag(socialreturnbedrag);
        return this;
    }

    public void setSocialreturnbedrag(BigDecimal socialreturnbedrag) {
        this.socialreturnbedrag = socialreturnbedrag;
    }

    public String getSocialreturnnagekomen() {
        return this.socialreturnnagekomen;
    }

    public Subsidie socialreturnnagekomen(String socialreturnnagekomen) {
        this.setSocialreturnnagekomen(socialreturnnagekomen);
        return this;
    }

    public void setSocialreturnnagekomen(String socialreturnnagekomen) {
        this.socialreturnnagekomen = socialreturnnagekomen;
    }

    public String getSocialreturnverplichting() {
        return this.socialreturnverplichting;
    }

    public Subsidie socialreturnverplichting(String socialreturnverplichting) {
        this.setSocialreturnverplichting(socialreturnverplichting);
        return this;
    }

    public void setSocialreturnverplichting(String socialreturnverplichting) {
        this.socialreturnverplichting = socialreturnverplichting;
    }

    public String getStatus() {
        return this.status;
    }

    public Subsidie status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getSubsidiebedrag() {
        return this.subsidiebedrag;
    }

    public Subsidie subsidiebedrag(BigDecimal subsidiebedrag) {
        this.setSubsidiebedrag(subsidiebedrag);
        return this;
    }

    public void setSubsidiebedrag(BigDecimal subsidiebedrag) {
        this.subsidiebedrag = subsidiebedrag;
    }

    public String getSubsidiesoort() {
        return this.subsidiesoort;
    }

    public Subsidie subsidiesoort(String subsidiesoort) {
        this.setSubsidiesoort(subsidiesoort);
        return this;
    }

    public void setSubsidiesoort(String subsidiesoort) {
        this.subsidiesoort = subsidiesoort;
    }

    public BigDecimal getSubsidievaststellingbedrag() {
        return this.subsidievaststellingbedrag;
    }

    public Subsidie subsidievaststellingbedrag(BigDecimal subsidievaststellingbedrag) {
        this.setSubsidievaststellingbedrag(subsidievaststellingbedrag);
        return this;
    }

    public void setSubsidievaststellingbedrag(BigDecimal subsidievaststellingbedrag) {
        this.subsidievaststellingbedrag = subsidievaststellingbedrag;
    }

    public String getUitgaandesubsidie() {
        return this.uitgaandesubsidie;
    }

    public Subsidie uitgaandesubsidie(String uitgaandesubsidie) {
        this.setUitgaandesubsidie(uitgaandesubsidie);
        return this;
    }

    public void setUitgaandesubsidie(String uitgaandesubsidie) {
        this.uitgaandesubsidie = uitgaandesubsidie;
    }

    public LocalDate getVerantwoordenop() {
        return this.verantwoordenop;
    }

    public Subsidie verantwoordenop(LocalDate verantwoordenop) {
        this.setVerantwoordenop(verantwoordenop);
        return this;
    }

    public void setVerantwoordenop(LocalDate verantwoordenop) {
        this.verantwoordenop = verantwoordenop;
    }

    public Zaak getHeeftZaak() {
        return this.heeftZaak;
    }

    public void setHeeftZaak(Zaak zaak) {
        this.heeftZaak = zaak;
    }

    public Subsidie heeftZaak(Zaak zaak) {
        this.setHeeftZaak(zaak);
        return this;
    }

    public Set<Rapportagemoment> getHeeftRapportagemoments() {
        return this.heeftRapportagemoments;
    }

    public void setHeeftRapportagemoments(Set<Rapportagemoment> rapportagemoments) {
        if (this.heeftRapportagemoments != null) {
            this.heeftRapportagemoments.forEach(i -> i.setHeeftSubsidie(null));
        }
        if (rapportagemoments != null) {
            rapportagemoments.forEach(i -> i.setHeeftSubsidie(this));
        }
        this.heeftRapportagemoments = rapportagemoments;
    }

    public Subsidie heeftRapportagemoments(Set<Rapportagemoment> rapportagemoments) {
        this.setHeeftRapportagemoments(rapportagemoments);
        return this;
    }

    public Subsidie addHeeftRapportagemoment(Rapportagemoment rapportagemoment) {
        this.heeftRapportagemoments.add(rapportagemoment);
        rapportagemoment.setHeeftSubsidie(this);
        return this;
    }

    public Subsidie removeHeeftRapportagemoment(Rapportagemoment rapportagemoment) {
        this.heeftRapportagemoments.remove(rapportagemoment);
        rapportagemoment.setHeeftSubsidie(null);
        return this;
    }

    public Set<Taak> getHeeftTaaks() {
        return this.heeftTaaks;
    }

    public void setHeeftTaaks(Set<Taak> taaks) {
        if (this.heeftTaaks != null) {
            this.heeftTaaks.forEach(i -> i.setHeeftSubsidie(null));
        }
        if (taaks != null) {
            taaks.forEach(i -> i.setHeeftSubsidie(this));
        }
        this.heeftTaaks = taaks;
    }

    public Subsidie heeftTaaks(Set<Taak> taaks) {
        this.setHeeftTaaks(taaks);
        return this;
    }

    public Subsidie addHeeftTaak(Taak taak) {
        this.heeftTaaks.add(taak);
        taak.setHeeftSubsidie(this);
        return this;
    }

    public Subsidie removeHeeftTaak(Taak taak) {
        this.heeftTaaks.remove(taak);
        taak.setHeeftSubsidie(null);
        return this;
    }

    public Sector getValtbinnenSector() {
        return this.valtbinnenSector;
    }

    public void setValtbinnenSector(Sector sector) {
        this.valtbinnenSector = sector;
    }

    public Subsidie valtbinnenSector(Sector sector) {
        this.setValtbinnenSector(sector);
        return this;
    }

    public Medewerker getBehandelaarMedewerker() {
        return this.behandelaarMedewerker;
    }

    public void setBehandelaarMedewerker(Medewerker medewerker) {
        this.behandelaarMedewerker = medewerker;
    }

    public Subsidie behandelaarMedewerker(Medewerker medewerker) {
        this.setBehandelaarMedewerker(medewerker);
        return this;
    }

    public Rechtspersoon getVerstrekkerRechtspersoon() {
        return this.verstrekkerRechtspersoon;
    }

    public void setVerstrekkerRechtspersoon(Rechtspersoon rechtspersoon) {
        this.verstrekkerRechtspersoon = rechtspersoon;
    }

    public Subsidie verstrekkerRechtspersoon(Rechtspersoon rechtspersoon) {
        this.setVerstrekkerRechtspersoon(rechtspersoon);
        return this;
    }

    public Kostenplaats getHeeftKostenplaats() {
        return this.heeftKostenplaats;
    }

    public void setHeeftKostenplaats(Kostenplaats kostenplaats) {
        this.heeftKostenplaats = kostenplaats;
    }

    public Subsidie heeftKostenplaats(Kostenplaats kostenplaats) {
        this.setHeeftKostenplaats(kostenplaats);
        return this;
    }

    public Document getHeeftDocument() {
        return this.heeftDocument;
    }

    public void setHeeftDocument(Document document) {
        this.heeftDocument = document;
    }

    public Subsidie heeftDocument(Document document) {
        this.setHeeftDocument(document);
        return this;
    }

    public Subsidieaanvraag getBetreftSubsidieaanvraag() {
        return this.betreftSubsidieaanvraag;
    }

    public void setBetreftSubsidieaanvraag(Subsidieaanvraag subsidieaanvraag) {
        if (this.betreftSubsidieaanvraag != null) {
            this.betreftSubsidieaanvraag.setBetreftSubsidie(null);
        }
        if (subsidieaanvraag != null) {
            subsidieaanvraag.setBetreftSubsidie(this);
        }
        this.betreftSubsidieaanvraag = subsidieaanvraag;
    }

    public Subsidie betreftSubsidieaanvraag(Subsidieaanvraag subsidieaanvraag) {
        this.setBetreftSubsidieaanvraag(subsidieaanvraag);
        return this;
    }

    public Subsidiebeschikking getBetreftSubsidiebeschikking() {
        return this.betreftSubsidiebeschikking;
    }

    public void setBetreftSubsidiebeschikking(Subsidiebeschikking subsidiebeschikking) {
        if (this.betreftSubsidiebeschikking != null) {
            this.betreftSubsidiebeschikking.setBetreftSubsidie(null);
        }
        if (subsidiebeschikking != null) {
            subsidiebeschikking.setBetreftSubsidie(this);
        }
        this.betreftSubsidiebeschikking = subsidiebeschikking;
    }

    public Subsidie betreftSubsidiebeschikking(Subsidiebeschikking subsidiebeschikking) {
        this.setBetreftSubsidiebeschikking(subsidiebeschikking);
        return this;
    }

    public Rechtspersoon getAanvragerRechtspersoon() {
        return this.aanvragerRechtspersoon;
    }

    public void setAanvragerRechtspersoon(Rechtspersoon rechtspersoon) {
        this.aanvragerRechtspersoon = rechtspersoon;
    }

    public Subsidie aanvragerRechtspersoon(Rechtspersoon rechtspersoon) {
        this.setAanvragerRechtspersoon(rechtspersoon);
        return this;
    }

    public Medewerker getAanvragerMedewerker() {
        return this.aanvragerMedewerker;
    }

    public void setAanvragerMedewerker(Medewerker medewerker) {
        this.aanvragerMedewerker = medewerker;
    }

    public Subsidie aanvragerMedewerker(Medewerker medewerker) {
        this.setAanvragerMedewerker(medewerker);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subsidie)) {
            return false;
        }
        return getId() != null && getId().equals(((Subsidie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Subsidie{" +
            "id=" + getId() +
            ", accountantscontrole='" + getAccountantscontrole() + "'" +
            ", cofinanciering=" + getCofinanciering() +
            ", datumbehandeltermijn='" + getDatumbehandeltermijn() + "'" +
            ", datumbewaartermijn='" + getDatumbewaartermijn() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", datumsubsidievaststelling='" + getDatumsubsidievaststelling() + "'" +
            ", datumverzendingeindeafrekening='" + getDatumverzendingeindeafrekening() + "'" +
            ", deadlineindiening='" + getDeadlineindiening() + "'" +
            ", doelstelling='" + getDoelstelling() + "'" +
            ", gerealiseerdeprojectkosten='" + getGerealiseerdeprojectkosten() + "'" +
            ", hoogtesubsidie=" + getHoogtesubsidie() +
            ", niveau='" + getNiveau() + "'" +
            ", onderwerp='" + getOnderwerp() + "'" +
            ", ontvangenbedrag=" + getOntvangenbedrag() +
            ", opmerkingen='" + getOpmerkingen() + "'" +
            ", opmerkingenvoorschotten='" + getOpmerkingenvoorschotten() + "'" +
            ", prestatiesubsidie='" + getPrestatiesubsidie() + "'" +
            ", socialreturnbedrag=" + getSocialreturnbedrag() +
            ", socialreturnnagekomen='" + getSocialreturnnagekomen() + "'" +
            ", socialreturnverplichting='" + getSocialreturnverplichting() + "'" +
            ", status='" + getStatus() + "'" +
            ", subsidiebedrag=" + getSubsidiebedrag() +
            ", subsidiesoort='" + getSubsidiesoort() + "'" +
            ", subsidievaststellingbedrag=" + getSubsidievaststellingbedrag() +
            ", uitgaandesubsidie='" + getUitgaandesubsidie() + "'" +
            ", verantwoordenop='" + getVerantwoordenop() + "'" +
            "}";
    }
}
