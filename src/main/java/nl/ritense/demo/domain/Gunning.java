package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Gunning.
 */
@Entity
@Table(name = "gunning")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Gunning implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bericht")
    private String bericht;

    @Column(name = "datumgunning")
    private String datumgunning;

    @Column(name = "datumpublicatie")
    private String datumpublicatie;

    @Column(name = "datumvoorlopigegunning")
    private String datumvoorlopigegunning;

    @Column(name = "gegundeprijs")
    private String gegundeprijs;

    @JsonIgnoreProperties(
        value = { "heeftSchool", "betreftAanbesteding", "betreftGunning", "heeftLeerling", "heeftLeverancier" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Inschrijving betreftInschrijving;

    @JsonIgnoreProperties(value = { "betreftGunning", "biedtaanLeverancier" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Kandidaat betreftKandidaat;

    @JsonIgnoreProperties(value = { "betreftAanbesteding", "ingedienddoorLeverancier", "betreftGunning" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Offerte betreftOfferte;

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
    private Medewerker inhuurMedewerker;

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
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "mondtuitGunning")
    private Aanbesteding mondtuitAanbesteding;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Gunning id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBericht() {
        return this.bericht;
    }

    public Gunning bericht(String bericht) {
        this.setBericht(bericht);
        return this;
    }

    public void setBericht(String bericht) {
        this.bericht = bericht;
    }

    public String getDatumgunning() {
        return this.datumgunning;
    }

    public Gunning datumgunning(String datumgunning) {
        this.setDatumgunning(datumgunning);
        return this;
    }

    public void setDatumgunning(String datumgunning) {
        this.datumgunning = datumgunning;
    }

    public String getDatumpublicatie() {
        return this.datumpublicatie;
    }

    public Gunning datumpublicatie(String datumpublicatie) {
        this.setDatumpublicatie(datumpublicatie);
        return this;
    }

    public void setDatumpublicatie(String datumpublicatie) {
        this.datumpublicatie = datumpublicatie;
    }

    public String getDatumvoorlopigegunning() {
        return this.datumvoorlopigegunning;
    }

    public Gunning datumvoorlopigegunning(String datumvoorlopigegunning) {
        this.setDatumvoorlopigegunning(datumvoorlopigegunning);
        return this;
    }

    public void setDatumvoorlopigegunning(String datumvoorlopigegunning) {
        this.datumvoorlopigegunning = datumvoorlopigegunning;
    }

    public String getGegundeprijs() {
        return this.gegundeprijs;
    }

    public Gunning gegundeprijs(String gegundeprijs) {
        this.setGegundeprijs(gegundeprijs);
        return this;
    }

    public void setGegundeprijs(String gegundeprijs) {
        this.gegundeprijs = gegundeprijs;
    }

    public Inschrijving getBetreftInschrijving() {
        return this.betreftInschrijving;
    }

    public void setBetreftInschrijving(Inschrijving inschrijving) {
        this.betreftInschrijving = inschrijving;
    }

    public Gunning betreftInschrijving(Inschrijving inschrijving) {
        this.setBetreftInschrijving(inschrijving);
        return this;
    }

    public Kandidaat getBetreftKandidaat() {
        return this.betreftKandidaat;
    }

    public void setBetreftKandidaat(Kandidaat kandidaat) {
        this.betreftKandidaat = kandidaat;
    }

    public Gunning betreftKandidaat(Kandidaat kandidaat) {
        this.setBetreftKandidaat(kandidaat);
        return this;
    }

    public Offerte getBetreftOfferte() {
        return this.betreftOfferte;
    }

    public void setBetreftOfferte(Offerte offerte) {
        this.betreftOfferte = offerte;
    }

    public Gunning betreftOfferte(Offerte offerte) {
        this.setBetreftOfferte(offerte);
        return this;
    }

    public Medewerker getInhuurMedewerker() {
        return this.inhuurMedewerker;
    }

    public void setInhuurMedewerker(Medewerker medewerker) {
        this.inhuurMedewerker = medewerker;
    }

    public Gunning inhuurMedewerker(Medewerker medewerker) {
        this.setInhuurMedewerker(medewerker);
        return this;
    }

    public Aanbesteding getMondtuitAanbesteding() {
        return this.mondtuitAanbesteding;
    }

    public void setMondtuitAanbesteding(Aanbesteding aanbesteding) {
        if (this.mondtuitAanbesteding != null) {
            this.mondtuitAanbesteding.setMondtuitGunning(null);
        }
        if (aanbesteding != null) {
            aanbesteding.setMondtuitGunning(this);
        }
        this.mondtuitAanbesteding = aanbesteding;
    }

    public Gunning mondtuitAanbesteding(Aanbesteding aanbesteding) {
        this.setMondtuitAanbesteding(aanbesteding);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gunning)) {
            return false;
        }
        return getId() != null && getId().equals(((Gunning) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Gunning{" +
            "id=" + getId() +
            ", bericht='" + getBericht() + "'" +
            ", datumgunning='" + getDatumgunning() + "'" +
            ", datumpublicatie='" + getDatumpublicatie() + "'" +
            ", datumvoorlopigegunning='" + getDatumvoorlopigegunning() + "'" +
            ", gegundeprijs='" + getGegundeprijs() + "'" +
            "}";
    }
}
