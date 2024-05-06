package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Installatie.
 */
@Entity
@Table(name = "installatie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Installatie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "breedte")
    private String breedte;

    @Column(name = "eancode")
    private String eancode;

    @Column(name = "fabrikant")
    private String fabrikant;

    @Column(name = "hoogte")
    private String hoogte;

    @Column(name = "inbelgegevens")
    private String inbelgegevens;

    @Column(name = "installateur")
    private String installateur;

    @Column(name = "jaaronderhouduitgevoerd")
    private String jaaronderhouduitgevoerd;

    @Column(name = "lengte")
    private String lengte;

    @Column(name = "leverancier")
    private String leverancier;

    @Column(name = "typecommunicatie")
    private String typecommunicatie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Installatie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBreedte() {
        return this.breedte;
    }

    public Installatie breedte(String breedte) {
        this.setBreedte(breedte);
        return this;
    }

    public void setBreedte(String breedte) {
        this.breedte = breedte;
    }

    public String getEancode() {
        return this.eancode;
    }

    public Installatie eancode(String eancode) {
        this.setEancode(eancode);
        return this;
    }

    public void setEancode(String eancode) {
        this.eancode = eancode;
    }

    public String getFabrikant() {
        return this.fabrikant;
    }

    public Installatie fabrikant(String fabrikant) {
        this.setFabrikant(fabrikant);
        return this;
    }

    public void setFabrikant(String fabrikant) {
        this.fabrikant = fabrikant;
    }

    public String getHoogte() {
        return this.hoogte;
    }

    public Installatie hoogte(String hoogte) {
        this.setHoogte(hoogte);
        return this;
    }

    public void setHoogte(String hoogte) {
        this.hoogte = hoogte;
    }

    public String getInbelgegevens() {
        return this.inbelgegevens;
    }

    public Installatie inbelgegevens(String inbelgegevens) {
        this.setInbelgegevens(inbelgegevens);
        return this;
    }

    public void setInbelgegevens(String inbelgegevens) {
        this.inbelgegevens = inbelgegevens;
    }

    public String getInstallateur() {
        return this.installateur;
    }

    public Installatie installateur(String installateur) {
        this.setInstallateur(installateur);
        return this;
    }

    public void setInstallateur(String installateur) {
        this.installateur = installateur;
    }

    public String getJaaronderhouduitgevoerd() {
        return this.jaaronderhouduitgevoerd;
    }

    public Installatie jaaronderhouduitgevoerd(String jaaronderhouduitgevoerd) {
        this.setJaaronderhouduitgevoerd(jaaronderhouduitgevoerd);
        return this;
    }

    public void setJaaronderhouduitgevoerd(String jaaronderhouduitgevoerd) {
        this.jaaronderhouduitgevoerd = jaaronderhouduitgevoerd;
    }

    public String getLengte() {
        return this.lengte;
    }

    public Installatie lengte(String lengte) {
        this.setLengte(lengte);
        return this;
    }

    public void setLengte(String lengte) {
        this.lengte = lengte;
    }

    public String getLeverancier() {
        return this.leverancier;
    }

    public Installatie leverancier(String leverancier) {
        this.setLeverancier(leverancier);
        return this;
    }

    public void setLeverancier(String leverancier) {
        this.leverancier = leverancier;
    }

    public String getTypecommunicatie() {
        return this.typecommunicatie;
    }

    public Installatie typecommunicatie(String typecommunicatie) {
        this.setTypecommunicatie(typecommunicatie);
        return this;
    }

    public void setTypecommunicatie(String typecommunicatie) {
        this.typecommunicatie = typecommunicatie;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Installatie)) {
            return false;
        }
        return getId() != null && getId().equals(((Installatie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Installatie{" +
            "id=" + getId() +
            ", breedte='" + getBreedte() + "'" +
            ", eancode='" + getEancode() + "'" +
            ", fabrikant='" + getFabrikant() + "'" +
            ", hoogte='" + getHoogte() + "'" +
            ", inbelgegevens='" + getInbelgegevens() + "'" +
            ", installateur='" + getInstallateur() + "'" +
            ", jaaronderhouduitgevoerd='" + getJaaronderhouduitgevoerd() + "'" +
            ", lengte='" + getLengte() + "'" +
            ", leverancier='" + getLeverancier() + "'" +
            ", typecommunicatie='" + getTypecommunicatie() + "'" +
            "}";
    }
}
