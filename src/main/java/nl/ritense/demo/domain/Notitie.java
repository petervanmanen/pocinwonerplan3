package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Notitie.
 */
@Entity
@Table(name = "notitie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Notitie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datum")
    private LocalDate datum;

    @Column(name = "inhoud")
    private String inhoud;

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Medewerker auteurMedewerker;

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Applicatie heeftnotitiesApplicatie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Notitie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public Notitie datum(LocalDate datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getInhoud() {
        return this.inhoud;
    }

    public Notitie inhoud(String inhoud) {
        this.setInhoud(inhoud);
        return this;
    }

    public void setInhoud(String inhoud) {
        this.inhoud = inhoud;
    }

    public Medewerker getAuteurMedewerker() {
        return this.auteurMedewerker;
    }

    public void setAuteurMedewerker(Medewerker medewerker) {
        this.auteurMedewerker = medewerker;
    }

    public Notitie auteurMedewerker(Medewerker medewerker) {
        this.setAuteurMedewerker(medewerker);
        return this;
    }

    public Applicatie getHeeftnotitiesApplicatie() {
        return this.heeftnotitiesApplicatie;
    }

    public void setHeeftnotitiesApplicatie(Applicatie applicatie) {
        this.heeftnotitiesApplicatie = applicatie;
    }

    public Notitie heeftnotitiesApplicatie(Applicatie applicatie) {
        this.setHeeftnotitiesApplicatie(applicatie);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notitie)) {
            return false;
        }
        return getId() != null && getId().equals(((Notitie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Notitie{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", inhoud='" + getInhoud() + "'" +
            "}";
    }
}
