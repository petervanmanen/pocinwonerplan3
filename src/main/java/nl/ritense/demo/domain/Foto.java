package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Foto.
 */
@Entity
@Table(name = "foto")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Foto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bestandsgrootte")
    private String bestandsgrootte;

    @Column(name = "bestandsnaam")
    private String bestandsnaam;

    @Column(name = "bestandstype")
    private String bestandstype;

    @Column(name = "datumtijd")
    private String datumtijd;

    @Column(name = "locatie")
    private String locatie;

    @Column(name = "pixelsx")
    private String pixelsx;

    @Column(name = "pixelsy")
    private String pixelsy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Foto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBestandsgrootte() {
        return this.bestandsgrootte;
    }

    public Foto bestandsgrootte(String bestandsgrootte) {
        this.setBestandsgrootte(bestandsgrootte);
        return this;
    }

    public void setBestandsgrootte(String bestandsgrootte) {
        this.bestandsgrootte = bestandsgrootte;
    }

    public String getBestandsnaam() {
        return this.bestandsnaam;
    }

    public Foto bestandsnaam(String bestandsnaam) {
        this.setBestandsnaam(bestandsnaam);
        return this;
    }

    public void setBestandsnaam(String bestandsnaam) {
        this.bestandsnaam = bestandsnaam;
    }

    public String getBestandstype() {
        return this.bestandstype;
    }

    public Foto bestandstype(String bestandstype) {
        this.setBestandstype(bestandstype);
        return this;
    }

    public void setBestandstype(String bestandstype) {
        this.bestandstype = bestandstype;
    }

    public String getDatumtijd() {
        return this.datumtijd;
    }

    public Foto datumtijd(String datumtijd) {
        this.setDatumtijd(datumtijd);
        return this;
    }

    public void setDatumtijd(String datumtijd) {
        this.datumtijd = datumtijd;
    }

    public String getLocatie() {
        return this.locatie;
    }

    public Foto locatie(String locatie) {
        this.setLocatie(locatie);
        return this;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getPixelsx() {
        return this.pixelsx;
    }

    public Foto pixelsx(String pixelsx) {
        this.setPixelsx(pixelsx);
        return this;
    }

    public void setPixelsx(String pixelsx) {
        this.pixelsx = pixelsx;
    }

    public String getPixelsy() {
        return this.pixelsy;
    }

    public Foto pixelsy(String pixelsy) {
        this.setPixelsy(pixelsy);
        return this;
    }

    public void setPixelsy(String pixelsy) {
        this.pixelsy = pixelsy;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Foto)) {
            return false;
        }
        return getId() != null && getId().equals(((Foto) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Foto{" +
            "id=" + getId() +
            ", bestandsgrootte='" + getBestandsgrootte() + "'" +
            ", bestandsnaam='" + getBestandsnaam() + "'" +
            ", bestandstype='" + getBestandstype() + "'" +
            ", datumtijd='" + getDatumtijd() + "'" +
            ", locatie='" + getLocatie() + "'" +
            ", pixelsx='" + getPixelsx() + "'" +
            ", pixelsy='" + getPixelsy() + "'" +
            "}";
    }
}
