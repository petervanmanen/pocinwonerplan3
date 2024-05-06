package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Werkbon.
 */
@Entity
@Table(name = "werkbon")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Werkbon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Vastgoedobject betreftVastgoedobject;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_werkbon__betreft_bouwdeel",
        joinColumns = @JoinColumn(name = "werkbon_id"),
        inverseJoinColumns = @JoinColumn(name = "betreft_bouwdeel_id")
    )
    @JsonIgnoreProperties(value = { "bestaatuitBouwdeelelements", "bestaatuitVastgoedobject", "betreftWerkbons" }, allowSetters = true)
    private Set<Bouwdeel> betreftBouwdeels = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_werkbon__betreft_bouwdeelelement",
        joinColumns = @JoinColumn(name = "werkbon_id"),
        inverseJoinColumns = @JoinColumn(name = "betreft_bouwdeelelement_id")
    )
    @JsonIgnoreProperties(value = { "bestaatuitBouwdeel", "betreftWerkbons" }, allowSetters = true)
    private Set<Bouwdeelelement> betreftBouwdeelelements = new HashSet<>();

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
    private Inkooporder hoortbijInkooporder;

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
    private Leverancier voertwerkuitconformLeverancier;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Werkbon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vastgoedobject getBetreftVastgoedobject() {
        return this.betreftVastgoedobject;
    }

    public void setBetreftVastgoedobject(Vastgoedobject vastgoedobject) {
        this.betreftVastgoedobject = vastgoedobject;
    }

    public Werkbon betreftVastgoedobject(Vastgoedobject vastgoedobject) {
        this.setBetreftVastgoedobject(vastgoedobject);
        return this;
    }

    public Set<Bouwdeel> getBetreftBouwdeels() {
        return this.betreftBouwdeels;
    }

    public void setBetreftBouwdeels(Set<Bouwdeel> bouwdeels) {
        this.betreftBouwdeels = bouwdeels;
    }

    public Werkbon betreftBouwdeels(Set<Bouwdeel> bouwdeels) {
        this.setBetreftBouwdeels(bouwdeels);
        return this;
    }

    public Werkbon addBetreftBouwdeel(Bouwdeel bouwdeel) {
        this.betreftBouwdeels.add(bouwdeel);
        return this;
    }

    public Werkbon removeBetreftBouwdeel(Bouwdeel bouwdeel) {
        this.betreftBouwdeels.remove(bouwdeel);
        return this;
    }

    public Set<Bouwdeelelement> getBetreftBouwdeelelements() {
        return this.betreftBouwdeelelements;
    }

    public void setBetreftBouwdeelelements(Set<Bouwdeelelement> bouwdeelelements) {
        this.betreftBouwdeelelements = bouwdeelelements;
    }

    public Werkbon betreftBouwdeelelements(Set<Bouwdeelelement> bouwdeelelements) {
        this.setBetreftBouwdeelelements(bouwdeelelements);
        return this;
    }

    public Werkbon addBetreftBouwdeelelement(Bouwdeelelement bouwdeelelement) {
        this.betreftBouwdeelelements.add(bouwdeelelement);
        return this;
    }

    public Werkbon removeBetreftBouwdeelelement(Bouwdeelelement bouwdeelelement) {
        this.betreftBouwdeelelements.remove(bouwdeelelement);
        return this;
    }

    public Inkooporder getHoortbijInkooporder() {
        return this.hoortbijInkooporder;
    }

    public void setHoortbijInkooporder(Inkooporder inkooporder) {
        this.hoortbijInkooporder = inkooporder;
    }

    public Werkbon hoortbijInkooporder(Inkooporder inkooporder) {
        this.setHoortbijInkooporder(inkooporder);
        return this;
    }

    public Leverancier getVoertwerkuitconformLeverancier() {
        return this.voertwerkuitconformLeverancier;
    }

    public void setVoertwerkuitconformLeverancier(Leverancier leverancier) {
        this.voertwerkuitconformLeverancier = leverancier;
    }

    public Werkbon voertwerkuitconformLeverancier(Leverancier leverancier) {
        this.setVoertwerkuitconformLeverancier(leverancier);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Werkbon)) {
            return false;
        }
        return getId() != null && getId().equals(((Werkbon) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Werkbon{" +
            "id=" + getId() +
            "}";
    }
}
