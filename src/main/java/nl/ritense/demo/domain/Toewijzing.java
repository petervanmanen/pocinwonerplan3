package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Toewijzing.
 */
@Entity
@Table(name = "toewijzing")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Toewijzing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @Column(name = "code", length = 20)
    private String code;

    @Column(name = "commentaar")
    private String commentaar;

    @Column(name = "datumaanschaf")
    private LocalDate datumaanschaf;

    @Column(name = "datumeindetoewijzing")
    private LocalDate datumeindetoewijzing;

    @Column(name = "datumstarttoewijzing")
    private LocalDate datumstarttoewijzing;

    @Column(name = "datumtoewijzing")
    private LocalDate datumtoewijzing;

    @Column(name = "eenheid")
    private String eenheid;

    @Column(name = "frequentie")
    private String frequentie;

    @Column(name = "omvang")
    private String omvang;

    @Column(name = "redenwijziging")
    private String redenwijziging;

    @Column(name = "toewijzingnummer")
    private String toewijzingnummer;

    @Column(name = "wet")
    private String wet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "isopbasisvanToewijzing")
    @JsonIgnoreProperties(
        value = { "isvoorBeschikking", "betreftClient", "valtbinnenDeclaratie", "isopbasisvanToewijzing" },
        allowSetters = true
    )
    private Set<Declaratieregel> isopbasisvanDeclaratieregels = new HashSet<>();

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
    private Leverancier levertvoorzieningLeverancier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "toewijzingToewijzings",
            "emptyClient",
            "geeftafClientbegeleider",
            "isgebaseerdopBeperkings",
            "geleverdeprestatieLeverings",
            "isvoorDeclaratieregels",
        },
        allowSetters = true
    )
    private Beschikking toewijzingBeschikking;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "geleverdezorgToewijzing")
    @JsonIgnoreProperties(
        value = {
            "geleverdeprestatieBeschikking",
            "prestatievoorClient",
            "geleverdezorgToewijzing",
            "voorzieningVoorziening",
            "leverdeprestatieLeverancier",
        },
        allowSetters = true
    )
    private Set<Levering> geleverdezorgLeverings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Toewijzing id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Toewijzing code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCommentaar() {
        return this.commentaar;
    }

    public Toewijzing commentaar(String commentaar) {
        this.setCommentaar(commentaar);
        return this;
    }

    public void setCommentaar(String commentaar) {
        this.commentaar = commentaar;
    }

    public LocalDate getDatumaanschaf() {
        return this.datumaanschaf;
    }

    public Toewijzing datumaanschaf(LocalDate datumaanschaf) {
        this.setDatumaanschaf(datumaanschaf);
        return this;
    }

    public void setDatumaanschaf(LocalDate datumaanschaf) {
        this.datumaanschaf = datumaanschaf;
    }

    public LocalDate getDatumeindetoewijzing() {
        return this.datumeindetoewijzing;
    }

    public Toewijzing datumeindetoewijzing(LocalDate datumeindetoewijzing) {
        this.setDatumeindetoewijzing(datumeindetoewijzing);
        return this;
    }

    public void setDatumeindetoewijzing(LocalDate datumeindetoewijzing) {
        this.datumeindetoewijzing = datumeindetoewijzing;
    }

    public LocalDate getDatumstarttoewijzing() {
        return this.datumstarttoewijzing;
    }

    public Toewijzing datumstarttoewijzing(LocalDate datumstarttoewijzing) {
        this.setDatumstarttoewijzing(datumstarttoewijzing);
        return this;
    }

    public void setDatumstarttoewijzing(LocalDate datumstarttoewijzing) {
        this.datumstarttoewijzing = datumstarttoewijzing;
    }

    public LocalDate getDatumtoewijzing() {
        return this.datumtoewijzing;
    }

    public Toewijzing datumtoewijzing(LocalDate datumtoewijzing) {
        this.setDatumtoewijzing(datumtoewijzing);
        return this;
    }

    public void setDatumtoewijzing(LocalDate datumtoewijzing) {
        this.datumtoewijzing = datumtoewijzing;
    }

    public String getEenheid() {
        return this.eenheid;
    }

    public Toewijzing eenheid(String eenheid) {
        this.setEenheid(eenheid);
        return this;
    }

    public void setEenheid(String eenheid) {
        this.eenheid = eenheid;
    }

    public String getFrequentie() {
        return this.frequentie;
    }

    public Toewijzing frequentie(String frequentie) {
        this.setFrequentie(frequentie);
        return this;
    }

    public void setFrequentie(String frequentie) {
        this.frequentie = frequentie;
    }

    public String getOmvang() {
        return this.omvang;
    }

    public Toewijzing omvang(String omvang) {
        this.setOmvang(omvang);
        return this;
    }

    public void setOmvang(String omvang) {
        this.omvang = omvang;
    }

    public String getRedenwijziging() {
        return this.redenwijziging;
    }

    public Toewijzing redenwijziging(String redenwijziging) {
        this.setRedenwijziging(redenwijziging);
        return this;
    }

    public void setRedenwijziging(String redenwijziging) {
        this.redenwijziging = redenwijziging;
    }

    public String getToewijzingnummer() {
        return this.toewijzingnummer;
    }

    public Toewijzing toewijzingnummer(String toewijzingnummer) {
        this.setToewijzingnummer(toewijzingnummer);
        return this;
    }

    public void setToewijzingnummer(String toewijzingnummer) {
        this.toewijzingnummer = toewijzingnummer;
    }

    public String getWet() {
        return this.wet;
    }

    public Toewijzing wet(String wet) {
        this.setWet(wet);
        return this;
    }

    public void setWet(String wet) {
        this.wet = wet;
    }

    public Set<Declaratieregel> getIsopbasisvanDeclaratieregels() {
        return this.isopbasisvanDeclaratieregels;
    }

    public void setIsopbasisvanDeclaratieregels(Set<Declaratieregel> declaratieregels) {
        if (this.isopbasisvanDeclaratieregels != null) {
            this.isopbasisvanDeclaratieregels.forEach(i -> i.setIsopbasisvanToewijzing(null));
        }
        if (declaratieregels != null) {
            declaratieregels.forEach(i -> i.setIsopbasisvanToewijzing(this));
        }
        this.isopbasisvanDeclaratieregels = declaratieregels;
    }

    public Toewijzing isopbasisvanDeclaratieregels(Set<Declaratieregel> declaratieregels) {
        this.setIsopbasisvanDeclaratieregels(declaratieregels);
        return this;
    }

    public Toewijzing addIsopbasisvanDeclaratieregel(Declaratieregel declaratieregel) {
        this.isopbasisvanDeclaratieregels.add(declaratieregel);
        declaratieregel.setIsopbasisvanToewijzing(this);
        return this;
    }

    public Toewijzing removeIsopbasisvanDeclaratieregel(Declaratieregel declaratieregel) {
        this.isopbasisvanDeclaratieregels.remove(declaratieregel);
        declaratieregel.setIsopbasisvanToewijzing(null);
        return this;
    }

    public Leverancier getLevertvoorzieningLeverancier() {
        return this.levertvoorzieningLeverancier;
    }

    public void setLevertvoorzieningLeverancier(Leverancier leverancier) {
        this.levertvoorzieningLeverancier = leverancier;
    }

    public Toewijzing levertvoorzieningLeverancier(Leverancier leverancier) {
        this.setLevertvoorzieningLeverancier(leverancier);
        return this;
    }

    public Beschikking getToewijzingBeschikking() {
        return this.toewijzingBeschikking;
    }

    public void setToewijzingBeschikking(Beschikking beschikking) {
        this.toewijzingBeschikking = beschikking;
    }

    public Toewijzing toewijzingBeschikking(Beschikking beschikking) {
        this.setToewijzingBeschikking(beschikking);
        return this;
    }

    public Set<Levering> getGeleverdezorgLeverings() {
        return this.geleverdezorgLeverings;
    }

    public void setGeleverdezorgLeverings(Set<Levering> leverings) {
        if (this.geleverdezorgLeverings != null) {
            this.geleverdezorgLeverings.forEach(i -> i.setGeleverdezorgToewijzing(null));
        }
        if (leverings != null) {
            leverings.forEach(i -> i.setGeleverdezorgToewijzing(this));
        }
        this.geleverdezorgLeverings = leverings;
    }

    public Toewijzing geleverdezorgLeverings(Set<Levering> leverings) {
        this.setGeleverdezorgLeverings(leverings);
        return this;
    }

    public Toewijzing addGeleverdezorgLevering(Levering levering) {
        this.geleverdezorgLeverings.add(levering);
        levering.setGeleverdezorgToewijzing(this);
        return this;
    }

    public Toewijzing removeGeleverdezorgLevering(Levering levering) {
        this.geleverdezorgLeverings.remove(levering);
        levering.setGeleverdezorgToewijzing(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Toewijzing)) {
            return false;
        }
        return getId() != null && getId().equals(((Toewijzing) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Toewijzing{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", commentaar='" + getCommentaar() + "'" +
            ", datumaanschaf='" + getDatumaanschaf() + "'" +
            ", datumeindetoewijzing='" + getDatumeindetoewijzing() + "'" +
            ", datumstarttoewijzing='" + getDatumstarttoewijzing() + "'" +
            ", datumtoewijzing='" + getDatumtoewijzing() + "'" +
            ", eenheid='" + getEenheid() + "'" +
            ", frequentie='" + getFrequentie() + "'" +
            ", omvang='" + getOmvang() + "'" +
            ", redenwijziging='" + getRedenwijziging() + "'" +
            ", toewijzingnummer='" + getToewijzingnummer() + "'" +
            ", wet='" + getWet() + "'" +
            "}";
    }
}
