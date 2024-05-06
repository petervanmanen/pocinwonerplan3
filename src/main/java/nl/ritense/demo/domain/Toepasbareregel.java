package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Toepasbareregel.
 */
@Entity
@Table(name = "toepasbareregel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Toepasbareregel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheid")
    private LocalDate datumbegingeldigheid;

    @Column(name = "datumeindegeldigheid")
    private LocalDate datumeindegeldigheid;

    @Column(name = "domein")
    private String domein;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "soortaansluitpunt")
    private String soortaansluitpunt;

    @Column(name = "toestemming")
    private String toestemming;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Toepasbareregel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbegingeldigheid() {
        return this.datumbegingeldigheid;
    }

    public Toepasbareregel datumbegingeldigheid(LocalDate datumbegingeldigheid) {
        this.setDatumbegingeldigheid(datumbegingeldigheid);
        return this;
    }

    public void setDatumbegingeldigheid(LocalDate datumbegingeldigheid) {
        this.datumbegingeldigheid = datumbegingeldigheid;
    }

    public LocalDate getDatumeindegeldigheid() {
        return this.datumeindegeldigheid;
    }

    public Toepasbareregel datumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.setDatumeindegeldigheid(datumeindegeldigheid);
        return this;
    }

    public void setDatumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.datumeindegeldigheid = datumeindegeldigheid;
    }

    public String getDomein() {
        return this.domein;
    }

    public Toepasbareregel domein(String domein) {
        this.setDomein(domein);
        return this;
    }

    public void setDomein(String domein) {
        this.domein = domein;
    }

    public String getNaam() {
        return this.naam;
    }

    public Toepasbareregel naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Toepasbareregel omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getSoortaansluitpunt() {
        return this.soortaansluitpunt;
    }

    public Toepasbareregel soortaansluitpunt(String soortaansluitpunt) {
        this.setSoortaansluitpunt(soortaansluitpunt);
        return this;
    }

    public void setSoortaansluitpunt(String soortaansluitpunt) {
        this.soortaansluitpunt = soortaansluitpunt;
    }

    public String getToestemming() {
        return this.toestemming;
    }

    public Toepasbareregel toestemming(String toestemming) {
        this.setToestemming(toestemming);
        return this;
    }

    public void setToestemming(String toestemming) {
        this.toestemming = toestemming;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Toepasbareregel)) {
            return false;
        }
        return getId() != null && getId().equals(((Toepasbareregel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Toepasbareregel{" +
            "id=" + getId() +
            ", datumbegingeldigheid='" + getDatumbegingeldigheid() + "'" +
            ", datumeindegeldigheid='" + getDatumeindegeldigheid() + "'" +
            ", domein='" + getDomein() + "'" +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", soortaansluitpunt='" + getSoortaansluitpunt() + "'" +
            ", toestemming='" + getToestemming() + "'" +
            "}";
    }
}
