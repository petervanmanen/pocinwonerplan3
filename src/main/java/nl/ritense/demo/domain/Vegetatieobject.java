package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Vegetatieobject.
 */
@Entity
@Table(name = "vegetatieobject")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vegetatieobject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "afvoeren")
    private Boolean afvoeren;

    @Column(name = "bereikbaarheid")
    private String bereikbaarheid;

    @Column(name = "ecologischbeheer")
    private Boolean ecologischbeheer;

    @Column(name = "kwaliteitsniveauactueel")
    private String kwaliteitsniveauactueel;

    @Column(name = "kwaliteitsniveaugewenst")
    private String kwaliteitsniveaugewenst;

    @Column(name = "kweker")
    private String kweker;

    @Column(name = "leverancier")
    private String leverancier;

    @Size(max = 20)
    @Column(name = "eobjectnummer", length = 20)
    private String eobjectnummer;

    @Column(name = "soortnaam")
    private String soortnaam;

    @Column(name = "typestandplaats")
    private String typestandplaats;

    @Column(name = "typestandplaatsplus")
    private String typestandplaatsplus;

    @Column(name = "vegetatieobjectbereikbaarheidplus")
    private String vegetatieobjectbereikbaarheidplus;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vegetatieobject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAfvoeren() {
        return this.afvoeren;
    }

    public Vegetatieobject afvoeren(Boolean afvoeren) {
        this.setAfvoeren(afvoeren);
        return this;
    }

    public void setAfvoeren(Boolean afvoeren) {
        this.afvoeren = afvoeren;
    }

    public String getBereikbaarheid() {
        return this.bereikbaarheid;
    }

    public Vegetatieobject bereikbaarheid(String bereikbaarheid) {
        this.setBereikbaarheid(bereikbaarheid);
        return this;
    }

    public void setBereikbaarheid(String bereikbaarheid) {
        this.bereikbaarheid = bereikbaarheid;
    }

    public Boolean getEcologischbeheer() {
        return this.ecologischbeheer;
    }

    public Vegetatieobject ecologischbeheer(Boolean ecologischbeheer) {
        this.setEcologischbeheer(ecologischbeheer);
        return this;
    }

    public void setEcologischbeheer(Boolean ecologischbeheer) {
        this.ecologischbeheer = ecologischbeheer;
    }

    public String getKwaliteitsniveauactueel() {
        return this.kwaliteitsniveauactueel;
    }

    public Vegetatieobject kwaliteitsniveauactueel(String kwaliteitsniveauactueel) {
        this.setKwaliteitsniveauactueel(kwaliteitsniveauactueel);
        return this;
    }

    public void setKwaliteitsniveauactueel(String kwaliteitsniveauactueel) {
        this.kwaliteitsniveauactueel = kwaliteitsniveauactueel;
    }

    public String getKwaliteitsniveaugewenst() {
        return this.kwaliteitsniveaugewenst;
    }

    public Vegetatieobject kwaliteitsniveaugewenst(String kwaliteitsniveaugewenst) {
        this.setKwaliteitsniveaugewenst(kwaliteitsniveaugewenst);
        return this;
    }

    public void setKwaliteitsniveaugewenst(String kwaliteitsniveaugewenst) {
        this.kwaliteitsniveaugewenst = kwaliteitsniveaugewenst;
    }

    public String getKweker() {
        return this.kweker;
    }

    public Vegetatieobject kweker(String kweker) {
        this.setKweker(kweker);
        return this;
    }

    public void setKweker(String kweker) {
        this.kweker = kweker;
    }

    public String getLeverancier() {
        return this.leverancier;
    }

    public Vegetatieobject leverancier(String leverancier) {
        this.setLeverancier(leverancier);
        return this;
    }

    public void setLeverancier(String leverancier) {
        this.leverancier = leverancier;
    }

    public String getEobjectnummer() {
        return this.eobjectnummer;
    }

    public Vegetatieobject eobjectnummer(String eobjectnummer) {
        this.setEobjectnummer(eobjectnummer);
        return this;
    }

    public void setEobjectnummer(String eobjectnummer) {
        this.eobjectnummer = eobjectnummer;
    }

    public String getSoortnaam() {
        return this.soortnaam;
    }

    public Vegetatieobject soortnaam(String soortnaam) {
        this.setSoortnaam(soortnaam);
        return this;
    }

    public void setSoortnaam(String soortnaam) {
        this.soortnaam = soortnaam;
    }

    public String getTypestandplaats() {
        return this.typestandplaats;
    }

    public Vegetatieobject typestandplaats(String typestandplaats) {
        this.setTypestandplaats(typestandplaats);
        return this;
    }

    public void setTypestandplaats(String typestandplaats) {
        this.typestandplaats = typestandplaats;
    }

    public String getTypestandplaatsplus() {
        return this.typestandplaatsplus;
    }

    public Vegetatieobject typestandplaatsplus(String typestandplaatsplus) {
        this.setTypestandplaatsplus(typestandplaatsplus);
        return this;
    }

    public void setTypestandplaatsplus(String typestandplaatsplus) {
        this.typestandplaatsplus = typestandplaatsplus;
    }

    public String getVegetatieobjectbereikbaarheidplus() {
        return this.vegetatieobjectbereikbaarheidplus;
    }

    public Vegetatieobject vegetatieobjectbereikbaarheidplus(String vegetatieobjectbereikbaarheidplus) {
        this.setVegetatieobjectbereikbaarheidplus(vegetatieobjectbereikbaarheidplus);
        return this;
    }

    public void setVegetatieobjectbereikbaarheidplus(String vegetatieobjectbereikbaarheidplus) {
        this.vegetatieobjectbereikbaarheidplus = vegetatieobjectbereikbaarheidplus;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vegetatieobject)) {
            return false;
        }
        return getId() != null && getId().equals(((Vegetatieobject) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vegetatieobject{" +
            "id=" + getId() +
            ", afvoeren='" + getAfvoeren() + "'" +
            ", bereikbaarheid='" + getBereikbaarheid() + "'" +
            ", ecologischbeheer='" + getEcologischbeheer() + "'" +
            ", kwaliteitsniveauactueel='" + getKwaliteitsniveauactueel() + "'" +
            ", kwaliteitsniveaugewenst='" + getKwaliteitsniveaugewenst() + "'" +
            ", kweker='" + getKweker() + "'" +
            ", leverancier='" + getLeverancier() + "'" +
            ", eobjectnummer='" + getEobjectnummer() + "'" +
            ", soortnaam='" + getSoortnaam() + "'" +
            ", typestandplaats='" + getTypestandplaats() + "'" +
            ", typestandplaatsplus='" + getTypestandplaatsplus() + "'" +
            ", vegetatieobjectbereikbaarheidplus='" + getVegetatieobjectbereikbaarheidplus() + "'" +
            "}";
    }
}
