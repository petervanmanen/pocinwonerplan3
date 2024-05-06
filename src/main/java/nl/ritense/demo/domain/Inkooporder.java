package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A Inkooporder.
 */
@Entity
@Table(name = "inkooporder")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Inkooporder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "artikelcode")
    private String artikelcode;

    @Column(name = "betalingmeerderejaren")
    private Boolean betalingmeerderejaren;

    @Column(name = "betreft")
    private String betreft;

    @Column(name = "datumeinde")
    private String datumeinde;

    @Column(name = "datumingediend")
    private String datumingediend;

    @Column(name = "datumstart")
    private String datumstart;

    @Column(name = "goederencode")
    private String goederencode;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Size(max = 8)
    @Column(name = "ordernummer", length = 8)
    private String ordernummer;

    @Column(name = "saldo", precision = 21, scale = 2)
    private BigDecimal saldo;

    @Column(name = "totaalnettobedrag", precision = 21, scale = 2)
    private BigDecimal totaalnettobedrag;

    @Column(name = "wijzevanaanbesteden")
    private String wijzevanaanbesteden;

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Contract betreftContract;

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Inkooporder oorspronkelijkInkooporder;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hoortbijInkooporder")
    @JsonIgnoreProperties(
        value = {
            "betreftVastgoedobject", "betreftBouwdeels", "betreftBouwdeelelements", "hoortbijInkooporder", "voertwerkuitconformLeverancier",
        },
        allowSetters = true
    )
    private Set<Werkbon> hoortbijWerkbons = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "gerelateerdInkooporder2")
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
    private Set<Inkooporder> gerelateerdInkooporders = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftInkooporders" }, allowSetters = true)
    private Inkooppakket heeftInkooppakket;

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
    private Leverancier verplichtingaanLeverancier;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_inkooporder__wordtgeschrevenop_hoofdrekening",
        joinColumns = @JoinColumn(name = "inkooporder_id"),
        inverseJoinColumns = @JoinColumn(name = "wordtgeschrevenop_hoofdrekening_id")
    )
    @JsonIgnoreProperties(
        value = {
            "valtbinnenHoofdrekenings",
            "heeftSubrekenings",
            "heeftWerkorders",
            "heeftActivas",
            "heeftKostenplaats",
            "valtbinnenHoofdrekening2",
            "betreftBegrotingregels",
            "vanMutaties",
            "naarMutaties",
            "wordtgeschrevenopInkooporders",
        },
        allowSetters = true
    )
    private Set<Hoofdrekening> wordtgeschrevenopHoofdrekenings = new HashSet<>();

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
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "oorspronkelijkInkooporder")
    private Inkooporder oorspronkelijkInkooporder2;

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Inkooporder gerelateerdInkooporder2;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "gedektviaInkooporder")
    @JsonIgnoreProperties(
        value = { "heeftFactuurregels", "schrijftopKostenplaats", "gedektviaInkooporder", "crediteurLeverancier", "heeftDebiteur" },
        allowSetters = true
    )
    private Set<Factuur> gedektviaFactuurs = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "heeftInkooporders")
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
    private Set<Kostenplaats> heeftKostenplaats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Inkooporder id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArtikelcode() {
        return this.artikelcode;
    }

    public Inkooporder artikelcode(String artikelcode) {
        this.setArtikelcode(artikelcode);
        return this;
    }

    public void setArtikelcode(String artikelcode) {
        this.artikelcode = artikelcode;
    }

    public Boolean getBetalingmeerderejaren() {
        return this.betalingmeerderejaren;
    }

    public Inkooporder betalingmeerderejaren(Boolean betalingmeerderejaren) {
        this.setBetalingmeerderejaren(betalingmeerderejaren);
        return this;
    }

    public void setBetalingmeerderejaren(Boolean betalingmeerderejaren) {
        this.betalingmeerderejaren = betalingmeerderejaren;
    }

    public String getBetreft() {
        return this.betreft;
    }

    public Inkooporder betreft(String betreft) {
        this.setBetreft(betreft);
        return this;
    }

    public void setBetreft(String betreft) {
        this.betreft = betreft;
    }

    public String getDatumeinde() {
        return this.datumeinde;
    }

    public Inkooporder datumeinde(String datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(String datumeinde) {
        this.datumeinde = datumeinde;
    }

    public String getDatumingediend() {
        return this.datumingediend;
    }

    public Inkooporder datumingediend(String datumingediend) {
        this.setDatumingediend(datumingediend);
        return this;
    }

    public void setDatumingediend(String datumingediend) {
        this.datumingediend = datumingediend;
    }

    public String getDatumstart() {
        return this.datumstart;
    }

    public Inkooporder datumstart(String datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(String datumstart) {
        this.datumstart = datumstart;
    }

    public String getGoederencode() {
        return this.goederencode;
    }

    public Inkooporder goederencode(String goederencode) {
        this.setGoederencode(goederencode);
        return this;
    }

    public void setGoederencode(String goederencode) {
        this.goederencode = goederencode;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Inkooporder omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOrdernummer() {
        return this.ordernummer;
    }

    public Inkooporder ordernummer(String ordernummer) {
        this.setOrdernummer(ordernummer);
        return this;
    }

    public void setOrdernummer(String ordernummer) {
        this.ordernummer = ordernummer;
    }

    public BigDecimal getSaldo() {
        return this.saldo;
    }

    public Inkooporder saldo(BigDecimal saldo) {
        this.setSaldo(saldo);
        return this;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getTotaalnettobedrag() {
        return this.totaalnettobedrag;
    }

    public Inkooporder totaalnettobedrag(BigDecimal totaalnettobedrag) {
        this.setTotaalnettobedrag(totaalnettobedrag);
        return this;
    }

    public void setTotaalnettobedrag(BigDecimal totaalnettobedrag) {
        this.totaalnettobedrag = totaalnettobedrag;
    }

    public String getWijzevanaanbesteden() {
        return this.wijzevanaanbesteden;
    }

    public Inkooporder wijzevanaanbesteden(String wijzevanaanbesteden) {
        this.setWijzevanaanbesteden(wijzevanaanbesteden);
        return this;
    }

    public void setWijzevanaanbesteden(String wijzevanaanbesteden) {
        this.wijzevanaanbesteden = wijzevanaanbesteden;
    }

    public Contract getBetreftContract() {
        return this.betreftContract;
    }

    public void setBetreftContract(Contract contract) {
        this.betreftContract = contract;
    }

    public Inkooporder betreftContract(Contract contract) {
        this.setBetreftContract(contract);
        return this;
    }

    public Inkooporder getOorspronkelijkInkooporder() {
        return this.oorspronkelijkInkooporder;
    }

    public void setOorspronkelijkInkooporder(Inkooporder inkooporder) {
        this.oorspronkelijkInkooporder = inkooporder;
    }

    public Inkooporder oorspronkelijkInkooporder(Inkooporder inkooporder) {
        this.setOorspronkelijkInkooporder(inkooporder);
        return this;
    }

    public Set<Werkbon> getHoortbijWerkbons() {
        return this.hoortbijWerkbons;
    }

    public void setHoortbijWerkbons(Set<Werkbon> werkbons) {
        if (this.hoortbijWerkbons != null) {
            this.hoortbijWerkbons.forEach(i -> i.setHoortbijInkooporder(null));
        }
        if (werkbons != null) {
            werkbons.forEach(i -> i.setHoortbijInkooporder(this));
        }
        this.hoortbijWerkbons = werkbons;
    }

    public Inkooporder hoortbijWerkbons(Set<Werkbon> werkbons) {
        this.setHoortbijWerkbons(werkbons);
        return this;
    }

    public Inkooporder addHoortbijWerkbon(Werkbon werkbon) {
        this.hoortbijWerkbons.add(werkbon);
        werkbon.setHoortbijInkooporder(this);
        return this;
    }

    public Inkooporder removeHoortbijWerkbon(Werkbon werkbon) {
        this.hoortbijWerkbons.remove(werkbon);
        werkbon.setHoortbijInkooporder(null);
        return this;
    }

    public Set<Inkooporder> getGerelateerdInkooporders() {
        return this.gerelateerdInkooporders;
    }

    public void setGerelateerdInkooporders(Set<Inkooporder> inkooporders) {
        if (this.gerelateerdInkooporders != null) {
            this.gerelateerdInkooporders.forEach(i -> i.setGerelateerdInkooporder2(null));
        }
        if (inkooporders != null) {
            inkooporders.forEach(i -> i.setGerelateerdInkooporder2(this));
        }
        this.gerelateerdInkooporders = inkooporders;
    }

    public Inkooporder gerelateerdInkooporders(Set<Inkooporder> inkooporders) {
        this.setGerelateerdInkooporders(inkooporders);
        return this;
    }

    public Inkooporder addGerelateerdInkooporder(Inkooporder inkooporder) {
        this.gerelateerdInkooporders.add(inkooporder);
        inkooporder.setGerelateerdInkooporder2(this);
        return this;
    }

    public Inkooporder removeGerelateerdInkooporder(Inkooporder inkooporder) {
        this.gerelateerdInkooporders.remove(inkooporder);
        inkooporder.setGerelateerdInkooporder2(null);
        return this;
    }

    public Inkooppakket getHeeftInkooppakket() {
        return this.heeftInkooppakket;
    }

    public void setHeeftInkooppakket(Inkooppakket inkooppakket) {
        this.heeftInkooppakket = inkooppakket;
    }

    public Inkooporder heeftInkooppakket(Inkooppakket inkooppakket) {
        this.setHeeftInkooppakket(inkooppakket);
        return this;
    }

    public Leverancier getVerplichtingaanLeverancier() {
        return this.verplichtingaanLeverancier;
    }

    public void setVerplichtingaanLeverancier(Leverancier leverancier) {
        this.verplichtingaanLeverancier = leverancier;
    }

    public Inkooporder verplichtingaanLeverancier(Leverancier leverancier) {
        this.setVerplichtingaanLeverancier(leverancier);
        return this;
    }

    public Set<Hoofdrekening> getWordtgeschrevenopHoofdrekenings() {
        return this.wordtgeschrevenopHoofdrekenings;
    }

    public void setWordtgeschrevenopHoofdrekenings(Set<Hoofdrekening> hoofdrekenings) {
        this.wordtgeschrevenopHoofdrekenings = hoofdrekenings;
    }

    public Inkooporder wordtgeschrevenopHoofdrekenings(Set<Hoofdrekening> hoofdrekenings) {
        this.setWordtgeschrevenopHoofdrekenings(hoofdrekenings);
        return this;
    }

    public Inkooporder addWordtgeschrevenopHoofdrekening(Hoofdrekening hoofdrekening) {
        this.wordtgeschrevenopHoofdrekenings.add(hoofdrekening);
        return this;
    }

    public Inkooporder removeWordtgeschrevenopHoofdrekening(Hoofdrekening hoofdrekening) {
        this.wordtgeschrevenopHoofdrekenings.remove(hoofdrekening);
        return this;
    }

    public Inkooporder getOorspronkelijkInkooporder2() {
        return this.oorspronkelijkInkooporder2;
    }

    public void setOorspronkelijkInkooporder2(Inkooporder inkooporder) {
        if (this.oorspronkelijkInkooporder2 != null) {
            this.oorspronkelijkInkooporder2.setOorspronkelijkInkooporder(null);
        }
        if (inkooporder != null) {
            inkooporder.setOorspronkelijkInkooporder(this);
        }
        this.oorspronkelijkInkooporder2 = inkooporder;
    }

    public Inkooporder oorspronkelijkInkooporder2(Inkooporder inkooporder) {
        this.setOorspronkelijkInkooporder2(inkooporder);
        return this;
    }

    public Inkooporder getGerelateerdInkooporder2() {
        return this.gerelateerdInkooporder2;
    }

    public void setGerelateerdInkooporder2(Inkooporder inkooporder) {
        this.gerelateerdInkooporder2 = inkooporder;
    }

    public Inkooporder gerelateerdInkooporder2(Inkooporder inkooporder) {
        this.setGerelateerdInkooporder2(inkooporder);
        return this;
    }

    public Set<Factuur> getGedektviaFactuurs() {
        return this.gedektviaFactuurs;
    }

    public void setGedektviaFactuurs(Set<Factuur> factuurs) {
        if (this.gedektviaFactuurs != null) {
            this.gedektviaFactuurs.forEach(i -> i.setGedektviaInkooporder(null));
        }
        if (factuurs != null) {
            factuurs.forEach(i -> i.setGedektviaInkooporder(this));
        }
        this.gedektviaFactuurs = factuurs;
    }

    public Inkooporder gedektviaFactuurs(Set<Factuur> factuurs) {
        this.setGedektviaFactuurs(factuurs);
        return this;
    }

    public Inkooporder addGedektviaFactuur(Factuur factuur) {
        this.gedektviaFactuurs.add(factuur);
        factuur.setGedektviaInkooporder(this);
        return this;
    }

    public Inkooporder removeGedektviaFactuur(Factuur factuur) {
        this.gedektviaFactuurs.remove(factuur);
        factuur.setGedektviaInkooporder(null);
        return this;
    }

    public Set<Kostenplaats> getHeeftKostenplaats() {
        return this.heeftKostenplaats;
    }

    public void setHeeftKostenplaats(Set<Kostenplaats> kostenplaats) {
        if (this.heeftKostenplaats != null) {
            this.heeftKostenplaats.forEach(i -> i.removeHeeftInkooporder(this));
        }
        if (kostenplaats != null) {
            kostenplaats.forEach(i -> i.addHeeftInkooporder(this));
        }
        this.heeftKostenplaats = kostenplaats;
    }

    public Inkooporder heeftKostenplaats(Set<Kostenplaats> kostenplaats) {
        this.setHeeftKostenplaats(kostenplaats);
        return this;
    }

    public Inkooporder addHeeftKostenplaats(Kostenplaats kostenplaats) {
        this.heeftKostenplaats.add(kostenplaats);
        kostenplaats.getHeeftInkooporders().add(this);
        return this;
    }

    public Inkooporder removeHeeftKostenplaats(Kostenplaats kostenplaats) {
        this.heeftKostenplaats.remove(kostenplaats);
        kostenplaats.getHeeftInkooporders().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inkooporder)) {
            return false;
        }
        return getId() != null && getId().equals(((Inkooporder) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inkooporder{" +
            "id=" + getId() +
            ", artikelcode='" + getArtikelcode() + "'" +
            ", betalingmeerderejaren='" + getBetalingmeerderejaren() + "'" +
            ", betreft='" + getBetreft() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumingediend='" + getDatumingediend() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", goederencode='" + getGoederencode() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", ordernummer='" + getOrdernummer() + "'" +
            ", saldo=" + getSaldo() +
            ", totaalnettobedrag=" + getTotaalnettobedrag() +
            ", wijzevanaanbesteden='" + getWijzevanaanbesteden() + "'" +
            "}";
    }
}
