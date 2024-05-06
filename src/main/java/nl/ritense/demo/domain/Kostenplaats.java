package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Kostenplaats.
 */
@Entity
@Table(name = "kostenplaats")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Kostenplaats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "btwcode")
    private String btwcode;

    @Column(name = "btwomschrijving")
    private String btwomschrijving;

    @Size(max = 20)
    @Column(name = "kostenplaatssoortcode", length = 20)
    private String kostenplaatssoortcode;

    @Column(name = "kostenplaatssoortomschrijving")
    private String kostenplaatssoortomschrijving;

    @Size(max = 20)
    @Column(name = "kostenplaatstypecode", length = 20)
    private String kostenplaatstypecode;

    @Column(name = "kostenplaatstypeomschrijving")
    private String kostenplaatstypeomschrijving;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftKostenplaats")
    @JsonIgnoreProperties(
        value = {
            "betreftPand",
            "bestaatuitBouwdeels",
            "betreftInspecties",
            "heeftVerblijfsobject",
            "heeftPand",
            "heeftKostenplaats",
            "betreftWerkbons",
        },
        allowSetters = true
    )
    private Set<Vastgoedobject> heeftVastgoedobjects = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftKostenplaats")
    @JsonIgnoreProperties(value = { "heeftHoofdrekening", "heeftKostenplaats" }, allowSetters = true)
    private Set<Werkorder> heeftWerkorders = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftKostenplaats")
    @JsonIgnoreProperties(value = { "heeftHoofdrekening", "heeftKostenplaats" }, allowSetters = true)
    private Set<Subrekening> heeftSubrekenings = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_kostenplaats__heeft_inkooporder",
        joinColumns = @JoinColumn(name = "kostenplaats_id"),
        inverseJoinColumns = @JoinColumn(name = "heeft_inkooporder_id")
    )
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
    private Set<Inkooporder> heeftInkooporders = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @NotNull
    @JoinTable(
        name = "rel_kostenplaats__heeft_taakveld",
        joinColumns = @JoinColumn(name = "kostenplaats_id"),
        inverseJoinColumns = @JoinColumn(name = "heeft_taakveld_id")
    )
    @JsonIgnoreProperties(value = { "heeftRaadsstuks", "heeftKostenplaats" }, allowSetters = true)
    private Set<Taakveld> heeftTaakvelds = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftKostenplaats")
    @JsonIgnoreProperties(
        value = {
            "bestaatuitActiviteits",
            "binnenprogrammaPlans",
            "heeftKostenplaats",
            "voorProgrammasoorts",
            "voorMuseumrelatie",
            "hoortbijRaadsstuks",
        },
        allowSetters = true
    )
    private Set<Programma> heeftProgrammas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftKostenplaats")
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
    private Set<Subsidie> heeftSubsidies = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftKostenplaats")
    @JsonIgnoreProperties(value = { "heeftBetaalmoments", "heeftKostenplaats" }, allowSetters = true)
    private Set<Subsidiecomponent> heeftSubsidiecomponents = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftKostenplaats")
    @JsonIgnoreProperties(
        value = {
            "betreftDoelstelling", "betreftProduct", "betreftKostenplaats", "betreftHoofdrekening", "betreftHoofdstuk", "heeftBegroting",
        },
        allowSetters = true
    )
    private Set<Begrotingregel> betreftBegrotingregels = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "schrijftopKostenplaats")
    @JsonIgnoreProperties(
        value = { "heeftFactuurregels", "schrijftopKostenplaats", "gedektviaInkooporder", "crediteurLeverancier", "heeftDebiteur" },
        allowSetters = true
    )
    private Set<Factuur> schrijftopFactuurs = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftbetrekkingopKostenplaats")
    @JsonIgnoreProperties(
        value = {
            "vanHoofdrekening",
            "naarHoofdrekening",
            "heeftbetrekkingopKostenplaats",
            "leidttotBankafschriftregel",
            "leidttotBatchregel",
            "leidttotFactuurregel",
        },
        allowSetters = true
    )
    private Set<Mutatie> heeftbetrekkingopMutaties = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftKostenplaats")
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
    private Set<Product> heeftProducts = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "heeftKostenplaats")
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
    private Set<Hoofdrekening> heeftHoofdrekenings = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "heeftKostenplaats")
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
    private Set<Project> heeftProjects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Kostenplaats id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBtwcode() {
        return this.btwcode;
    }

    public Kostenplaats btwcode(String btwcode) {
        this.setBtwcode(btwcode);
        return this;
    }

    public void setBtwcode(String btwcode) {
        this.btwcode = btwcode;
    }

    public String getBtwomschrijving() {
        return this.btwomschrijving;
    }

    public Kostenplaats btwomschrijving(String btwomschrijving) {
        this.setBtwomschrijving(btwomschrijving);
        return this;
    }

    public void setBtwomschrijving(String btwomschrijving) {
        this.btwomschrijving = btwomschrijving;
    }

    public String getKostenplaatssoortcode() {
        return this.kostenplaatssoortcode;
    }

    public Kostenplaats kostenplaatssoortcode(String kostenplaatssoortcode) {
        this.setKostenplaatssoortcode(kostenplaatssoortcode);
        return this;
    }

    public void setKostenplaatssoortcode(String kostenplaatssoortcode) {
        this.kostenplaatssoortcode = kostenplaatssoortcode;
    }

    public String getKostenplaatssoortomschrijving() {
        return this.kostenplaatssoortomschrijving;
    }

    public Kostenplaats kostenplaatssoortomschrijving(String kostenplaatssoortomschrijving) {
        this.setKostenplaatssoortomschrijving(kostenplaatssoortomschrijving);
        return this;
    }

    public void setKostenplaatssoortomschrijving(String kostenplaatssoortomschrijving) {
        this.kostenplaatssoortomschrijving = kostenplaatssoortomschrijving;
    }

    public String getKostenplaatstypecode() {
        return this.kostenplaatstypecode;
    }

    public Kostenplaats kostenplaatstypecode(String kostenplaatstypecode) {
        this.setKostenplaatstypecode(kostenplaatstypecode);
        return this;
    }

    public void setKostenplaatstypecode(String kostenplaatstypecode) {
        this.kostenplaatstypecode = kostenplaatstypecode;
    }

    public String getKostenplaatstypeomschrijving() {
        return this.kostenplaatstypeomschrijving;
    }

    public Kostenplaats kostenplaatstypeomschrijving(String kostenplaatstypeomschrijving) {
        this.setKostenplaatstypeomschrijving(kostenplaatstypeomschrijving);
        return this;
    }

    public void setKostenplaatstypeomschrijving(String kostenplaatstypeomschrijving) {
        this.kostenplaatstypeomschrijving = kostenplaatstypeomschrijving;
    }

    public String getNaam() {
        return this.naam;
    }

    public Kostenplaats naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Kostenplaats omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Vastgoedobject> getHeeftVastgoedobjects() {
        return this.heeftVastgoedobjects;
    }

    public void setHeeftVastgoedobjects(Set<Vastgoedobject> vastgoedobjects) {
        if (this.heeftVastgoedobjects != null) {
            this.heeftVastgoedobjects.forEach(i -> i.setHeeftKostenplaats(null));
        }
        if (vastgoedobjects != null) {
            vastgoedobjects.forEach(i -> i.setHeeftKostenplaats(this));
        }
        this.heeftVastgoedobjects = vastgoedobjects;
    }

    public Kostenplaats heeftVastgoedobjects(Set<Vastgoedobject> vastgoedobjects) {
        this.setHeeftVastgoedobjects(vastgoedobjects);
        return this;
    }

    public Kostenplaats addHeeftVastgoedobject(Vastgoedobject vastgoedobject) {
        this.heeftVastgoedobjects.add(vastgoedobject);
        vastgoedobject.setHeeftKostenplaats(this);
        return this;
    }

    public Kostenplaats removeHeeftVastgoedobject(Vastgoedobject vastgoedobject) {
        this.heeftVastgoedobjects.remove(vastgoedobject);
        vastgoedobject.setHeeftKostenplaats(null);
        return this;
    }

    public Set<Werkorder> getHeeftWerkorders() {
        return this.heeftWerkorders;
    }

    public void setHeeftWerkorders(Set<Werkorder> werkorders) {
        if (this.heeftWerkorders != null) {
            this.heeftWerkorders.forEach(i -> i.setHeeftKostenplaats(null));
        }
        if (werkorders != null) {
            werkorders.forEach(i -> i.setHeeftKostenplaats(this));
        }
        this.heeftWerkorders = werkorders;
    }

    public Kostenplaats heeftWerkorders(Set<Werkorder> werkorders) {
        this.setHeeftWerkorders(werkorders);
        return this;
    }

    public Kostenplaats addHeeftWerkorder(Werkorder werkorder) {
        this.heeftWerkorders.add(werkorder);
        werkorder.setHeeftKostenplaats(this);
        return this;
    }

    public Kostenplaats removeHeeftWerkorder(Werkorder werkorder) {
        this.heeftWerkorders.remove(werkorder);
        werkorder.setHeeftKostenplaats(null);
        return this;
    }

    public Set<Subrekening> getHeeftSubrekenings() {
        return this.heeftSubrekenings;
    }

    public void setHeeftSubrekenings(Set<Subrekening> subrekenings) {
        if (this.heeftSubrekenings != null) {
            this.heeftSubrekenings.forEach(i -> i.setHeeftKostenplaats(null));
        }
        if (subrekenings != null) {
            subrekenings.forEach(i -> i.setHeeftKostenplaats(this));
        }
        this.heeftSubrekenings = subrekenings;
    }

    public Kostenplaats heeftSubrekenings(Set<Subrekening> subrekenings) {
        this.setHeeftSubrekenings(subrekenings);
        return this;
    }

    public Kostenplaats addHeeftSubrekening(Subrekening subrekening) {
        this.heeftSubrekenings.add(subrekening);
        subrekening.setHeeftKostenplaats(this);
        return this;
    }

    public Kostenplaats removeHeeftSubrekening(Subrekening subrekening) {
        this.heeftSubrekenings.remove(subrekening);
        subrekening.setHeeftKostenplaats(null);
        return this;
    }

    public Set<Inkooporder> getHeeftInkooporders() {
        return this.heeftInkooporders;
    }

    public void setHeeftInkooporders(Set<Inkooporder> inkooporders) {
        this.heeftInkooporders = inkooporders;
    }

    public Kostenplaats heeftInkooporders(Set<Inkooporder> inkooporders) {
        this.setHeeftInkooporders(inkooporders);
        return this;
    }

    public Kostenplaats addHeeftInkooporder(Inkooporder inkooporder) {
        this.heeftInkooporders.add(inkooporder);
        return this;
    }

    public Kostenplaats removeHeeftInkooporder(Inkooporder inkooporder) {
        this.heeftInkooporders.remove(inkooporder);
        return this;
    }

    public Set<Taakveld> getHeeftTaakvelds() {
        return this.heeftTaakvelds;
    }

    public void setHeeftTaakvelds(Set<Taakveld> taakvelds) {
        this.heeftTaakvelds = taakvelds;
    }

    public Kostenplaats heeftTaakvelds(Set<Taakveld> taakvelds) {
        this.setHeeftTaakvelds(taakvelds);
        return this;
    }

    public Kostenplaats addHeeftTaakveld(Taakveld taakveld) {
        this.heeftTaakvelds.add(taakveld);
        return this;
    }

    public Kostenplaats removeHeeftTaakveld(Taakveld taakveld) {
        this.heeftTaakvelds.remove(taakveld);
        return this;
    }

    public Set<Programma> getHeeftProgrammas() {
        return this.heeftProgrammas;
    }

    public void setHeeftProgrammas(Set<Programma> programmas) {
        if (this.heeftProgrammas != null) {
            this.heeftProgrammas.forEach(i -> i.setHeeftKostenplaats(null));
        }
        if (programmas != null) {
            programmas.forEach(i -> i.setHeeftKostenplaats(this));
        }
        this.heeftProgrammas = programmas;
    }

    public Kostenplaats heeftProgrammas(Set<Programma> programmas) {
        this.setHeeftProgrammas(programmas);
        return this;
    }

    public Kostenplaats addHeeftProgramma(Programma programma) {
        this.heeftProgrammas.add(programma);
        programma.setHeeftKostenplaats(this);
        return this;
    }

    public Kostenplaats removeHeeftProgramma(Programma programma) {
        this.heeftProgrammas.remove(programma);
        programma.setHeeftKostenplaats(null);
        return this;
    }

    public Set<Subsidie> getHeeftSubsidies() {
        return this.heeftSubsidies;
    }

    public void setHeeftSubsidies(Set<Subsidie> subsidies) {
        if (this.heeftSubsidies != null) {
            this.heeftSubsidies.forEach(i -> i.setHeeftKostenplaats(null));
        }
        if (subsidies != null) {
            subsidies.forEach(i -> i.setHeeftKostenplaats(this));
        }
        this.heeftSubsidies = subsidies;
    }

    public Kostenplaats heeftSubsidies(Set<Subsidie> subsidies) {
        this.setHeeftSubsidies(subsidies);
        return this;
    }

    public Kostenplaats addHeeftSubsidie(Subsidie subsidie) {
        this.heeftSubsidies.add(subsidie);
        subsidie.setHeeftKostenplaats(this);
        return this;
    }

    public Kostenplaats removeHeeftSubsidie(Subsidie subsidie) {
        this.heeftSubsidies.remove(subsidie);
        subsidie.setHeeftKostenplaats(null);
        return this;
    }

    public Set<Subsidiecomponent> getHeeftSubsidiecomponents() {
        return this.heeftSubsidiecomponents;
    }

    public void setHeeftSubsidiecomponents(Set<Subsidiecomponent> subsidiecomponents) {
        if (this.heeftSubsidiecomponents != null) {
            this.heeftSubsidiecomponents.forEach(i -> i.setHeeftKostenplaats(null));
        }
        if (subsidiecomponents != null) {
            subsidiecomponents.forEach(i -> i.setHeeftKostenplaats(this));
        }
        this.heeftSubsidiecomponents = subsidiecomponents;
    }

    public Kostenplaats heeftSubsidiecomponents(Set<Subsidiecomponent> subsidiecomponents) {
        this.setHeeftSubsidiecomponents(subsidiecomponents);
        return this;
    }

    public Kostenplaats addHeeftSubsidiecomponent(Subsidiecomponent subsidiecomponent) {
        this.heeftSubsidiecomponents.add(subsidiecomponent);
        subsidiecomponent.setHeeftKostenplaats(this);
        return this;
    }

    public Kostenplaats removeHeeftSubsidiecomponent(Subsidiecomponent subsidiecomponent) {
        this.heeftSubsidiecomponents.remove(subsidiecomponent);
        subsidiecomponent.setHeeftKostenplaats(null);
        return this;
    }

    public Set<Begrotingregel> getBetreftBegrotingregels() {
        return this.betreftBegrotingregels;
    }

    public void setBetreftBegrotingregels(Set<Begrotingregel> begrotingregels) {
        if (this.betreftBegrotingregels != null) {
            this.betreftBegrotingregels.forEach(i -> i.setBetreftKostenplaats(null));
        }
        if (begrotingregels != null) {
            begrotingregels.forEach(i -> i.setBetreftKostenplaats(this));
        }
        this.betreftBegrotingregels = begrotingregels;
    }

    public Kostenplaats betreftBegrotingregels(Set<Begrotingregel> begrotingregels) {
        this.setBetreftBegrotingregels(begrotingregels);
        return this;
    }

    public Kostenplaats addBetreftBegrotingregel(Begrotingregel begrotingregel) {
        this.betreftBegrotingregels.add(begrotingregel);
        begrotingregel.setBetreftKostenplaats(this);
        return this;
    }

    public Kostenplaats removeBetreftBegrotingregel(Begrotingregel begrotingregel) {
        this.betreftBegrotingregels.remove(begrotingregel);
        begrotingregel.setBetreftKostenplaats(null);
        return this;
    }

    public Set<Factuur> getSchrijftopFactuurs() {
        return this.schrijftopFactuurs;
    }

    public void setSchrijftopFactuurs(Set<Factuur> factuurs) {
        if (this.schrijftopFactuurs != null) {
            this.schrijftopFactuurs.forEach(i -> i.setSchrijftopKostenplaats(null));
        }
        if (factuurs != null) {
            factuurs.forEach(i -> i.setSchrijftopKostenplaats(this));
        }
        this.schrijftopFactuurs = factuurs;
    }

    public Kostenplaats schrijftopFactuurs(Set<Factuur> factuurs) {
        this.setSchrijftopFactuurs(factuurs);
        return this;
    }

    public Kostenplaats addSchrijftopFactuur(Factuur factuur) {
        this.schrijftopFactuurs.add(factuur);
        factuur.setSchrijftopKostenplaats(this);
        return this;
    }

    public Kostenplaats removeSchrijftopFactuur(Factuur factuur) {
        this.schrijftopFactuurs.remove(factuur);
        factuur.setSchrijftopKostenplaats(null);
        return this;
    }

    public Set<Mutatie> getHeeftbetrekkingopMutaties() {
        return this.heeftbetrekkingopMutaties;
    }

    public void setHeeftbetrekkingopMutaties(Set<Mutatie> mutaties) {
        if (this.heeftbetrekkingopMutaties != null) {
            this.heeftbetrekkingopMutaties.forEach(i -> i.setHeeftbetrekkingopKostenplaats(null));
        }
        if (mutaties != null) {
            mutaties.forEach(i -> i.setHeeftbetrekkingopKostenplaats(this));
        }
        this.heeftbetrekkingopMutaties = mutaties;
    }

    public Kostenplaats heeftbetrekkingopMutaties(Set<Mutatie> mutaties) {
        this.setHeeftbetrekkingopMutaties(mutaties);
        return this;
    }

    public Kostenplaats addHeeftbetrekkingopMutatie(Mutatie mutatie) {
        this.heeftbetrekkingopMutaties.add(mutatie);
        mutatie.setHeeftbetrekkingopKostenplaats(this);
        return this;
    }

    public Kostenplaats removeHeeftbetrekkingopMutatie(Mutatie mutatie) {
        this.heeftbetrekkingopMutaties.remove(mutatie);
        mutatie.setHeeftbetrekkingopKostenplaats(null);
        return this;
    }

    public Set<Product> getHeeftProducts() {
        return this.heeftProducts;
    }

    public void setHeeftProducts(Set<Product> products) {
        if (this.heeftProducts != null) {
            this.heeftProducts.forEach(i -> i.setHeeftKostenplaats(null));
        }
        if (products != null) {
            products.forEach(i -> i.setHeeftKostenplaats(this));
        }
        this.heeftProducts = products;
    }

    public Kostenplaats heeftProducts(Set<Product> products) {
        this.setHeeftProducts(products);
        return this;
    }

    public Kostenplaats addHeeftProduct(Product product) {
        this.heeftProducts.add(product);
        product.setHeeftKostenplaats(this);
        return this;
    }

    public Kostenplaats removeHeeftProduct(Product product) {
        this.heeftProducts.remove(product);
        product.setHeeftKostenplaats(null);
        return this;
    }

    public Set<Hoofdrekening> getHeeftHoofdrekenings() {
        return this.heeftHoofdrekenings;
    }

    public void setHeeftHoofdrekenings(Set<Hoofdrekening> hoofdrekenings) {
        if (this.heeftHoofdrekenings != null) {
            this.heeftHoofdrekenings.forEach(i -> i.removeHeeftKostenplaats(this));
        }
        if (hoofdrekenings != null) {
            hoofdrekenings.forEach(i -> i.addHeeftKostenplaats(this));
        }
        this.heeftHoofdrekenings = hoofdrekenings;
    }

    public Kostenplaats heeftHoofdrekenings(Set<Hoofdrekening> hoofdrekenings) {
        this.setHeeftHoofdrekenings(hoofdrekenings);
        return this;
    }

    public Kostenplaats addHeeftHoofdrekening(Hoofdrekening hoofdrekening) {
        this.heeftHoofdrekenings.add(hoofdrekening);
        hoofdrekening.getHeeftKostenplaats().add(this);
        return this;
    }

    public Kostenplaats removeHeeftHoofdrekening(Hoofdrekening hoofdrekening) {
        this.heeftHoofdrekenings.remove(hoofdrekening);
        hoofdrekening.getHeeftKostenplaats().remove(this);
        return this;
    }

    public Set<Project> getHeeftProjects() {
        return this.heeftProjects;
    }

    public void setHeeftProjects(Set<Project> projects) {
        if (this.heeftProjects != null) {
            this.heeftProjects.forEach(i -> i.removeHeeftKostenplaats(this));
        }
        if (projects != null) {
            projects.forEach(i -> i.addHeeftKostenplaats(this));
        }
        this.heeftProjects = projects;
    }

    public Kostenplaats heeftProjects(Set<Project> projects) {
        this.setHeeftProjects(projects);
        return this;
    }

    public Kostenplaats addHeeftProject(Project project) {
        this.heeftProjects.add(project);
        project.getHeeftKostenplaats().add(this);
        return this;
    }

    public Kostenplaats removeHeeftProject(Project project) {
        this.heeftProjects.remove(project);
        project.getHeeftKostenplaats().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Kostenplaats)) {
            return false;
        }
        return getId() != null && getId().equals(((Kostenplaats) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Kostenplaats{" +
            "id=" + getId() +
            ", btwcode='" + getBtwcode() + "'" +
            ", btwomschrijving='" + getBtwomschrijving() + "'" +
            ", kostenplaatssoortcode='" + getKostenplaatssoortcode() + "'" +
            ", kostenplaatssoortomschrijving='" + getKostenplaatssoortomschrijving() + "'" +
            ", kostenplaatstypecode='" + getKostenplaatstypecode() + "'" +
            ", kostenplaatstypeomschrijving='" + getKostenplaatstypeomschrijving() + "'" +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
