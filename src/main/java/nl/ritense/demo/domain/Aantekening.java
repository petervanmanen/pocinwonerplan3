package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Aantekening.
 */
@Entity
@Table(name = "aantekening")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Aantekening implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 100)
    @Column(name = "aard", length = 100)
    private String aard;

    @Column(name = "begrenzing")
    private String begrenzing;

    @Column(name = "betreftgedeeltevanperceel")
    private Boolean betreftgedeeltevanperceel;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumeinderecht")
    private LocalDate datumeinderecht;

    @Size(max = 100)
    @Column(name = "identificatie", length = 100)
    private String identificatie;

    @Column(name = "omschrijving")
    private String omschrijving;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "emptyAantekenings", "heeftRechtspersoon", "bezwaartZekerheidsrechts" }, allowSetters = true)
    private Tenaamstelling emptyTenaamstelling;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Aantekening id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAard() {
        return this.aard;
    }

    public Aantekening aard(String aard) {
        this.setAard(aard);
        return this;
    }

    public void setAard(String aard) {
        this.aard = aard;
    }

    public String getBegrenzing() {
        return this.begrenzing;
    }

    public Aantekening begrenzing(String begrenzing) {
        this.setBegrenzing(begrenzing);
        return this;
    }

    public void setBegrenzing(String begrenzing) {
        this.begrenzing = begrenzing;
    }

    public Boolean getBetreftgedeeltevanperceel() {
        return this.betreftgedeeltevanperceel;
    }

    public Aantekening betreftgedeeltevanperceel(Boolean betreftgedeeltevanperceel) {
        this.setBetreftgedeeltevanperceel(betreftgedeeltevanperceel);
        return this;
    }

    public void setBetreftgedeeltevanperceel(Boolean betreftgedeeltevanperceel) {
        this.betreftgedeeltevanperceel = betreftgedeeltevanperceel;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Aantekening datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumeinderecht() {
        return this.datumeinderecht;
    }

    public Aantekening datumeinderecht(LocalDate datumeinderecht) {
        this.setDatumeinderecht(datumeinderecht);
        return this;
    }

    public void setDatumeinderecht(LocalDate datumeinderecht) {
        this.datumeinderecht = datumeinderecht;
    }

    public String getIdentificatie() {
        return this.identificatie;
    }

    public Aantekening identificatie(String identificatie) {
        this.setIdentificatie(identificatie);
        return this;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Aantekening omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Tenaamstelling getEmptyTenaamstelling() {
        return this.emptyTenaamstelling;
    }

    public void setEmptyTenaamstelling(Tenaamstelling tenaamstelling) {
        this.emptyTenaamstelling = tenaamstelling;
    }

    public Aantekening emptyTenaamstelling(Tenaamstelling tenaamstelling) {
        this.setEmptyTenaamstelling(tenaamstelling);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aantekening)) {
            return false;
        }
        return getId() != null && getId().equals(((Aantekening) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Aantekening{" +
            "id=" + getId() +
            ", aard='" + getAard() + "'" +
            ", begrenzing='" + getBegrenzing() + "'" +
            ", betreftgedeeltevanperceel='" + getBetreftgedeeltevanperceel() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumeinderecht='" + getDatumeinderecht() + "'" +
            ", identificatie='" + getIdentificatie() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
