package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Kwalificatie.
 */
@Entity
@Table(name = "kwalificatie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Kwalificatie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "eindegeldigheid")
    private LocalDate eindegeldigheid;

    @Column(name = "startgeldigheid")
    private LocalDate startgeldigheid;

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
    private Leverancier heeftLeverancier;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Kwalificatie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEindegeldigheid() {
        return this.eindegeldigheid;
    }

    public Kwalificatie eindegeldigheid(LocalDate eindegeldigheid) {
        this.setEindegeldigheid(eindegeldigheid);
        return this;
    }

    public void setEindegeldigheid(LocalDate eindegeldigheid) {
        this.eindegeldigheid = eindegeldigheid;
    }

    public LocalDate getStartgeldigheid() {
        return this.startgeldigheid;
    }

    public Kwalificatie startgeldigheid(LocalDate startgeldigheid) {
        this.setStartgeldigheid(startgeldigheid);
        return this;
    }

    public void setStartgeldigheid(LocalDate startgeldigheid) {
        this.startgeldigheid = startgeldigheid;
    }

    public Aanbesteding getBetreftAanbesteding() {
        return this.betreftAanbesteding;
    }

    public void setBetreftAanbesteding(Aanbesteding aanbesteding) {
        this.betreftAanbesteding = aanbesteding;
    }

    public Kwalificatie betreftAanbesteding(Aanbesteding aanbesteding) {
        this.setBetreftAanbesteding(aanbesteding);
        return this;
    }

    public Leverancier getHeeftLeverancier() {
        return this.heeftLeverancier;
    }

    public void setHeeftLeverancier(Leverancier leverancier) {
        this.heeftLeverancier = leverancier;
    }

    public Kwalificatie heeftLeverancier(Leverancier leverancier) {
        this.setHeeftLeverancier(leverancier);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Kwalificatie)) {
            return false;
        }
        return getId() != null && getId().equals(((Kwalificatie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Kwalificatie{" +
            "id=" + getId() +
            ", eindegeldigheid='" + getEindegeldigheid() + "'" +
            ", startgeldigheid='" + getStartgeldigheid() + "'" +
            "}";
    }
}
