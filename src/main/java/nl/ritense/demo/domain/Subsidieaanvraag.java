package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Subsidieaanvraag.
 */
@Entity
@Table(name = "subsidieaanvraag")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Subsidieaanvraag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aangevraagdbedrag", precision = 21, scale = 2)
    private BigDecimal aangevraagdbedrag;

    @Column(name = "datumindiening")
    private LocalDate datumindiening;

    @Column(name = "kenmerk")
    private String kenmerk;

    @Column(name = "ontvangstbevestiging")
    private LocalDate ontvangstbevestiging;

    @Column(name = "verwachtebeschikking")
    private LocalDate verwachtebeschikking;

    @JsonIgnoreProperties(
        value = {
            "heeftZaak",
            "heeftRapportagemoments",
            "heeftTaaks",
            "valtbinnenSector",
            "behandelaarMedewerker",
            "verstrekkerRechtspersoon",
            "heeftKostenplaats",
            "heeftDocument",
            "betreftSubsidieaanvraag",
            "betreftSubsidiebeschikking",
            "aanvragerRechtspersoon",
            "aanvragerMedewerker",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Subsidie betreftSubsidie;

    @JsonIgnoreProperties(value = { "betreftSubsidie", "mondtuitSubsidieaanvraag" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Subsidiebeschikking mondtuitSubsidiebeschikking;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Subsidieaanvraag id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAangevraagdbedrag() {
        return this.aangevraagdbedrag;
    }

    public Subsidieaanvraag aangevraagdbedrag(BigDecimal aangevraagdbedrag) {
        this.setAangevraagdbedrag(aangevraagdbedrag);
        return this;
    }

    public void setAangevraagdbedrag(BigDecimal aangevraagdbedrag) {
        this.aangevraagdbedrag = aangevraagdbedrag;
    }

    public LocalDate getDatumindiening() {
        return this.datumindiening;
    }

    public Subsidieaanvraag datumindiening(LocalDate datumindiening) {
        this.setDatumindiening(datumindiening);
        return this;
    }

    public void setDatumindiening(LocalDate datumindiening) {
        this.datumindiening = datumindiening;
    }

    public String getKenmerk() {
        return this.kenmerk;
    }

    public Subsidieaanvraag kenmerk(String kenmerk) {
        this.setKenmerk(kenmerk);
        return this;
    }

    public void setKenmerk(String kenmerk) {
        this.kenmerk = kenmerk;
    }

    public LocalDate getOntvangstbevestiging() {
        return this.ontvangstbevestiging;
    }

    public Subsidieaanvraag ontvangstbevestiging(LocalDate ontvangstbevestiging) {
        this.setOntvangstbevestiging(ontvangstbevestiging);
        return this;
    }

    public void setOntvangstbevestiging(LocalDate ontvangstbevestiging) {
        this.ontvangstbevestiging = ontvangstbevestiging;
    }

    public LocalDate getVerwachtebeschikking() {
        return this.verwachtebeschikking;
    }

    public Subsidieaanvraag verwachtebeschikking(LocalDate verwachtebeschikking) {
        this.setVerwachtebeschikking(verwachtebeschikking);
        return this;
    }

    public void setVerwachtebeschikking(LocalDate verwachtebeschikking) {
        this.verwachtebeschikking = verwachtebeschikking;
    }

    public Subsidie getBetreftSubsidie() {
        return this.betreftSubsidie;
    }

    public void setBetreftSubsidie(Subsidie subsidie) {
        this.betreftSubsidie = subsidie;
    }

    public Subsidieaanvraag betreftSubsidie(Subsidie subsidie) {
        this.setBetreftSubsidie(subsidie);
        return this;
    }

    public Subsidiebeschikking getMondtuitSubsidiebeschikking() {
        return this.mondtuitSubsidiebeschikking;
    }

    public void setMondtuitSubsidiebeschikking(Subsidiebeschikking subsidiebeschikking) {
        this.mondtuitSubsidiebeschikking = subsidiebeschikking;
    }

    public Subsidieaanvraag mondtuitSubsidiebeschikking(Subsidiebeschikking subsidiebeschikking) {
        this.setMondtuitSubsidiebeschikking(subsidiebeschikking);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subsidieaanvraag)) {
            return false;
        }
        return getId() != null && getId().equals(((Subsidieaanvraag) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Subsidieaanvraag{" +
            "id=" + getId() +
            ", aangevraagdbedrag=" + getAangevraagdbedrag() +
            ", datumindiening='" + getDatumindiening() + "'" +
            ", kenmerk='" + getKenmerk() + "'" +
            ", ontvangstbevestiging='" + getOntvangstbevestiging() + "'" +
            ", verwachtebeschikking='" + getVerwachtebeschikking() + "'" +
            "}";
    }
}
