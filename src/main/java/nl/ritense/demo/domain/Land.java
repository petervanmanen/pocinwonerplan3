package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Land.
 */
@Entity
@Table(name = "land")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Land implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumeindefictief")
    private Boolean datumeindefictief;

    @Column(name = "datumeindeland")
    private String datumeindeland;

    @Column(name = "datumingangland")
    private String datumingangland;

    @Column(name = "landcode")
    private String landcode;

    @Column(name = "landcodeisodrieletterig")
    private String landcodeisodrieletterig;

    @Column(name = "landcodeisotweeletterig")
    private String landcodeisotweeletterig;

    @Column(name = "landnaam")
    private String landnaam;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Land id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDatumeindefictief() {
        return this.datumeindefictief;
    }

    public Land datumeindefictief(Boolean datumeindefictief) {
        this.setDatumeindefictief(datumeindefictief);
        return this;
    }

    public void setDatumeindefictief(Boolean datumeindefictief) {
        this.datumeindefictief = datumeindefictief;
    }

    public String getDatumeindeland() {
        return this.datumeindeland;
    }

    public Land datumeindeland(String datumeindeland) {
        this.setDatumeindeland(datumeindeland);
        return this;
    }

    public void setDatumeindeland(String datumeindeland) {
        this.datumeindeland = datumeindeland;
    }

    public String getDatumingangland() {
        return this.datumingangland;
    }

    public Land datumingangland(String datumingangland) {
        this.setDatumingangland(datumingangland);
        return this;
    }

    public void setDatumingangland(String datumingangland) {
        this.datumingangland = datumingangland;
    }

    public String getLandcode() {
        return this.landcode;
    }

    public Land landcode(String landcode) {
        this.setLandcode(landcode);
        return this;
    }

    public void setLandcode(String landcode) {
        this.landcode = landcode;
    }

    public String getLandcodeisodrieletterig() {
        return this.landcodeisodrieletterig;
    }

    public Land landcodeisodrieletterig(String landcodeisodrieletterig) {
        this.setLandcodeisodrieletterig(landcodeisodrieletterig);
        return this;
    }

    public void setLandcodeisodrieletterig(String landcodeisodrieletterig) {
        this.landcodeisodrieletterig = landcodeisodrieletterig;
    }

    public String getLandcodeisotweeletterig() {
        return this.landcodeisotweeletterig;
    }

    public Land landcodeisotweeletterig(String landcodeisotweeletterig) {
        this.setLandcodeisotweeletterig(landcodeisotweeletterig);
        return this;
    }

    public void setLandcodeisotweeletterig(String landcodeisotweeletterig) {
        this.landcodeisotweeletterig = landcodeisotweeletterig;
    }

    public String getLandnaam() {
        return this.landnaam;
    }

    public Land landnaam(String landnaam) {
        this.setLandnaam(landnaam);
        return this;
    }

    public void setLandnaam(String landnaam) {
        this.landnaam = landnaam;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Land)) {
            return false;
        }
        return getId() != null && getId().equals(((Land) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Land{" +
            "id=" + getId() +
            ", datumeindefictief='" + getDatumeindefictief() + "'" +
            ", datumeindeland='" + getDatumeindeland() + "'" +
            ", datumingangland='" + getDatumingangland() + "'" +
            ", landcode='" + getLandcode() + "'" +
            ", landcodeisodrieletterig='" + getLandcodeisodrieletterig() + "'" +
            ", landcodeisotweeletterig='" + getLandcodeisotweeletterig() + "'" +
            ", landnaam='" + getLandnaam() + "'" +
            "}";
    }
}
