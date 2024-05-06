package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Verlof.
 */
@Entity
@Table(name = "verlof")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Verlof implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumaanvraag")
    private LocalDate datumaanvraag;

    @Column(name = "datumtijdeinde")
    private String datumtijdeinde;

    @Column(name = "datumtijdstart")
    private String datumtijdstart;

    @Column(name = "datumtoekenning")
    private LocalDate datumtoekenning;

    @Column(name = "goedgekeurd")
    private Boolean goedgekeurd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "soortverlofVerlofs" }, allowSetters = true)
    private Verlofsoort soortverlofVerlofsoort;

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Werknemer heeftverlofWerknemer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Verlof id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumaanvraag() {
        return this.datumaanvraag;
    }

    public Verlof datumaanvraag(LocalDate datumaanvraag) {
        this.setDatumaanvraag(datumaanvraag);
        return this;
    }

    public void setDatumaanvraag(LocalDate datumaanvraag) {
        this.datumaanvraag = datumaanvraag;
    }

    public String getDatumtijdeinde() {
        return this.datumtijdeinde;
    }

    public Verlof datumtijdeinde(String datumtijdeinde) {
        this.setDatumtijdeinde(datumtijdeinde);
        return this;
    }

    public void setDatumtijdeinde(String datumtijdeinde) {
        this.datumtijdeinde = datumtijdeinde;
    }

    public String getDatumtijdstart() {
        return this.datumtijdstart;
    }

    public Verlof datumtijdstart(String datumtijdstart) {
        this.setDatumtijdstart(datumtijdstart);
        return this;
    }

    public void setDatumtijdstart(String datumtijdstart) {
        this.datumtijdstart = datumtijdstart;
    }

    public LocalDate getDatumtoekenning() {
        return this.datumtoekenning;
    }

    public Verlof datumtoekenning(LocalDate datumtoekenning) {
        this.setDatumtoekenning(datumtoekenning);
        return this;
    }

    public void setDatumtoekenning(LocalDate datumtoekenning) {
        this.datumtoekenning = datumtoekenning;
    }

    public Boolean getGoedgekeurd() {
        return this.goedgekeurd;
    }

    public Verlof goedgekeurd(Boolean goedgekeurd) {
        this.setGoedgekeurd(goedgekeurd);
        return this;
    }

    public void setGoedgekeurd(Boolean goedgekeurd) {
        this.goedgekeurd = goedgekeurd;
    }

    public Verlofsoort getSoortverlofVerlofsoort() {
        return this.soortverlofVerlofsoort;
    }

    public void setSoortverlofVerlofsoort(Verlofsoort verlofsoort) {
        this.soortverlofVerlofsoort = verlofsoort;
    }

    public Verlof soortverlofVerlofsoort(Verlofsoort verlofsoort) {
        this.setSoortverlofVerlofsoort(verlofsoort);
        return this;
    }

    public Werknemer getHeeftverlofWerknemer() {
        return this.heeftverlofWerknemer;
    }

    public void setHeeftverlofWerknemer(Werknemer werknemer) {
        this.heeftverlofWerknemer = werknemer;
    }

    public Verlof heeftverlofWerknemer(Werknemer werknemer) {
        this.setHeeftverlofWerknemer(werknemer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Verlof)) {
            return false;
        }
        return getId() != null && getId().equals(((Verlof) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Verlof{" +
            "id=" + getId() +
            ", datumaanvraag='" + getDatumaanvraag() + "'" +
            ", datumtijdeinde='" + getDatumtijdeinde() + "'" +
            ", datumtijdstart='" + getDatumtijdstart() + "'" +
            ", datumtoekenning='" + getDatumtoekenning() + "'" +
            ", goedgekeurd='" + getGoedgekeurd() + "'" +
            "}";
    }
}
