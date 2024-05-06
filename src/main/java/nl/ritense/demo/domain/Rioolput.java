package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Rioolput.
 */
@Entity
@Table(name = "rioolput")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Rioolput implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aantalbedrijven")
    private String aantalbedrijven;

    @Column(name = "aantalrecreatie")
    private String aantalrecreatie;

    @Column(name = "aantalwoningen")
    private String aantalwoningen;

    @Column(name = "afvoerendoppervlak")
    private String afvoerendoppervlak;

    @Column(name = "bergendoppervlak")
    private String bergendoppervlak;

    @Column(name = "rioolputconstructieonderdeel")
    private String rioolputconstructieonderdeel;

    @Column(name = "rioolputrioolleiding")
    private String rioolputrioolleiding;

    @Column(name = "risicogebied")
    private String risicogebied;

    @Column(name = "toegangbreedte")
    private String toegangbreedte;

    @Column(name = "toeganglengte")
    private String toeganglengte;

    @Column(name = "type")
    private String type;

    @Column(name = "typeplus")
    private String typeplus;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Rioolput id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAantalbedrijven() {
        return this.aantalbedrijven;
    }

    public Rioolput aantalbedrijven(String aantalbedrijven) {
        this.setAantalbedrijven(aantalbedrijven);
        return this;
    }

    public void setAantalbedrijven(String aantalbedrijven) {
        this.aantalbedrijven = aantalbedrijven;
    }

    public String getAantalrecreatie() {
        return this.aantalrecreatie;
    }

    public Rioolput aantalrecreatie(String aantalrecreatie) {
        this.setAantalrecreatie(aantalrecreatie);
        return this;
    }

    public void setAantalrecreatie(String aantalrecreatie) {
        this.aantalrecreatie = aantalrecreatie;
    }

    public String getAantalwoningen() {
        return this.aantalwoningen;
    }

    public Rioolput aantalwoningen(String aantalwoningen) {
        this.setAantalwoningen(aantalwoningen);
        return this;
    }

    public void setAantalwoningen(String aantalwoningen) {
        this.aantalwoningen = aantalwoningen;
    }

    public String getAfvoerendoppervlak() {
        return this.afvoerendoppervlak;
    }

    public Rioolput afvoerendoppervlak(String afvoerendoppervlak) {
        this.setAfvoerendoppervlak(afvoerendoppervlak);
        return this;
    }

    public void setAfvoerendoppervlak(String afvoerendoppervlak) {
        this.afvoerendoppervlak = afvoerendoppervlak;
    }

    public String getBergendoppervlak() {
        return this.bergendoppervlak;
    }

    public Rioolput bergendoppervlak(String bergendoppervlak) {
        this.setBergendoppervlak(bergendoppervlak);
        return this;
    }

    public void setBergendoppervlak(String bergendoppervlak) {
        this.bergendoppervlak = bergendoppervlak;
    }

    public String getRioolputconstructieonderdeel() {
        return this.rioolputconstructieonderdeel;
    }

    public Rioolput rioolputconstructieonderdeel(String rioolputconstructieonderdeel) {
        this.setRioolputconstructieonderdeel(rioolputconstructieonderdeel);
        return this;
    }

    public void setRioolputconstructieonderdeel(String rioolputconstructieonderdeel) {
        this.rioolputconstructieonderdeel = rioolputconstructieonderdeel;
    }

    public String getRioolputrioolleiding() {
        return this.rioolputrioolleiding;
    }

    public Rioolput rioolputrioolleiding(String rioolputrioolleiding) {
        this.setRioolputrioolleiding(rioolputrioolleiding);
        return this;
    }

    public void setRioolputrioolleiding(String rioolputrioolleiding) {
        this.rioolputrioolleiding = rioolputrioolleiding;
    }

    public String getRisicogebied() {
        return this.risicogebied;
    }

    public Rioolput risicogebied(String risicogebied) {
        this.setRisicogebied(risicogebied);
        return this;
    }

    public void setRisicogebied(String risicogebied) {
        this.risicogebied = risicogebied;
    }

    public String getToegangbreedte() {
        return this.toegangbreedte;
    }

    public Rioolput toegangbreedte(String toegangbreedte) {
        this.setToegangbreedte(toegangbreedte);
        return this;
    }

    public void setToegangbreedte(String toegangbreedte) {
        this.toegangbreedte = toegangbreedte;
    }

    public String getToeganglengte() {
        return this.toeganglengte;
    }

    public Rioolput toeganglengte(String toeganglengte) {
        this.setToeganglengte(toeganglengte);
        return this;
    }

    public void setToeganglengte(String toeganglengte) {
        this.toeganglengte = toeganglengte;
    }

    public String getType() {
        return this.type;
    }

    public Rioolput type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeplus() {
        return this.typeplus;
    }

    public Rioolput typeplus(String typeplus) {
        this.setTypeplus(typeplus);
        return this;
    }

    public void setTypeplus(String typeplus) {
        this.typeplus = typeplus;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rioolput)) {
            return false;
        }
        return getId() != null && getId().equals(((Rioolput) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rioolput{" +
            "id=" + getId() +
            ", aantalbedrijven='" + getAantalbedrijven() + "'" +
            ", aantalrecreatie='" + getAantalrecreatie() + "'" +
            ", aantalwoningen='" + getAantalwoningen() + "'" +
            ", afvoerendoppervlak='" + getAfvoerendoppervlak() + "'" +
            ", bergendoppervlak='" + getBergendoppervlak() + "'" +
            ", rioolputconstructieonderdeel='" + getRioolputconstructieonderdeel() + "'" +
            ", rioolputrioolleiding='" + getRioolputrioolleiding() + "'" +
            ", risicogebied='" + getRisicogebied() + "'" +
            ", toegangbreedte='" + getToegangbreedte() + "'" +
            ", toeganglengte='" + getToeganglengte() + "'" +
            ", type='" + getType() + "'" +
            ", typeplus='" + getTypeplus() + "'" +
            "}";
    }
}
