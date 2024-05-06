package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Aanbestedinginhuur.
 */
@Entity
@Table(name = "aanbestedinginhuur")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Aanbestedinginhuur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aanvraaggesloten")
    private String aanvraaggesloten;

    @Column(name = "aanvraagnummer")
    private String aanvraagnummer;

    @Column(name = "datumcreatie")
    private String datumcreatie;

    @Column(name = "datumopeningkluis")
    private String datumopeningkluis;

    @Column(name = "datumsluiting")
    private String datumsluiting;

    @Column(name = "datumverzending")
    private String datumverzending;

    @Column(name = "fase")
    private String fase;

    @Column(name = "hoogstetarief")
    private String hoogstetarief;

    @Column(name = "laagstetarief")
    private String laagstetarief;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "perceel")
    private String perceel;

    @Column(name = "procedure")
    private String procedure;

    @Column(name = "projectnaam")
    private String projectnaam;

    @Column(name = "projectreferentie")
    private String projectreferentie;

    @Column(name = "publicatie")
    private String publicatie;

    @Column(name = "referentie")
    private String referentie;

    @Column(name = "status")
    private String status;

    @Column(name = "titel")
    private String titel;

    @Column(name = "type")
    private String type;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Aanbestedinginhuur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAanvraaggesloten() {
        return this.aanvraaggesloten;
    }

    public Aanbestedinginhuur aanvraaggesloten(String aanvraaggesloten) {
        this.setAanvraaggesloten(aanvraaggesloten);
        return this;
    }

    public void setAanvraaggesloten(String aanvraaggesloten) {
        this.aanvraaggesloten = aanvraaggesloten;
    }

    public String getAanvraagnummer() {
        return this.aanvraagnummer;
    }

    public Aanbestedinginhuur aanvraagnummer(String aanvraagnummer) {
        this.setAanvraagnummer(aanvraagnummer);
        return this;
    }

    public void setAanvraagnummer(String aanvraagnummer) {
        this.aanvraagnummer = aanvraagnummer;
    }

    public String getDatumcreatie() {
        return this.datumcreatie;
    }

    public Aanbestedinginhuur datumcreatie(String datumcreatie) {
        this.setDatumcreatie(datumcreatie);
        return this;
    }

    public void setDatumcreatie(String datumcreatie) {
        this.datumcreatie = datumcreatie;
    }

    public String getDatumopeningkluis() {
        return this.datumopeningkluis;
    }

    public Aanbestedinginhuur datumopeningkluis(String datumopeningkluis) {
        this.setDatumopeningkluis(datumopeningkluis);
        return this;
    }

    public void setDatumopeningkluis(String datumopeningkluis) {
        this.datumopeningkluis = datumopeningkluis;
    }

    public String getDatumsluiting() {
        return this.datumsluiting;
    }

    public Aanbestedinginhuur datumsluiting(String datumsluiting) {
        this.setDatumsluiting(datumsluiting);
        return this;
    }

    public void setDatumsluiting(String datumsluiting) {
        this.datumsluiting = datumsluiting;
    }

    public String getDatumverzending() {
        return this.datumverzending;
    }

    public Aanbestedinginhuur datumverzending(String datumverzending) {
        this.setDatumverzending(datumverzending);
        return this;
    }

    public void setDatumverzending(String datumverzending) {
        this.datumverzending = datumverzending;
    }

    public String getFase() {
        return this.fase;
    }

    public Aanbestedinginhuur fase(String fase) {
        this.setFase(fase);
        return this;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public String getHoogstetarief() {
        return this.hoogstetarief;
    }

    public Aanbestedinginhuur hoogstetarief(String hoogstetarief) {
        this.setHoogstetarief(hoogstetarief);
        return this;
    }

    public void setHoogstetarief(String hoogstetarief) {
        this.hoogstetarief = hoogstetarief;
    }

    public String getLaagstetarief() {
        return this.laagstetarief;
    }

    public Aanbestedinginhuur laagstetarief(String laagstetarief) {
        this.setLaagstetarief(laagstetarief);
        return this;
    }

    public void setLaagstetarief(String laagstetarief) {
        this.laagstetarief = laagstetarief;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Aanbestedinginhuur omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getPerceel() {
        return this.perceel;
    }

    public Aanbestedinginhuur perceel(String perceel) {
        this.setPerceel(perceel);
        return this;
    }

    public void setPerceel(String perceel) {
        this.perceel = perceel;
    }

    public String getProcedure() {
        return this.procedure;
    }

    public Aanbestedinginhuur procedure(String procedure) {
        this.setProcedure(procedure);
        return this;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public String getProjectnaam() {
        return this.projectnaam;
    }

    public Aanbestedinginhuur projectnaam(String projectnaam) {
        this.setProjectnaam(projectnaam);
        return this;
    }

    public void setProjectnaam(String projectnaam) {
        this.projectnaam = projectnaam;
    }

    public String getProjectreferentie() {
        return this.projectreferentie;
    }

    public Aanbestedinginhuur projectreferentie(String projectreferentie) {
        this.setProjectreferentie(projectreferentie);
        return this;
    }

    public void setProjectreferentie(String projectreferentie) {
        this.projectreferentie = projectreferentie;
    }

    public String getPublicatie() {
        return this.publicatie;
    }

    public Aanbestedinginhuur publicatie(String publicatie) {
        this.setPublicatie(publicatie);
        return this;
    }

    public void setPublicatie(String publicatie) {
        this.publicatie = publicatie;
    }

    public String getReferentie() {
        return this.referentie;
    }

    public Aanbestedinginhuur referentie(String referentie) {
        this.setReferentie(referentie);
        return this;
    }

    public void setReferentie(String referentie) {
        this.referentie = referentie;
    }

    public String getStatus() {
        return this.status;
    }

    public Aanbestedinginhuur status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitel() {
        return this.titel;
    }

    public Aanbestedinginhuur titel(String titel) {
        this.setTitel(titel);
        return this;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getType() {
        return this.type;
    }

    public Aanbestedinginhuur type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aanbestedinginhuur)) {
            return false;
        }
        return getId() != null && getId().equals(((Aanbestedinginhuur) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Aanbestedinginhuur{" +
            "id=" + getId() +
            ", aanvraaggesloten='" + getAanvraaggesloten() + "'" +
            ", aanvraagnummer='" + getAanvraagnummer() + "'" +
            ", datumcreatie='" + getDatumcreatie() + "'" +
            ", datumopeningkluis='" + getDatumopeningkluis() + "'" +
            ", datumsluiting='" + getDatumsluiting() + "'" +
            ", datumverzending='" + getDatumverzending() + "'" +
            ", fase='" + getFase() + "'" +
            ", hoogstetarief='" + getHoogstetarief() + "'" +
            ", laagstetarief='" + getLaagstetarief() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", perceel='" + getPerceel() + "'" +
            ", procedure='" + getProcedure() + "'" +
            ", projectnaam='" + getProjectnaam() + "'" +
            ", projectreferentie='" + getProjectreferentie() + "'" +
            ", publicatie='" + getPublicatie() + "'" +
            ", referentie='" + getReferentie() + "'" +
            ", status='" + getStatus() + "'" +
            ", titel='" + getTitel() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
