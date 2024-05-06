package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A Factuur.
 */
@Entity
@Table(name = "factuur")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Factuur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "betaalbaarper")
    private String betaalbaarper;

    @Column(name = "betaaltermijn")
    private String betaaltermijn;

    @Column(name = "code")
    private String code;

    @Column(name = "datumfactuur")
    private String datumfactuur;

    @Column(name = "factuurbedragbtw", precision = 21, scale = 2)
    private BigDecimal factuurbedragbtw;

    @Column(name = "factuurbedragexclusiefbtw")
    private String factuurbedragexclusiefbtw;

    @Column(name = "omschrijving")
    private String omschrijving;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftFactuur")
    @JsonIgnoreProperties(value = { "leidttotMutatie", "heeftFactuur" }, allowSetters = true)
    private Set<Factuurregel> heeftFactuurregels = new HashSet<>();

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
    private Kostenplaats schrijftopKostenplaats;

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
    private Inkooporder gedektviaInkooporder;

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
    private Leverancier crediteurLeverancier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftFactuurs" }, allowSetters = true)
    private Debiteur heeftDebiteur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Factuur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBetaalbaarper() {
        return this.betaalbaarper;
    }

    public Factuur betaalbaarper(String betaalbaarper) {
        this.setBetaalbaarper(betaalbaarper);
        return this;
    }

    public void setBetaalbaarper(String betaalbaarper) {
        this.betaalbaarper = betaalbaarper;
    }

    public String getBetaaltermijn() {
        return this.betaaltermijn;
    }

    public Factuur betaaltermijn(String betaaltermijn) {
        this.setBetaaltermijn(betaaltermijn);
        return this;
    }

    public void setBetaaltermijn(String betaaltermijn) {
        this.betaaltermijn = betaaltermijn;
    }

    public String getCode() {
        return this.code;
    }

    public Factuur code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDatumfactuur() {
        return this.datumfactuur;
    }

    public Factuur datumfactuur(String datumfactuur) {
        this.setDatumfactuur(datumfactuur);
        return this;
    }

    public void setDatumfactuur(String datumfactuur) {
        this.datumfactuur = datumfactuur;
    }

    public BigDecimal getFactuurbedragbtw() {
        return this.factuurbedragbtw;
    }

    public Factuur factuurbedragbtw(BigDecimal factuurbedragbtw) {
        this.setFactuurbedragbtw(factuurbedragbtw);
        return this;
    }

    public void setFactuurbedragbtw(BigDecimal factuurbedragbtw) {
        this.factuurbedragbtw = factuurbedragbtw;
    }

    public String getFactuurbedragexclusiefbtw() {
        return this.factuurbedragexclusiefbtw;
    }

    public Factuur factuurbedragexclusiefbtw(String factuurbedragexclusiefbtw) {
        this.setFactuurbedragexclusiefbtw(factuurbedragexclusiefbtw);
        return this;
    }

    public void setFactuurbedragexclusiefbtw(String factuurbedragexclusiefbtw) {
        this.factuurbedragexclusiefbtw = factuurbedragexclusiefbtw;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Factuur omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Factuurregel> getHeeftFactuurregels() {
        return this.heeftFactuurregels;
    }

    public void setHeeftFactuurregels(Set<Factuurregel> factuurregels) {
        if (this.heeftFactuurregels != null) {
            this.heeftFactuurregels.forEach(i -> i.setHeeftFactuur(null));
        }
        if (factuurregels != null) {
            factuurregels.forEach(i -> i.setHeeftFactuur(this));
        }
        this.heeftFactuurregels = factuurregels;
    }

    public Factuur heeftFactuurregels(Set<Factuurregel> factuurregels) {
        this.setHeeftFactuurregels(factuurregels);
        return this;
    }

    public Factuur addHeeftFactuurregel(Factuurregel factuurregel) {
        this.heeftFactuurregels.add(factuurregel);
        factuurregel.setHeeftFactuur(this);
        return this;
    }

    public Factuur removeHeeftFactuurregel(Factuurregel factuurregel) {
        this.heeftFactuurregels.remove(factuurregel);
        factuurregel.setHeeftFactuur(null);
        return this;
    }

    public Kostenplaats getSchrijftopKostenplaats() {
        return this.schrijftopKostenplaats;
    }

    public void setSchrijftopKostenplaats(Kostenplaats kostenplaats) {
        this.schrijftopKostenplaats = kostenplaats;
    }

    public Factuur schrijftopKostenplaats(Kostenplaats kostenplaats) {
        this.setSchrijftopKostenplaats(kostenplaats);
        return this;
    }

    public Inkooporder getGedektviaInkooporder() {
        return this.gedektviaInkooporder;
    }

    public void setGedektviaInkooporder(Inkooporder inkooporder) {
        this.gedektviaInkooporder = inkooporder;
    }

    public Factuur gedektviaInkooporder(Inkooporder inkooporder) {
        this.setGedektviaInkooporder(inkooporder);
        return this;
    }

    public Leverancier getCrediteurLeverancier() {
        return this.crediteurLeverancier;
    }

    public void setCrediteurLeverancier(Leverancier leverancier) {
        this.crediteurLeverancier = leverancier;
    }

    public Factuur crediteurLeverancier(Leverancier leverancier) {
        this.setCrediteurLeverancier(leverancier);
        return this;
    }

    public Debiteur getHeeftDebiteur() {
        return this.heeftDebiteur;
    }

    public void setHeeftDebiteur(Debiteur debiteur) {
        this.heeftDebiteur = debiteur;
    }

    public Factuur heeftDebiteur(Debiteur debiteur) {
        this.setHeeftDebiteur(debiteur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Factuur)) {
            return false;
        }
        return getId() != null && getId().equals(((Factuur) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Factuur{" +
            "id=" + getId() +
            ", betaalbaarper='" + getBetaalbaarper() + "'" +
            ", betaaltermijn='" + getBetaaltermijn() + "'" +
            ", code='" + getCode() + "'" +
            ", datumfactuur='" + getDatumfactuur() + "'" +
            ", factuurbedragbtw=" + getFactuurbedragbtw() +
            ", factuurbedragexclusiefbtw='" + getFactuurbedragexclusiefbtw() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
