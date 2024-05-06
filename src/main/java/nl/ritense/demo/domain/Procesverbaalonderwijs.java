package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Procesverbaalonderwijs.
 */
@Entity
@Table(name = "procesverbaalonderwijs")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Procesverbaalonderwijs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumafgehandeld")
    private LocalDate datumafgehandeld;

    @Column(name = "datumeindeproeftijd")
    private LocalDate datumeindeproeftijd;

    @Column(name = "datumingelicht")
    private LocalDate datumingelicht;

    @Column(name = "datumuitspraak")
    private LocalDate datumuitspraak;

    @Column(name = "datumzitting")
    private LocalDate datumzitting;

    @Column(name = "geldboete", precision = 21, scale = 2)
    private BigDecimal geldboete;

    @Column(name = "geldboetevoorwaardelijk")
    private String geldboetevoorwaardelijk;

    @Column(name = "opmerkingen")
    private String opmerkingen;

    @Column(name = "proeftijd")
    private String proeftijd;

    @Column(name = "reden")
    private String reden;

    @Column(name = "sanctiesoort")
    private String sanctiesoort;

    @Column(name = "uitspraak")
    private String uitspraak;

    @Column(name = "verzuimsoort")
    private String verzuimsoort;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Procesverbaalonderwijs id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumafgehandeld() {
        return this.datumafgehandeld;
    }

    public Procesverbaalonderwijs datumafgehandeld(LocalDate datumafgehandeld) {
        this.setDatumafgehandeld(datumafgehandeld);
        return this;
    }

    public void setDatumafgehandeld(LocalDate datumafgehandeld) {
        this.datumafgehandeld = datumafgehandeld;
    }

    public LocalDate getDatumeindeproeftijd() {
        return this.datumeindeproeftijd;
    }

    public Procesverbaalonderwijs datumeindeproeftijd(LocalDate datumeindeproeftijd) {
        this.setDatumeindeproeftijd(datumeindeproeftijd);
        return this;
    }

    public void setDatumeindeproeftijd(LocalDate datumeindeproeftijd) {
        this.datumeindeproeftijd = datumeindeproeftijd;
    }

    public LocalDate getDatumingelicht() {
        return this.datumingelicht;
    }

    public Procesverbaalonderwijs datumingelicht(LocalDate datumingelicht) {
        this.setDatumingelicht(datumingelicht);
        return this;
    }

    public void setDatumingelicht(LocalDate datumingelicht) {
        this.datumingelicht = datumingelicht;
    }

    public LocalDate getDatumuitspraak() {
        return this.datumuitspraak;
    }

    public Procesverbaalonderwijs datumuitspraak(LocalDate datumuitspraak) {
        this.setDatumuitspraak(datumuitspraak);
        return this;
    }

    public void setDatumuitspraak(LocalDate datumuitspraak) {
        this.datumuitspraak = datumuitspraak;
    }

    public LocalDate getDatumzitting() {
        return this.datumzitting;
    }

    public Procesverbaalonderwijs datumzitting(LocalDate datumzitting) {
        this.setDatumzitting(datumzitting);
        return this;
    }

    public void setDatumzitting(LocalDate datumzitting) {
        this.datumzitting = datumzitting;
    }

    public BigDecimal getGeldboete() {
        return this.geldboete;
    }

    public Procesverbaalonderwijs geldboete(BigDecimal geldboete) {
        this.setGeldboete(geldboete);
        return this;
    }

    public void setGeldboete(BigDecimal geldboete) {
        this.geldboete = geldboete;
    }

    public String getGeldboetevoorwaardelijk() {
        return this.geldboetevoorwaardelijk;
    }

    public Procesverbaalonderwijs geldboetevoorwaardelijk(String geldboetevoorwaardelijk) {
        this.setGeldboetevoorwaardelijk(geldboetevoorwaardelijk);
        return this;
    }

    public void setGeldboetevoorwaardelijk(String geldboetevoorwaardelijk) {
        this.geldboetevoorwaardelijk = geldboetevoorwaardelijk;
    }

    public String getOpmerkingen() {
        return this.opmerkingen;
    }

    public Procesverbaalonderwijs opmerkingen(String opmerkingen) {
        this.setOpmerkingen(opmerkingen);
        return this;
    }

    public void setOpmerkingen(String opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public String getProeftijd() {
        return this.proeftijd;
    }

    public Procesverbaalonderwijs proeftijd(String proeftijd) {
        this.setProeftijd(proeftijd);
        return this;
    }

    public void setProeftijd(String proeftijd) {
        this.proeftijd = proeftijd;
    }

    public String getReden() {
        return this.reden;
    }

    public Procesverbaalonderwijs reden(String reden) {
        this.setReden(reden);
        return this;
    }

    public void setReden(String reden) {
        this.reden = reden;
    }

    public String getSanctiesoort() {
        return this.sanctiesoort;
    }

    public Procesverbaalonderwijs sanctiesoort(String sanctiesoort) {
        this.setSanctiesoort(sanctiesoort);
        return this;
    }

    public void setSanctiesoort(String sanctiesoort) {
        this.sanctiesoort = sanctiesoort;
    }

    public String getUitspraak() {
        return this.uitspraak;
    }

    public Procesverbaalonderwijs uitspraak(String uitspraak) {
        this.setUitspraak(uitspraak);
        return this;
    }

    public void setUitspraak(String uitspraak) {
        this.uitspraak = uitspraak;
    }

    public String getVerzuimsoort() {
        return this.verzuimsoort;
    }

    public Procesverbaalonderwijs verzuimsoort(String verzuimsoort) {
        this.setVerzuimsoort(verzuimsoort);
        return this;
    }

    public void setVerzuimsoort(String verzuimsoort) {
        this.verzuimsoort = verzuimsoort;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Procesverbaalonderwijs)) {
            return false;
        }
        return getId() != null && getId().equals(((Procesverbaalonderwijs) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Procesverbaalonderwijs{" +
            "id=" + getId() +
            ", datumafgehandeld='" + getDatumafgehandeld() + "'" +
            ", datumeindeproeftijd='" + getDatumeindeproeftijd() + "'" +
            ", datumingelicht='" + getDatumingelicht() + "'" +
            ", datumuitspraak='" + getDatumuitspraak() + "'" +
            ", datumzitting='" + getDatumzitting() + "'" +
            ", geldboete=" + getGeldboete() +
            ", geldboetevoorwaardelijk='" + getGeldboetevoorwaardelijk() + "'" +
            ", opmerkingen='" + getOpmerkingen() + "'" +
            ", proeftijd='" + getProeftijd() + "'" +
            ", reden='" + getReden() + "'" +
            ", sanctiesoort='" + getSanctiesoort() + "'" +
            ", uitspraak='" + getUitspraak() + "'" +
            ", verzuimsoort='" + getVerzuimsoort() + "'" +
            "}";
    }
}
