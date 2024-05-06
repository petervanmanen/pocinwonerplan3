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
 * A Product.
 */
@Entity
@Table(name = "product")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "codemuseumjaarkaart")
    private String codemuseumjaarkaart;

    @Column(name = "datumeindegeldigheid")
    private LocalDate datumeindegeldigheid;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "entreekaart")
    private String entreekaart;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "prijs", precision = 21, scale = 2)
    private BigDecimal prijs;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftprijsProduct")
    @JsonIgnoreProperties(value = { "heeftprijsProduct", "tegenprijsBalieverkoops" }, allowSetters = true)
    private Set<Prijs> heeftprijsPrijs = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftProduct")
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
    private Set<Zaaktype> betreftZaaktypes = new HashSet<>();

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
    private Leverancier leverancierLeverancier;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_product__valtbinnen_omzetgroep",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "valtbinnen_omzetgroep_id")
    )
    @JsonIgnoreProperties(value = { "valtbinnenProducts" }, allowSetters = true)
    private Set<Omzetgroep> valtbinnenOmzetgroeps = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_product__valtbinnen_productgroep",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "valtbinnen_productgroep_id")
    )
    @JsonIgnoreProperties(value = { "soortParkeervergunnings", "valtbinnenProductsoorts", "valtbinnenProducts" }, allowSetters = true)
    private Set<Productgroep> valtbinnenProductgroeps = new HashSet<>();

    @JsonIgnoreProperties(value = { "betreftProduct" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "betreftProduct")
    private Winkelvoorraaditem betreftWinkelvoorraaditem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "heeftProducts", "isvansoortDoelstellingsoort", "heeftHoofdstuk", "betreftBegrotingregels" },
        allowSetters = true
    )
    private Doelstelling heeftDoelstelling;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "isopdrachtgeverProducts", "uitgevoerddoorFunctie" }, allowSetters = true)
    private Opdrachtgever isopdrachtgeverOpdrachtgever;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "isopdrachtnemerProducts", "uitgevoerddoorFunctie" }, allowSetters = true)
    private Opdrachtnemer isopdrachtnemerOpdrachtnemer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftProduct")
    @JsonIgnoreProperties(value = { "tegenprijsPrijs", "betreftProduct" }, allowSetters = true)
    private Set<Balieverkoop> betreftBalieverkoops = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftProduct")
    @JsonIgnoreProperties(value = { "startZaaktype", "heeftOnderwerp", "betreftProduct" }, allowSetters = true)
    private Set<Dienst> betreftDiensts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftProduct")
    @JsonIgnoreProperties(
        value = {
            "betreftDoelstelling", "betreftProduct", "betreftKostenplaats", "betreftHoofdrekening", "betreftHoofdstuk", "heeftBegroting",
        },
        allowSetters = true
    )
    private Set<Begrotingregel> betreftBegrotingregels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodemuseumjaarkaart() {
        return this.codemuseumjaarkaart;
    }

    public Product codemuseumjaarkaart(String codemuseumjaarkaart) {
        this.setCodemuseumjaarkaart(codemuseumjaarkaart);
        return this;
    }

    public void setCodemuseumjaarkaart(String codemuseumjaarkaart) {
        this.codemuseumjaarkaart = codemuseumjaarkaart;
    }

    public LocalDate getDatumeindegeldigheid() {
        return this.datumeindegeldigheid;
    }

    public Product datumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.setDatumeindegeldigheid(datumeindegeldigheid);
        return this;
    }

    public void setDatumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.datumeindegeldigheid = datumeindegeldigheid;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Product datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public String getEntreekaart() {
        return this.entreekaart;
    }

    public Product entreekaart(String entreekaart) {
        this.setEntreekaart(entreekaart);
        return this;
    }

    public void setEntreekaart(String entreekaart) {
        this.entreekaart = entreekaart;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Product omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public BigDecimal getPrijs() {
        return this.prijs;
    }

    public Product prijs(BigDecimal prijs) {
        this.setPrijs(prijs);
        return this;
    }

    public void setPrijs(BigDecimal prijs) {
        this.prijs = prijs;
    }

    public Set<Prijs> getHeeftprijsPrijs() {
        return this.heeftprijsPrijs;
    }

    public void setHeeftprijsPrijs(Set<Prijs> prijs) {
        if (this.heeftprijsPrijs != null) {
            this.heeftprijsPrijs.forEach(i -> i.setHeeftprijsProduct(null));
        }
        if (prijs != null) {
            prijs.forEach(i -> i.setHeeftprijsProduct(this));
        }
        this.heeftprijsPrijs = prijs;
    }

    public Product heeftprijsPrijs(Set<Prijs> prijs) {
        this.setHeeftprijsPrijs(prijs);
        return this;
    }

    public Product addHeeftprijsPrijs(Prijs prijs) {
        this.heeftprijsPrijs.add(prijs);
        prijs.setHeeftprijsProduct(this);
        return this;
    }

    public Product removeHeeftprijsPrijs(Prijs prijs) {
        this.heeftprijsPrijs.remove(prijs);
        prijs.setHeeftprijsProduct(null);
        return this;
    }

    public Set<Zaaktype> getBetreftZaaktypes() {
        return this.betreftZaaktypes;
    }

    public void setBetreftZaaktypes(Set<Zaaktype> zaaktypes) {
        if (this.betreftZaaktypes != null) {
            this.betreftZaaktypes.forEach(i -> i.setBetreftProduct(null));
        }
        if (zaaktypes != null) {
            zaaktypes.forEach(i -> i.setBetreftProduct(this));
        }
        this.betreftZaaktypes = zaaktypes;
    }

    public Product betreftZaaktypes(Set<Zaaktype> zaaktypes) {
        this.setBetreftZaaktypes(zaaktypes);
        return this;
    }

    public Product addBetreftZaaktype(Zaaktype zaaktype) {
        this.betreftZaaktypes.add(zaaktype);
        zaaktype.setBetreftProduct(this);
        return this;
    }

    public Product removeBetreftZaaktype(Zaaktype zaaktype) {
        this.betreftZaaktypes.remove(zaaktype);
        zaaktype.setBetreftProduct(null);
        return this;
    }

    public Leverancier getLeverancierLeverancier() {
        return this.leverancierLeverancier;
    }

    public void setLeverancierLeverancier(Leverancier leverancier) {
        this.leverancierLeverancier = leverancier;
    }

    public Product leverancierLeverancier(Leverancier leverancier) {
        this.setLeverancierLeverancier(leverancier);
        return this;
    }

    public Kostenplaats getHeeftKostenplaats() {
        return this.heeftKostenplaats;
    }

    public void setHeeftKostenplaats(Kostenplaats kostenplaats) {
        this.heeftKostenplaats = kostenplaats;
    }

    public Product heeftKostenplaats(Kostenplaats kostenplaats) {
        this.setHeeftKostenplaats(kostenplaats);
        return this;
    }

    public Set<Omzetgroep> getValtbinnenOmzetgroeps() {
        return this.valtbinnenOmzetgroeps;
    }

    public void setValtbinnenOmzetgroeps(Set<Omzetgroep> omzetgroeps) {
        this.valtbinnenOmzetgroeps = omzetgroeps;
    }

    public Product valtbinnenOmzetgroeps(Set<Omzetgroep> omzetgroeps) {
        this.setValtbinnenOmzetgroeps(omzetgroeps);
        return this;
    }

    public Product addValtbinnenOmzetgroep(Omzetgroep omzetgroep) {
        this.valtbinnenOmzetgroeps.add(omzetgroep);
        return this;
    }

    public Product removeValtbinnenOmzetgroep(Omzetgroep omzetgroep) {
        this.valtbinnenOmzetgroeps.remove(omzetgroep);
        return this;
    }

    public Set<Productgroep> getValtbinnenProductgroeps() {
        return this.valtbinnenProductgroeps;
    }

    public void setValtbinnenProductgroeps(Set<Productgroep> productgroeps) {
        this.valtbinnenProductgroeps = productgroeps;
    }

    public Product valtbinnenProductgroeps(Set<Productgroep> productgroeps) {
        this.setValtbinnenProductgroeps(productgroeps);
        return this;
    }

    public Product addValtbinnenProductgroep(Productgroep productgroep) {
        this.valtbinnenProductgroeps.add(productgroep);
        return this;
    }

    public Product removeValtbinnenProductgroep(Productgroep productgroep) {
        this.valtbinnenProductgroeps.remove(productgroep);
        return this;
    }

    public Winkelvoorraaditem getBetreftWinkelvoorraaditem() {
        return this.betreftWinkelvoorraaditem;
    }

    public void setBetreftWinkelvoorraaditem(Winkelvoorraaditem winkelvoorraaditem) {
        if (this.betreftWinkelvoorraaditem != null) {
            this.betreftWinkelvoorraaditem.setBetreftProduct(null);
        }
        if (winkelvoorraaditem != null) {
            winkelvoorraaditem.setBetreftProduct(this);
        }
        this.betreftWinkelvoorraaditem = winkelvoorraaditem;
    }

    public Product betreftWinkelvoorraaditem(Winkelvoorraaditem winkelvoorraaditem) {
        this.setBetreftWinkelvoorraaditem(winkelvoorraaditem);
        return this;
    }

    public Doelstelling getHeeftDoelstelling() {
        return this.heeftDoelstelling;
    }

    public void setHeeftDoelstelling(Doelstelling doelstelling) {
        this.heeftDoelstelling = doelstelling;
    }

    public Product heeftDoelstelling(Doelstelling doelstelling) {
        this.setHeeftDoelstelling(doelstelling);
        return this;
    }

    public Opdrachtgever getIsopdrachtgeverOpdrachtgever() {
        return this.isopdrachtgeverOpdrachtgever;
    }

    public void setIsopdrachtgeverOpdrachtgever(Opdrachtgever opdrachtgever) {
        this.isopdrachtgeverOpdrachtgever = opdrachtgever;
    }

    public Product isopdrachtgeverOpdrachtgever(Opdrachtgever opdrachtgever) {
        this.setIsopdrachtgeverOpdrachtgever(opdrachtgever);
        return this;
    }

    public Opdrachtnemer getIsopdrachtnemerOpdrachtnemer() {
        return this.isopdrachtnemerOpdrachtnemer;
    }

    public void setIsopdrachtnemerOpdrachtnemer(Opdrachtnemer opdrachtnemer) {
        this.isopdrachtnemerOpdrachtnemer = opdrachtnemer;
    }

    public Product isopdrachtnemerOpdrachtnemer(Opdrachtnemer opdrachtnemer) {
        this.setIsopdrachtnemerOpdrachtnemer(opdrachtnemer);
        return this;
    }

    public Set<Balieverkoop> getBetreftBalieverkoops() {
        return this.betreftBalieverkoops;
    }

    public void setBetreftBalieverkoops(Set<Balieverkoop> balieverkoops) {
        if (this.betreftBalieverkoops != null) {
            this.betreftBalieverkoops.forEach(i -> i.setBetreftProduct(null));
        }
        if (balieverkoops != null) {
            balieverkoops.forEach(i -> i.setBetreftProduct(this));
        }
        this.betreftBalieverkoops = balieverkoops;
    }

    public Product betreftBalieverkoops(Set<Balieverkoop> balieverkoops) {
        this.setBetreftBalieverkoops(balieverkoops);
        return this;
    }

    public Product addBetreftBalieverkoop(Balieverkoop balieverkoop) {
        this.betreftBalieverkoops.add(balieverkoop);
        balieverkoop.setBetreftProduct(this);
        return this;
    }

    public Product removeBetreftBalieverkoop(Balieverkoop balieverkoop) {
        this.betreftBalieverkoops.remove(balieverkoop);
        balieverkoop.setBetreftProduct(null);
        return this;
    }

    public Set<Dienst> getBetreftDiensts() {
        return this.betreftDiensts;
    }

    public void setBetreftDiensts(Set<Dienst> diensts) {
        if (this.betreftDiensts != null) {
            this.betreftDiensts.forEach(i -> i.setBetreftProduct(null));
        }
        if (diensts != null) {
            diensts.forEach(i -> i.setBetreftProduct(this));
        }
        this.betreftDiensts = diensts;
    }

    public Product betreftDiensts(Set<Dienst> diensts) {
        this.setBetreftDiensts(diensts);
        return this;
    }

    public Product addBetreftDienst(Dienst dienst) {
        this.betreftDiensts.add(dienst);
        dienst.setBetreftProduct(this);
        return this;
    }

    public Product removeBetreftDienst(Dienst dienst) {
        this.betreftDiensts.remove(dienst);
        dienst.setBetreftProduct(null);
        return this;
    }

    public Set<Begrotingregel> getBetreftBegrotingregels() {
        return this.betreftBegrotingregels;
    }

    public void setBetreftBegrotingregels(Set<Begrotingregel> begrotingregels) {
        if (this.betreftBegrotingregels != null) {
            this.betreftBegrotingregels.forEach(i -> i.setBetreftProduct(null));
        }
        if (begrotingregels != null) {
            begrotingregels.forEach(i -> i.setBetreftProduct(this));
        }
        this.betreftBegrotingregels = begrotingregels;
    }

    public Product betreftBegrotingregels(Set<Begrotingregel> begrotingregels) {
        this.setBetreftBegrotingregels(begrotingregels);
        return this;
    }

    public Product addBetreftBegrotingregel(Begrotingregel begrotingregel) {
        this.betreftBegrotingregels.add(begrotingregel);
        begrotingregel.setBetreftProduct(this);
        return this;
    }

    public Product removeBetreftBegrotingregel(Begrotingregel begrotingregel) {
        this.betreftBegrotingregels.remove(begrotingregel);
        begrotingregel.setBetreftProduct(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return getId() != null && getId().equals(((Product) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", codemuseumjaarkaart='" + getCodemuseumjaarkaart() + "'" +
            ", datumeindegeldigheid='" + getDatumeindegeldigheid() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", entreekaart='" + getEntreekaart() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", prijs=" + getPrijs() +
            "}";
    }
}
