package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Kandidaat.
 */
@Entity
@Table(name = "kandidaat")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Kandidaat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumingestuurd")
    private String datumingestuurd;

    @JsonIgnoreProperties(
        value = { "betreftInschrijving", "betreftKandidaat", "betreftOfferte", "inhuurMedewerker", "mondtuitAanbesteding" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "betreftKandidaat")
    private Gunning betreftGunning;

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
    private Leverancier biedtaanLeverancier;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Kandidaat id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatumingestuurd() {
        return this.datumingestuurd;
    }

    public Kandidaat datumingestuurd(String datumingestuurd) {
        this.setDatumingestuurd(datumingestuurd);
        return this;
    }

    public void setDatumingestuurd(String datumingestuurd) {
        this.datumingestuurd = datumingestuurd;
    }

    public Gunning getBetreftGunning() {
        return this.betreftGunning;
    }

    public void setBetreftGunning(Gunning gunning) {
        if (this.betreftGunning != null) {
            this.betreftGunning.setBetreftKandidaat(null);
        }
        if (gunning != null) {
            gunning.setBetreftKandidaat(this);
        }
        this.betreftGunning = gunning;
    }

    public Kandidaat betreftGunning(Gunning gunning) {
        this.setBetreftGunning(gunning);
        return this;
    }

    public Leverancier getBiedtaanLeverancier() {
        return this.biedtaanLeverancier;
    }

    public void setBiedtaanLeverancier(Leverancier leverancier) {
        this.biedtaanLeverancier = leverancier;
    }

    public Kandidaat biedtaanLeverancier(Leverancier leverancier) {
        this.setBiedtaanLeverancier(leverancier);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Kandidaat)) {
            return false;
        }
        return getId() != null && getId().equals(((Kandidaat) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Kandidaat{" +
            "id=" + getId() +
            ", datumingestuurd='" + getDatumingestuurd() + "'" +
            "}";
    }
}
