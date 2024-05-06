package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Document.
 */
@Entity
@Table(name = "document")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "cocumentbeschrijving")
    private String cocumentbeschrijving;

    @Column(name = "datumcreatiedocument")
    private String datumcreatiedocument;

    @Column(name = "datumontvangstdocument")
    private String datumontvangstdocument;

    @Column(name = "datumverzendingdocument")
    private String datumverzendingdocument;

    @Column(name = "documentauteur")
    private String documentauteur;

    @Column(name = "documentidentificatie")
    private String documentidentificatie;

    @Column(name = "documenttitel")
    private String documenttitel;

    @Size(max = 20)
    @Column(name = "vertrouwelijkaanduiding", length = 20)
    private String vertrouwelijkaanduiding;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftkenmerkDocuments" }, allowSetters = true)
    private Identificatiekenmerk heeftkenmerkIdentificatiekenmerk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "isvanDocuments" }, allowSetters = true)
    private Documenttype isvanDocumenttype;

    @JsonIgnoreProperties(value = { "isvastgelegdinDocument" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "isvastgelegdinDocument")
    private Verkeersbesluit isvastgelegdinVerkeersbesluit;

    @JsonIgnoreProperties(
        value = { "isvastgelegdinDocument", "isuitkomstvanZaak", "isvanBesluittype", "kanvastgelegdzijnalsDocuments" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "isvastgelegdinDocument")
    private Besluit isvastgelegdinBesluit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "inspectierapportDocuments", "isgevestigdinVerblijfsobject", "bedientWijk", "heeftBelijnings", "heeftSportmateriaals" },
        allowSetters = true
    )
    private Binnenlocatie inspectierapportBinnenlocatie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftDocuments", "heeftSubsidie", "projectleiderRechtspersoon" }, allowSetters = true)
    private Rapportagemoment heeftRapportagemoment;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftDocument")
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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "heeftdocumentenDocuments")
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
    private Set<Applicatie> heeftdocumentenApplicaties = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "kanvastgelegdzijnalsDocuments")
    @JsonIgnoreProperties(
        value = { "isvastgelegdinDocument", "isuitkomstvanZaak", "isvanBesluittype", "kanvastgelegdzijnalsDocuments" },
        allowSetters = true
    )
    private Set<Besluit> kanvastgelegdzijnalsBesluits = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "kentDocuments")
    @JsonIgnoreProperties(
        value = {
            "heeftproductProducttype",
            "heeftKlantbeoordeling",
            "heeftHeffing",
            "heeftbetalingBetalings",
            "heeftStatuses",
            "betreftProject",
            "isvanZaaktype",
            "kentDocuments",
            "afhandelendmedewerkerMedewerkers",
            "leidttotVerzoek",
            "heeftSubsidie",
            "betreftAanbesteding",
            "heeftbetrekkingopBalieafspraaks",
            "isuitkomstvanBesluits",
            "heeftbetrekkingopKlantcontacts",
            "heeftGrondslags",
            "uitgevoerdbinnenBedrijfsproces",
            "oefentuitBetrokkenes",
        },
        allowSetters = true
    )
    private Set<Zaak> kentZaaks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Document id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCocumentbeschrijving() {
        return this.cocumentbeschrijving;
    }

    public Document cocumentbeschrijving(String cocumentbeschrijving) {
        this.setCocumentbeschrijving(cocumentbeschrijving);
        return this;
    }

    public void setCocumentbeschrijving(String cocumentbeschrijving) {
        this.cocumentbeschrijving = cocumentbeschrijving;
    }

    public String getDatumcreatiedocument() {
        return this.datumcreatiedocument;
    }

    public Document datumcreatiedocument(String datumcreatiedocument) {
        this.setDatumcreatiedocument(datumcreatiedocument);
        return this;
    }

    public void setDatumcreatiedocument(String datumcreatiedocument) {
        this.datumcreatiedocument = datumcreatiedocument;
    }

    public String getDatumontvangstdocument() {
        return this.datumontvangstdocument;
    }

    public Document datumontvangstdocument(String datumontvangstdocument) {
        this.setDatumontvangstdocument(datumontvangstdocument);
        return this;
    }

    public void setDatumontvangstdocument(String datumontvangstdocument) {
        this.datumontvangstdocument = datumontvangstdocument;
    }

    public String getDatumverzendingdocument() {
        return this.datumverzendingdocument;
    }

    public Document datumverzendingdocument(String datumverzendingdocument) {
        this.setDatumverzendingdocument(datumverzendingdocument);
        return this;
    }

    public void setDatumverzendingdocument(String datumverzendingdocument) {
        this.datumverzendingdocument = datumverzendingdocument;
    }

    public String getDocumentauteur() {
        return this.documentauteur;
    }

    public Document documentauteur(String documentauteur) {
        this.setDocumentauteur(documentauteur);
        return this;
    }

    public void setDocumentauteur(String documentauteur) {
        this.documentauteur = documentauteur;
    }

    public String getDocumentidentificatie() {
        return this.documentidentificatie;
    }

    public Document documentidentificatie(String documentidentificatie) {
        this.setDocumentidentificatie(documentidentificatie);
        return this;
    }

    public void setDocumentidentificatie(String documentidentificatie) {
        this.documentidentificatie = documentidentificatie;
    }

    public String getDocumenttitel() {
        return this.documenttitel;
    }

    public Document documenttitel(String documenttitel) {
        this.setDocumenttitel(documenttitel);
        return this;
    }

    public void setDocumenttitel(String documenttitel) {
        this.documenttitel = documenttitel;
    }

    public String getVertrouwelijkaanduiding() {
        return this.vertrouwelijkaanduiding;
    }

    public Document vertrouwelijkaanduiding(String vertrouwelijkaanduiding) {
        this.setVertrouwelijkaanduiding(vertrouwelijkaanduiding);
        return this;
    }

    public void setVertrouwelijkaanduiding(String vertrouwelijkaanduiding) {
        this.vertrouwelijkaanduiding = vertrouwelijkaanduiding;
    }

    public Identificatiekenmerk getHeeftkenmerkIdentificatiekenmerk() {
        return this.heeftkenmerkIdentificatiekenmerk;
    }

    public void setHeeftkenmerkIdentificatiekenmerk(Identificatiekenmerk identificatiekenmerk) {
        this.heeftkenmerkIdentificatiekenmerk = identificatiekenmerk;
    }

    public Document heeftkenmerkIdentificatiekenmerk(Identificatiekenmerk identificatiekenmerk) {
        this.setHeeftkenmerkIdentificatiekenmerk(identificatiekenmerk);
        return this;
    }

    public Documenttype getIsvanDocumenttype() {
        return this.isvanDocumenttype;
    }

    public void setIsvanDocumenttype(Documenttype documenttype) {
        this.isvanDocumenttype = documenttype;
    }

    public Document isvanDocumenttype(Documenttype documenttype) {
        this.setIsvanDocumenttype(documenttype);
        return this;
    }

    public Verkeersbesluit getIsvastgelegdinVerkeersbesluit() {
        return this.isvastgelegdinVerkeersbesluit;
    }

    public void setIsvastgelegdinVerkeersbesluit(Verkeersbesluit verkeersbesluit) {
        if (this.isvastgelegdinVerkeersbesluit != null) {
            this.isvastgelegdinVerkeersbesluit.setIsvastgelegdinDocument(null);
        }
        if (verkeersbesluit != null) {
            verkeersbesluit.setIsvastgelegdinDocument(this);
        }
        this.isvastgelegdinVerkeersbesluit = verkeersbesluit;
    }

    public Document isvastgelegdinVerkeersbesluit(Verkeersbesluit verkeersbesluit) {
        this.setIsvastgelegdinVerkeersbesluit(verkeersbesluit);
        return this;
    }

    public Besluit getIsvastgelegdinBesluit() {
        return this.isvastgelegdinBesluit;
    }

    public void setIsvastgelegdinBesluit(Besluit besluit) {
        if (this.isvastgelegdinBesluit != null) {
            this.isvastgelegdinBesluit.setIsvastgelegdinDocument(null);
        }
        if (besluit != null) {
            besluit.setIsvastgelegdinDocument(this);
        }
        this.isvastgelegdinBesluit = besluit;
    }

    public Document isvastgelegdinBesluit(Besluit besluit) {
        this.setIsvastgelegdinBesluit(besluit);
        return this;
    }

    public Binnenlocatie getInspectierapportBinnenlocatie() {
        return this.inspectierapportBinnenlocatie;
    }

    public void setInspectierapportBinnenlocatie(Binnenlocatie binnenlocatie) {
        this.inspectierapportBinnenlocatie = binnenlocatie;
    }

    public Document inspectierapportBinnenlocatie(Binnenlocatie binnenlocatie) {
        this.setInspectierapportBinnenlocatie(binnenlocatie);
        return this;
    }

    public Rapportagemoment getHeeftRapportagemoment() {
        return this.heeftRapportagemoment;
    }

    public void setHeeftRapportagemoment(Rapportagemoment rapportagemoment) {
        this.heeftRapportagemoment = rapportagemoment;
    }

    public Document heeftRapportagemoment(Rapportagemoment rapportagemoment) {
        this.setHeeftRapportagemoment(rapportagemoment);
        return this;
    }

    public Set<Subsidie> getHeeftSubsidies() {
        return this.heeftSubsidies;
    }

    public void setHeeftSubsidies(Set<Subsidie> subsidies) {
        if (this.heeftSubsidies != null) {
            this.heeftSubsidies.forEach(i -> i.setHeeftDocument(null));
        }
        if (subsidies != null) {
            subsidies.forEach(i -> i.setHeeftDocument(this));
        }
        this.heeftSubsidies = subsidies;
    }

    public Document heeftSubsidies(Set<Subsidie> subsidies) {
        this.setHeeftSubsidies(subsidies);
        return this;
    }

    public Document addHeeftSubsidie(Subsidie subsidie) {
        this.heeftSubsidies.add(subsidie);
        subsidie.setHeeftDocument(this);
        return this;
    }

    public Document removeHeeftSubsidie(Subsidie subsidie) {
        this.heeftSubsidies.remove(subsidie);
        subsidie.setHeeftDocument(null);
        return this;
    }

    public Set<Applicatie> getHeeftdocumentenApplicaties() {
        return this.heeftdocumentenApplicaties;
    }

    public void setHeeftdocumentenApplicaties(Set<Applicatie> applicaties) {
        if (this.heeftdocumentenApplicaties != null) {
            this.heeftdocumentenApplicaties.forEach(i -> i.removeHeeftdocumentenDocument(this));
        }
        if (applicaties != null) {
            applicaties.forEach(i -> i.addHeeftdocumentenDocument(this));
        }
        this.heeftdocumentenApplicaties = applicaties;
    }

    public Document heeftdocumentenApplicaties(Set<Applicatie> applicaties) {
        this.setHeeftdocumentenApplicaties(applicaties);
        return this;
    }

    public Document addHeeftdocumentenApplicatie(Applicatie applicatie) {
        this.heeftdocumentenApplicaties.add(applicatie);
        applicatie.getHeeftdocumentenDocuments().add(this);
        return this;
    }

    public Document removeHeeftdocumentenApplicatie(Applicatie applicatie) {
        this.heeftdocumentenApplicaties.remove(applicatie);
        applicatie.getHeeftdocumentenDocuments().remove(this);
        return this;
    }

    public Set<Besluit> getKanvastgelegdzijnalsBesluits() {
        return this.kanvastgelegdzijnalsBesluits;
    }

    public void setKanvastgelegdzijnalsBesluits(Set<Besluit> besluits) {
        if (this.kanvastgelegdzijnalsBesluits != null) {
            this.kanvastgelegdzijnalsBesluits.forEach(i -> i.removeKanvastgelegdzijnalsDocument(this));
        }
        if (besluits != null) {
            besluits.forEach(i -> i.addKanvastgelegdzijnalsDocument(this));
        }
        this.kanvastgelegdzijnalsBesluits = besluits;
    }

    public Document kanvastgelegdzijnalsBesluits(Set<Besluit> besluits) {
        this.setKanvastgelegdzijnalsBesluits(besluits);
        return this;
    }

    public Document addKanvastgelegdzijnalsBesluit(Besluit besluit) {
        this.kanvastgelegdzijnalsBesluits.add(besluit);
        besluit.getKanvastgelegdzijnalsDocuments().add(this);
        return this;
    }

    public Document removeKanvastgelegdzijnalsBesluit(Besluit besluit) {
        this.kanvastgelegdzijnalsBesluits.remove(besluit);
        besluit.getKanvastgelegdzijnalsDocuments().remove(this);
        return this;
    }

    public Set<Zaak> getKentZaaks() {
        return this.kentZaaks;
    }

    public void setKentZaaks(Set<Zaak> zaaks) {
        if (this.kentZaaks != null) {
            this.kentZaaks.forEach(i -> i.removeKentDocument(this));
        }
        if (zaaks != null) {
            zaaks.forEach(i -> i.addKentDocument(this));
        }
        this.kentZaaks = zaaks;
    }

    public Document kentZaaks(Set<Zaak> zaaks) {
        this.setKentZaaks(zaaks);
        return this;
    }

    public Document addKentZaak(Zaak zaak) {
        this.kentZaaks.add(zaak);
        zaak.getKentDocuments().add(this);
        return this;
    }

    public Document removeKentZaak(Zaak zaak) {
        this.kentZaaks.remove(zaak);
        zaak.getKentDocuments().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Document)) {
            return false;
        }
        return getId() != null && getId().equals(((Document) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Document{" +
            "id=" + getId() +
            ", cocumentbeschrijving='" + getCocumentbeschrijving() + "'" +
            ", datumcreatiedocument='" + getDatumcreatiedocument() + "'" +
            ", datumontvangstdocument='" + getDatumontvangstdocument() + "'" +
            ", datumverzendingdocument='" + getDatumverzendingdocument() + "'" +
            ", documentauteur='" + getDocumentauteur() + "'" +
            ", documentidentificatie='" + getDocumentidentificatie() + "'" +
            ", documenttitel='" + getDocumenttitel() + "'" +
            ", vertrouwelijkaanduiding='" + getVertrouwelijkaanduiding() + "'" +
            "}";
    }
}
