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
 * A Vastgoedobject.
 */
@Entity
@Table(name = "vastgoedobject")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vastgoedobject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aantaletages")
    private String aantaletages;

    @Column(name = "aantalparkeerplaatsen")
    private String aantalparkeerplaatsen;

    @Column(name = "aantalrioleringen")
    private String aantalrioleringen;

    @Column(name = "adresaanduiding")
    private String adresaanduiding;

    @Column(name = "afgekochteerfpacht")
    private String afgekochteerfpacht;

    @Column(name = "afgesprokenconditiescore")
    private String afgesprokenconditiescore;

    @Column(name = "afkoopwaarde", precision = 21, scale = 2)
    private BigDecimal afkoopwaarde;

    @Column(name = "asbestrapportageaanwezig")
    private String asbestrapportageaanwezig;

    @Column(name = "bedragaankoop", precision = 21, scale = 2)
    private BigDecimal bedragaankoop;

    @Column(name = "bestemmingsplan")
    private String bestemmingsplan;

    @Column(name = "boekwaarde", precision = 21, scale = 2)
    private BigDecimal boekwaarde;

    @Column(name = "bouwjaar")
    private String bouwjaar;

    @Column(name = "bouwwerk")
    private String bouwwerk;

    @Column(name = "bovenliggendniveau")
    private String bovenliggendniveau;

    @Size(max = 20)
    @Column(name = "bovenliggendniveaucode", length = 20)
    private String bovenliggendniveaucode;

    @Column(name = "brutovloeroppervlakte")
    private String brutovloeroppervlakte;

    @Column(name = "co_2_uitstoot")
    private String co2uitstoot;

    @Column(name = "conditiescore")
    private String conditiescore;

    @Column(name = "datumafstoten")
    private LocalDate datumafstoten;

    @Column(name = "datumberekeningoppervlak")
    private LocalDate datumberekeningoppervlak;

    @Column(name = "datumeigendom")
    private LocalDate datumeigendom;

    @Column(name = "datumverkoop")
    private LocalDate datumverkoop;

    @Column(name = "deelportefeuille")
    private String deelportefeuille;

    @Column(name = "energiekosten", precision = 21, scale = 2)
    private BigDecimal energiekosten;

    @Column(name = "energielabel")
    private String energielabel;

    @Column(name = "energieverbruik")
    private String energieverbruik;

    @Column(name = "fiscalewaarde", precision = 21, scale = 2)
    private BigDecimal fiscalewaarde;

    @Column(name = "foto")
    private String foto;

    @Column(name = "gearchiveerd")
    private String gearchiveerd;

    @Column(name = "herbouwwaarde", precision = 21, scale = 2)
    private BigDecimal herbouwwaarde;

    @Column(name = "hoofdstuk")
    private String hoofdstuk;

    @Column(name = "identificatie")
    private String identificatie;

    @Column(name = "jaarlaatsterenovatie")
    private String jaarlaatsterenovatie;

    @Column(name = "locatie")
    private String locatie;

    @Column(name = "marktwaarde", precision = 21, scale = 2)
    private BigDecimal marktwaarde;

    @Column(name = "monument")
    private String monument;

    @Column(name = "naam")
    private String naam;

    @Column(name = "eobjectstatus")
    private String eobjectstatus;

    @Column(name = "eobjectstatuscode")
    private String eobjectstatuscode;

    @Column(name = "eobjecttype")
    private String eobjecttype;

    @Column(name = "eobjecttypecode")
    private String eobjecttypecode;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "onderhoudscategorie")
    private String onderhoudscategorie;

    @Column(name = "oppervlaktekantoor")
    private String oppervlaktekantoor;

    @Column(name = "portefeuille")
    private String portefeuille;

    @Column(name = "portefeuillecode")
    private String portefeuillecode;

    @Column(name = "provincie")
    private String provincie;

    @Column(name = "toelichting")
    private String toelichting;

    @Column(name = "verhuurbaarvloeroppervlak")
    private String verhuurbaarvloeroppervlak;

    @Column(name = "verkoopbaarheid")
    private String verkoopbaarheid;

    @Column(name = "verkoopbedrag", precision = 21, scale = 2)
    private BigDecimal verkoopbedrag;

    @Column(name = "verzekerdewaarde", precision = 21, scale = 2)
    private BigDecimal verzekerdewaarde;

    @Column(name = "waardegrond", precision = 21, scale = 2)
    private BigDecimal waardegrond;

    @Column(name = "waardeopstal", precision = 21, scale = 2)
    private BigDecimal waardeopstal;

    @Column(name = "wijk")
    private String wijk;

    @Column(name = "wozwaarde", precision = 21, scale = 2)
    private BigDecimal wozwaarde;

    @JsonIgnoreProperties(
        value = { "heeftVastgoedobject", "zonderverblijfsobjectligtinBuurt", "betreftVastgoedobject", "maaktdeeluitvanVerblijfsobjects" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Pand betreftPand;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bestaatuitVastgoedobject")
    @JsonIgnoreProperties(value = { "bestaatuitBouwdeelelements", "bestaatuitVastgoedobject", "betreftWerkbons" }, allowSetters = true)
    private Set<Bouwdeel> bestaatuitBouwdeels = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftVastgoedobject")
    @JsonIgnoreProperties(value = { "betreftVastgoedobject" }, allowSetters = true)
    private Set<Inspectie> betreftInspecties = new HashSet<>();

    @JsonIgnoreProperties(value = { "heeftVastgoedobject", "maaktdeeluitvanPands", "isgevestigdinBinnenlocaties" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "heeftVastgoedobject")
    private Verblijfsobject heeftVerblijfsobject;

    @JsonIgnoreProperties(
        value = { "heeftVastgoedobject", "zonderverblijfsobjectligtinBuurt", "betreftVastgoedobject", "maaktdeeluitvanVerblijfsobjects" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "heeftVastgoedobject")
    private Pand heeftPand;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftVastgoedobject")
    @JsonIgnoreProperties(
        value = {
            "betreftVastgoedobject", "betreftBouwdeels", "betreftBouwdeelelements", "hoortbijInkooporder", "voertwerkuitconformLeverancier",
        },
        allowSetters = true
    )
    private Set<Werkbon> betreftWerkbons = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vastgoedobject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAantaletages() {
        return this.aantaletages;
    }

    public Vastgoedobject aantaletages(String aantaletages) {
        this.setAantaletages(aantaletages);
        return this;
    }

    public void setAantaletages(String aantaletages) {
        this.aantaletages = aantaletages;
    }

    public String getAantalparkeerplaatsen() {
        return this.aantalparkeerplaatsen;
    }

    public Vastgoedobject aantalparkeerplaatsen(String aantalparkeerplaatsen) {
        this.setAantalparkeerplaatsen(aantalparkeerplaatsen);
        return this;
    }

    public void setAantalparkeerplaatsen(String aantalparkeerplaatsen) {
        this.aantalparkeerplaatsen = aantalparkeerplaatsen;
    }

    public String getAantalrioleringen() {
        return this.aantalrioleringen;
    }

    public Vastgoedobject aantalrioleringen(String aantalrioleringen) {
        this.setAantalrioleringen(aantalrioleringen);
        return this;
    }

    public void setAantalrioleringen(String aantalrioleringen) {
        this.aantalrioleringen = aantalrioleringen;
    }

    public String getAdresaanduiding() {
        return this.adresaanduiding;
    }

    public Vastgoedobject adresaanduiding(String adresaanduiding) {
        this.setAdresaanduiding(adresaanduiding);
        return this;
    }

    public void setAdresaanduiding(String adresaanduiding) {
        this.adresaanduiding = adresaanduiding;
    }

    public String getAfgekochteerfpacht() {
        return this.afgekochteerfpacht;
    }

    public Vastgoedobject afgekochteerfpacht(String afgekochteerfpacht) {
        this.setAfgekochteerfpacht(afgekochteerfpacht);
        return this;
    }

    public void setAfgekochteerfpacht(String afgekochteerfpacht) {
        this.afgekochteerfpacht = afgekochteerfpacht;
    }

    public String getAfgesprokenconditiescore() {
        return this.afgesprokenconditiescore;
    }

    public Vastgoedobject afgesprokenconditiescore(String afgesprokenconditiescore) {
        this.setAfgesprokenconditiescore(afgesprokenconditiescore);
        return this;
    }

    public void setAfgesprokenconditiescore(String afgesprokenconditiescore) {
        this.afgesprokenconditiescore = afgesprokenconditiescore;
    }

    public BigDecimal getAfkoopwaarde() {
        return this.afkoopwaarde;
    }

    public Vastgoedobject afkoopwaarde(BigDecimal afkoopwaarde) {
        this.setAfkoopwaarde(afkoopwaarde);
        return this;
    }

    public void setAfkoopwaarde(BigDecimal afkoopwaarde) {
        this.afkoopwaarde = afkoopwaarde;
    }

    public String getAsbestrapportageaanwezig() {
        return this.asbestrapportageaanwezig;
    }

    public Vastgoedobject asbestrapportageaanwezig(String asbestrapportageaanwezig) {
        this.setAsbestrapportageaanwezig(asbestrapportageaanwezig);
        return this;
    }

    public void setAsbestrapportageaanwezig(String asbestrapportageaanwezig) {
        this.asbestrapportageaanwezig = asbestrapportageaanwezig;
    }

    public BigDecimal getBedragaankoop() {
        return this.bedragaankoop;
    }

    public Vastgoedobject bedragaankoop(BigDecimal bedragaankoop) {
        this.setBedragaankoop(bedragaankoop);
        return this;
    }

    public void setBedragaankoop(BigDecimal bedragaankoop) {
        this.bedragaankoop = bedragaankoop;
    }

    public String getBestemmingsplan() {
        return this.bestemmingsplan;
    }

    public Vastgoedobject bestemmingsplan(String bestemmingsplan) {
        this.setBestemmingsplan(bestemmingsplan);
        return this;
    }

    public void setBestemmingsplan(String bestemmingsplan) {
        this.bestemmingsplan = bestemmingsplan;
    }

    public BigDecimal getBoekwaarde() {
        return this.boekwaarde;
    }

    public Vastgoedobject boekwaarde(BigDecimal boekwaarde) {
        this.setBoekwaarde(boekwaarde);
        return this;
    }

    public void setBoekwaarde(BigDecimal boekwaarde) {
        this.boekwaarde = boekwaarde;
    }

    public String getBouwjaar() {
        return this.bouwjaar;
    }

    public Vastgoedobject bouwjaar(String bouwjaar) {
        this.setBouwjaar(bouwjaar);
        return this;
    }

    public void setBouwjaar(String bouwjaar) {
        this.bouwjaar = bouwjaar;
    }

    public String getBouwwerk() {
        return this.bouwwerk;
    }

    public Vastgoedobject bouwwerk(String bouwwerk) {
        this.setBouwwerk(bouwwerk);
        return this;
    }

    public void setBouwwerk(String bouwwerk) {
        this.bouwwerk = bouwwerk;
    }

    public String getBovenliggendniveau() {
        return this.bovenliggendniveau;
    }

    public Vastgoedobject bovenliggendniveau(String bovenliggendniveau) {
        this.setBovenliggendniveau(bovenliggendniveau);
        return this;
    }

    public void setBovenliggendniveau(String bovenliggendniveau) {
        this.bovenliggendniveau = bovenliggendniveau;
    }

    public String getBovenliggendniveaucode() {
        return this.bovenliggendniveaucode;
    }

    public Vastgoedobject bovenliggendniveaucode(String bovenliggendniveaucode) {
        this.setBovenliggendniveaucode(bovenliggendniveaucode);
        return this;
    }

    public void setBovenliggendniveaucode(String bovenliggendniveaucode) {
        this.bovenliggendniveaucode = bovenliggendniveaucode;
    }

    public String getBrutovloeroppervlakte() {
        return this.brutovloeroppervlakte;
    }

    public Vastgoedobject brutovloeroppervlakte(String brutovloeroppervlakte) {
        this.setBrutovloeroppervlakte(brutovloeroppervlakte);
        return this;
    }

    public void setBrutovloeroppervlakte(String brutovloeroppervlakte) {
        this.brutovloeroppervlakte = brutovloeroppervlakte;
    }

    public String getCo2uitstoot() {
        return this.co2uitstoot;
    }

    public Vastgoedobject co2uitstoot(String co2uitstoot) {
        this.setCo2uitstoot(co2uitstoot);
        return this;
    }

    public void setCo2uitstoot(String co2uitstoot) {
        this.co2uitstoot = co2uitstoot;
    }

    public String getConditiescore() {
        return this.conditiescore;
    }

    public Vastgoedobject conditiescore(String conditiescore) {
        this.setConditiescore(conditiescore);
        return this;
    }

    public void setConditiescore(String conditiescore) {
        this.conditiescore = conditiescore;
    }

    public LocalDate getDatumafstoten() {
        return this.datumafstoten;
    }

    public Vastgoedobject datumafstoten(LocalDate datumafstoten) {
        this.setDatumafstoten(datumafstoten);
        return this;
    }

    public void setDatumafstoten(LocalDate datumafstoten) {
        this.datumafstoten = datumafstoten;
    }

    public LocalDate getDatumberekeningoppervlak() {
        return this.datumberekeningoppervlak;
    }

    public Vastgoedobject datumberekeningoppervlak(LocalDate datumberekeningoppervlak) {
        this.setDatumberekeningoppervlak(datumberekeningoppervlak);
        return this;
    }

    public void setDatumberekeningoppervlak(LocalDate datumberekeningoppervlak) {
        this.datumberekeningoppervlak = datumberekeningoppervlak;
    }

    public LocalDate getDatumeigendom() {
        return this.datumeigendom;
    }

    public Vastgoedobject datumeigendom(LocalDate datumeigendom) {
        this.setDatumeigendom(datumeigendom);
        return this;
    }

    public void setDatumeigendom(LocalDate datumeigendom) {
        this.datumeigendom = datumeigendom;
    }

    public LocalDate getDatumverkoop() {
        return this.datumverkoop;
    }

    public Vastgoedobject datumverkoop(LocalDate datumverkoop) {
        this.setDatumverkoop(datumverkoop);
        return this;
    }

    public void setDatumverkoop(LocalDate datumverkoop) {
        this.datumverkoop = datumverkoop;
    }

    public String getDeelportefeuille() {
        return this.deelportefeuille;
    }

    public Vastgoedobject deelportefeuille(String deelportefeuille) {
        this.setDeelportefeuille(deelportefeuille);
        return this;
    }

    public void setDeelportefeuille(String deelportefeuille) {
        this.deelportefeuille = deelportefeuille;
    }

    public BigDecimal getEnergiekosten() {
        return this.energiekosten;
    }

    public Vastgoedobject energiekosten(BigDecimal energiekosten) {
        this.setEnergiekosten(energiekosten);
        return this;
    }

    public void setEnergiekosten(BigDecimal energiekosten) {
        this.energiekosten = energiekosten;
    }

    public String getEnergielabel() {
        return this.energielabel;
    }

    public Vastgoedobject energielabel(String energielabel) {
        this.setEnergielabel(energielabel);
        return this;
    }

    public void setEnergielabel(String energielabel) {
        this.energielabel = energielabel;
    }

    public String getEnergieverbruik() {
        return this.energieverbruik;
    }

    public Vastgoedobject energieverbruik(String energieverbruik) {
        this.setEnergieverbruik(energieverbruik);
        return this;
    }

    public void setEnergieverbruik(String energieverbruik) {
        this.energieverbruik = energieverbruik;
    }

    public BigDecimal getFiscalewaarde() {
        return this.fiscalewaarde;
    }

    public Vastgoedobject fiscalewaarde(BigDecimal fiscalewaarde) {
        this.setFiscalewaarde(fiscalewaarde);
        return this;
    }

    public void setFiscalewaarde(BigDecimal fiscalewaarde) {
        this.fiscalewaarde = fiscalewaarde;
    }

    public String getFoto() {
        return this.foto;
    }

    public Vastgoedobject foto(String foto) {
        this.setFoto(foto);
        return this;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getGearchiveerd() {
        return this.gearchiveerd;
    }

    public Vastgoedobject gearchiveerd(String gearchiveerd) {
        this.setGearchiveerd(gearchiveerd);
        return this;
    }

    public void setGearchiveerd(String gearchiveerd) {
        this.gearchiveerd = gearchiveerd;
    }

    public BigDecimal getHerbouwwaarde() {
        return this.herbouwwaarde;
    }

    public Vastgoedobject herbouwwaarde(BigDecimal herbouwwaarde) {
        this.setHerbouwwaarde(herbouwwaarde);
        return this;
    }

    public void setHerbouwwaarde(BigDecimal herbouwwaarde) {
        this.herbouwwaarde = herbouwwaarde;
    }

    public String getHoofdstuk() {
        return this.hoofdstuk;
    }

    public Vastgoedobject hoofdstuk(String hoofdstuk) {
        this.setHoofdstuk(hoofdstuk);
        return this;
    }

    public void setHoofdstuk(String hoofdstuk) {
        this.hoofdstuk = hoofdstuk;
    }

    public String getIdentificatie() {
        return this.identificatie;
    }

    public Vastgoedobject identificatie(String identificatie) {
        this.setIdentificatie(identificatie);
        return this;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public String getJaarlaatsterenovatie() {
        return this.jaarlaatsterenovatie;
    }

    public Vastgoedobject jaarlaatsterenovatie(String jaarlaatsterenovatie) {
        this.setJaarlaatsterenovatie(jaarlaatsterenovatie);
        return this;
    }

    public void setJaarlaatsterenovatie(String jaarlaatsterenovatie) {
        this.jaarlaatsterenovatie = jaarlaatsterenovatie;
    }

    public String getLocatie() {
        return this.locatie;
    }

    public Vastgoedobject locatie(String locatie) {
        this.setLocatie(locatie);
        return this;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public BigDecimal getMarktwaarde() {
        return this.marktwaarde;
    }

    public Vastgoedobject marktwaarde(BigDecimal marktwaarde) {
        this.setMarktwaarde(marktwaarde);
        return this;
    }

    public void setMarktwaarde(BigDecimal marktwaarde) {
        this.marktwaarde = marktwaarde;
    }

    public String getMonument() {
        return this.monument;
    }

    public Vastgoedobject monument(String monument) {
        this.setMonument(monument);
        return this;
    }

    public void setMonument(String monument) {
        this.monument = monument;
    }

    public String getNaam() {
        return this.naam;
    }

    public Vastgoedobject naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getEobjectstatus() {
        return this.eobjectstatus;
    }

    public Vastgoedobject eobjectstatus(String eobjectstatus) {
        this.setEobjectstatus(eobjectstatus);
        return this;
    }

    public void setEobjectstatus(String eobjectstatus) {
        this.eobjectstatus = eobjectstatus;
    }

    public String getEobjectstatuscode() {
        return this.eobjectstatuscode;
    }

    public Vastgoedobject eobjectstatuscode(String eobjectstatuscode) {
        this.setEobjectstatuscode(eobjectstatuscode);
        return this;
    }

    public void setEobjectstatuscode(String eobjectstatuscode) {
        this.eobjectstatuscode = eobjectstatuscode;
    }

    public String getEobjecttype() {
        return this.eobjecttype;
    }

    public Vastgoedobject eobjecttype(String eobjecttype) {
        this.setEobjecttype(eobjecttype);
        return this;
    }

    public void setEobjecttype(String eobjecttype) {
        this.eobjecttype = eobjecttype;
    }

    public String getEobjecttypecode() {
        return this.eobjecttypecode;
    }

    public Vastgoedobject eobjecttypecode(String eobjecttypecode) {
        this.setEobjecttypecode(eobjecttypecode);
        return this;
    }

    public void setEobjecttypecode(String eobjecttypecode) {
        this.eobjecttypecode = eobjecttypecode;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Vastgoedobject omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOnderhoudscategorie() {
        return this.onderhoudscategorie;
    }

    public Vastgoedobject onderhoudscategorie(String onderhoudscategorie) {
        this.setOnderhoudscategorie(onderhoudscategorie);
        return this;
    }

    public void setOnderhoudscategorie(String onderhoudscategorie) {
        this.onderhoudscategorie = onderhoudscategorie;
    }

    public String getOppervlaktekantoor() {
        return this.oppervlaktekantoor;
    }

    public Vastgoedobject oppervlaktekantoor(String oppervlaktekantoor) {
        this.setOppervlaktekantoor(oppervlaktekantoor);
        return this;
    }

    public void setOppervlaktekantoor(String oppervlaktekantoor) {
        this.oppervlaktekantoor = oppervlaktekantoor;
    }

    public String getPortefeuille() {
        return this.portefeuille;
    }

    public Vastgoedobject portefeuille(String portefeuille) {
        this.setPortefeuille(portefeuille);
        return this;
    }

    public void setPortefeuille(String portefeuille) {
        this.portefeuille = portefeuille;
    }

    public String getPortefeuillecode() {
        return this.portefeuillecode;
    }

    public Vastgoedobject portefeuillecode(String portefeuillecode) {
        this.setPortefeuillecode(portefeuillecode);
        return this;
    }

    public void setPortefeuillecode(String portefeuillecode) {
        this.portefeuillecode = portefeuillecode;
    }

    public String getProvincie() {
        return this.provincie;
    }

    public Vastgoedobject provincie(String provincie) {
        this.setProvincie(provincie);
        return this;
    }

    public void setProvincie(String provincie) {
        this.provincie = provincie;
    }

    public String getToelichting() {
        return this.toelichting;
    }

    public Vastgoedobject toelichting(String toelichting) {
        this.setToelichting(toelichting);
        return this;
    }

    public void setToelichting(String toelichting) {
        this.toelichting = toelichting;
    }

    public String getVerhuurbaarvloeroppervlak() {
        return this.verhuurbaarvloeroppervlak;
    }

    public Vastgoedobject verhuurbaarvloeroppervlak(String verhuurbaarvloeroppervlak) {
        this.setVerhuurbaarvloeroppervlak(verhuurbaarvloeroppervlak);
        return this;
    }

    public void setVerhuurbaarvloeroppervlak(String verhuurbaarvloeroppervlak) {
        this.verhuurbaarvloeroppervlak = verhuurbaarvloeroppervlak;
    }

    public String getVerkoopbaarheid() {
        return this.verkoopbaarheid;
    }

    public Vastgoedobject verkoopbaarheid(String verkoopbaarheid) {
        this.setVerkoopbaarheid(verkoopbaarheid);
        return this;
    }

    public void setVerkoopbaarheid(String verkoopbaarheid) {
        this.verkoopbaarheid = verkoopbaarheid;
    }

    public BigDecimal getVerkoopbedrag() {
        return this.verkoopbedrag;
    }

    public Vastgoedobject verkoopbedrag(BigDecimal verkoopbedrag) {
        this.setVerkoopbedrag(verkoopbedrag);
        return this;
    }

    public void setVerkoopbedrag(BigDecimal verkoopbedrag) {
        this.verkoopbedrag = verkoopbedrag;
    }

    public BigDecimal getVerzekerdewaarde() {
        return this.verzekerdewaarde;
    }

    public Vastgoedobject verzekerdewaarde(BigDecimal verzekerdewaarde) {
        this.setVerzekerdewaarde(verzekerdewaarde);
        return this;
    }

    public void setVerzekerdewaarde(BigDecimal verzekerdewaarde) {
        this.verzekerdewaarde = verzekerdewaarde;
    }

    public BigDecimal getWaardegrond() {
        return this.waardegrond;
    }

    public Vastgoedobject waardegrond(BigDecimal waardegrond) {
        this.setWaardegrond(waardegrond);
        return this;
    }

    public void setWaardegrond(BigDecimal waardegrond) {
        this.waardegrond = waardegrond;
    }

    public BigDecimal getWaardeopstal() {
        return this.waardeopstal;
    }

    public Vastgoedobject waardeopstal(BigDecimal waardeopstal) {
        this.setWaardeopstal(waardeopstal);
        return this;
    }

    public void setWaardeopstal(BigDecimal waardeopstal) {
        this.waardeopstal = waardeopstal;
    }

    public String getWijk() {
        return this.wijk;
    }

    public Vastgoedobject wijk(String wijk) {
        this.setWijk(wijk);
        return this;
    }

    public void setWijk(String wijk) {
        this.wijk = wijk;
    }

    public BigDecimal getWozwaarde() {
        return this.wozwaarde;
    }

    public Vastgoedobject wozwaarde(BigDecimal wozwaarde) {
        this.setWozwaarde(wozwaarde);
        return this;
    }

    public void setWozwaarde(BigDecimal wozwaarde) {
        this.wozwaarde = wozwaarde;
    }

    public Pand getBetreftPand() {
        return this.betreftPand;
    }

    public void setBetreftPand(Pand pand) {
        this.betreftPand = pand;
    }

    public Vastgoedobject betreftPand(Pand pand) {
        this.setBetreftPand(pand);
        return this;
    }

    public Set<Bouwdeel> getBestaatuitBouwdeels() {
        return this.bestaatuitBouwdeels;
    }

    public void setBestaatuitBouwdeels(Set<Bouwdeel> bouwdeels) {
        if (this.bestaatuitBouwdeels != null) {
            this.bestaatuitBouwdeels.forEach(i -> i.setBestaatuitVastgoedobject(null));
        }
        if (bouwdeels != null) {
            bouwdeels.forEach(i -> i.setBestaatuitVastgoedobject(this));
        }
        this.bestaatuitBouwdeels = bouwdeels;
    }

    public Vastgoedobject bestaatuitBouwdeels(Set<Bouwdeel> bouwdeels) {
        this.setBestaatuitBouwdeels(bouwdeels);
        return this;
    }

    public Vastgoedobject addBestaatuitBouwdeel(Bouwdeel bouwdeel) {
        this.bestaatuitBouwdeels.add(bouwdeel);
        bouwdeel.setBestaatuitVastgoedobject(this);
        return this;
    }

    public Vastgoedobject removeBestaatuitBouwdeel(Bouwdeel bouwdeel) {
        this.bestaatuitBouwdeels.remove(bouwdeel);
        bouwdeel.setBestaatuitVastgoedobject(null);
        return this;
    }

    public Set<Inspectie> getBetreftInspecties() {
        return this.betreftInspecties;
    }

    public void setBetreftInspecties(Set<Inspectie> inspecties) {
        if (this.betreftInspecties != null) {
            this.betreftInspecties.forEach(i -> i.setBetreftVastgoedobject(null));
        }
        if (inspecties != null) {
            inspecties.forEach(i -> i.setBetreftVastgoedobject(this));
        }
        this.betreftInspecties = inspecties;
    }

    public Vastgoedobject betreftInspecties(Set<Inspectie> inspecties) {
        this.setBetreftInspecties(inspecties);
        return this;
    }

    public Vastgoedobject addBetreftInspectie(Inspectie inspectie) {
        this.betreftInspecties.add(inspectie);
        inspectie.setBetreftVastgoedobject(this);
        return this;
    }

    public Vastgoedobject removeBetreftInspectie(Inspectie inspectie) {
        this.betreftInspecties.remove(inspectie);
        inspectie.setBetreftVastgoedobject(null);
        return this;
    }

    public Verblijfsobject getHeeftVerblijfsobject() {
        return this.heeftVerblijfsobject;
    }

    public void setHeeftVerblijfsobject(Verblijfsobject verblijfsobject) {
        if (this.heeftVerblijfsobject != null) {
            this.heeftVerblijfsobject.setHeeftVastgoedobject(null);
        }
        if (verblijfsobject != null) {
            verblijfsobject.setHeeftVastgoedobject(this);
        }
        this.heeftVerblijfsobject = verblijfsobject;
    }

    public Vastgoedobject heeftVerblijfsobject(Verblijfsobject verblijfsobject) {
        this.setHeeftVerblijfsobject(verblijfsobject);
        return this;
    }

    public Pand getHeeftPand() {
        return this.heeftPand;
    }

    public void setHeeftPand(Pand pand) {
        if (this.heeftPand != null) {
            this.heeftPand.setHeeftVastgoedobject(null);
        }
        if (pand != null) {
            pand.setHeeftVastgoedobject(this);
        }
        this.heeftPand = pand;
    }

    public Vastgoedobject heeftPand(Pand pand) {
        this.setHeeftPand(pand);
        return this;
    }

    public Kostenplaats getHeeftKostenplaats() {
        return this.heeftKostenplaats;
    }

    public void setHeeftKostenplaats(Kostenplaats kostenplaats) {
        this.heeftKostenplaats = kostenplaats;
    }

    public Vastgoedobject heeftKostenplaats(Kostenplaats kostenplaats) {
        this.setHeeftKostenplaats(kostenplaats);
        return this;
    }

    public Set<Werkbon> getBetreftWerkbons() {
        return this.betreftWerkbons;
    }

    public void setBetreftWerkbons(Set<Werkbon> werkbons) {
        if (this.betreftWerkbons != null) {
            this.betreftWerkbons.forEach(i -> i.setBetreftVastgoedobject(null));
        }
        if (werkbons != null) {
            werkbons.forEach(i -> i.setBetreftVastgoedobject(this));
        }
        this.betreftWerkbons = werkbons;
    }

    public Vastgoedobject betreftWerkbons(Set<Werkbon> werkbons) {
        this.setBetreftWerkbons(werkbons);
        return this;
    }

    public Vastgoedobject addBetreftWerkbon(Werkbon werkbon) {
        this.betreftWerkbons.add(werkbon);
        werkbon.setBetreftVastgoedobject(this);
        return this;
    }

    public Vastgoedobject removeBetreftWerkbon(Werkbon werkbon) {
        this.betreftWerkbons.remove(werkbon);
        werkbon.setBetreftVastgoedobject(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vastgoedobject)) {
            return false;
        }
        return getId() != null && getId().equals(((Vastgoedobject) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vastgoedobject{" +
            "id=" + getId() +
            ", aantaletages='" + getAantaletages() + "'" +
            ", aantalparkeerplaatsen='" + getAantalparkeerplaatsen() + "'" +
            ", aantalrioleringen='" + getAantalrioleringen() + "'" +
            ", adresaanduiding='" + getAdresaanduiding() + "'" +
            ", afgekochteerfpacht='" + getAfgekochteerfpacht() + "'" +
            ", afgesprokenconditiescore='" + getAfgesprokenconditiescore() + "'" +
            ", afkoopwaarde=" + getAfkoopwaarde() +
            ", asbestrapportageaanwezig='" + getAsbestrapportageaanwezig() + "'" +
            ", bedragaankoop=" + getBedragaankoop() +
            ", bestemmingsplan='" + getBestemmingsplan() + "'" +
            ", boekwaarde=" + getBoekwaarde() +
            ", bouwjaar='" + getBouwjaar() + "'" +
            ", bouwwerk='" + getBouwwerk() + "'" +
            ", bovenliggendniveau='" + getBovenliggendniveau() + "'" +
            ", bovenliggendniveaucode='" + getBovenliggendniveaucode() + "'" +
            ", brutovloeroppervlakte='" + getBrutovloeroppervlakte() + "'" +
            ", co2uitstoot='" + getCo2uitstoot() + "'" +
            ", conditiescore='" + getConditiescore() + "'" +
            ", datumafstoten='" + getDatumafstoten() + "'" +
            ", datumberekeningoppervlak='" + getDatumberekeningoppervlak() + "'" +
            ", datumeigendom='" + getDatumeigendom() + "'" +
            ", datumverkoop='" + getDatumverkoop() + "'" +
            ", deelportefeuille='" + getDeelportefeuille() + "'" +
            ", energiekosten=" + getEnergiekosten() +
            ", energielabel='" + getEnergielabel() + "'" +
            ", energieverbruik='" + getEnergieverbruik() + "'" +
            ", fiscalewaarde=" + getFiscalewaarde() +
            ", foto='" + getFoto() + "'" +
            ", gearchiveerd='" + getGearchiveerd() + "'" +
            ", herbouwwaarde=" + getHerbouwwaarde() +
            ", hoofdstuk='" + getHoofdstuk() + "'" +
            ", identificatie='" + getIdentificatie() + "'" +
            ", jaarlaatsterenovatie='" + getJaarlaatsterenovatie() + "'" +
            ", locatie='" + getLocatie() + "'" +
            ", marktwaarde=" + getMarktwaarde() +
            ", monument='" + getMonument() + "'" +
            ", naam='" + getNaam() + "'" +
            ", eobjectstatus='" + getEobjectstatus() + "'" +
            ", eobjectstatuscode='" + getEobjectstatuscode() + "'" +
            ", eobjecttype='" + getEobjecttype() + "'" +
            ", eobjecttypecode='" + getEobjecttypecode() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", onderhoudscategorie='" + getOnderhoudscategorie() + "'" +
            ", oppervlaktekantoor='" + getOppervlaktekantoor() + "'" +
            ", portefeuille='" + getPortefeuille() + "'" +
            ", portefeuillecode='" + getPortefeuillecode() + "'" +
            ", provincie='" + getProvincie() + "'" +
            ", toelichting='" + getToelichting() + "'" +
            ", verhuurbaarvloeroppervlak='" + getVerhuurbaarvloeroppervlak() + "'" +
            ", verkoopbaarheid='" + getVerkoopbaarheid() + "'" +
            ", verkoopbedrag=" + getVerkoopbedrag() +
            ", verzekerdewaarde=" + getVerzekerdewaarde() +
            ", waardegrond=" + getWaardegrond() +
            ", waardeopstal=" + getWaardeopstal() +
            ", wijk='" + getWijk() + "'" +
            ", wozwaarde=" + getWozwaarde() +
            "}";
    }
}
