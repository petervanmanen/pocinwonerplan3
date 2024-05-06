package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Vastgoedcontract.
 */
@Entity
@Table(name = "vastgoedcontract")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vastgoedcontract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "beschrijving")
    private String beschrijving;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "identificatie")
    private String identificatie;

    @Column(name = "maandbedrag", precision = 21, scale = 2)
    private BigDecimal maandbedrag;

    @Column(name = "opzegtermijn")
    private String opzegtermijn;

    @Column(name = "status")
    private String status;

    @Column(name = "type")
    private String type;

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
    private Rechtspersoon heeftRechtspersoon;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vastgoedcontract id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeschrijving() {
        return this.beschrijving;
    }

    public Vastgoedcontract beschrijving(String beschrijving) {
        this.setBeschrijving(beschrijving);
        return this;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Vastgoedcontract datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Vastgoedcontract datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public String getIdentificatie() {
        return this.identificatie;
    }

    public Vastgoedcontract identificatie(String identificatie) {
        this.setIdentificatie(identificatie);
        return this;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public BigDecimal getMaandbedrag() {
        return this.maandbedrag;
    }

    public Vastgoedcontract maandbedrag(BigDecimal maandbedrag) {
        this.setMaandbedrag(maandbedrag);
        return this;
    }

    public void setMaandbedrag(BigDecimal maandbedrag) {
        this.maandbedrag = maandbedrag;
    }

    public String getOpzegtermijn() {
        return this.opzegtermijn;
    }

    public Vastgoedcontract opzegtermijn(String opzegtermijn) {
        this.setOpzegtermijn(opzegtermijn);
        return this;
    }

    public void setOpzegtermijn(String opzegtermijn) {
        this.opzegtermijn = opzegtermijn;
    }

    public String getStatus() {
        return this.status;
    }

    public Vastgoedcontract status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return this.type;
    }

    public Vastgoedcontract type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Rechtspersoon getHeeftRechtspersoon() {
        return this.heeftRechtspersoon;
    }

    public void setHeeftRechtspersoon(Rechtspersoon rechtspersoon) {
        this.heeftRechtspersoon = rechtspersoon;
    }

    public Vastgoedcontract heeftRechtspersoon(Rechtspersoon rechtspersoon) {
        this.setHeeftRechtspersoon(rechtspersoon);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vastgoedcontract)) {
            return false;
        }
        return getId() != null && getId().equals(((Vastgoedcontract) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vastgoedcontract{" +
            "id=" + getId() +
            ", beschrijving='" + getBeschrijving() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", identificatie='" + getIdentificatie() + "'" +
            ", maandbedrag=" + getMaandbedrag() +
            ", opzegtermijn='" + getOpzegtermijn() + "'" +
            ", status='" + getStatus() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
