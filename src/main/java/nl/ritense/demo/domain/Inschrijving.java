package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Inschrijving.
 */
@Entity
@Table(name = "inschrijving")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Inschrijving implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datum")
    private LocalDate datum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftUitschrijvings",
            "kentOnderwijsloopbaans",
            "heeftOnderwijssoorts",
            "gebruiktSportlocaties",
            "betreftBeslissings",
            "heeftVerzuimmeldings",
            "heeftVrijstellings",
            "heeftInschrijvings",
        },
        allowSetters = true
    )
    private School heeftSchool;

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

    @JsonIgnoreProperties(
        value = { "betreftInschrijving", "betreftKandidaat", "betreftOfferte", "inhuurMedewerker", "mondtuitAanbesteding" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "betreftInschrijving")
    private Gunning betreftGunning;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftStartkwalificatie",
            "heeftVerzuimmeldings",
            "heeftVrijstellings",
            "heeftInschrijvings",
            "heeftOnderwijsloopbaans",
            "heeftUitschrijvings",
            "betreftBeslissings",
        },
        allowSetters = true
    )
    private Leerling heeftLeerling;

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
    private Leverancier heeftLeverancier;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Inschrijving id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public Inschrijving datum(LocalDate datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public School getHeeftSchool() {
        return this.heeftSchool;
    }

    public void setHeeftSchool(School school) {
        this.heeftSchool = school;
    }

    public Inschrijving heeftSchool(School school) {
        this.setHeeftSchool(school);
        return this;
    }

    public Aanbesteding getBetreftAanbesteding() {
        return this.betreftAanbesteding;
    }

    public void setBetreftAanbesteding(Aanbesteding aanbesteding) {
        this.betreftAanbesteding = aanbesteding;
    }

    public Inschrijving betreftAanbesteding(Aanbesteding aanbesteding) {
        this.setBetreftAanbesteding(aanbesteding);
        return this;
    }

    public Gunning getBetreftGunning() {
        return this.betreftGunning;
    }

    public void setBetreftGunning(Gunning gunning) {
        if (this.betreftGunning != null) {
            this.betreftGunning.setBetreftInschrijving(null);
        }
        if (gunning != null) {
            gunning.setBetreftInschrijving(this);
        }
        this.betreftGunning = gunning;
    }

    public Inschrijving betreftGunning(Gunning gunning) {
        this.setBetreftGunning(gunning);
        return this;
    }

    public Leerling getHeeftLeerling() {
        return this.heeftLeerling;
    }

    public void setHeeftLeerling(Leerling leerling) {
        this.heeftLeerling = leerling;
    }

    public Inschrijving heeftLeerling(Leerling leerling) {
        this.setHeeftLeerling(leerling);
        return this;
    }

    public Leverancier getHeeftLeverancier() {
        return this.heeftLeverancier;
    }

    public void setHeeftLeverancier(Leverancier leverancier) {
        this.heeftLeverancier = leverancier;
    }

    public Inschrijving heeftLeverancier(Leverancier leverancier) {
        this.setHeeftLeverancier(leverancier);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inschrijving)) {
            return false;
        }
        return getId() != null && getId().equals(((Inschrijving) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inschrijving{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            "}";
    }
}
