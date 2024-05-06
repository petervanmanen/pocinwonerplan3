package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Vthmelding.
 */
@Entity
@Table(name = "vthmelding")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vthmelding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "activiteit")
    private String activiteit;

    @Column(name = "beoordeling")
    private String beoordeling;

    @Column(name = "datumseponering")
    private LocalDate datumseponering;

    @Column(name = "datumtijdtot")
    private String datumtijdtot;

    @Column(name = "geseponeerd")
    private String geseponeerd;

    @Column(name = "locatie")
    private String locatie;

    @Column(name = "organisatieonderdeel")
    private String organisatieonderdeel;

    @Size(max = 20)
    @Column(name = "overtredingscode", length = 20)
    private String overtredingscode;

    @Column(name = "overtredingsgroep")
    private String overtredingsgroep;

    @Column(name = "referentienummer")
    private String referentienummer;

    @Column(name = "resultaat")
    private String resultaat;

    @Column(name = "soortvthmelding")
    private String soortvthmelding;

    @Column(name = "status")
    private String status;

    @Column(name = "straatnaam")
    private String straatnaam;

    @Column(name = "taaktype")
    private String taaktype;

    @Size(max = 20)
    @Column(name = "zaaknummer", length = 20)
    private String zaaknummer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vthmelding id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActiviteit() {
        return this.activiteit;
    }

    public Vthmelding activiteit(String activiteit) {
        this.setActiviteit(activiteit);
        return this;
    }

    public void setActiviteit(String activiteit) {
        this.activiteit = activiteit;
    }

    public String getBeoordeling() {
        return this.beoordeling;
    }

    public Vthmelding beoordeling(String beoordeling) {
        this.setBeoordeling(beoordeling);
        return this;
    }

    public void setBeoordeling(String beoordeling) {
        this.beoordeling = beoordeling;
    }

    public LocalDate getDatumseponering() {
        return this.datumseponering;
    }

    public Vthmelding datumseponering(LocalDate datumseponering) {
        this.setDatumseponering(datumseponering);
        return this;
    }

    public void setDatumseponering(LocalDate datumseponering) {
        this.datumseponering = datumseponering;
    }

    public String getDatumtijdtot() {
        return this.datumtijdtot;
    }

    public Vthmelding datumtijdtot(String datumtijdtot) {
        this.setDatumtijdtot(datumtijdtot);
        return this;
    }

    public void setDatumtijdtot(String datumtijdtot) {
        this.datumtijdtot = datumtijdtot;
    }

    public String getGeseponeerd() {
        return this.geseponeerd;
    }

    public Vthmelding geseponeerd(String geseponeerd) {
        this.setGeseponeerd(geseponeerd);
        return this;
    }

    public void setGeseponeerd(String geseponeerd) {
        this.geseponeerd = geseponeerd;
    }

    public String getLocatie() {
        return this.locatie;
    }

    public Vthmelding locatie(String locatie) {
        this.setLocatie(locatie);
        return this;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getOrganisatieonderdeel() {
        return this.organisatieonderdeel;
    }

    public Vthmelding organisatieonderdeel(String organisatieonderdeel) {
        this.setOrganisatieonderdeel(organisatieonderdeel);
        return this;
    }

    public void setOrganisatieonderdeel(String organisatieonderdeel) {
        this.organisatieonderdeel = organisatieonderdeel;
    }

    public String getOvertredingscode() {
        return this.overtredingscode;
    }

    public Vthmelding overtredingscode(String overtredingscode) {
        this.setOvertredingscode(overtredingscode);
        return this;
    }

    public void setOvertredingscode(String overtredingscode) {
        this.overtredingscode = overtredingscode;
    }

    public String getOvertredingsgroep() {
        return this.overtredingsgroep;
    }

    public Vthmelding overtredingsgroep(String overtredingsgroep) {
        this.setOvertredingsgroep(overtredingsgroep);
        return this;
    }

    public void setOvertredingsgroep(String overtredingsgroep) {
        this.overtredingsgroep = overtredingsgroep;
    }

    public String getReferentienummer() {
        return this.referentienummer;
    }

    public Vthmelding referentienummer(String referentienummer) {
        this.setReferentienummer(referentienummer);
        return this;
    }

    public void setReferentienummer(String referentienummer) {
        this.referentienummer = referentienummer;
    }

    public String getResultaat() {
        return this.resultaat;
    }

    public Vthmelding resultaat(String resultaat) {
        this.setResultaat(resultaat);
        return this;
    }

    public void setResultaat(String resultaat) {
        this.resultaat = resultaat;
    }

    public String getSoortvthmelding() {
        return this.soortvthmelding;
    }

    public Vthmelding soortvthmelding(String soortvthmelding) {
        this.setSoortvthmelding(soortvthmelding);
        return this;
    }

    public void setSoortvthmelding(String soortvthmelding) {
        this.soortvthmelding = soortvthmelding;
    }

    public String getStatus() {
        return this.status;
    }

    public Vthmelding status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStraatnaam() {
        return this.straatnaam;
    }

    public Vthmelding straatnaam(String straatnaam) {
        this.setStraatnaam(straatnaam);
        return this;
    }

    public void setStraatnaam(String straatnaam) {
        this.straatnaam = straatnaam;
    }

    public String getTaaktype() {
        return this.taaktype;
    }

    public Vthmelding taaktype(String taaktype) {
        this.setTaaktype(taaktype);
        return this;
    }

    public void setTaaktype(String taaktype) {
        this.taaktype = taaktype;
    }

    public String getZaaknummer() {
        return this.zaaknummer;
    }

    public Vthmelding zaaknummer(String zaaknummer) {
        this.setZaaknummer(zaaknummer);
        return this;
    }

    public void setZaaknummer(String zaaknummer) {
        this.zaaknummer = zaaknummer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vthmelding)) {
            return false;
        }
        return getId() != null && getId().equals(((Vthmelding) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vthmelding{" +
            "id=" + getId() +
            ", activiteit='" + getActiviteit() + "'" +
            ", beoordeling='" + getBeoordeling() + "'" +
            ", datumseponering='" + getDatumseponering() + "'" +
            ", datumtijdtot='" + getDatumtijdtot() + "'" +
            ", geseponeerd='" + getGeseponeerd() + "'" +
            ", locatie='" + getLocatie() + "'" +
            ", organisatieonderdeel='" + getOrganisatieonderdeel() + "'" +
            ", overtredingscode='" + getOvertredingscode() + "'" +
            ", overtredingsgroep='" + getOvertredingsgroep() + "'" +
            ", referentienummer='" + getReferentienummer() + "'" +
            ", resultaat='" + getResultaat() + "'" +
            ", soortvthmelding='" + getSoortvthmelding() + "'" +
            ", status='" + getStatus() + "'" +
            ", straatnaam='" + getStraatnaam() + "'" +
            ", taaktype='" + getTaaktype() + "'" +
            ", zaaknummer='" + getZaaknummer() + "'" +
            "}";
    }
}
