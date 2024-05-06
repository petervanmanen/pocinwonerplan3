package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Hoofdrekening.
 */
@Entity
@Table(name = "hoofdrekening")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Hoofdrekening implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @Size(max = 8)
    @Column(name = "nummer", length = 8)
    private String nummer;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "piahoofcategorieomschrijving")
    private String piahoofcategorieomschrijving;

    @Size(max = 20)
    @Column(name = "piahoofdcategoriecode", length = 20)
    private String piahoofdcategoriecode;

    @Size(max = 20)
    @Column(name = "subcode", length = 20)
    private String subcode;

    @Column(name = "subcodeomschrijving")
    private String subcodeomschrijving;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "valtbinnenHoofdrekening2")
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
    private Set<Hoofdrekening> valtbinnenHoofdrekenings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftHoofdrekening")
    @JsonIgnoreProperties(value = { "heeftHoofdrekening", "heeftKostenplaats" }, allowSetters = true)
    private Set<Subrekening> heeftSubrekenings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftHoofdrekening")
    @JsonIgnoreProperties(value = { "heeftHoofdrekening", "heeftKostenplaats" }, allowSetters = true)
    private Set<Werkorder> heeftWerkorders = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_hoofdrekening__heeft_activa",
        joinColumns = @JoinColumn(name = "hoofdrekening_id"),
        inverseJoinColumns = @JoinColumn(name = "heeft_activa_id")
    )
    @JsonIgnoreProperties(value = { "issoortActivasoort", "heeftHoofdrekenings" }, allowSetters = true)
    private Set<Activa> heeftActivas = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @NotNull
    @JoinTable(
        name = "rel_hoofdrekening__heeft_kostenplaats",
        joinColumns = @JoinColumn(name = "hoofdrekening_id"),
        inverseJoinColumns = @JoinColumn(name = "heeft_kostenplaats_id")
    )
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

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Hoofdrekening valtbinnenHoofdrekening2;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftHoofdrekening")
    @JsonIgnoreProperties(
        value = {
            "betreftDoelstelling", "betreftProduct", "betreftKostenplaats", "betreftHoofdrekening", "betreftHoofdstuk", "heeftBegroting",
        },
        allowSetters = true
    )
    private Set<Begrotingregel> betreftBegrotingregels = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vanHoofdrekening")
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
    private Set<Mutatie> vanMutaties = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "naarHoofdrekening")
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
    private Set<Mutatie> naarMutaties = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "wordtgeschrevenopHoofdrekenings")
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
    private Set<Inkooporder> wordtgeschrevenopInkooporders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Hoofdrekening id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Hoofdrekening naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getNummer() {
        return this.nummer;
    }

    public Hoofdrekening nummer(String nummer) {
        this.setNummer(nummer);
        return this;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Hoofdrekening omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getPiahoofcategorieomschrijving() {
        return this.piahoofcategorieomschrijving;
    }

    public Hoofdrekening piahoofcategorieomschrijving(String piahoofcategorieomschrijving) {
        this.setPiahoofcategorieomschrijving(piahoofcategorieomschrijving);
        return this;
    }

    public void setPiahoofcategorieomschrijving(String piahoofcategorieomschrijving) {
        this.piahoofcategorieomschrijving = piahoofcategorieomschrijving;
    }

    public String getPiahoofdcategoriecode() {
        return this.piahoofdcategoriecode;
    }

    public Hoofdrekening piahoofdcategoriecode(String piahoofdcategoriecode) {
        this.setPiahoofdcategoriecode(piahoofdcategoriecode);
        return this;
    }

    public void setPiahoofdcategoriecode(String piahoofdcategoriecode) {
        this.piahoofdcategoriecode = piahoofdcategoriecode;
    }

    public String getSubcode() {
        return this.subcode;
    }

    public Hoofdrekening subcode(String subcode) {
        this.setSubcode(subcode);
        return this;
    }

    public void setSubcode(String subcode) {
        this.subcode = subcode;
    }

    public String getSubcodeomschrijving() {
        return this.subcodeomschrijving;
    }

    public Hoofdrekening subcodeomschrijving(String subcodeomschrijving) {
        this.setSubcodeomschrijving(subcodeomschrijving);
        return this;
    }

    public void setSubcodeomschrijving(String subcodeomschrijving) {
        this.subcodeomschrijving = subcodeomschrijving;
    }

    public Set<Hoofdrekening> getValtbinnenHoofdrekenings() {
        return this.valtbinnenHoofdrekenings;
    }

    public void setValtbinnenHoofdrekenings(Set<Hoofdrekening> hoofdrekenings) {
        if (this.valtbinnenHoofdrekenings != null) {
            this.valtbinnenHoofdrekenings.forEach(i -> i.setValtbinnenHoofdrekening2(null));
        }
        if (hoofdrekenings != null) {
            hoofdrekenings.forEach(i -> i.setValtbinnenHoofdrekening2(this));
        }
        this.valtbinnenHoofdrekenings = hoofdrekenings;
    }

    public Hoofdrekening valtbinnenHoofdrekenings(Set<Hoofdrekening> hoofdrekenings) {
        this.setValtbinnenHoofdrekenings(hoofdrekenings);
        return this;
    }

    public Hoofdrekening addValtbinnenHoofdrekening(Hoofdrekening hoofdrekening) {
        this.valtbinnenHoofdrekenings.add(hoofdrekening);
        hoofdrekening.setValtbinnenHoofdrekening2(this);
        return this;
    }

    public Hoofdrekening removeValtbinnenHoofdrekening(Hoofdrekening hoofdrekening) {
        this.valtbinnenHoofdrekenings.remove(hoofdrekening);
        hoofdrekening.setValtbinnenHoofdrekening2(null);
        return this;
    }

    public Set<Subrekening> getHeeftSubrekenings() {
        return this.heeftSubrekenings;
    }

    public void setHeeftSubrekenings(Set<Subrekening> subrekenings) {
        if (this.heeftSubrekenings != null) {
            this.heeftSubrekenings.forEach(i -> i.setHeeftHoofdrekening(null));
        }
        if (subrekenings != null) {
            subrekenings.forEach(i -> i.setHeeftHoofdrekening(this));
        }
        this.heeftSubrekenings = subrekenings;
    }

    public Hoofdrekening heeftSubrekenings(Set<Subrekening> subrekenings) {
        this.setHeeftSubrekenings(subrekenings);
        return this;
    }

    public Hoofdrekening addHeeftSubrekening(Subrekening subrekening) {
        this.heeftSubrekenings.add(subrekening);
        subrekening.setHeeftHoofdrekening(this);
        return this;
    }

    public Hoofdrekening removeHeeftSubrekening(Subrekening subrekening) {
        this.heeftSubrekenings.remove(subrekening);
        subrekening.setHeeftHoofdrekening(null);
        return this;
    }

    public Set<Werkorder> getHeeftWerkorders() {
        return this.heeftWerkorders;
    }

    public void setHeeftWerkorders(Set<Werkorder> werkorders) {
        if (this.heeftWerkorders != null) {
            this.heeftWerkorders.forEach(i -> i.setHeeftHoofdrekening(null));
        }
        if (werkorders != null) {
            werkorders.forEach(i -> i.setHeeftHoofdrekening(this));
        }
        this.heeftWerkorders = werkorders;
    }

    public Hoofdrekening heeftWerkorders(Set<Werkorder> werkorders) {
        this.setHeeftWerkorders(werkorders);
        return this;
    }

    public Hoofdrekening addHeeftWerkorder(Werkorder werkorder) {
        this.heeftWerkorders.add(werkorder);
        werkorder.setHeeftHoofdrekening(this);
        return this;
    }

    public Hoofdrekening removeHeeftWerkorder(Werkorder werkorder) {
        this.heeftWerkorders.remove(werkorder);
        werkorder.setHeeftHoofdrekening(null);
        return this;
    }

    public Set<Activa> getHeeftActivas() {
        return this.heeftActivas;
    }

    public void setHeeftActivas(Set<Activa> activas) {
        this.heeftActivas = activas;
    }

    public Hoofdrekening heeftActivas(Set<Activa> activas) {
        this.setHeeftActivas(activas);
        return this;
    }

    public Hoofdrekening addHeeftActiva(Activa activa) {
        this.heeftActivas.add(activa);
        return this;
    }

    public Hoofdrekening removeHeeftActiva(Activa activa) {
        this.heeftActivas.remove(activa);
        return this;
    }

    public Set<Kostenplaats> getHeeftKostenplaats() {
        return this.heeftKostenplaats;
    }

    public void setHeeftKostenplaats(Set<Kostenplaats> kostenplaats) {
        this.heeftKostenplaats = kostenplaats;
    }

    public Hoofdrekening heeftKostenplaats(Set<Kostenplaats> kostenplaats) {
        this.setHeeftKostenplaats(kostenplaats);
        return this;
    }

    public Hoofdrekening addHeeftKostenplaats(Kostenplaats kostenplaats) {
        this.heeftKostenplaats.add(kostenplaats);
        return this;
    }

    public Hoofdrekening removeHeeftKostenplaats(Kostenplaats kostenplaats) {
        this.heeftKostenplaats.remove(kostenplaats);
        return this;
    }

    public Hoofdrekening getValtbinnenHoofdrekening2() {
        return this.valtbinnenHoofdrekening2;
    }

    public void setValtbinnenHoofdrekening2(Hoofdrekening hoofdrekening) {
        this.valtbinnenHoofdrekening2 = hoofdrekening;
    }

    public Hoofdrekening valtbinnenHoofdrekening2(Hoofdrekening hoofdrekening) {
        this.setValtbinnenHoofdrekening2(hoofdrekening);
        return this;
    }

    public Set<Begrotingregel> getBetreftBegrotingregels() {
        return this.betreftBegrotingregels;
    }

    public void setBetreftBegrotingregels(Set<Begrotingregel> begrotingregels) {
        if (this.betreftBegrotingregels != null) {
            this.betreftBegrotingregels.forEach(i -> i.setBetreftHoofdrekening(null));
        }
        if (begrotingregels != null) {
            begrotingregels.forEach(i -> i.setBetreftHoofdrekening(this));
        }
        this.betreftBegrotingregels = begrotingregels;
    }

    public Hoofdrekening betreftBegrotingregels(Set<Begrotingregel> begrotingregels) {
        this.setBetreftBegrotingregels(begrotingregels);
        return this;
    }

    public Hoofdrekening addBetreftBegrotingregel(Begrotingregel begrotingregel) {
        this.betreftBegrotingregels.add(begrotingregel);
        begrotingregel.setBetreftHoofdrekening(this);
        return this;
    }

    public Hoofdrekening removeBetreftBegrotingregel(Begrotingregel begrotingregel) {
        this.betreftBegrotingregels.remove(begrotingregel);
        begrotingregel.setBetreftHoofdrekening(null);
        return this;
    }

    public Set<Mutatie> getVanMutaties() {
        return this.vanMutaties;
    }

    public void setVanMutaties(Set<Mutatie> mutaties) {
        if (this.vanMutaties != null) {
            this.vanMutaties.forEach(i -> i.setVanHoofdrekening(null));
        }
        if (mutaties != null) {
            mutaties.forEach(i -> i.setVanHoofdrekening(this));
        }
        this.vanMutaties = mutaties;
    }

    public Hoofdrekening vanMutaties(Set<Mutatie> mutaties) {
        this.setVanMutaties(mutaties);
        return this;
    }

    public Hoofdrekening addVanMutatie(Mutatie mutatie) {
        this.vanMutaties.add(mutatie);
        mutatie.setVanHoofdrekening(this);
        return this;
    }

    public Hoofdrekening removeVanMutatie(Mutatie mutatie) {
        this.vanMutaties.remove(mutatie);
        mutatie.setVanHoofdrekening(null);
        return this;
    }

    public Set<Mutatie> getNaarMutaties() {
        return this.naarMutaties;
    }

    public void setNaarMutaties(Set<Mutatie> mutaties) {
        if (this.naarMutaties != null) {
            this.naarMutaties.forEach(i -> i.setNaarHoofdrekening(null));
        }
        if (mutaties != null) {
            mutaties.forEach(i -> i.setNaarHoofdrekening(this));
        }
        this.naarMutaties = mutaties;
    }

    public Hoofdrekening naarMutaties(Set<Mutatie> mutaties) {
        this.setNaarMutaties(mutaties);
        return this;
    }

    public Hoofdrekening addNaarMutatie(Mutatie mutatie) {
        this.naarMutaties.add(mutatie);
        mutatie.setNaarHoofdrekening(this);
        return this;
    }

    public Hoofdrekening removeNaarMutatie(Mutatie mutatie) {
        this.naarMutaties.remove(mutatie);
        mutatie.setNaarHoofdrekening(null);
        return this;
    }

    public Set<Inkooporder> getWordtgeschrevenopInkooporders() {
        return this.wordtgeschrevenopInkooporders;
    }

    public void setWordtgeschrevenopInkooporders(Set<Inkooporder> inkooporders) {
        if (this.wordtgeschrevenopInkooporders != null) {
            this.wordtgeschrevenopInkooporders.forEach(i -> i.removeWordtgeschrevenopHoofdrekening(this));
        }
        if (inkooporders != null) {
            inkooporders.forEach(i -> i.addWordtgeschrevenopHoofdrekening(this));
        }
        this.wordtgeschrevenopInkooporders = inkooporders;
    }

    public Hoofdrekening wordtgeschrevenopInkooporders(Set<Inkooporder> inkooporders) {
        this.setWordtgeschrevenopInkooporders(inkooporders);
        return this;
    }

    public Hoofdrekening addWordtgeschrevenopInkooporder(Inkooporder inkooporder) {
        this.wordtgeschrevenopInkooporders.add(inkooporder);
        inkooporder.getWordtgeschrevenopHoofdrekenings().add(this);
        return this;
    }

    public Hoofdrekening removeWordtgeschrevenopInkooporder(Inkooporder inkooporder) {
        this.wordtgeschrevenopInkooporders.remove(inkooporder);
        inkooporder.getWordtgeschrevenopHoofdrekenings().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hoofdrekening)) {
            return false;
        }
        return getId() != null && getId().equals(((Hoofdrekening) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Hoofdrekening{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", nummer='" + getNummer() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", piahoofcategorieomschrijving='" + getPiahoofcategorieomschrijving() + "'" +
            ", piahoofdcategoriecode='" + getPiahoofdcategoriecode() + "'" +
            ", subcode='" + getSubcode() + "'" +
            ", subcodeomschrijving='" + getSubcodeomschrijving() + "'" +
            "}";
    }
}
