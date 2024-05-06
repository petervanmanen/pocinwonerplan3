package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Bedrijfsproces.
 */
@Entity
@Table(name = "bedrijfsproces")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bedrijfsproces implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "afgerond")
    private String afgerond;

    @Column(name = "datumeind")
    private LocalDate datumeind;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @ManyToMany(fetch = FetchType.LAZY)
    @NotNull
    @JoinTable(
        name = "rel_bedrijfsproces__uitgevoerdbinnen_zaak",
        joinColumns = @JoinColumn(name = "bedrijfsproces_id"),
        inverseJoinColumns = @JoinColumn(name = "uitgevoerdbinnen_zaak_id")
    )
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
    private Set<Zaak> uitgevoerdbinnenZaaks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bedrijfsproces id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAfgerond() {
        return this.afgerond;
    }

    public Bedrijfsproces afgerond(String afgerond) {
        this.setAfgerond(afgerond);
        return this;
    }

    public void setAfgerond(String afgerond) {
        this.afgerond = afgerond;
    }

    public LocalDate getDatumeind() {
        return this.datumeind;
    }

    public Bedrijfsproces datumeind(LocalDate datumeind) {
        this.setDatumeind(datumeind);
        return this;
    }

    public void setDatumeind(LocalDate datumeind) {
        this.datumeind = datumeind;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Bedrijfsproces datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public String getNaam() {
        return this.naam;
    }

    public Bedrijfsproces naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Bedrijfsproces omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Zaak> getUitgevoerdbinnenZaaks() {
        return this.uitgevoerdbinnenZaaks;
    }

    public void setUitgevoerdbinnenZaaks(Set<Zaak> zaaks) {
        this.uitgevoerdbinnenZaaks = zaaks;
    }

    public Bedrijfsproces uitgevoerdbinnenZaaks(Set<Zaak> zaaks) {
        this.setUitgevoerdbinnenZaaks(zaaks);
        return this;
    }

    public Bedrijfsproces addUitgevoerdbinnenZaak(Zaak zaak) {
        this.uitgevoerdbinnenZaaks.add(zaak);
        return this;
    }

    public Bedrijfsproces removeUitgevoerdbinnenZaak(Zaak zaak) {
        this.uitgevoerdbinnenZaaks.remove(zaak);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bedrijfsproces)) {
            return false;
        }
        return getId() != null && getId().equals(((Bedrijfsproces) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bedrijfsproces{" +
            "id=" + getId() +
            ", afgerond='" + getAfgerond() + "'" +
            ", datumeind='" + getDatumeind() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
