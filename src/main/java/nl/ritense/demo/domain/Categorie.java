package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Categorie.
 */
@Entity
@Table(name = "categorie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Categorie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftCategorie")
    @JsonIgnoreProperties(
        value = {
            "heeftTaakveld",
            "behandeltAgendapunts",
            "hoortbijProgrammas",
            "wordtbehandeldinVergaderings",
            "heeftverslagVergadering",
            "betreftStemming",
            "heeftCategorie",
            "hoortbijDossiers",
            "heeftIndieners",
        },
        allowSetters = true
    )
    private Set<Raadsstuk> heeftRaadsstuks = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hoofdcategorieCategorie")
    @JsonIgnoreProperties(
        value = {
            "hoofdcategorieCategorie",
            "subcategorieCategorie",
            "betreftContainertype",
            "betreftFractie",
            "betreftLocatie",
            "melderMedewerker",
            "uitvoerderLeverancier",
            "uitvoerderMedewerker",
            "betreftBeheerobjects",
            "heeftSchouwronde",
        },
        allowSetters = true
    )
    private Set<Melding> hoofdcategorieMeldings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subcategorieCategorie")
    @JsonIgnoreProperties(
        value = {
            "hoofdcategorieCategorie",
            "subcategorieCategorie",
            "betreftContainertype",
            "betreftFractie",
            "betreftLocatie",
            "melderMedewerker",
            "uitvoerderLeverancier",
            "uitvoerderMedewerker",
            "betreftBeheerobjects",
            "heeftSchouwronde",
        },
        allowSetters = true
    )
    private Set<Melding> subcategorieMeldings = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "gekwalificeerdCategories")
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
    private Set<Leverancier> gekwalificeerdLeveranciers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Categorie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Categorie naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Set<Raadsstuk> getHeeftRaadsstuks() {
        return this.heeftRaadsstuks;
    }

    public void setHeeftRaadsstuks(Set<Raadsstuk> raadsstuks) {
        if (this.heeftRaadsstuks != null) {
            this.heeftRaadsstuks.forEach(i -> i.setHeeftCategorie(null));
        }
        if (raadsstuks != null) {
            raadsstuks.forEach(i -> i.setHeeftCategorie(this));
        }
        this.heeftRaadsstuks = raadsstuks;
    }

    public Categorie heeftRaadsstuks(Set<Raadsstuk> raadsstuks) {
        this.setHeeftRaadsstuks(raadsstuks);
        return this;
    }

    public Categorie addHeeftRaadsstuk(Raadsstuk raadsstuk) {
        this.heeftRaadsstuks.add(raadsstuk);
        raadsstuk.setHeeftCategorie(this);
        return this;
    }

    public Categorie removeHeeftRaadsstuk(Raadsstuk raadsstuk) {
        this.heeftRaadsstuks.remove(raadsstuk);
        raadsstuk.setHeeftCategorie(null);
        return this;
    }

    public Set<Melding> getHoofdcategorieMeldings() {
        return this.hoofdcategorieMeldings;
    }

    public void setHoofdcategorieMeldings(Set<Melding> meldings) {
        if (this.hoofdcategorieMeldings != null) {
            this.hoofdcategorieMeldings.forEach(i -> i.setHoofdcategorieCategorie(null));
        }
        if (meldings != null) {
            meldings.forEach(i -> i.setHoofdcategorieCategorie(this));
        }
        this.hoofdcategorieMeldings = meldings;
    }

    public Categorie hoofdcategorieMeldings(Set<Melding> meldings) {
        this.setHoofdcategorieMeldings(meldings);
        return this;
    }

    public Categorie addHoofdcategorieMelding(Melding melding) {
        this.hoofdcategorieMeldings.add(melding);
        melding.setHoofdcategorieCategorie(this);
        return this;
    }

    public Categorie removeHoofdcategorieMelding(Melding melding) {
        this.hoofdcategorieMeldings.remove(melding);
        melding.setHoofdcategorieCategorie(null);
        return this;
    }

    public Set<Melding> getSubcategorieMeldings() {
        return this.subcategorieMeldings;
    }

    public void setSubcategorieMeldings(Set<Melding> meldings) {
        if (this.subcategorieMeldings != null) {
            this.subcategorieMeldings.forEach(i -> i.setSubcategorieCategorie(null));
        }
        if (meldings != null) {
            meldings.forEach(i -> i.setSubcategorieCategorie(this));
        }
        this.subcategorieMeldings = meldings;
    }

    public Categorie subcategorieMeldings(Set<Melding> meldings) {
        this.setSubcategorieMeldings(meldings);
        return this;
    }

    public Categorie addSubcategorieMelding(Melding melding) {
        this.subcategorieMeldings.add(melding);
        melding.setSubcategorieCategorie(this);
        return this;
    }

    public Categorie removeSubcategorieMelding(Melding melding) {
        this.subcategorieMeldings.remove(melding);
        melding.setSubcategorieCategorie(null);
        return this;
    }

    public Set<Leverancier> getGekwalificeerdLeveranciers() {
        return this.gekwalificeerdLeveranciers;
    }

    public void setGekwalificeerdLeveranciers(Set<Leverancier> leveranciers) {
        if (this.gekwalificeerdLeveranciers != null) {
            this.gekwalificeerdLeveranciers.forEach(i -> i.removeGekwalificeerdCategorie(this));
        }
        if (leveranciers != null) {
            leveranciers.forEach(i -> i.addGekwalificeerdCategorie(this));
        }
        this.gekwalificeerdLeveranciers = leveranciers;
    }

    public Categorie gekwalificeerdLeveranciers(Set<Leverancier> leveranciers) {
        this.setGekwalificeerdLeveranciers(leveranciers);
        return this;
    }

    public Categorie addGekwalificeerdLeverancier(Leverancier leverancier) {
        this.gekwalificeerdLeveranciers.add(leverancier);
        leverancier.getGekwalificeerdCategories().add(this);
        return this;
    }

    public Categorie removeGekwalificeerdLeverancier(Leverancier leverancier) {
        this.gekwalificeerdLeveranciers.remove(leverancier);
        leverancier.getGekwalificeerdCategories().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Categorie)) {
            return false;
        }
        return getId() != null && getId().equals(((Categorie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Categorie{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            "}";
    }
}
