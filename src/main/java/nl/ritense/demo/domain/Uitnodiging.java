package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Uitnodiging.
 */
@Entity
@Table(name = "uitnodiging")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Uitnodiging implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "afgewezen")
    private String afgewezen;

    @Column(name = "datum")
    private String datum;

    @Column(name = "geaccepteerd")
    private String geaccepteerd;

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

    public Uitnodiging id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAfgewezen() {
        return this.afgewezen;
    }

    public Uitnodiging afgewezen(String afgewezen) {
        this.setAfgewezen(afgewezen);
        return this;
    }

    public void setAfgewezen(String afgewezen) {
        this.afgewezen = afgewezen;
    }

    public String getDatum() {
        return this.datum;
    }

    public Uitnodiging datum(String datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getGeaccepteerd() {
        return this.geaccepteerd;
    }

    public Uitnodiging geaccepteerd(String geaccepteerd) {
        this.setGeaccepteerd(geaccepteerd);
        return this;
    }

    public void setGeaccepteerd(String geaccepteerd) {
        this.geaccepteerd = geaccepteerd;
    }

    public Leverancier getGerichtaanLeverancier() {
        return this.gerichtaanLeverancier;
    }

    public void setGerichtaanLeverancier(Leverancier leverancier) {
        this.gerichtaanLeverancier = leverancier;
    }

    public Uitnodiging gerichtaanLeverancier(Leverancier leverancier) {
        this.setGerichtaanLeverancier(leverancier);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Uitnodiging)) {
            return false;
        }
        return getId() != null && getId().equals(((Uitnodiging) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Uitnodiging{" +
            "id=" + getId() +
            ", afgewezen='" + getAfgewezen() + "'" +
            ", datum='" + getDatum() + "'" +
            ", geaccepteerd='" + getGeaccepteerd() + "'" +
            "}";
    }
}
