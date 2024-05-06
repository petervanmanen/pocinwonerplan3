package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Dienstverband.
 */
@Entity
@Table(name = "dienstverband")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Dienstverband implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "periodiek")
    private String periodiek;

    @Column(name = "salaris", precision = 21, scale = 2)
    private BigDecimal salaris;

    @Column(name = "schaal")
    private String schaal;

    @Column(name = "urenperweek")
    private String urenperweek;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "uitgevoerddoorOpdrachtgevers",
            "uitgevoerddoorOpdrachtnemers",
            "dienstverbandconformfunctieDienstverbands",
            "vacaturebijfunctieVacatures",
        },
        allowSetters = true
    )
    private Functie dienstverbandconformfunctieFunctie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "aantalvolgensinzetDienstverbands" }, allowSetters = true)
    private Uren aantalvolgensinzetUren;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "beoordeeltdoorBeoordelings",
            "beoordelingvanBeoordelings",
            "dientinDeclaraties",
            "medewerkerheeftdienstverbandDienstverbands",
            "solliciteertSollicitaties",
            "heeftverlofVerlofs",
            "heeftverzuimVerzuims",
            "heeftondergaanGeweldsincident",
            "heeftRols",
            "doetsollicitatiegesprekSollicitatiegespreks",
        },
        allowSetters = true
    )
    private Werknemer medewerkerheeftdienstverbandWerknemer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "aantalvolgensinzetDienstverbands" }, allowSetters = true)
    private Inzet aantalvolgensinzetInzet;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Dienstverband id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Dienstverband datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Dienstverband datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public String getPeriodiek() {
        return this.periodiek;
    }

    public Dienstverband periodiek(String periodiek) {
        this.setPeriodiek(periodiek);
        return this;
    }

    public void setPeriodiek(String periodiek) {
        this.periodiek = periodiek;
    }

    public BigDecimal getSalaris() {
        return this.salaris;
    }

    public Dienstverband salaris(BigDecimal salaris) {
        this.setSalaris(salaris);
        return this;
    }

    public void setSalaris(BigDecimal salaris) {
        this.salaris = salaris;
    }

    public String getSchaal() {
        return this.schaal;
    }

    public Dienstverband schaal(String schaal) {
        this.setSchaal(schaal);
        return this;
    }

    public void setSchaal(String schaal) {
        this.schaal = schaal;
    }

    public String getUrenperweek() {
        return this.urenperweek;
    }

    public Dienstverband urenperweek(String urenperweek) {
        this.setUrenperweek(urenperweek);
        return this;
    }

    public void setUrenperweek(String urenperweek) {
        this.urenperweek = urenperweek;
    }

    public Functie getDienstverbandconformfunctieFunctie() {
        return this.dienstverbandconformfunctieFunctie;
    }

    public void setDienstverbandconformfunctieFunctie(Functie functie) {
        this.dienstverbandconformfunctieFunctie = functie;
    }

    public Dienstverband dienstverbandconformfunctieFunctie(Functie functie) {
        this.setDienstverbandconformfunctieFunctie(functie);
        return this;
    }

    public Uren getAantalvolgensinzetUren() {
        return this.aantalvolgensinzetUren;
    }

    public void setAantalvolgensinzetUren(Uren uren) {
        this.aantalvolgensinzetUren = uren;
    }

    public Dienstverband aantalvolgensinzetUren(Uren uren) {
        this.setAantalvolgensinzetUren(uren);
        return this;
    }

    public Werknemer getMedewerkerheeftdienstverbandWerknemer() {
        return this.medewerkerheeftdienstverbandWerknemer;
    }

    public void setMedewerkerheeftdienstverbandWerknemer(Werknemer werknemer) {
        this.medewerkerheeftdienstverbandWerknemer = werknemer;
    }

    public Dienstverband medewerkerheeftdienstverbandWerknemer(Werknemer werknemer) {
        this.setMedewerkerheeftdienstverbandWerknemer(werknemer);
        return this;
    }

    public Inzet getAantalvolgensinzetInzet() {
        return this.aantalvolgensinzetInzet;
    }

    public void setAantalvolgensinzetInzet(Inzet inzet) {
        this.aantalvolgensinzetInzet = inzet;
    }

    public Dienstverband aantalvolgensinzetInzet(Inzet inzet) {
        this.setAantalvolgensinzetInzet(inzet);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dienstverband)) {
            return false;
        }
        return getId() != null && getId().equals(((Dienstverband) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dienstverband{" +
            "id=" + getId() +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", periodiek='" + getPeriodiek() + "'" +
            ", salaris=" + getSalaris() +
            ", schaal='" + getSchaal() + "'" +
            ", urenperweek='" + getUrenperweek() + "'" +
            "}";
    }
}
