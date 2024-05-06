package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Applicatie.
 */
@Entity
@Table(name = "applicatie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Applicatie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "applicatieurl")
    private String applicatieurl;

    @Column(name = "beheerstatus")
    private String beheerstatus;

    @Column(name = "beleidsdomein")
    private String beleidsdomein;

    @Column(name = "categorie")
    private String categorie;

    @Column(name = "guid")
    private String guid;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "packagingstatus")
    private String packagingstatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftversiesApplicatie")
    @JsonIgnoreProperties(value = { "heeftversiesApplicatie" }, allowSetters = true)
    private Set<Versie> heeftversiesVersies = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bevatApplicatie")
    @JsonIgnoreProperties(value = { "geclassificeerdalsClassificaties", "bevatApplicatie" }, allowSetters = true)
    private Set<Gegeven> bevatGegevens = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftherkomstApplicatie")
    @JsonIgnoreProperties(value = { "heeftBatchregels", "heeftherkomstApplicatie" }, allowSetters = true)
    private Set<Batch> heeftherkomstBatches = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftnotitiesApplicatie")
    @JsonIgnoreProperties(value = { "auteurMedewerker", "heeftnotitiesApplicatie" }, allowSetters = true)
    private Set<Notitie> heeftnotitiesNotities = new HashSet<>();

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
    private Leverancier heeftleverancierLeverancier;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_applicatie__heeftdocumenten_document",
        joinColumns = @JoinColumn(name = "applicatie_id"),
        inverseJoinColumns = @JoinColumn(name = "heeftdocumenten_document_id")
    )
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
    private Set<Document> heeftdocumentenDocuments = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_applicatie__rollen_medewerker",
        joinColumns = @JoinColumn(name = "applicatie_id"),
        inverseJoinColumns = @JoinColumn(name = "rollen_medewerker_id")
    )
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
    private Set<Medewerker> rollenMedewerkers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Applicatie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicatieurl() {
        return this.applicatieurl;
    }

    public Applicatie applicatieurl(String applicatieurl) {
        this.setApplicatieurl(applicatieurl);
        return this;
    }

    public void setApplicatieurl(String applicatieurl) {
        this.applicatieurl = applicatieurl;
    }

    public String getBeheerstatus() {
        return this.beheerstatus;
    }

    public Applicatie beheerstatus(String beheerstatus) {
        this.setBeheerstatus(beheerstatus);
        return this;
    }

    public void setBeheerstatus(String beheerstatus) {
        this.beheerstatus = beheerstatus;
    }

    public String getBeleidsdomein() {
        return this.beleidsdomein;
    }

    public Applicatie beleidsdomein(String beleidsdomein) {
        this.setBeleidsdomein(beleidsdomein);
        return this;
    }

    public void setBeleidsdomein(String beleidsdomein) {
        this.beleidsdomein = beleidsdomein;
    }

    public String getCategorie() {
        return this.categorie;
    }

    public Applicatie categorie(String categorie) {
        this.setCategorie(categorie);
        return this;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getGuid() {
        return this.guid;
    }

    public Applicatie guid(String guid) {
        this.setGuid(guid);
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getNaam() {
        return this.naam;
    }

    public Applicatie naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Applicatie omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getPackagingstatus() {
        return this.packagingstatus;
    }

    public Applicatie packagingstatus(String packagingstatus) {
        this.setPackagingstatus(packagingstatus);
        return this;
    }

    public void setPackagingstatus(String packagingstatus) {
        this.packagingstatus = packagingstatus;
    }

    public Set<Versie> getHeeftversiesVersies() {
        return this.heeftversiesVersies;
    }

    public void setHeeftversiesVersies(Set<Versie> versies) {
        if (this.heeftversiesVersies != null) {
            this.heeftversiesVersies.forEach(i -> i.setHeeftversiesApplicatie(null));
        }
        if (versies != null) {
            versies.forEach(i -> i.setHeeftversiesApplicatie(this));
        }
        this.heeftversiesVersies = versies;
    }

    public Applicatie heeftversiesVersies(Set<Versie> versies) {
        this.setHeeftversiesVersies(versies);
        return this;
    }

    public Applicatie addHeeftversiesVersie(Versie versie) {
        this.heeftversiesVersies.add(versie);
        versie.setHeeftversiesApplicatie(this);
        return this;
    }

    public Applicatie removeHeeftversiesVersie(Versie versie) {
        this.heeftversiesVersies.remove(versie);
        versie.setHeeftversiesApplicatie(null);
        return this;
    }

    public Set<Gegeven> getBevatGegevens() {
        return this.bevatGegevens;
    }

    public void setBevatGegevens(Set<Gegeven> gegevens) {
        if (this.bevatGegevens != null) {
            this.bevatGegevens.forEach(i -> i.setBevatApplicatie(null));
        }
        if (gegevens != null) {
            gegevens.forEach(i -> i.setBevatApplicatie(this));
        }
        this.bevatGegevens = gegevens;
    }

    public Applicatie bevatGegevens(Set<Gegeven> gegevens) {
        this.setBevatGegevens(gegevens);
        return this;
    }

    public Applicatie addBevatGegeven(Gegeven gegeven) {
        this.bevatGegevens.add(gegeven);
        gegeven.setBevatApplicatie(this);
        return this;
    }

    public Applicatie removeBevatGegeven(Gegeven gegeven) {
        this.bevatGegevens.remove(gegeven);
        gegeven.setBevatApplicatie(null);
        return this;
    }

    public Set<Batch> getHeeftherkomstBatches() {
        return this.heeftherkomstBatches;
    }

    public void setHeeftherkomstBatches(Set<Batch> batches) {
        if (this.heeftherkomstBatches != null) {
            this.heeftherkomstBatches.forEach(i -> i.setHeeftherkomstApplicatie(null));
        }
        if (batches != null) {
            batches.forEach(i -> i.setHeeftherkomstApplicatie(this));
        }
        this.heeftherkomstBatches = batches;
    }

    public Applicatie heeftherkomstBatches(Set<Batch> batches) {
        this.setHeeftherkomstBatches(batches);
        return this;
    }

    public Applicatie addHeeftherkomstBatch(Batch batch) {
        this.heeftherkomstBatches.add(batch);
        batch.setHeeftherkomstApplicatie(this);
        return this;
    }

    public Applicatie removeHeeftherkomstBatch(Batch batch) {
        this.heeftherkomstBatches.remove(batch);
        batch.setHeeftherkomstApplicatie(null);
        return this;
    }

    public Set<Notitie> getHeeftnotitiesNotities() {
        return this.heeftnotitiesNotities;
    }

    public void setHeeftnotitiesNotities(Set<Notitie> notities) {
        if (this.heeftnotitiesNotities != null) {
            this.heeftnotitiesNotities.forEach(i -> i.setHeeftnotitiesApplicatie(null));
        }
        if (notities != null) {
            notities.forEach(i -> i.setHeeftnotitiesApplicatie(this));
        }
        this.heeftnotitiesNotities = notities;
    }

    public Applicatie heeftnotitiesNotities(Set<Notitie> notities) {
        this.setHeeftnotitiesNotities(notities);
        return this;
    }

    public Applicatie addHeeftnotitiesNotitie(Notitie notitie) {
        this.heeftnotitiesNotities.add(notitie);
        notitie.setHeeftnotitiesApplicatie(this);
        return this;
    }

    public Applicatie removeHeeftnotitiesNotitie(Notitie notitie) {
        this.heeftnotitiesNotities.remove(notitie);
        notitie.setHeeftnotitiesApplicatie(null);
        return this;
    }

    public Leverancier getHeeftleverancierLeverancier() {
        return this.heeftleverancierLeverancier;
    }

    public void setHeeftleverancierLeverancier(Leverancier leverancier) {
        this.heeftleverancierLeverancier = leverancier;
    }

    public Applicatie heeftleverancierLeverancier(Leverancier leverancier) {
        this.setHeeftleverancierLeverancier(leverancier);
        return this;
    }

    public Set<Document> getHeeftdocumentenDocuments() {
        return this.heeftdocumentenDocuments;
    }

    public void setHeeftdocumentenDocuments(Set<Document> documents) {
        this.heeftdocumentenDocuments = documents;
    }

    public Applicatie heeftdocumentenDocuments(Set<Document> documents) {
        this.setHeeftdocumentenDocuments(documents);
        return this;
    }

    public Applicatie addHeeftdocumentenDocument(Document document) {
        this.heeftdocumentenDocuments.add(document);
        return this;
    }

    public Applicatie removeHeeftdocumentenDocument(Document document) {
        this.heeftdocumentenDocuments.remove(document);
        return this;
    }

    public Set<Medewerker> getRollenMedewerkers() {
        return this.rollenMedewerkers;
    }

    public void setRollenMedewerkers(Set<Medewerker> medewerkers) {
        this.rollenMedewerkers = medewerkers;
    }

    public Applicatie rollenMedewerkers(Set<Medewerker> medewerkers) {
        this.setRollenMedewerkers(medewerkers);
        return this;
    }

    public Applicatie addRollenMedewerker(Medewerker medewerker) {
        this.rollenMedewerkers.add(medewerker);
        return this;
    }

    public Applicatie removeRollenMedewerker(Medewerker medewerker) {
        this.rollenMedewerkers.remove(medewerker);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Applicatie)) {
            return false;
        }
        return getId() != null && getId().equals(((Applicatie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Applicatie{" +
            "id=" + getId() +
            ", applicatieurl='" + getApplicatieurl() + "'" +
            ", beheerstatus='" + getBeheerstatus() + "'" +
            ", beleidsdomein='" + getBeleidsdomein() + "'" +
            ", categorie='" + getCategorie() + "'" +
            ", guid='" + getGuid() + "'" +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", packagingstatus='" + getPackagingstatus() + "'" +
            "}";
    }
}
