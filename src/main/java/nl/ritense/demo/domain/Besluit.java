package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Besluit.
 */
@Entity
@Table(name = "besluit")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Besluit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "besluit")
    private String besluit;

    @Column(name = "besluitidentificatie")
    private String besluitidentificatie;

    @Column(name = "besluittoelichting")
    private String besluittoelichting;

    @Column(name = "datumbesluit")
    private String datumbesluit;

    @Column(name = "datumpublicatie")
    private String datumpublicatie;

    @Column(name = "datumstart")
    private String datumstart;

    @Column(name = "datumuiterlijkereactie")
    private String datumuiterlijkereactie;

    @Column(name = "datumverval")
    private String datumverval;

    @Column(name = "datumverzending")
    private String datumverzending;

    @Column(name = "redenverval")
    private String redenverval;

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Document isvastgelegdinDocument;

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Zaak isuitkomstvanZaak;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "isvanBesluits" }, allowSetters = true)
    private Besluittype isvanBesluittype;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_besluit__kanvastgelegdzijnals_document",
        joinColumns = @JoinColumn(name = "besluit_id"),
        inverseJoinColumns = @JoinColumn(name = "kanvastgelegdzijnals_document_id")
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
    private Set<Document> kanvastgelegdzijnalsDocuments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Besluit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBesluit() {
        return this.besluit;
    }

    public Besluit besluit(String besluit) {
        this.setBesluit(besluit);
        return this;
    }

    public void setBesluit(String besluit) {
        this.besluit = besluit;
    }

    public String getBesluitidentificatie() {
        return this.besluitidentificatie;
    }

    public Besluit besluitidentificatie(String besluitidentificatie) {
        this.setBesluitidentificatie(besluitidentificatie);
        return this;
    }

    public void setBesluitidentificatie(String besluitidentificatie) {
        this.besluitidentificatie = besluitidentificatie;
    }

    public String getBesluittoelichting() {
        return this.besluittoelichting;
    }

    public Besluit besluittoelichting(String besluittoelichting) {
        this.setBesluittoelichting(besluittoelichting);
        return this;
    }

    public void setBesluittoelichting(String besluittoelichting) {
        this.besluittoelichting = besluittoelichting;
    }

    public String getDatumbesluit() {
        return this.datumbesluit;
    }

    public Besluit datumbesluit(String datumbesluit) {
        this.setDatumbesluit(datumbesluit);
        return this;
    }

    public void setDatumbesluit(String datumbesluit) {
        this.datumbesluit = datumbesluit;
    }

    public String getDatumpublicatie() {
        return this.datumpublicatie;
    }

    public Besluit datumpublicatie(String datumpublicatie) {
        this.setDatumpublicatie(datumpublicatie);
        return this;
    }

    public void setDatumpublicatie(String datumpublicatie) {
        this.datumpublicatie = datumpublicatie;
    }

    public String getDatumstart() {
        return this.datumstart;
    }

    public Besluit datumstart(String datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(String datumstart) {
        this.datumstart = datumstart;
    }

    public String getDatumuiterlijkereactie() {
        return this.datumuiterlijkereactie;
    }

    public Besluit datumuiterlijkereactie(String datumuiterlijkereactie) {
        this.setDatumuiterlijkereactie(datumuiterlijkereactie);
        return this;
    }

    public void setDatumuiterlijkereactie(String datumuiterlijkereactie) {
        this.datumuiterlijkereactie = datumuiterlijkereactie;
    }

    public String getDatumverval() {
        return this.datumverval;
    }

    public Besluit datumverval(String datumverval) {
        this.setDatumverval(datumverval);
        return this;
    }

    public void setDatumverval(String datumverval) {
        this.datumverval = datumverval;
    }

    public String getDatumverzending() {
        return this.datumverzending;
    }

    public Besluit datumverzending(String datumverzending) {
        this.setDatumverzending(datumverzending);
        return this;
    }

    public void setDatumverzending(String datumverzending) {
        this.datumverzending = datumverzending;
    }

    public String getRedenverval() {
        return this.redenverval;
    }

    public Besluit redenverval(String redenverval) {
        this.setRedenverval(redenverval);
        return this;
    }

    public void setRedenverval(String redenverval) {
        this.redenverval = redenverval;
    }

    public Document getIsvastgelegdinDocument() {
        return this.isvastgelegdinDocument;
    }

    public void setIsvastgelegdinDocument(Document document) {
        this.isvastgelegdinDocument = document;
    }

    public Besluit isvastgelegdinDocument(Document document) {
        this.setIsvastgelegdinDocument(document);
        return this;
    }

    public Zaak getIsuitkomstvanZaak() {
        return this.isuitkomstvanZaak;
    }

    public void setIsuitkomstvanZaak(Zaak zaak) {
        this.isuitkomstvanZaak = zaak;
    }

    public Besluit isuitkomstvanZaak(Zaak zaak) {
        this.setIsuitkomstvanZaak(zaak);
        return this;
    }

    public Besluittype getIsvanBesluittype() {
        return this.isvanBesluittype;
    }

    public void setIsvanBesluittype(Besluittype besluittype) {
        this.isvanBesluittype = besluittype;
    }

    public Besluit isvanBesluittype(Besluittype besluittype) {
        this.setIsvanBesluittype(besluittype);
        return this;
    }

    public Set<Document> getKanvastgelegdzijnalsDocuments() {
        return this.kanvastgelegdzijnalsDocuments;
    }

    public void setKanvastgelegdzijnalsDocuments(Set<Document> documents) {
        this.kanvastgelegdzijnalsDocuments = documents;
    }

    public Besluit kanvastgelegdzijnalsDocuments(Set<Document> documents) {
        this.setKanvastgelegdzijnalsDocuments(documents);
        return this;
    }

    public Besluit addKanvastgelegdzijnalsDocument(Document document) {
        this.kanvastgelegdzijnalsDocuments.add(document);
        return this;
    }

    public Besluit removeKanvastgelegdzijnalsDocument(Document document) {
        this.kanvastgelegdzijnalsDocuments.remove(document);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Besluit)) {
            return false;
        }
        return getId() != null && getId().equals(((Besluit) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Besluit{" +
            "id=" + getId() +
            ", besluit='" + getBesluit() + "'" +
            ", besluitidentificatie='" + getBesluitidentificatie() + "'" +
            ", besluittoelichting='" + getBesluittoelichting() + "'" +
            ", datumbesluit='" + getDatumbesluit() + "'" +
            ", datumpublicatie='" + getDatumpublicatie() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", datumuiterlijkereactie='" + getDatumuiterlijkereactie() + "'" +
            ", datumverval='" + getDatumverval() + "'" +
            ", datumverzending='" + getDatumverzending() + "'" +
            ", redenverval='" + getRedenverval() + "'" +
            "}";
    }
}
