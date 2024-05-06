package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Kosten.
 */
@Entity
@Table(name = "kosten")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Kosten implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aangemaaktdoor")
    private String aangemaaktdoor;

    @Size(max = 20)
    @Column(name = "aantal", length = 20)
    private String aantal;

    @Column(name = "bedrag")
    private String bedrag;

    @Column(name = "bedragtotaal")
    private String bedragtotaal;

    @Column(name = "datumaanmaak")
    private LocalDate datumaanmaak;

    @Column(name = "datummutatie")
    private LocalDate datummutatie;

    @Size(max = 20)
    @Column(name = "eenheid", length = 20)
    private String eenheid;

    @Size(max = 20)
    @Column(name = "geaccordeerd", length = 20)
    private String geaccordeerd;

    @Column(name = "gefactureerdop")
    private LocalDate gefactureerdop;

    @Column(name = "gemuteerddoor")
    private String gemuteerddoor;

    @Size(max = 20)
    @Column(name = "naam", length = 20)
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "opbasisvangrondslag")
    private String opbasisvangrondslag;

    @Column(name = "tarief")
    private String tarief;

    @Column(name = "type")
    private String type;

    @Column(name = "vastgesteldbedrag")
    private String vastgesteldbedrag;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Kosten id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAangemaaktdoor() {
        return this.aangemaaktdoor;
    }

    public Kosten aangemaaktdoor(String aangemaaktdoor) {
        this.setAangemaaktdoor(aangemaaktdoor);
        return this;
    }

    public void setAangemaaktdoor(String aangemaaktdoor) {
        this.aangemaaktdoor = aangemaaktdoor;
    }

    public String getAantal() {
        return this.aantal;
    }

    public Kosten aantal(String aantal) {
        this.setAantal(aantal);
        return this;
    }

    public void setAantal(String aantal) {
        this.aantal = aantal;
    }

    public String getBedrag() {
        return this.bedrag;
    }

    public Kosten bedrag(String bedrag) {
        this.setBedrag(bedrag);
        return this;
    }

    public void setBedrag(String bedrag) {
        this.bedrag = bedrag;
    }

    public String getBedragtotaal() {
        return this.bedragtotaal;
    }

    public Kosten bedragtotaal(String bedragtotaal) {
        this.setBedragtotaal(bedragtotaal);
        return this;
    }

    public void setBedragtotaal(String bedragtotaal) {
        this.bedragtotaal = bedragtotaal;
    }

    public LocalDate getDatumaanmaak() {
        return this.datumaanmaak;
    }

    public Kosten datumaanmaak(LocalDate datumaanmaak) {
        this.setDatumaanmaak(datumaanmaak);
        return this;
    }

    public void setDatumaanmaak(LocalDate datumaanmaak) {
        this.datumaanmaak = datumaanmaak;
    }

    public LocalDate getDatummutatie() {
        return this.datummutatie;
    }

    public Kosten datummutatie(LocalDate datummutatie) {
        this.setDatummutatie(datummutatie);
        return this;
    }

    public void setDatummutatie(LocalDate datummutatie) {
        this.datummutatie = datummutatie;
    }

    public String getEenheid() {
        return this.eenheid;
    }

    public Kosten eenheid(String eenheid) {
        this.setEenheid(eenheid);
        return this;
    }

    public void setEenheid(String eenheid) {
        this.eenheid = eenheid;
    }

    public String getGeaccordeerd() {
        return this.geaccordeerd;
    }

    public Kosten geaccordeerd(String geaccordeerd) {
        this.setGeaccordeerd(geaccordeerd);
        return this;
    }

    public void setGeaccordeerd(String geaccordeerd) {
        this.geaccordeerd = geaccordeerd;
    }

    public LocalDate getGefactureerdop() {
        return this.gefactureerdop;
    }

    public Kosten gefactureerdop(LocalDate gefactureerdop) {
        this.setGefactureerdop(gefactureerdop);
        return this;
    }

    public void setGefactureerdop(LocalDate gefactureerdop) {
        this.gefactureerdop = gefactureerdop;
    }

    public String getGemuteerddoor() {
        return this.gemuteerddoor;
    }

    public Kosten gemuteerddoor(String gemuteerddoor) {
        this.setGemuteerddoor(gemuteerddoor);
        return this;
    }

    public void setGemuteerddoor(String gemuteerddoor) {
        this.gemuteerddoor = gemuteerddoor;
    }

    public String getNaam() {
        return this.naam;
    }

    public Kosten naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Kosten omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOpbasisvangrondslag() {
        return this.opbasisvangrondslag;
    }

    public Kosten opbasisvangrondslag(String opbasisvangrondslag) {
        this.setOpbasisvangrondslag(opbasisvangrondslag);
        return this;
    }

    public void setOpbasisvangrondslag(String opbasisvangrondslag) {
        this.opbasisvangrondslag = opbasisvangrondslag;
    }

    public String getTarief() {
        return this.tarief;
    }

    public Kosten tarief(String tarief) {
        this.setTarief(tarief);
        return this;
    }

    public void setTarief(String tarief) {
        this.tarief = tarief;
    }

    public String getType() {
        return this.type;
    }

    public Kosten type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVastgesteldbedrag() {
        return this.vastgesteldbedrag;
    }

    public Kosten vastgesteldbedrag(String vastgesteldbedrag) {
        this.setVastgesteldbedrag(vastgesteldbedrag);
        return this;
    }

    public void setVastgesteldbedrag(String vastgesteldbedrag) {
        this.vastgesteldbedrag = vastgesteldbedrag;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Kosten)) {
            return false;
        }
        return getId() != null && getId().equals(((Kosten) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Kosten{" +
            "id=" + getId() +
            ", aangemaaktdoor='" + getAangemaaktdoor() + "'" +
            ", aantal='" + getAantal() + "'" +
            ", bedrag='" + getBedrag() + "'" +
            ", bedragtotaal='" + getBedragtotaal() + "'" +
            ", datumaanmaak='" + getDatumaanmaak() + "'" +
            ", datummutatie='" + getDatummutatie() + "'" +
            ", eenheid='" + getEenheid() + "'" +
            ", geaccordeerd='" + getGeaccordeerd() + "'" +
            ", gefactureerdop='" + getGefactureerdop() + "'" +
            ", gemuteerddoor='" + getGemuteerddoor() + "'" +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", opbasisvangrondslag='" + getOpbasisvangrondslag() + "'" +
            ", tarief='" + getTarief() + "'" +
            ", type='" + getType() + "'" +
            ", vastgesteldbedrag='" + getVastgesteldbedrag() + "'" +
            "}";
    }
}
