package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Offerte.
 */
@Entity
@Table(name = "offerte")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Offerte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

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
    private Leverancier ingedienddoorLeverancier;

    @JsonIgnoreProperties(
        value = { "betreftInschrijving", "betreftKandidaat", "betreftOfferte", "inhuurMedewerker", "mondtuitAanbesteding" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "betreftOfferte")
    private Gunning betreftGunning;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Offerte id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Aanbesteding getBetreftAanbesteding() {
        return this.betreftAanbesteding;
    }

    public void setBetreftAanbesteding(Aanbesteding aanbesteding) {
        this.betreftAanbesteding = aanbesteding;
    }

    public Offerte betreftAanbesteding(Aanbesteding aanbesteding) {
        this.setBetreftAanbesteding(aanbesteding);
        return this;
    }

    public Leverancier getIngedienddoorLeverancier() {
        return this.ingedienddoorLeverancier;
    }

    public void setIngedienddoorLeverancier(Leverancier leverancier) {
        this.ingedienddoorLeverancier = leverancier;
    }

    public Offerte ingedienddoorLeverancier(Leverancier leverancier) {
        this.setIngedienddoorLeverancier(leverancier);
        return this;
    }

    public Gunning getBetreftGunning() {
        return this.betreftGunning;
    }

    public void setBetreftGunning(Gunning gunning) {
        if (this.betreftGunning != null) {
            this.betreftGunning.setBetreftOfferte(null);
        }
        if (gunning != null) {
            gunning.setBetreftOfferte(this);
        }
        this.betreftGunning = gunning;
    }

    public Offerte betreftGunning(Gunning gunning) {
        this.setBetreftGunning(gunning);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Offerte)) {
            return false;
        }
        return getId() != null && getId().equals(((Offerte) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Offerte{" +
            "id=" + getId() +
            "}";
    }
}
