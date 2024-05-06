package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Nationaliteit.
 */
@Entity
@Table(name = "nationaliteit")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Nationaliteit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "buitenlandsenationaliteit")
    private Boolean buitenlandsenationaliteit;

    @Column(name = "datumeindegeldigheid")
    private LocalDate datumeindegeldigheid;

    @Column(name = "datuminganggeldigheid")
    private LocalDate datuminganggeldigheid;

    @Column(name = "datumopnamen")
    private String datumopnamen;

    @Column(name = "datumverliesnationaliteit")
    private LocalDate datumverliesnationaliteit;

    @Column(name = "nationaliteit")
    private String nationaliteit;

    @Column(name = "nationaliteitcode")
    private String nationaliteitcode;

    @Size(max = 100)
    @Column(name = "redenverkrijgingnederlandsenationaliteit", length = 100)
    private String redenverkrijgingnederlandsenationaliteit;

    @Size(max = 100)
    @Column(name = "redenverliesnederlandsenationaliteit", length = 100)
    private String redenverliesnederlandsenationaliteit;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Nationaliteit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getBuitenlandsenationaliteit() {
        return this.buitenlandsenationaliteit;
    }

    public Nationaliteit buitenlandsenationaliteit(Boolean buitenlandsenationaliteit) {
        this.setBuitenlandsenationaliteit(buitenlandsenationaliteit);
        return this;
    }

    public void setBuitenlandsenationaliteit(Boolean buitenlandsenationaliteit) {
        this.buitenlandsenationaliteit = buitenlandsenationaliteit;
    }

    public LocalDate getDatumeindegeldigheid() {
        return this.datumeindegeldigheid;
    }

    public Nationaliteit datumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.setDatumeindegeldigheid(datumeindegeldigheid);
        return this;
    }

    public void setDatumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.datumeindegeldigheid = datumeindegeldigheid;
    }

    public LocalDate getDatuminganggeldigheid() {
        return this.datuminganggeldigheid;
    }

    public Nationaliteit datuminganggeldigheid(LocalDate datuminganggeldigheid) {
        this.setDatuminganggeldigheid(datuminganggeldigheid);
        return this;
    }

    public void setDatuminganggeldigheid(LocalDate datuminganggeldigheid) {
        this.datuminganggeldigheid = datuminganggeldigheid;
    }

    public String getDatumopnamen() {
        return this.datumopnamen;
    }

    public Nationaliteit datumopnamen(String datumopnamen) {
        this.setDatumopnamen(datumopnamen);
        return this;
    }

    public void setDatumopnamen(String datumopnamen) {
        this.datumopnamen = datumopnamen;
    }

    public LocalDate getDatumverliesnationaliteit() {
        return this.datumverliesnationaliteit;
    }

    public Nationaliteit datumverliesnationaliteit(LocalDate datumverliesnationaliteit) {
        this.setDatumverliesnationaliteit(datumverliesnationaliteit);
        return this;
    }

    public void setDatumverliesnationaliteit(LocalDate datumverliesnationaliteit) {
        this.datumverliesnationaliteit = datumverliesnationaliteit;
    }

    public String getNationaliteit() {
        return this.nationaliteit;
    }

    public Nationaliteit nationaliteit(String nationaliteit) {
        this.setNationaliteit(nationaliteit);
        return this;
    }

    public void setNationaliteit(String nationaliteit) {
        this.nationaliteit = nationaliteit;
    }

    public String getNationaliteitcode() {
        return this.nationaliteitcode;
    }

    public Nationaliteit nationaliteitcode(String nationaliteitcode) {
        this.setNationaliteitcode(nationaliteitcode);
        return this;
    }

    public void setNationaliteitcode(String nationaliteitcode) {
        this.nationaliteitcode = nationaliteitcode;
    }

    public String getRedenverkrijgingnederlandsenationaliteit() {
        return this.redenverkrijgingnederlandsenationaliteit;
    }

    public Nationaliteit redenverkrijgingnederlandsenationaliteit(String redenverkrijgingnederlandsenationaliteit) {
        this.setRedenverkrijgingnederlandsenationaliteit(redenverkrijgingnederlandsenationaliteit);
        return this;
    }

    public void setRedenverkrijgingnederlandsenationaliteit(String redenverkrijgingnederlandsenationaliteit) {
        this.redenverkrijgingnederlandsenationaliteit = redenverkrijgingnederlandsenationaliteit;
    }

    public String getRedenverliesnederlandsenationaliteit() {
        return this.redenverliesnederlandsenationaliteit;
    }

    public Nationaliteit redenverliesnederlandsenationaliteit(String redenverliesnederlandsenationaliteit) {
        this.setRedenverliesnederlandsenationaliteit(redenverliesnederlandsenationaliteit);
        return this;
    }

    public void setRedenverliesnederlandsenationaliteit(String redenverliesnederlandsenationaliteit) {
        this.redenverliesnederlandsenationaliteit = redenverliesnederlandsenationaliteit;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nationaliteit)) {
            return false;
        }
        return getId() != null && getId().equals(((Nationaliteit) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nationaliteit{" +
            "id=" + getId() +
            ", buitenlandsenationaliteit='" + getBuitenlandsenationaliteit() + "'" +
            ", datumeindegeldigheid='" + getDatumeindegeldigheid() + "'" +
            ", datuminganggeldigheid='" + getDatuminganggeldigheid() + "'" +
            ", datumopnamen='" + getDatumopnamen() + "'" +
            ", datumverliesnationaliteit='" + getDatumverliesnationaliteit() + "'" +
            ", nationaliteit='" + getNationaliteit() + "'" +
            ", nationaliteitcode='" + getNationaliteitcode() + "'" +
            ", redenverkrijgingnederlandsenationaliteit='" + getRedenverkrijgingnederlandsenationaliteit() + "'" +
            ", redenverliesnederlandsenationaliteit='" + getRedenverliesnederlandsenationaliteit() + "'" +
            "}";
    }
}
