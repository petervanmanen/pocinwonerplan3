package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Subsidiebeschikking.
 */
@Entity
@Table(name = "subsidiebeschikking")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Subsidiebeschikking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "beschikkingsnummer")
    private String beschikkingsnummer;

    @Column(name = "beschiktbedrag", precision = 21, scale = 2)
    private BigDecimal beschiktbedrag;

    @Column(name = "besluit")
    private String besluit;

    @Column(name = "internkenmerk")
    private String internkenmerk;

    @Column(name = "kenmerk")
    private String kenmerk;

    @Column(name = "ontvangen")
    private LocalDate ontvangen;

    @Column(name = "opmerkingen")
    private String opmerkingen;

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Subsidie betreftSubsidie;

    @JsonIgnoreProperties(value = { "betreftSubsidie", "mondtuitSubsidiebeschikking" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "mondtuitSubsidiebeschikking")
    private Subsidieaanvraag mondtuitSubsidieaanvraag;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Subsidiebeschikking id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeschikkingsnummer() {
        return this.beschikkingsnummer;
    }

    public Subsidiebeschikking beschikkingsnummer(String beschikkingsnummer) {
        this.setBeschikkingsnummer(beschikkingsnummer);
        return this;
    }

    public void setBeschikkingsnummer(String beschikkingsnummer) {
        this.beschikkingsnummer = beschikkingsnummer;
    }

    public BigDecimal getBeschiktbedrag() {
        return this.beschiktbedrag;
    }

    public Subsidiebeschikking beschiktbedrag(BigDecimal beschiktbedrag) {
        this.setBeschiktbedrag(beschiktbedrag);
        return this;
    }

    public void setBeschiktbedrag(BigDecimal beschiktbedrag) {
        this.beschiktbedrag = beschiktbedrag;
    }

    public String getBesluit() {
        return this.besluit;
    }

    public Subsidiebeschikking besluit(String besluit) {
        this.setBesluit(besluit);
        return this;
    }

    public void setBesluit(String besluit) {
        this.besluit = besluit;
    }

    public String getInternkenmerk() {
        return this.internkenmerk;
    }

    public Subsidiebeschikking internkenmerk(String internkenmerk) {
        this.setInternkenmerk(internkenmerk);
        return this;
    }

    public void setInternkenmerk(String internkenmerk) {
        this.internkenmerk = internkenmerk;
    }

    public String getKenmerk() {
        return this.kenmerk;
    }

    public Subsidiebeschikking kenmerk(String kenmerk) {
        this.setKenmerk(kenmerk);
        return this;
    }

    public void setKenmerk(String kenmerk) {
        this.kenmerk = kenmerk;
    }

    public LocalDate getOntvangen() {
        return this.ontvangen;
    }

    public Subsidiebeschikking ontvangen(LocalDate ontvangen) {
        this.setOntvangen(ontvangen);
        return this;
    }

    public void setOntvangen(LocalDate ontvangen) {
        this.ontvangen = ontvangen;
    }

    public String getOpmerkingen() {
        return this.opmerkingen;
    }

    public Subsidiebeschikking opmerkingen(String opmerkingen) {
        this.setOpmerkingen(opmerkingen);
        return this;
    }

    public void setOpmerkingen(String opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public Subsidie getBetreftSubsidie() {
        return this.betreftSubsidie;
    }

    public void setBetreftSubsidie(Subsidie subsidie) {
        this.betreftSubsidie = subsidie;
    }

    public Subsidiebeschikking betreftSubsidie(Subsidie subsidie) {
        this.setBetreftSubsidie(subsidie);
        return this;
    }

    public Subsidieaanvraag getMondtuitSubsidieaanvraag() {
        return this.mondtuitSubsidieaanvraag;
    }

    public void setMondtuitSubsidieaanvraag(Subsidieaanvraag subsidieaanvraag) {
        if (this.mondtuitSubsidieaanvraag != null) {
            this.mondtuitSubsidieaanvraag.setMondtuitSubsidiebeschikking(null);
        }
        if (subsidieaanvraag != null) {
            subsidieaanvraag.setMondtuitSubsidiebeschikking(this);
        }
        this.mondtuitSubsidieaanvraag = subsidieaanvraag;
    }

    public Subsidiebeschikking mondtuitSubsidieaanvraag(Subsidieaanvraag subsidieaanvraag) {
        this.setMondtuitSubsidieaanvraag(subsidieaanvraag);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subsidiebeschikking)) {
            return false;
        }
        return getId() != null && getId().equals(((Subsidiebeschikking) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Subsidiebeschikking{" +
            "id=" + getId() +
            ", beschikkingsnummer='" + getBeschikkingsnummer() + "'" +
            ", beschiktbedrag=" + getBeschiktbedrag() +
            ", besluit='" + getBesluit() + "'" +
            ", internkenmerk='" + getInternkenmerk() + "'" +
            ", kenmerk='" + getKenmerk() + "'" +
            ", ontvangen='" + getOntvangen() + "'" +
            ", opmerkingen='" + getOpmerkingen() + "'" +
            "}";
    }
}
