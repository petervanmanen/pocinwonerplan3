package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Aanbesteding.
 */
@Entity
@Table(name = "aanbesteding")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Aanbesteding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumpublicatie")
    private String datumpublicatie;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "digitaal")
    private String digitaal;

    @Column(name = "naam")
    private String naam;

    @Column(name = "procedure")
    private String procedure;

    @Column(name = "referentienummer")
    private String referentienummer;

    @Column(name = "scoremaximaal")
    private String scoremaximaal;

    @Column(name = "status")
    private String status;

    @Column(name = "tendernedkenmerk")
    private String tendernedkenmerk;

    @Column(name = "type")
    private String type;

    @Column(name = "volgendesluiting")
    private String volgendesluiting;

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Zaak betreftZaak;

    @JsonIgnoreProperties(
        value = { "betreftInschrijving", "betreftKandidaat", "betreftOfferte", "inhuurMedewerker", "mondtuitAanbesteding" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Gunning mondtuitGunning;

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
    private Medewerker procesleiderMedewerker;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftAanbesteding")
    @JsonIgnoreProperties(value = { "betreftAanbesteding", "heeftLeverancier" }, allowSetters = true)
    private Set<Kwalificatie> betreftKwalificaties = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftAanbesteding")
    @JsonIgnoreProperties(value = { "betreftAanbesteding", "gerichtaanLeverancier" }, allowSetters = true)
    private Set<Offerteaanvraag> betreftOfferteaanvraags = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mondtuitAanbesteding")
    @JsonIgnoreProperties(value = { "mondtuitAanbesteding" }, allowSetters = true)
    private Set<Aankondiging> mondtuitAankondigings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftAanbesteding")
    @JsonIgnoreProperties(value = { "betreftAanbesteding", "ingedienddoorLeverancier", "betreftGunning" }, allowSetters = true)
    private Set<Offerte> betreftOffertes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftAanbesteding")
    @JsonIgnoreProperties(
        value = { "heeftSchool", "betreftAanbesteding", "betreftGunning", "heeftLeerling", "heeftLeverancier" },
        allowSetters = true
    )
    private Set<Inschrijving> betreftInschrijvings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Aanbesteding id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatumpublicatie() {
        return this.datumpublicatie;
    }

    public Aanbesteding datumpublicatie(String datumpublicatie) {
        this.setDatumpublicatie(datumpublicatie);
        return this;
    }

    public void setDatumpublicatie(String datumpublicatie) {
        this.datumpublicatie = datumpublicatie;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Aanbesteding datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public String getDigitaal() {
        return this.digitaal;
    }

    public Aanbesteding digitaal(String digitaal) {
        this.setDigitaal(digitaal);
        return this;
    }

    public void setDigitaal(String digitaal) {
        this.digitaal = digitaal;
    }

    public String getNaam() {
        return this.naam;
    }

    public Aanbesteding naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getProcedure() {
        return this.procedure;
    }

    public Aanbesteding procedure(String procedure) {
        this.setProcedure(procedure);
        return this;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public String getReferentienummer() {
        return this.referentienummer;
    }

    public Aanbesteding referentienummer(String referentienummer) {
        this.setReferentienummer(referentienummer);
        return this;
    }

    public void setReferentienummer(String referentienummer) {
        this.referentienummer = referentienummer;
    }

    public String getScoremaximaal() {
        return this.scoremaximaal;
    }

    public Aanbesteding scoremaximaal(String scoremaximaal) {
        this.setScoremaximaal(scoremaximaal);
        return this;
    }

    public void setScoremaximaal(String scoremaximaal) {
        this.scoremaximaal = scoremaximaal;
    }

    public String getStatus() {
        return this.status;
    }

    public Aanbesteding status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTendernedkenmerk() {
        return this.tendernedkenmerk;
    }

    public Aanbesteding tendernedkenmerk(String tendernedkenmerk) {
        this.setTendernedkenmerk(tendernedkenmerk);
        return this;
    }

    public void setTendernedkenmerk(String tendernedkenmerk) {
        this.tendernedkenmerk = tendernedkenmerk;
    }

    public String getType() {
        return this.type;
    }

    public Aanbesteding type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVolgendesluiting() {
        return this.volgendesluiting;
    }

    public Aanbesteding volgendesluiting(String volgendesluiting) {
        this.setVolgendesluiting(volgendesluiting);
        return this;
    }

    public void setVolgendesluiting(String volgendesluiting) {
        this.volgendesluiting = volgendesluiting;
    }

    public Zaak getBetreftZaak() {
        return this.betreftZaak;
    }

    public void setBetreftZaak(Zaak zaak) {
        this.betreftZaak = zaak;
    }

    public Aanbesteding betreftZaak(Zaak zaak) {
        this.setBetreftZaak(zaak);
        return this;
    }

    public Gunning getMondtuitGunning() {
        return this.mondtuitGunning;
    }

    public void setMondtuitGunning(Gunning gunning) {
        this.mondtuitGunning = gunning;
    }

    public Aanbesteding mondtuitGunning(Gunning gunning) {
        this.setMondtuitGunning(gunning);
        return this;
    }

    public Medewerker getProcesleiderMedewerker() {
        return this.procesleiderMedewerker;
    }

    public void setProcesleiderMedewerker(Medewerker medewerker) {
        this.procesleiderMedewerker = medewerker;
    }

    public Aanbesteding procesleiderMedewerker(Medewerker medewerker) {
        this.setProcesleiderMedewerker(medewerker);
        return this;
    }

    public Set<Kwalificatie> getBetreftKwalificaties() {
        return this.betreftKwalificaties;
    }

    public void setBetreftKwalificaties(Set<Kwalificatie> kwalificaties) {
        if (this.betreftKwalificaties != null) {
            this.betreftKwalificaties.forEach(i -> i.setBetreftAanbesteding(null));
        }
        if (kwalificaties != null) {
            kwalificaties.forEach(i -> i.setBetreftAanbesteding(this));
        }
        this.betreftKwalificaties = kwalificaties;
    }

    public Aanbesteding betreftKwalificaties(Set<Kwalificatie> kwalificaties) {
        this.setBetreftKwalificaties(kwalificaties);
        return this;
    }

    public Aanbesteding addBetreftKwalificatie(Kwalificatie kwalificatie) {
        this.betreftKwalificaties.add(kwalificatie);
        kwalificatie.setBetreftAanbesteding(this);
        return this;
    }

    public Aanbesteding removeBetreftKwalificatie(Kwalificatie kwalificatie) {
        this.betreftKwalificaties.remove(kwalificatie);
        kwalificatie.setBetreftAanbesteding(null);
        return this;
    }

    public Set<Offerteaanvraag> getBetreftOfferteaanvraags() {
        return this.betreftOfferteaanvraags;
    }

    public void setBetreftOfferteaanvraags(Set<Offerteaanvraag> offerteaanvraags) {
        if (this.betreftOfferteaanvraags != null) {
            this.betreftOfferteaanvraags.forEach(i -> i.setBetreftAanbesteding(null));
        }
        if (offerteaanvraags != null) {
            offerteaanvraags.forEach(i -> i.setBetreftAanbesteding(this));
        }
        this.betreftOfferteaanvraags = offerteaanvraags;
    }

    public Aanbesteding betreftOfferteaanvraags(Set<Offerteaanvraag> offerteaanvraags) {
        this.setBetreftOfferteaanvraags(offerteaanvraags);
        return this;
    }

    public Aanbesteding addBetreftOfferteaanvraag(Offerteaanvraag offerteaanvraag) {
        this.betreftOfferteaanvraags.add(offerteaanvraag);
        offerteaanvraag.setBetreftAanbesteding(this);
        return this;
    }

    public Aanbesteding removeBetreftOfferteaanvraag(Offerteaanvraag offerteaanvraag) {
        this.betreftOfferteaanvraags.remove(offerteaanvraag);
        offerteaanvraag.setBetreftAanbesteding(null);
        return this;
    }

    public Set<Aankondiging> getMondtuitAankondigings() {
        return this.mondtuitAankondigings;
    }

    public void setMondtuitAankondigings(Set<Aankondiging> aankondigings) {
        if (this.mondtuitAankondigings != null) {
            this.mondtuitAankondigings.forEach(i -> i.setMondtuitAanbesteding(null));
        }
        if (aankondigings != null) {
            aankondigings.forEach(i -> i.setMondtuitAanbesteding(this));
        }
        this.mondtuitAankondigings = aankondigings;
    }

    public Aanbesteding mondtuitAankondigings(Set<Aankondiging> aankondigings) {
        this.setMondtuitAankondigings(aankondigings);
        return this;
    }

    public Aanbesteding addMondtuitAankondiging(Aankondiging aankondiging) {
        this.mondtuitAankondigings.add(aankondiging);
        aankondiging.setMondtuitAanbesteding(this);
        return this;
    }

    public Aanbesteding removeMondtuitAankondiging(Aankondiging aankondiging) {
        this.mondtuitAankondigings.remove(aankondiging);
        aankondiging.setMondtuitAanbesteding(null);
        return this;
    }

    public Set<Offerte> getBetreftOffertes() {
        return this.betreftOffertes;
    }

    public void setBetreftOffertes(Set<Offerte> offertes) {
        if (this.betreftOffertes != null) {
            this.betreftOffertes.forEach(i -> i.setBetreftAanbesteding(null));
        }
        if (offertes != null) {
            offertes.forEach(i -> i.setBetreftAanbesteding(this));
        }
        this.betreftOffertes = offertes;
    }

    public Aanbesteding betreftOffertes(Set<Offerte> offertes) {
        this.setBetreftOffertes(offertes);
        return this;
    }

    public Aanbesteding addBetreftOfferte(Offerte offerte) {
        this.betreftOffertes.add(offerte);
        offerte.setBetreftAanbesteding(this);
        return this;
    }

    public Aanbesteding removeBetreftOfferte(Offerte offerte) {
        this.betreftOffertes.remove(offerte);
        offerte.setBetreftAanbesteding(null);
        return this;
    }

    public Set<Inschrijving> getBetreftInschrijvings() {
        return this.betreftInschrijvings;
    }

    public void setBetreftInschrijvings(Set<Inschrijving> inschrijvings) {
        if (this.betreftInschrijvings != null) {
            this.betreftInschrijvings.forEach(i -> i.setBetreftAanbesteding(null));
        }
        if (inschrijvings != null) {
            inschrijvings.forEach(i -> i.setBetreftAanbesteding(this));
        }
        this.betreftInschrijvings = inschrijvings;
    }

    public Aanbesteding betreftInschrijvings(Set<Inschrijving> inschrijvings) {
        this.setBetreftInschrijvings(inschrijvings);
        return this;
    }

    public Aanbesteding addBetreftInschrijving(Inschrijving inschrijving) {
        this.betreftInschrijvings.add(inschrijving);
        inschrijving.setBetreftAanbesteding(this);
        return this;
    }

    public Aanbesteding removeBetreftInschrijving(Inschrijving inschrijving) {
        this.betreftInschrijvings.remove(inschrijving);
        inschrijving.setBetreftAanbesteding(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aanbesteding)) {
            return false;
        }
        return getId() != null && getId().equals(((Aanbesteding) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Aanbesteding{" +
            "id=" + getId() +
            ", datumpublicatie='" + getDatumpublicatie() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", digitaal='" + getDigitaal() + "'" +
            ", naam='" + getNaam() + "'" +
            ", procedure='" + getProcedure() + "'" +
            ", referentienummer='" + getReferentienummer() + "'" +
            ", scoremaximaal='" + getScoremaximaal() + "'" +
            ", status='" + getStatus() + "'" +
            ", tendernedkenmerk='" + getTendernedkenmerk() + "'" +
            ", type='" + getType() + "'" +
            ", volgendesluiting='" + getVolgendesluiting() + "'" +
            "}";
    }
}
