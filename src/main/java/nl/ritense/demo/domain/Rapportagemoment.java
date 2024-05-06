package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Rapportagemoment.
 */
@Entity
@Table(name = "rapportagemoment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Rapportagemoment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datum")
    private LocalDate datum;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "termijn")
    private String termijn;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftRapportagemoment")
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
    private Set<Document> heeftDocuments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Subsidie heeftSubsidie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "projectleiderRapportagemoments",
            "aanvragerSubsidies",
            "heeftTenaamstellings",
            "betrokkenenKadastralemutaties",
            "isIndiener",
            "houderParkeervergunnings",
            "verstrekkerSubsidies",
            "projectleiderTaaks",
            "heeftVastgoedcontracts",
        },
        allowSetters = true
    )
    private Rechtspersoon projectleiderRechtspersoon;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Rapportagemoment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public Rapportagemoment datum(LocalDate datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getNaam() {
        return this.naam;
    }

    public Rapportagemoment naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Rapportagemoment omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getTermijn() {
        return this.termijn;
    }

    public Rapportagemoment termijn(String termijn) {
        this.setTermijn(termijn);
        return this;
    }

    public void setTermijn(String termijn) {
        this.termijn = termijn;
    }

    public Set<Document> getHeeftDocuments() {
        return this.heeftDocuments;
    }

    public void setHeeftDocuments(Set<Document> documents) {
        if (this.heeftDocuments != null) {
            this.heeftDocuments.forEach(i -> i.setHeeftRapportagemoment(null));
        }
        if (documents != null) {
            documents.forEach(i -> i.setHeeftRapportagemoment(this));
        }
        this.heeftDocuments = documents;
    }

    public Rapportagemoment heeftDocuments(Set<Document> documents) {
        this.setHeeftDocuments(documents);
        return this;
    }

    public Rapportagemoment addHeeftDocument(Document document) {
        this.heeftDocuments.add(document);
        document.setHeeftRapportagemoment(this);
        return this;
    }

    public Rapportagemoment removeHeeftDocument(Document document) {
        this.heeftDocuments.remove(document);
        document.setHeeftRapportagemoment(null);
        return this;
    }

    public Subsidie getHeeftSubsidie() {
        return this.heeftSubsidie;
    }

    public void setHeeftSubsidie(Subsidie subsidie) {
        this.heeftSubsidie = subsidie;
    }

    public Rapportagemoment heeftSubsidie(Subsidie subsidie) {
        this.setHeeftSubsidie(subsidie);
        return this;
    }

    public Rechtspersoon getProjectleiderRechtspersoon() {
        return this.projectleiderRechtspersoon;
    }

    public void setProjectleiderRechtspersoon(Rechtspersoon rechtspersoon) {
        this.projectleiderRechtspersoon = rechtspersoon;
    }

    public Rapportagemoment projectleiderRechtspersoon(Rechtspersoon rechtspersoon) {
        this.setProjectleiderRechtspersoon(rechtspersoon);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rapportagemoment)) {
            return false;
        }
        return getId() != null && getId().equals(((Rapportagemoment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rapportagemoment{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", termijn='" + getTermijn() + "'" +
            "}";
    }
}
