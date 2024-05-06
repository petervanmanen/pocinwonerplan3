package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Offerteaanvraag.
 */
@Entity
@Table(name = "offerteaanvraag")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Offerteaanvraag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumaanvraag")
    private LocalDate datumaanvraag;

    @Column(name = "datumsluiting")
    private String datumsluiting;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "betreftZaak",
            "mondtuitGunning",
            "procesleiderMedewerker",
            "betreftKwalificaties",
            "betreftOfferteaanvraags",
            "mondtuitAankondigings",
            "betreftOffertes",
            "betreftInschrijvings",
        },
        allowSetters = true
    )
    private Aanbesteding betreftAanbesteding;

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
    private Leverancier gerichtaanLeverancier;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Offerteaanvraag id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumaanvraag() {
        return this.datumaanvraag;
    }

    public Offerteaanvraag datumaanvraag(LocalDate datumaanvraag) {
        this.setDatumaanvraag(datumaanvraag);
        return this;
    }

    public void setDatumaanvraag(LocalDate datumaanvraag) {
        this.datumaanvraag = datumaanvraag;
    }

    public String getDatumsluiting() {
        return this.datumsluiting;
    }

    public Offerteaanvraag datumsluiting(String datumsluiting) {
        this.setDatumsluiting(datumsluiting);
        return this;
    }

    public void setDatumsluiting(String datumsluiting) {
        this.datumsluiting = datumsluiting;
    }

    public String getNaam() {
        return this.naam;
    }

    public Offerteaanvraag naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Offerteaanvraag omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Aanbesteding getBetreftAanbesteding() {
        return this.betreftAanbesteding;
    }

    public void setBetreftAanbesteding(Aanbesteding aanbesteding) {
        this.betreftAanbesteding = aanbesteding;
    }

    public Offerteaanvraag betreftAanbesteding(Aanbesteding aanbesteding) {
        this.setBetreftAanbesteding(aanbesteding);
        return this;
    }

    public Leverancier getGerichtaanLeverancier() {
        return this.gerichtaanLeverancier;
    }

    public void setGerichtaanLeverancier(Leverancier leverancier) {
        this.gerichtaanLeverancier = leverancier;
    }

    public Offerteaanvraag gerichtaanLeverancier(Leverancier leverancier) {
        this.setGerichtaanLeverancier(leverancier);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Offerteaanvraag)) {
            return false;
        }
        return getId() != null && getId().equals(((Offerteaanvraag) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Offerteaanvraag{" +
            "id=" + getId() +
            ", datumaanvraag='" + getDatumaanvraag() + "'" +
            ", datumsluiting='" + getDatumsluiting() + "'" +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
