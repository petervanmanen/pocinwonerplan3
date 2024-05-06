package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Parkeervergunning.
 */
@Entity
@Table(name = "parkeervergunning")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Parkeervergunning implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumeindegeldigheid")
    private String datumeindegeldigheid;

    @Column(name = "datumreservering")
    private LocalDate datumreservering;

    @Column(name = "datumstart")
    private String datumstart;

    @Column(name = "kenteken")
    private String kenteken;

    @Column(name = "minutenafgeschreven")
    private String minutenafgeschreven;

    @Column(name = "minutengeldig")
    private String minutengeldig;

    @Column(name = "minutenresterend")
    private String minutenresterend;

    @Column(name = "nummer")
    private String nummer;

    @Column(name = "type")
    private String type;

    @JsonIgnoreProperties(value = { "leverancierBelprovider", "betreftVoertuig", "resulteertParkeervergunning" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Parkeerrecht resulteertParkeerrecht;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "projectleiderRapportagemoments",
            "aanvragerSubsidies",
            "heeftTenaamstellings",
            "betrokkenenKadastralemutaties",
            "isIndiener",
            "houderParkeervergunnings",
            "verstrekkerSubsidies",
            "projectleiderTaaks",
            "heeftVastgoedcontracts",
        },
        allowSetters = true
    )
    private Rechtspersoon houderRechtspersoon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "soortParkeervergunnings", "valtbinnenProductsoorts", "valtbinnenProducts" }, allowSetters = true)
    private Productgroep soortProductgroep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "soortParkeervergunnings", "valtbinnenProductgroep" }, allowSetters = true)
    private Productsoort soortProductsoort;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Parkeervergunning id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatumeindegeldigheid() {
        return this.datumeindegeldigheid;
    }

    public Parkeervergunning datumeindegeldigheid(String datumeindegeldigheid) {
        this.setDatumeindegeldigheid(datumeindegeldigheid);
        return this;
    }

    public void setDatumeindegeldigheid(String datumeindegeldigheid) {
        this.datumeindegeldigheid = datumeindegeldigheid;
    }

    public LocalDate getDatumreservering() {
        return this.datumreservering;
    }

    public Parkeervergunning datumreservering(LocalDate datumreservering) {
        this.setDatumreservering(datumreservering);
        return this;
    }

    public void setDatumreservering(LocalDate datumreservering) {
        this.datumreservering = datumreservering;
    }

    public String getDatumstart() {
        return this.datumstart;
    }

    public Parkeervergunning datumstart(String datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(String datumstart) {
        this.datumstart = datumstart;
    }

    public String getKenteken() {
        return this.kenteken;
    }

    public Parkeervergunning kenteken(String kenteken) {
        this.setKenteken(kenteken);
        return this;
    }

    public void setKenteken(String kenteken) {
        this.kenteken = kenteken;
    }

    public String getMinutenafgeschreven() {
        return this.minutenafgeschreven;
    }

    public Parkeervergunning minutenafgeschreven(String minutenafgeschreven) {
        this.setMinutenafgeschreven(minutenafgeschreven);
        return this;
    }

    public void setMinutenafgeschreven(String minutenafgeschreven) {
        this.minutenafgeschreven = minutenafgeschreven;
    }

    public String getMinutengeldig() {
        return this.minutengeldig;
    }

    public Parkeervergunning minutengeldig(String minutengeldig) {
        this.setMinutengeldig(minutengeldig);
        return this;
    }

    public void setMinutengeldig(String minutengeldig) {
        this.minutengeldig = minutengeldig;
    }

    public String getMinutenresterend() {
        return this.minutenresterend;
    }

    public Parkeervergunning minutenresterend(String minutenresterend) {
        this.setMinutenresterend(minutenresterend);
        return this;
    }

    public void setMinutenresterend(String minutenresterend) {
        this.minutenresterend = minutenresterend;
    }

    public String getNummer() {
        return this.nummer;
    }

    public Parkeervergunning nummer(String nummer) {
        this.setNummer(nummer);
        return this;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }

    public String getType() {
        return this.type;
    }

    public Parkeervergunning type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Parkeerrecht getResulteertParkeerrecht() {
        return this.resulteertParkeerrecht;
    }

    public void setResulteertParkeerrecht(Parkeerrecht parkeerrecht) {
        this.resulteertParkeerrecht = parkeerrecht;
    }

    public Parkeervergunning resulteertParkeerrecht(Parkeerrecht parkeerrecht) {
        this.setResulteertParkeerrecht(parkeerrecht);
        return this;
    }

    public Rechtspersoon getHouderRechtspersoon() {
        return this.houderRechtspersoon;
    }

    public void setHouderRechtspersoon(Rechtspersoon rechtspersoon) {
        this.houderRechtspersoon = rechtspersoon;
    }

    public Parkeervergunning houderRechtspersoon(Rechtspersoon rechtspersoon) {
        this.setHouderRechtspersoon(rechtspersoon);
        return this;
    }

    public Productgroep getSoortProductgroep() {
        return this.soortProductgroep;
    }

    public void setSoortProductgroep(Productgroep productgroep) {
        this.soortProductgroep = productgroep;
    }

    public Parkeervergunning soortProductgroep(Productgroep productgroep) {
        this.setSoortProductgroep(productgroep);
        return this;
    }

    public Productsoort getSoortProductsoort() {
        return this.soortProductsoort;
    }

    public void setSoortProductsoort(Productsoort productsoort) {
        this.soortProductsoort = productsoort;
    }

    public Parkeervergunning soortProductsoort(Productsoort productsoort) {
        this.setSoortProductsoort(productsoort);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Parkeervergunning)) {
            return false;
        }
        return getId() != null && getId().equals(((Parkeervergunning) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Parkeervergunning{" +
            "id=" + getId() +
            ", datumeindegeldigheid='" + getDatumeindegeldigheid() + "'" +
            ", datumreservering='" + getDatumreservering() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", kenteken='" + getKenteken() + "'" +
            ", minutenafgeschreven='" + getMinutenafgeschreven() + "'" +
            ", minutengeldig='" + getMinutengeldig() + "'" +
            ", minutenresterend='" + getMinutenresterend() + "'" +
            ", nummer='" + getNummer() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
