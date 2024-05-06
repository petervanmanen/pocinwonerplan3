package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Legesgrondslag.
 */
@Entity
@Table(name = "legesgrondslag")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Legesgrondslag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aangemaaktdoor")
    private String aangemaaktdoor;

    @Size(max = 20)
    @Column(name = "aantalopgegeven", length = 20)
    private String aantalopgegeven;

    @Size(max = 20)
    @Column(name = "aantalvastgesteld", length = 20)
    private String aantalvastgesteld;

    @Size(max = 20)
    @Column(name = "automatisch", length = 20)
    private String automatisch;

    @Column(name = "datumaanmaak")
    private String datumaanmaak;

    @Column(name = "datummutatie")
    private LocalDate datummutatie;

    @Size(max = 20)
    @Column(name = "eenheid", length = 20)
    private String eenheid;

    @Column(name = "gemuteerddoor")
    private String gemuteerddoor;

    @Column(name = "legesgrondslag")
    private String legesgrondslag;

    @Column(name = "omschrijving")
    private String omschrijving;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Legesgrondslag id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAangemaaktdoor() {
        return this.aangemaaktdoor;
    }

    public Legesgrondslag aangemaaktdoor(String aangemaaktdoor) {
        this.setAangemaaktdoor(aangemaaktdoor);
        return this;
    }

    public void setAangemaaktdoor(String aangemaaktdoor) {
        this.aangemaaktdoor = aangemaaktdoor;
    }

    public String getAantalopgegeven() {
        return this.aantalopgegeven;
    }

    public Legesgrondslag aantalopgegeven(String aantalopgegeven) {
        this.setAantalopgegeven(aantalopgegeven);
        return this;
    }

    public void setAantalopgegeven(String aantalopgegeven) {
        this.aantalopgegeven = aantalopgegeven;
    }

    public String getAantalvastgesteld() {
        return this.aantalvastgesteld;
    }

    public Legesgrondslag aantalvastgesteld(String aantalvastgesteld) {
        this.setAantalvastgesteld(aantalvastgesteld);
        return this;
    }

    public void setAantalvastgesteld(String aantalvastgesteld) {
        this.aantalvastgesteld = aantalvastgesteld;
    }

    public String getAutomatisch() {
        return this.automatisch;
    }

    public Legesgrondslag automatisch(String automatisch) {
        this.setAutomatisch(automatisch);
        return this;
    }

    public void setAutomatisch(String automatisch) {
        this.automatisch = automatisch;
    }

    public String getDatumaanmaak() {
        return this.datumaanmaak;
    }

    public Legesgrondslag datumaanmaak(String datumaanmaak) {
        this.setDatumaanmaak(datumaanmaak);
        return this;
    }

    public void setDatumaanmaak(String datumaanmaak) {
        this.datumaanmaak = datumaanmaak;
    }

    public LocalDate getDatummutatie() {
        return this.datummutatie;
    }

    public Legesgrondslag datummutatie(LocalDate datummutatie) {
        this.setDatummutatie(datummutatie);
        return this;
    }

    public void setDatummutatie(LocalDate datummutatie) {
        this.datummutatie = datummutatie;
    }

    public String getEenheid() {
        return this.eenheid;
    }

    public Legesgrondslag eenheid(String eenheid) {
        this.setEenheid(eenheid);
        return this;
    }

    public void setEenheid(String eenheid) {
        this.eenheid = eenheid;
    }

    public String getGemuteerddoor() {
        return this.gemuteerddoor;
    }

    public Legesgrondslag gemuteerddoor(String gemuteerddoor) {
        this.setGemuteerddoor(gemuteerddoor);
        return this;
    }

    public void setGemuteerddoor(String gemuteerddoor) {
        this.gemuteerddoor = gemuteerddoor;
    }

    public String getLegesgrondslag() {
        return this.legesgrondslag;
    }

    public Legesgrondslag legesgrondslag(String legesgrondslag) {
        this.setLegesgrondslag(legesgrondslag);
        return this;
    }

    public void setLegesgrondslag(String legesgrondslag) {
        this.legesgrondslag = legesgrondslag;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Legesgrondslag omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Legesgrondslag)) {
            return false;
        }
        return getId() != null && getId().equals(((Legesgrondslag) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Legesgrondslag{" +
            "id=" + getId() +
            ", aangemaaktdoor='" + getAangemaaktdoor() + "'" +
            ", aantalopgegeven='" + getAantalopgegeven() + "'" +
            ", aantalvastgesteld='" + getAantalvastgesteld() + "'" +
            ", automatisch='" + getAutomatisch() + "'" +
            ", datumaanmaak='" + getDatumaanmaak() + "'" +
            ", datummutatie='" + getDatummutatie() + "'" +
            ", eenheid='" + getEenheid() + "'" +
            ", gemuteerddoor='" + getGemuteerddoor() + "'" +
            ", legesgrondslag='" + getLegesgrondslag() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
