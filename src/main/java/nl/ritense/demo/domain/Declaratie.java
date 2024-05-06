package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Declaratie.
 */
@Entity
@Table(name = "declaratie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Declaratie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumdeclaratie")
    private String datumdeclaratie;

    @Column(name = "declaratiebedrag")
    private String declaratiebedrag;

    @Column(name = "declaratiestatus")
    private String declaratiestatus;

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
    private Leverancier ingedienddoorLeverancier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "soortdeclaratieDeclaraties" }, allowSetters = true)
    private Declaratiesoort soortdeclaratieDeclaratiesoort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "beoordeeltdoorBeoordelings",
            "beoordelingvanBeoordelings",
            "dientinDeclaraties",
            "medewerkerheeftdienstverbandDienstverbands",
            "solliciteertSollicitaties",
            "heeftverlofVerlofs",
            "heeftverzuimVerzuims",
            "heeftondergaanGeweldsincident",
            "heeftRols",
            "doetsollicitatiegesprekSollicitatiegespreks",
        },
        allowSetters = true
    )
    private Werknemer dientinWerknemer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "valtbinnenDeclaratie")
    @JsonIgnoreProperties(
        value = { "isvoorBeschikking", "betreftClient", "valtbinnenDeclaratie", "isopbasisvanToewijzing" },
        allowSetters = true
    )
    private Set<Declaratieregel> valtbinnenDeclaratieregels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Declaratie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatumdeclaratie() {
        return this.datumdeclaratie;
    }

    public Declaratie datumdeclaratie(String datumdeclaratie) {
        this.setDatumdeclaratie(datumdeclaratie);
        return this;
    }

    public void setDatumdeclaratie(String datumdeclaratie) {
        this.datumdeclaratie = datumdeclaratie;
    }

    public String getDeclaratiebedrag() {
        return this.declaratiebedrag;
    }

    public Declaratie declaratiebedrag(String declaratiebedrag) {
        this.setDeclaratiebedrag(declaratiebedrag);
        return this;
    }

    public void setDeclaratiebedrag(String declaratiebedrag) {
        this.declaratiebedrag = declaratiebedrag;
    }

    public String getDeclaratiestatus() {
        return this.declaratiestatus;
    }

    public Declaratie declaratiestatus(String declaratiestatus) {
        this.setDeclaratiestatus(declaratiestatus);
        return this;
    }

    public void setDeclaratiestatus(String declaratiestatus) {
        this.declaratiestatus = declaratiestatus;
    }

    public Leverancier getIngedienddoorLeverancier() {
        return this.ingedienddoorLeverancier;
    }

    public void setIngedienddoorLeverancier(Leverancier leverancier) {
        this.ingedienddoorLeverancier = leverancier;
    }

    public Declaratie ingedienddoorLeverancier(Leverancier leverancier) {
        this.setIngedienddoorLeverancier(leverancier);
        return this;
    }

    public Declaratiesoort getSoortdeclaratieDeclaratiesoort() {
        return this.soortdeclaratieDeclaratiesoort;
    }

    public void setSoortdeclaratieDeclaratiesoort(Declaratiesoort declaratiesoort) {
        this.soortdeclaratieDeclaratiesoort = declaratiesoort;
    }

    public Declaratie soortdeclaratieDeclaratiesoort(Declaratiesoort declaratiesoort) {
        this.setSoortdeclaratieDeclaratiesoort(declaratiesoort);
        return this;
    }

    public Werknemer getDientinWerknemer() {
        return this.dientinWerknemer;
    }

    public void setDientinWerknemer(Werknemer werknemer) {
        this.dientinWerknemer = werknemer;
    }

    public Declaratie dientinWerknemer(Werknemer werknemer) {
        this.setDientinWerknemer(werknemer);
        return this;
    }

    public Set<Declaratieregel> getValtbinnenDeclaratieregels() {
        return this.valtbinnenDeclaratieregels;
    }

    public void setValtbinnenDeclaratieregels(Set<Declaratieregel> declaratieregels) {
        if (this.valtbinnenDeclaratieregels != null) {
            this.valtbinnenDeclaratieregels.forEach(i -> i.setValtbinnenDeclaratie(null));
        }
        if (declaratieregels != null) {
            declaratieregels.forEach(i -> i.setValtbinnenDeclaratie(this));
        }
        this.valtbinnenDeclaratieregels = declaratieregels;
    }

    public Declaratie valtbinnenDeclaratieregels(Set<Declaratieregel> declaratieregels) {
        this.setValtbinnenDeclaratieregels(declaratieregels);
        return this;
    }

    public Declaratie addValtbinnenDeclaratieregel(Declaratieregel declaratieregel) {
        this.valtbinnenDeclaratieregels.add(declaratieregel);
        declaratieregel.setValtbinnenDeclaratie(this);
        return this;
    }

    public Declaratie removeValtbinnenDeclaratieregel(Declaratieregel declaratieregel) {
        this.valtbinnenDeclaratieregels.remove(declaratieregel);
        declaratieregel.setValtbinnenDeclaratie(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Declaratie)) {
            return false;
        }
        return getId() != null && getId().equals(((Declaratie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Declaratie{" +
            "id=" + getId() +
            ", datumdeclaratie='" + getDatumdeclaratie() + "'" +
            ", declaratiebedrag='" + getDeclaratiebedrag() + "'" +
            ", declaratiestatus='" + getDeclaratiestatus() + "'" +
            "}";
    }
}
