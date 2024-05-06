package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Leverancier.
 */
@Entity
@Table(name = "leverancier")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Leverancier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "agbcode")
    private String agbcode;

    @Size(max = 8)
    @Column(name = "leverancierscode", length = 8)
    private String leverancierscode;

    @Column(name = "naam")
    private String naam;

    @Column(name = "soortleverancier")
    private String soortleverancier;

    @Size(max = 8)
    @Column(name = "soortleveranciercode", length = 8)
    private String soortleveranciercode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftLeverancier")
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
    private Set<Contract> heeftContracts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "leverdeprestatieLeverancier")
    @JsonIgnoreProperties(
        value = {
            "geleverdeprestatieBeschikking",
            "prestatievoorClient",
            "geleverdezorgToewijzing",
            "voorzieningVoorziening",
            "leverdeprestatieLeverancier",
        },
        allowSetters = true
    )
    private Set<Levering> leverdeprestatieLeverings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "voertwerkuitconformLeverancier")
    @JsonIgnoreProperties(
        value = {
            "betreftVastgoedobject", "betreftBouwdeels", "betreftBouwdeelelements", "hoortbijInkooporder", "voertwerkuitconformLeverancier",
        },
        allowSetters = true
    )
    private Set<Werkbon> voertwerkuitconformWerkbons = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contractantLeverancier")
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
    private Set<Contract> contractantContracts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftLeverancier")
    @JsonIgnoreProperties(
        value = { "heeftSchool", "betreftAanbesteding", "betreftGunning", "heeftLeerling", "heeftLeverancier" },
        allowSetters = true
    )
    private Set<Inschrijving> heeftInschrijvings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "biedtaanLeverancier")
    @JsonIgnoreProperties(value = { "betreftGunning", "biedtaanLeverancier" }, allowSetters = true)
    private Set<Kandidaat> biedtaanKandidaats = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftLeverancier")
    @JsonIgnoreProperties(value = { "betreftAanbesteding", "heeftLeverancier" }, allowSetters = true)
    private Set<Kwalificatie> heeftKwalificaties = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_leverancier__gekwalificeerd_categorie",
        joinColumns = @JoinColumn(name = "leverancier_id"),
        inverseJoinColumns = @JoinColumn(name = "gekwalificeerd_categorie_id")
    )
    @JsonIgnoreProperties(
        value = { "heeftRaadsstuks", "hoofdcategorieMeldings", "subcategorieMeldings", "gekwalificeerdLeveranciers" },
        allowSetters = true
    )
    private Set<Categorie> gekwalificeerdCategories = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "leverancierLeverancier")
    @JsonIgnoreProperties(
        value = {
            "heeftprijsPrijs",
            "betreftZaaktypes",
            "leverancierLeverancier",
            "heeftKostenplaats",
            "valtbinnenOmzetgroeps",
            "valtbinnenProductgroeps",
            "betreftWinkelvoorraaditem",
            "heeftDoelstelling",
            "isopdrachtgeverOpdrachtgever",
            "isopdrachtnemerOpdrachtnemer",
            "betreftBalieverkoops",
            "betreftDiensts",
            "betreftBegrotingregels",
        },
        allowSetters = true
    )
    private Set<Product> leverancierProducts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ingedienddoorLeverancier")
    @JsonIgnoreProperties(
        value = { "ingedienddoorLeverancier", "soortdeclaratieDeclaratiesoort", "dientinWerknemer", "valtbinnenDeclaratieregels" },
        allowSetters = true
    )
    private Set<Declaratie> ingedienddoorDeclaraties = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "levertvoorzieningLeverancier")
    @JsonIgnoreProperties(
        value = { "isopbasisvanDeclaratieregels", "levertvoorzieningLeverancier", "toewijzingBeschikking", "geleverdezorgLeverings" },
        allowSetters = true
    )
    private Set<Toewijzing> levertvoorzieningToewijzings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftLeverancier")
    @JsonIgnoreProperties(value = { "heeftLeverancier", "bevatContract", "heeftVoorziening" }, allowSetters = true)
    private Set<Tarief> heeftTariefs = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "uitvoerderLeverancier")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftleverancierLeverancier")
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
    private Set<Applicatie> heeftleverancierApplicaties = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftleverancierLeverancier")
    @JsonIgnoreProperties(value = { "heeftleverancierLeverancier", "servervandatabaseDatabases" }, allowSetters = true)
    private Set<Server> heeftleverancierServers = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "crediteurLeverancier")
    @JsonIgnoreProperties(
        value = { "heeftFactuurregels", "schrijftopKostenplaats", "gedektviaInkooporder", "crediteurLeverancier", "heeftDebiteur" },
        allowSetters = true
    )
    private Set<Factuur> crediteurFactuurs = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "verplichtingaanLeverancier")
    @JsonIgnoreProperties(
        value = {
            "betreftContract",
            "oorspronkelijkInkooporder",
            "hoortbijWerkbons",
            "gerelateerdInkooporders",
            "heeftInkooppakket",
            "verplichtingaanLeverancier",
            "wordtgeschrevenopHoofdrekenings",
            "oorspronkelijkInkooporder2",
            "gerelateerdInkooporder2",
            "gedektviaFactuurs",
            "heeftKostenplaats",
        },
        allowSetters = true
    )
    private Set<Inkooporder> verplichtingaanInkooporders = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "gerichtaanLeverancier")
    @JsonIgnoreProperties(value = { "gerichtaanLeverancier" }, allowSetters = true)
    private Set<Uitnodiging> gerichtaanUitnodigings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "geleverdviaLeverancier")
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
    private Set<Medewerker> geleverdviaMedewerkers = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "gerichtaanLeverancier")
    @JsonIgnoreProperties(value = { "betreftAanbesteding", "gerichtaanLeverancier" }, allowSetters = true)
    private Set<Offerteaanvraag> gerichtaanOfferteaanvraags = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ingedienddoorLeverancier")
    @JsonIgnoreProperties(value = { "betreftAanbesteding", "ingedienddoorLeverancier", "betreftGunning" }, allowSetters = true)
    private Set<Offerte> ingedienddoorOffertes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Leverancier id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgbcode() {
        return this.agbcode;
    }

    public Leverancier agbcode(String agbcode) {
        this.setAgbcode(agbcode);
        return this;
    }

    public void setAgbcode(String agbcode) {
        this.agbcode = agbcode;
    }

    public String getLeverancierscode() {
        return this.leverancierscode;
    }

    public Leverancier leverancierscode(String leverancierscode) {
        this.setLeverancierscode(leverancierscode);
        return this;
    }

    public void setLeverancierscode(String leverancierscode) {
        this.leverancierscode = leverancierscode;
    }

    public String getNaam() {
        return this.naam;
    }

    public Leverancier naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getSoortleverancier() {
        return this.soortleverancier;
    }

    public Leverancier soortleverancier(String soortleverancier) {
        this.setSoortleverancier(soortleverancier);
        return this;
    }

    public void setSoortleverancier(String soortleverancier) {
        this.soortleverancier = soortleverancier;
    }

    public String getSoortleveranciercode() {
        return this.soortleveranciercode;
    }

    public Leverancier soortleveranciercode(String soortleveranciercode) {
        this.setSoortleveranciercode(soortleveranciercode);
        return this;
    }

    public void setSoortleveranciercode(String soortleveranciercode) {
        this.soortleveranciercode = soortleveranciercode;
    }

    public Set<Contract> getHeeftContracts() {
        return this.heeftContracts;
    }

    public void setHeeftContracts(Set<Contract> contracts) {
        if (this.heeftContracts != null) {
            this.heeftContracts.forEach(i -> i.setHeeftLeverancier(null));
        }
        if (contracts != null) {
            contracts.forEach(i -> i.setHeeftLeverancier(this));
        }
        this.heeftContracts = contracts;
    }

    public Leverancier heeftContracts(Set<Contract> contracts) {
        this.setHeeftContracts(contracts);
        return this;
    }

    public Leverancier addHeeftContract(Contract contract) {
        this.heeftContracts.add(contract);
        contract.setHeeftLeverancier(this);
        return this;
    }

    public Leverancier removeHeeftContract(Contract contract) {
        this.heeftContracts.remove(contract);
        contract.setHeeftLeverancier(null);
        return this;
    }

    public Set<Levering> getLeverdeprestatieLeverings() {
        return this.leverdeprestatieLeverings;
    }

    public void setLeverdeprestatieLeverings(Set<Levering> leverings) {
        if (this.leverdeprestatieLeverings != null) {
            this.leverdeprestatieLeverings.forEach(i -> i.setLeverdeprestatieLeverancier(null));
        }
        if (leverings != null) {
            leverings.forEach(i -> i.setLeverdeprestatieLeverancier(this));
        }
        this.leverdeprestatieLeverings = leverings;
    }

    public Leverancier leverdeprestatieLeverings(Set<Levering> leverings) {
        this.setLeverdeprestatieLeverings(leverings);
        return this;
    }

    public Leverancier addLeverdeprestatieLevering(Levering levering) {
        this.leverdeprestatieLeverings.add(levering);
        levering.setLeverdeprestatieLeverancier(this);
        return this;
    }

    public Leverancier removeLeverdeprestatieLevering(Levering levering) {
        this.leverdeprestatieLeverings.remove(levering);
        levering.setLeverdeprestatieLeverancier(null);
        return this;
    }

    public Set<Werkbon> getVoertwerkuitconformWerkbons() {
        return this.voertwerkuitconformWerkbons;
    }

    public void setVoertwerkuitconformWerkbons(Set<Werkbon> werkbons) {
        if (this.voertwerkuitconformWerkbons != null) {
            this.voertwerkuitconformWerkbons.forEach(i -> i.setVoertwerkuitconformLeverancier(null));
        }
        if (werkbons != null) {
            werkbons.forEach(i -> i.setVoertwerkuitconformLeverancier(this));
        }
        this.voertwerkuitconformWerkbons = werkbons;
    }

    public Leverancier voertwerkuitconformWerkbons(Set<Werkbon> werkbons) {
        this.setVoertwerkuitconformWerkbons(werkbons);
        return this;
    }

    public Leverancier addVoertwerkuitconformWerkbon(Werkbon werkbon) {
        this.voertwerkuitconformWerkbons.add(werkbon);
        werkbon.setVoertwerkuitconformLeverancier(this);
        return this;
    }

    public Leverancier removeVoertwerkuitconformWerkbon(Werkbon werkbon) {
        this.voertwerkuitconformWerkbons.remove(werkbon);
        werkbon.setVoertwerkuitconformLeverancier(null);
        return this;
    }

    public Set<Contract> getContractantContracts() {
        return this.contractantContracts;
    }

    public void setContractantContracts(Set<Contract> contracts) {
        if (this.contractantContracts != null) {
            this.contractantContracts.forEach(i -> i.setContractantLeverancier(null));
        }
        if (contracts != null) {
            contracts.forEach(i -> i.setContractantLeverancier(this));
        }
        this.contractantContracts = contracts;
    }

    public Leverancier contractantContracts(Set<Contract> contracts) {
        this.setContractantContracts(contracts);
        return this;
    }

    public Leverancier addContractantContract(Contract contract) {
        this.contractantContracts.add(contract);
        contract.setContractantLeverancier(this);
        return this;
    }

    public Leverancier removeContractantContract(Contract contract) {
        this.contractantContracts.remove(contract);
        contract.setContractantLeverancier(null);
        return this;
    }

    public Set<Inschrijving> getHeeftInschrijvings() {
        return this.heeftInschrijvings;
    }

    public void setHeeftInschrijvings(Set<Inschrijving> inschrijvings) {
        if (this.heeftInschrijvings != null) {
            this.heeftInschrijvings.forEach(i -> i.setHeeftLeverancier(null));
        }
        if (inschrijvings != null) {
            inschrijvings.forEach(i -> i.setHeeftLeverancier(this));
        }
        this.heeftInschrijvings = inschrijvings;
    }

    public Leverancier heeftInschrijvings(Set<Inschrijving> inschrijvings) {
        this.setHeeftInschrijvings(inschrijvings);
        return this;
    }

    public Leverancier addHeeftInschrijving(Inschrijving inschrijving) {
        this.heeftInschrijvings.add(inschrijving);
        inschrijving.setHeeftLeverancier(this);
        return this;
    }

    public Leverancier removeHeeftInschrijving(Inschrijving inschrijving) {
        this.heeftInschrijvings.remove(inschrijving);
        inschrijving.setHeeftLeverancier(null);
        return this;
    }

    public Set<Kandidaat> getBiedtaanKandidaats() {
        return this.biedtaanKandidaats;
    }

    public void setBiedtaanKandidaats(Set<Kandidaat> kandidaats) {
        if (this.biedtaanKandidaats != null) {
            this.biedtaanKandidaats.forEach(i -> i.setBiedtaanLeverancier(null));
        }
        if (kandidaats != null) {
            kandidaats.forEach(i -> i.setBiedtaanLeverancier(this));
        }
        this.biedtaanKandidaats = kandidaats;
    }

    public Leverancier biedtaanKandidaats(Set<Kandidaat> kandidaats) {
        this.setBiedtaanKandidaats(kandidaats);
        return this;
    }

    public Leverancier addBiedtaanKandidaat(Kandidaat kandidaat) {
        this.biedtaanKandidaats.add(kandidaat);
        kandidaat.setBiedtaanLeverancier(this);
        return this;
    }

    public Leverancier removeBiedtaanKandidaat(Kandidaat kandidaat) {
        this.biedtaanKandidaats.remove(kandidaat);
        kandidaat.setBiedtaanLeverancier(null);
        return this;
    }

    public Set<Kwalificatie> getHeeftKwalificaties() {
        return this.heeftKwalificaties;
    }

    public void setHeeftKwalificaties(Set<Kwalificatie> kwalificaties) {
        if (this.heeftKwalificaties != null) {
            this.heeftKwalificaties.forEach(i -> i.setHeeftLeverancier(null));
        }
        if (kwalificaties != null) {
            kwalificaties.forEach(i -> i.setHeeftLeverancier(this));
        }
        this.heeftKwalificaties = kwalificaties;
    }

    public Leverancier heeftKwalificaties(Set<Kwalificatie> kwalificaties) {
        this.setHeeftKwalificaties(kwalificaties);
        return this;
    }

    public Leverancier addHeeftKwalificatie(Kwalificatie kwalificatie) {
        this.heeftKwalificaties.add(kwalificatie);
        kwalificatie.setHeeftLeverancier(this);
        return this;
    }

    public Leverancier removeHeeftKwalificatie(Kwalificatie kwalificatie) {
        this.heeftKwalificaties.remove(kwalificatie);
        kwalificatie.setHeeftLeverancier(null);
        return this;
    }

    public Set<Categorie> getGekwalificeerdCategories() {
        return this.gekwalificeerdCategories;
    }

    public void setGekwalificeerdCategories(Set<Categorie> categories) {
        this.gekwalificeerdCategories = categories;
    }

    public Leverancier gekwalificeerdCategories(Set<Categorie> categories) {
        this.setGekwalificeerdCategories(categories);
        return this;
    }

    public Leverancier addGekwalificeerdCategorie(Categorie categorie) {
        this.gekwalificeerdCategories.add(categorie);
        return this;
    }

    public Leverancier removeGekwalificeerdCategorie(Categorie categorie) {
        this.gekwalificeerdCategories.remove(categorie);
        return this;
    }

    public Set<Product> getLeverancierProducts() {
        return this.leverancierProducts;
    }

    public void setLeverancierProducts(Set<Product> products) {
        if (this.leverancierProducts != null) {
            this.leverancierProducts.forEach(i -> i.setLeverancierLeverancier(null));
        }
        if (products != null) {
            products.forEach(i -> i.setLeverancierLeverancier(this));
        }
        this.leverancierProducts = products;
    }

    public Leverancier leverancierProducts(Set<Product> products) {
        this.setLeverancierProducts(products);
        return this;
    }

    public Leverancier addLeverancierProduct(Product product) {
        this.leverancierProducts.add(product);
        product.setLeverancierLeverancier(this);
        return this;
    }

    public Leverancier removeLeverancierProduct(Product product) {
        this.leverancierProducts.remove(product);
        product.setLeverancierLeverancier(null);
        return this;
    }

    public Set<Declaratie> getIngedienddoorDeclaraties() {
        return this.ingedienddoorDeclaraties;
    }

    public void setIngedienddoorDeclaraties(Set<Declaratie> declaraties) {
        if (this.ingedienddoorDeclaraties != null) {
            this.ingedienddoorDeclaraties.forEach(i -> i.setIngedienddoorLeverancier(null));
        }
        if (declaraties != null) {
            declaraties.forEach(i -> i.setIngedienddoorLeverancier(this));
        }
        this.ingedienddoorDeclaraties = declaraties;
    }

    public Leverancier ingedienddoorDeclaraties(Set<Declaratie> declaraties) {
        this.setIngedienddoorDeclaraties(declaraties);
        return this;
    }

    public Leverancier addIngedienddoorDeclaratie(Declaratie declaratie) {
        this.ingedienddoorDeclaraties.add(declaratie);
        declaratie.setIngedienddoorLeverancier(this);
        return this;
    }

    public Leverancier removeIngedienddoorDeclaratie(Declaratie declaratie) {
        this.ingedienddoorDeclaraties.remove(declaratie);
        declaratie.setIngedienddoorLeverancier(null);
        return this;
    }

    public Set<Toewijzing> getLevertvoorzieningToewijzings() {
        return this.levertvoorzieningToewijzings;
    }

    public void setLevertvoorzieningToewijzings(Set<Toewijzing> toewijzings) {
        if (this.levertvoorzieningToewijzings != null) {
            this.levertvoorzieningToewijzings.forEach(i -> i.setLevertvoorzieningLeverancier(null));
        }
        if (toewijzings != null) {
            toewijzings.forEach(i -> i.setLevertvoorzieningLeverancier(this));
        }
        this.levertvoorzieningToewijzings = toewijzings;
    }

    public Leverancier levertvoorzieningToewijzings(Set<Toewijzing> toewijzings) {
        this.setLevertvoorzieningToewijzings(toewijzings);
        return this;
    }

    public Leverancier addLevertvoorzieningToewijzing(Toewijzing toewijzing) {
        this.levertvoorzieningToewijzings.add(toewijzing);
        toewijzing.setLevertvoorzieningLeverancier(this);
        return this;
    }

    public Leverancier removeLevertvoorzieningToewijzing(Toewijzing toewijzing) {
        this.levertvoorzieningToewijzings.remove(toewijzing);
        toewijzing.setLevertvoorzieningLeverancier(null);
        return this;
    }

    public Set<Tarief> getHeeftTariefs() {
        return this.heeftTariefs;
    }

    public void setHeeftTariefs(Set<Tarief> tariefs) {
        if (this.heeftTariefs != null) {
            this.heeftTariefs.forEach(i -> i.setHeeftLeverancier(null));
        }
        if (tariefs != null) {
            tariefs.forEach(i -> i.setHeeftLeverancier(this));
        }
        this.heeftTariefs = tariefs;
    }

    public Leverancier heeftTariefs(Set<Tarief> tariefs) {
        this.setHeeftTariefs(tariefs);
        return this;
    }

    public Leverancier addHeeftTarief(Tarief tarief) {
        this.heeftTariefs.add(tarief);
        tarief.setHeeftLeverancier(this);
        return this;
    }

    public Leverancier removeHeeftTarief(Tarief tarief) {
        this.heeftTariefs.remove(tarief);
        tarief.setHeeftLeverancier(null);
        return this;
    }

    public Set<Melding> getUitvoerderMeldings() {
        return this.uitvoerderMeldings;
    }

    public void setUitvoerderMeldings(Set<Melding> meldings) {
        if (this.uitvoerderMeldings != null) {
            this.uitvoerderMeldings.forEach(i -> i.setUitvoerderLeverancier(null));
        }
        if (meldings != null) {
            meldings.forEach(i -> i.setUitvoerderLeverancier(this));
        }
        this.uitvoerderMeldings = meldings;
    }

    public Leverancier uitvoerderMeldings(Set<Melding> meldings) {
        this.setUitvoerderMeldings(meldings);
        return this;
    }

    public Leverancier addUitvoerderMelding(Melding melding) {
        this.uitvoerderMeldings.add(melding);
        melding.setUitvoerderLeverancier(this);
        return this;
    }

    public Leverancier removeUitvoerderMelding(Melding melding) {
        this.uitvoerderMeldings.remove(melding);
        melding.setUitvoerderLeverancier(null);
        return this;
    }

    public Set<Applicatie> getHeeftleverancierApplicaties() {
        return this.heeftleverancierApplicaties;
    }

    public void setHeeftleverancierApplicaties(Set<Applicatie> applicaties) {
        if (this.heeftleverancierApplicaties != null) {
            this.heeftleverancierApplicaties.forEach(i -> i.setHeeftleverancierLeverancier(null));
        }
        if (applicaties != null) {
            applicaties.forEach(i -> i.setHeeftleverancierLeverancier(this));
        }
        this.heeftleverancierApplicaties = applicaties;
    }

    public Leverancier heeftleverancierApplicaties(Set<Applicatie> applicaties) {
        this.setHeeftleverancierApplicaties(applicaties);
        return this;
    }

    public Leverancier addHeeftleverancierApplicatie(Applicatie applicatie) {
        this.heeftleverancierApplicaties.add(applicatie);
        applicatie.setHeeftleverancierLeverancier(this);
        return this;
    }

    public Leverancier removeHeeftleverancierApplicatie(Applicatie applicatie) {
        this.heeftleverancierApplicaties.remove(applicatie);
        applicatie.setHeeftleverancierLeverancier(null);
        return this;
    }

    public Set<Server> getHeeftleverancierServers() {
        return this.heeftleverancierServers;
    }

    public void setHeeftleverancierServers(Set<Server> servers) {
        if (this.heeftleverancierServers != null) {
            this.heeftleverancierServers.forEach(i -> i.setHeeftleverancierLeverancier(null));
        }
        if (servers != null) {
            servers.forEach(i -> i.setHeeftleverancierLeverancier(this));
        }
        this.heeftleverancierServers = servers;
    }

    public Leverancier heeftleverancierServers(Set<Server> servers) {
        this.setHeeftleverancierServers(servers);
        return this;
    }

    public Leverancier addHeeftleverancierServer(Server server) {
        this.heeftleverancierServers.add(server);
        server.setHeeftleverancierLeverancier(this);
        return this;
    }

    public Leverancier removeHeeftleverancierServer(Server server) {
        this.heeftleverancierServers.remove(server);
        server.setHeeftleverancierLeverancier(null);
        return this;
    }

    public Set<Factuur> getCrediteurFactuurs() {
        return this.crediteurFactuurs;
    }

    public void setCrediteurFactuurs(Set<Factuur> factuurs) {
        if (this.crediteurFactuurs != null) {
            this.crediteurFactuurs.forEach(i -> i.setCrediteurLeverancier(null));
        }
        if (factuurs != null) {
            factuurs.forEach(i -> i.setCrediteurLeverancier(this));
        }
        this.crediteurFactuurs = factuurs;
    }

    public Leverancier crediteurFactuurs(Set<Factuur> factuurs) {
        this.setCrediteurFactuurs(factuurs);
        return this;
    }

    public Leverancier addCrediteurFactuur(Factuur factuur) {
        this.crediteurFactuurs.add(factuur);
        factuur.setCrediteurLeverancier(this);
        return this;
    }

    public Leverancier removeCrediteurFactuur(Factuur factuur) {
        this.crediteurFactuurs.remove(factuur);
        factuur.setCrediteurLeverancier(null);
        return this;
    }

    public Set<Inkooporder> getVerplichtingaanInkooporders() {
        return this.verplichtingaanInkooporders;
    }

    public void setVerplichtingaanInkooporders(Set<Inkooporder> inkooporders) {
        if (this.verplichtingaanInkooporders != null) {
            this.verplichtingaanInkooporders.forEach(i -> i.setVerplichtingaanLeverancier(null));
        }
        if (inkooporders != null) {
            inkooporders.forEach(i -> i.setVerplichtingaanLeverancier(this));
        }
        this.verplichtingaanInkooporders = inkooporders;
    }

    public Leverancier verplichtingaanInkooporders(Set<Inkooporder> inkooporders) {
        this.setVerplichtingaanInkooporders(inkooporders);
        return this;
    }

    public Leverancier addVerplichtingaanInkooporder(Inkooporder inkooporder) {
        this.verplichtingaanInkooporders.add(inkooporder);
        inkooporder.setVerplichtingaanLeverancier(this);
        return this;
    }

    public Leverancier removeVerplichtingaanInkooporder(Inkooporder inkooporder) {
        this.verplichtingaanInkooporders.remove(inkooporder);
        inkooporder.setVerplichtingaanLeverancier(null);
        return this;
    }

    public Set<Uitnodiging> getGerichtaanUitnodigings() {
        return this.gerichtaanUitnodigings;
    }

    public void setGerichtaanUitnodigings(Set<Uitnodiging> uitnodigings) {
        if (this.gerichtaanUitnodigings != null) {
            this.gerichtaanUitnodigings.forEach(i -> i.setGerichtaanLeverancier(null));
        }
        if (uitnodigings != null) {
            uitnodigings.forEach(i -> i.setGerichtaanLeverancier(this));
        }
        this.gerichtaanUitnodigings = uitnodigings;
    }

    public Leverancier gerichtaanUitnodigings(Set<Uitnodiging> uitnodigings) {
        this.setGerichtaanUitnodigings(uitnodigings);
        return this;
    }

    public Leverancier addGerichtaanUitnodiging(Uitnodiging uitnodiging) {
        this.gerichtaanUitnodigings.add(uitnodiging);
        uitnodiging.setGerichtaanLeverancier(this);
        return this;
    }

    public Leverancier removeGerichtaanUitnodiging(Uitnodiging uitnodiging) {
        this.gerichtaanUitnodigings.remove(uitnodiging);
        uitnodiging.setGerichtaanLeverancier(null);
        return this;
    }

    public Set<Medewerker> getGeleverdviaMedewerkers() {
        return this.geleverdviaMedewerkers;
    }

    public void setGeleverdviaMedewerkers(Set<Medewerker> medewerkers) {
        if (this.geleverdviaMedewerkers != null) {
            this.geleverdviaMedewerkers.forEach(i -> i.setGeleverdviaLeverancier(null));
        }
        if (medewerkers != null) {
            medewerkers.forEach(i -> i.setGeleverdviaLeverancier(this));
        }
        this.geleverdviaMedewerkers = medewerkers;
    }

    public Leverancier geleverdviaMedewerkers(Set<Medewerker> medewerkers) {
        this.setGeleverdviaMedewerkers(medewerkers);
        return this;
    }

    public Leverancier addGeleverdviaMedewerker(Medewerker medewerker) {
        this.geleverdviaMedewerkers.add(medewerker);
        medewerker.setGeleverdviaLeverancier(this);
        return this;
    }

    public Leverancier removeGeleverdviaMedewerker(Medewerker medewerker) {
        this.geleverdviaMedewerkers.remove(medewerker);
        medewerker.setGeleverdviaLeverancier(null);
        return this;
    }

    public Set<Offerteaanvraag> getGerichtaanOfferteaanvraags() {
        return this.gerichtaanOfferteaanvraags;
    }

    public void setGerichtaanOfferteaanvraags(Set<Offerteaanvraag> offerteaanvraags) {
        if (this.gerichtaanOfferteaanvraags != null) {
            this.gerichtaanOfferteaanvraags.forEach(i -> i.setGerichtaanLeverancier(null));
        }
        if (offerteaanvraags != null) {
            offerteaanvraags.forEach(i -> i.setGerichtaanLeverancier(this));
        }
        this.gerichtaanOfferteaanvraags = offerteaanvraags;
    }

    public Leverancier gerichtaanOfferteaanvraags(Set<Offerteaanvraag> offerteaanvraags) {
        this.setGerichtaanOfferteaanvraags(offerteaanvraags);
        return this;
    }

    public Leverancier addGerichtaanOfferteaanvraag(Offerteaanvraag offerteaanvraag) {
        this.gerichtaanOfferteaanvraags.add(offerteaanvraag);
        offerteaanvraag.setGerichtaanLeverancier(this);
        return this;
    }

    public Leverancier removeGerichtaanOfferteaanvraag(Offerteaanvraag offerteaanvraag) {
        this.gerichtaanOfferteaanvraags.remove(offerteaanvraag);
        offerteaanvraag.setGerichtaanLeverancier(null);
        return this;
    }

    public Set<Offerte> getIngedienddoorOffertes() {
        return this.ingedienddoorOffertes;
    }

    public void setIngedienddoorOffertes(Set<Offerte> offertes) {
        if (this.ingedienddoorOffertes != null) {
            this.ingedienddoorOffertes.forEach(i -> i.setIngedienddoorLeverancier(null));
        }
        if (offertes != null) {
            offertes.forEach(i -> i.setIngedienddoorLeverancier(this));
        }
        this.ingedienddoorOffertes = offertes;
    }

    public Leverancier ingedienddoorOffertes(Set<Offerte> offertes) {
        this.setIngedienddoorOffertes(offertes);
        return this;
    }

    public Leverancier addIngedienddoorOfferte(Offerte offerte) {
        this.ingedienddoorOffertes.add(offerte);
        offerte.setIngedienddoorLeverancier(this);
        return this;
    }

    public Leverancier removeIngedienddoorOfferte(Offerte offerte) {
        this.ingedienddoorOffertes.remove(offerte);
        offerte.setIngedienddoorLeverancier(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Leverancier)) {
            return false;
        }
        return getId() != null && getId().equals(((Leverancier) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Leverancier{" +
            "id=" + getId() +
            ", agbcode='" + getAgbcode() + "'" +
            ", leverancierscode='" + getLeverancierscode() + "'" +
            ", naam='" + getNaam() + "'" +
            ", soortleverancier='" + getSoortleverancier() + "'" +
            ", soortleveranciercode='" + getSoortleveranciercode() + "'" +
            "}";
    }
}
