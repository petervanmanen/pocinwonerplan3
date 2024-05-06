package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Melding.
 */
@Entity
@Table(name = "melding")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Melding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "vierentwintiguurs")
    private String vierentwintiguurs;

    @Column(name = "datumtijd")
    private String datumtijd;

    @Column(name = "illegaal")
    private String illegaal;

    @Column(name = "meldingnummer")
    private String meldingnummer;

    @Column(name = "omschrijving")
    private String omschrijving;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "heeftRaadsstuks", "hoofdcategorieMeldings", "subcategorieMeldings", "gekwalificeerdLeveranciers" },
        allowSetters = true
    )
    private Categorie hoofdcategorieCategorie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "heeftRaadsstuks", "hoofdcategorieMeldings", "subcategorieMeldings", "gekwalificeerdLeveranciers" },
        allowSetters = true
    )
    private Categorie subcategorieCategorie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "soortContainers", "betreftMeldings", "geschiktvoorVuilniswagens" }, allowSetters = true)
    private Containertype betreftContainertype;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "geschiktvoorContainers", "betreftMeldings", "inzamelpuntvanMilieustraats", "fractieStortings" },
        allowSetters = true
    )
    private Fractie betreftFractie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftContainers",
            "betreftMeldings",
            "gestoptopOphaalmoments",
            "betreftProjectlocaties",
            "heeftlocatiePuts",
            "wordtbegrensddoorProjects",
            "betreftVerzoeks",
            "werkingsgebiedRegelteksts",
            "isverbondenmetActiviteits",
            "verwijstnaarGebiedsaanwijzings",
            "geldtvoorNormwaardes",
        },
        allowSetters = true
    )
    private Locatie betreftLocatie;

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
    private Medewerker melderMedewerker;

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
    private Leverancier uitvoerderLeverancier;

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
    private Medewerker uitvoerderMedewerker;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_melding__betreft_beheerobject",
        joinColumns = @JoinColumn(name = "melding_id"),
        inverseJoinColumns = @JoinColumn(name = "betreft_beheerobject_id")
    )
    @JsonIgnoreProperties(value = { "betreftMeldings" }, allowSetters = true)
    private Set<Beheerobject> betreftBeheerobjects = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftMeldings", "voertuitMedewerker", "binnenAreaals" }, allowSetters = true)
    private Schouwronde heeftSchouwronde;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Melding id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVierentwintiguurs() {
        return this.vierentwintiguurs;
    }

    public Melding vierentwintiguurs(String vierentwintiguurs) {
        this.setVierentwintiguurs(vierentwintiguurs);
        return this;
    }

    public void setVierentwintiguurs(String vierentwintiguurs) {
        this.vierentwintiguurs = vierentwintiguurs;
    }

    public String getDatumtijd() {
        return this.datumtijd;
    }

    public Melding datumtijd(String datumtijd) {
        this.setDatumtijd(datumtijd);
        return this;
    }

    public void setDatumtijd(String datumtijd) {
        this.datumtijd = datumtijd;
    }

    public String getIllegaal() {
        return this.illegaal;
    }

    public Melding illegaal(String illegaal) {
        this.setIllegaal(illegaal);
        return this;
    }

    public void setIllegaal(String illegaal) {
        this.illegaal = illegaal;
    }

    public String getMeldingnummer() {
        return this.meldingnummer;
    }

    public Melding meldingnummer(String meldingnummer) {
        this.setMeldingnummer(meldingnummer);
        return this;
    }

    public void setMeldingnummer(String meldingnummer) {
        this.meldingnummer = meldingnummer;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Melding omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Categorie getHoofdcategorieCategorie() {
        return this.hoofdcategorieCategorie;
    }

    public void setHoofdcategorieCategorie(Categorie categorie) {
        this.hoofdcategorieCategorie = categorie;
    }

    public Melding hoofdcategorieCategorie(Categorie categorie) {
        this.setHoofdcategorieCategorie(categorie);
        return this;
    }

    public Categorie getSubcategorieCategorie() {
        return this.subcategorieCategorie;
    }

    public void setSubcategorieCategorie(Categorie categorie) {
        this.subcategorieCategorie = categorie;
    }

    public Melding subcategorieCategorie(Categorie categorie) {
        this.setSubcategorieCategorie(categorie);
        return this;
    }

    public Containertype getBetreftContainertype() {
        return this.betreftContainertype;
    }

    public void setBetreftContainertype(Containertype containertype) {
        this.betreftContainertype = containertype;
    }

    public Melding betreftContainertype(Containertype containertype) {
        this.setBetreftContainertype(containertype);
        return this;
    }

    public Fractie getBetreftFractie() {
        return this.betreftFractie;
    }

    public void setBetreftFractie(Fractie fractie) {
        this.betreftFractie = fractie;
    }

    public Melding betreftFractie(Fractie fractie) {
        this.setBetreftFractie(fractie);
        return this;
    }

    public Locatie getBetreftLocatie() {
        return this.betreftLocatie;
    }

    public void setBetreftLocatie(Locatie locatie) {
        this.betreftLocatie = locatie;
    }

    public Melding betreftLocatie(Locatie locatie) {
        this.setBetreftLocatie(locatie);
        return this;
    }

    public Medewerker getMelderMedewerker() {
        return this.melderMedewerker;
    }

    public void setMelderMedewerker(Medewerker medewerker) {
        this.melderMedewerker = medewerker;
    }

    public Melding melderMedewerker(Medewerker medewerker) {
        this.setMelderMedewerker(medewerker);
        return this;
    }

    public Leverancier getUitvoerderLeverancier() {
        return this.uitvoerderLeverancier;
    }

    public void setUitvoerderLeverancier(Leverancier leverancier) {
        this.uitvoerderLeverancier = leverancier;
    }

    public Melding uitvoerderLeverancier(Leverancier leverancier) {
        this.setUitvoerderLeverancier(leverancier);
        return this;
    }

    public Medewerker getUitvoerderMedewerker() {
        return this.uitvoerderMedewerker;
    }

    public void setUitvoerderMedewerker(Medewerker medewerker) {
        this.uitvoerderMedewerker = medewerker;
    }

    public Melding uitvoerderMedewerker(Medewerker medewerker) {
        this.setUitvoerderMedewerker(medewerker);
        return this;
    }

    public Set<Beheerobject> getBetreftBeheerobjects() {
        return this.betreftBeheerobjects;
    }

    public void setBetreftBeheerobjects(Set<Beheerobject> beheerobjects) {
        this.betreftBeheerobjects = beheerobjects;
    }

    public Melding betreftBeheerobjects(Set<Beheerobject> beheerobjects) {
        this.setBetreftBeheerobjects(beheerobjects);
        return this;
    }

    public Melding addBetreftBeheerobject(Beheerobject beheerobject) {
        this.betreftBeheerobjects.add(beheerobject);
        return this;
    }

    public Melding removeBetreftBeheerobject(Beheerobject beheerobject) {
        this.betreftBeheerobjects.remove(beheerobject);
        return this;
    }

    public Schouwronde getHeeftSchouwronde() {
        return this.heeftSchouwronde;
    }

    public void setHeeftSchouwronde(Schouwronde schouwronde) {
        this.heeftSchouwronde = schouwronde;
    }

    public Melding heeftSchouwronde(Schouwronde schouwronde) {
        this.setHeeftSchouwronde(schouwronde);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Melding)) {
            return false;
        }
        return getId() != null && getId().equals(((Melding) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Melding{" +
            "id=" + getId() +
            ", vierentwintiguurs='" + getVierentwintiguurs() + "'" +
            ", datumtijd='" + getDatumtijd() + "'" +
            ", illegaal='" + getIllegaal() + "'" +
            ", meldingnummer='" + getMeldingnummer() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
