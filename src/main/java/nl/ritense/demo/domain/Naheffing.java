package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Naheffing.
 */
@Entity
@Table(name = "naheffing")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Naheffing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bedrag", precision = 21, scale = 2)
    private BigDecimal bedrag;

    @Column(name = "bezwaarafgehandeld")
    private LocalDate bezwaarafgehandeld;

    @Column(name = "bezwaaringetrokken")
    private LocalDate bezwaaringetrokken;

    @Column(name = "bezwaartoegewezen")
    private LocalDate bezwaartoegewezen;

    @Column(name = "bonnummer")
    private String bonnummer;

    @Column(name = "datumbetaling")
    private LocalDate datumbetaling;

    @Column(name = "datumbezwaar")
    private LocalDate datumbezwaar;

    @Column(name = "datumgeseponeerd")
    private LocalDate datumgeseponeerd;

    @Column(name = "datumindiening")
    private LocalDate datumindiening;

    @Column(name = "dienstcd")
    private String dienstcd;

    @Column(name = "fiscaal")
    private Boolean fiscaal;

    @Column(name = "organisatie")
    private String organisatie;

    @Column(name = "overtreding")
    private String overtreding;

    @Column(name = "parkeertarief", precision = 21, scale = 2)
    private BigDecimal parkeertarief;

    @Column(name = "redenseponeren")
    private String redenseponeren;

    @Column(name = "vorderingnummer")
    private String vorderingnummer;

    @JsonIgnoreProperties(
        value = { "komtvoortuitNaheffing", "uitgevoerddoorMedewerker", "betreftVoertuig", "betreftParkeervlak" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "komtvoortuitNaheffing")
    private Parkeerscan komtvoortuitParkeerscan;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Naheffing id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBedrag() {
        return this.bedrag;
    }

    public Naheffing bedrag(BigDecimal bedrag) {
        this.setBedrag(bedrag);
        return this;
    }

    public void setBedrag(BigDecimal bedrag) {
        this.bedrag = bedrag;
    }

    public LocalDate getBezwaarafgehandeld() {
        return this.bezwaarafgehandeld;
    }

    public Naheffing bezwaarafgehandeld(LocalDate bezwaarafgehandeld) {
        this.setBezwaarafgehandeld(bezwaarafgehandeld);
        return this;
    }

    public void setBezwaarafgehandeld(LocalDate bezwaarafgehandeld) {
        this.bezwaarafgehandeld = bezwaarafgehandeld;
    }

    public LocalDate getBezwaaringetrokken() {
        return this.bezwaaringetrokken;
    }

    public Naheffing bezwaaringetrokken(LocalDate bezwaaringetrokken) {
        this.setBezwaaringetrokken(bezwaaringetrokken);
        return this;
    }

    public void setBezwaaringetrokken(LocalDate bezwaaringetrokken) {
        this.bezwaaringetrokken = bezwaaringetrokken;
    }

    public LocalDate getBezwaartoegewezen() {
        return this.bezwaartoegewezen;
    }

    public Naheffing bezwaartoegewezen(LocalDate bezwaartoegewezen) {
        this.setBezwaartoegewezen(bezwaartoegewezen);
        return this;
    }

    public void setBezwaartoegewezen(LocalDate bezwaartoegewezen) {
        this.bezwaartoegewezen = bezwaartoegewezen;
    }

    public String getBonnummer() {
        return this.bonnummer;
    }

    public Naheffing bonnummer(String bonnummer) {
        this.setBonnummer(bonnummer);
        return this;
    }

    public void setBonnummer(String bonnummer) {
        this.bonnummer = bonnummer;
    }

    public LocalDate getDatumbetaling() {
        return this.datumbetaling;
    }

    public Naheffing datumbetaling(LocalDate datumbetaling) {
        this.setDatumbetaling(datumbetaling);
        return this;
    }

    public void setDatumbetaling(LocalDate datumbetaling) {
        this.datumbetaling = datumbetaling;
    }

    public LocalDate getDatumbezwaar() {
        return this.datumbezwaar;
    }

    public Naheffing datumbezwaar(LocalDate datumbezwaar) {
        this.setDatumbezwaar(datumbezwaar);
        return this;
    }

    public void setDatumbezwaar(LocalDate datumbezwaar) {
        this.datumbezwaar = datumbezwaar;
    }

    public LocalDate getDatumgeseponeerd() {
        return this.datumgeseponeerd;
    }

    public Naheffing datumgeseponeerd(LocalDate datumgeseponeerd) {
        this.setDatumgeseponeerd(datumgeseponeerd);
        return this;
    }

    public void setDatumgeseponeerd(LocalDate datumgeseponeerd) {
        this.datumgeseponeerd = datumgeseponeerd;
    }

    public LocalDate getDatumindiening() {
        return this.datumindiening;
    }

    public Naheffing datumindiening(LocalDate datumindiening) {
        this.setDatumindiening(datumindiening);
        return this;
    }

    public void setDatumindiening(LocalDate datumindiening) {
        this.datumindiening = datumindiening;
    }

    public String getDienstcd() {
        return this.dienstcd;
    }

    public Naheffing dienstcd(String dienstcd) {
        this.setDienstcd(dienstcd);
        return this;
    }

    public void setDienstcd(String dienstcd) {
        this.dienstcd = dienstcd;
    }

    public Boolean getFiscaal() {
        return this.fiscaal;
    }

    public Naheffing fiscaal(Boolean fiscaal) {
        this.setFiscaal(fiscaal);
        return this;
    }

    public void setFiscaal(Boolean fiscaal) {
        this.fiscaal = fiscaal;
    }

    public String getOrganisatie() {
        return this.organisatie;
    }

    public Naheffing organisatie(String organisatie) {
        this.setOrganisatie(organisatie);
        return this;
    }

    public void setOrganisatie(String organisatie) {
        this.organisatie = organisatie;
    }

    public String getOvertreding() {
        return this.overtreding;
    }

    public Naheffing overtreding(String overtreding) {
        this.setOvertreding(overtreding);
        return this;
    }

    public void setOvertreding(String overtreding) {
        this.overtreding = overtreding;
    }

    public BigDecimal getParkeertarief() {
        return this.parkeertarief;
    }

    public Naheffing parkeertarief(BigDecimal parkeertarief) {
        this.setParkeertarief(parkeertarief);
        return this;
    }

    public void setParkeertarief(BigDecimal parkeertarief) {
        this.parkeertarief = parkeertarief;
    }

    public String getRedenseponeren() {
        return this.redenseponeren;
    }

    public Naheffing redenseponeren(String redenseponeren) {
        this.setRedenseponeren(redenseponeren);
        return this;
    }

    public void setRedenseponeren(String redenseponeren) {
        this.redenseponeren = redenseponeren;
    }

    public String getVorderingnummer() {
        return this.vorderingnummer;
    }

    public Naheffing vorderingnummer(String vorderingnummer) {
        this.setVorderingnummer(vorderingnummer);
        return this;
    }

    public void setVorderingnummer(String vorderingnummer) {
        this.vorderingnummer = vorderingnummer;
    }

    public Parkeerscan getKomtvoortuitParkeerscan() {
        return this.komtvoortuitParkeerscan;
    }

    public void setKomtvoortuitParkeerscan(Parkeerscan parkeerscan) {
        if (this.komtvoortuitParkeerscan != null) {
            this.komtvoortuitParkeerscan.setKomtvoortuitNaheffing(null);
        }
        if (parkeerscan != null) {
            parkeerscan.setKomtvoortuitNaheffing(this);
        }
        this.komtvoortuitParkeerscan = parkeerscan;
    }

    public Naheffing komtvoortuitParkeerscan(Parkeerscan parkeerscan) {
        this.setKomtvoortuitParkeerscan(parkeerscan);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Naheffing)) {
            return false;
        }
        return getId() != null && getId().equals(((Naheffing) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Naheffing{" +
            "id=" + getId() +
            ", bedrag=" + getBedrag() +
            ", bezwaarafgehandeld='" + getBezwaarafgehandeld() + "'" +
            ", bezwaaringetrokken='" + getBezwaaringetrokken() + "'" +
            ", bezwaartoegewezen='" + getBezwaartoegewezen() + "'" +
            ", bonnummer='" + getBonnummer() + "'" +
            ", datumbetaling='" + getDatumbetaling() + "'" +
            ", datumbezwaar='" + getDatumbezwaar() + "'" +
            ", datumgeseponeerd='" + getDatumgeseponeerd() + "'" +
            ", datumindiening='" + getDatumindiening() + "'" +
            ", dienstcd='" + getDienstcd() + "'" +
            ", fiscaal='" + getFiscaal() + "'" +
            ", organisatie='" + getOrganisatie() + "'" +
            ", overtreding='" + getOvertreding() + "'" +
            ", parkeertarief=" + getParkeertarief() +
            ", redenseponeren='" + getRedenseponeren() + "'" +
            ", vorderingnummer='" + getVorderingnummer() + "'" +
            "}";
    }
}
