package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Formuliersoortveld.
 */
@Entity
@Table(name = "formuliersoortveld")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Formuliersoortveld implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "helptekst")
    private String helptekst;

    @Column(name = "isverplicht")
    private Boolean isverplicht;

    @Column(name = "label")
    private String label;

    @Column(name = "maxlengte")
    private String maxlengte;

    @Column(name = "veldnaam")
    private String veldnaam;

    @Column(name = "veldtype")
    private String veldtype;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftveldenFormuliersoortvelds", "isaanleidingvoorZaaktypes" }, allowSetters = true)
    private Formuliersoort heeftveldenFormuliersoort;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "isconformFormuliersoortveld")
    @JsonIgnoreProperties(value = { "isconformFormuliersoortveld" }, allowSetters = true)
    private Set<Aanvraagdata> isconformAanvraagdata = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Formuliersoortveld id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHelptekst() {
        return this.helptekst;
    }

    public Formuliersoortveld helptekst(String helptekst) {
        this.setHelptekst(helptekst);
        return this;
    }

    public void setHelptekst(String helptekst) {
        this.helptekst = helptekst;
    }

    public Boolean getIsverplicht() {
        return this.isverplicht;
    }

    public Formuliersoortveld isverplicht(Boolean isverplicht) {
        this.setIsverplicht(isverplicht);
        return this;
    }

    public void setIsverplicht(Boolean isverplicht) {
        this.isverplicht = isverplicht;
    }

    public String getLabel() {
        return this.label;
    }

    public Formuliersoortveld label(String label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getMaxlengte() {
        return this.maxlengte;
    }

    public Formuliersoortveld maxlengte(String maxlengte) {
        this.setMaxlengte(maxlengte);
        return this;
    }

    public void setMaxlengte(String maxlengte) {
        this.maxlengte = maxlengte;
    }

    public String getVeldnaam() {
        return this.veldnaam;
    }

    public Formuliersoortveld veldnaam(String veldnaam) {
        this.setVeldnaam(veldnaam);
        return this;
    }

    public void setVeldnaam(String veldnaam) {
        this.veldnaam = veldnaam;
    }

    public String getVeldtype() {
        return this.veldtype;
    }

    public Formuliersoortveld veldtype(String veldtype) {
        this.setVeldtype(veldtype);
        return this;
    }

    public void setVeldtype(String veldtype) {
        this.veldtype = veldtype;
    }

    public Formuliersoort getHeeftveldenFormuliersoort() {
        return this.heeftveldenFormuliersoort;
    }

    public void setHeeftveldenFormuliersoort(Formuliersoort formuliersoort) {
        this.heeftveldenFormuliersoort = formuliersoort;
    }

    public Formuliersoortveld heeftveldenFormuliersoort(Formuliersoort formuliersoort) {
        this.setHeeftveldenFormuliersoort(formuliersoort);
        return this;
    }

    public Set<Aanvraagdata> getIsconformAanvraagdata() {
        return this.isconformAanvraagdata;
    }

    public void setIsconformAanvraagdata(Set<Aanvraagdata> aanvraagdata) {
        if (this.isconformAanvraagdata != null) {
            this.isconformAanvraagdata.forEach(i -> i.setIsconformFormuliersoortveld(null));
        }
        if (aanvraagdata != null) {
            aanvraagdata.forEach(i -> i.setIsconformFormuliersoortveld(this));
        }
        this.isconformAanvraagdata = aanvraagdata;
    }

    public Formuliersoortveld isconformAanvraagdata(Set<Aanvraagdata> aanvraagdata) {
        this.setIsconformAanvraagdata(aanvraagdata);
        return this;
    }

    public Formuliersoortveld addIsconformAanvraagdata(Aanvraagdata aanvraagdata) {
        this.isconformAanvraagdata.add(aanvraagdata);
        aanvraagdata.setIsconformFormuliersoortveld(this);
        return this;
    }

    public Formuliersoortveld removeIsconformAanvraagdata(Aanvraagdata aanvraagdata) {
        this.isconformAanvraagdata.remove(aanvraagdata);
        aanvraagdata.setIsconformFormuliersoortveld(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Formuliersoortveld)) {
            return false;
        }
        return getId() != null && getId().equals(((Formuliersoortveld) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Formuliersoortveld{" +
            "id=" + getId() +
            ", helptekst='" + getHelptekst() + "'" +
            ", isverplicht='" + getIsverplicht() + "'" +
            ", label='" + getLabel() + "'" +
            ", maxlengte='" + getMaxlengte() + "'" +
            ", veldnaam='" + getVeldnaam() + "'" +
            ", veldtype='" + getVeldtype() + "'" +
            "}";
    }
}
