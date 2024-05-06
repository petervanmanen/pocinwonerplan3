package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Leiding.
 */
@Entity
@Table(name = "leiding")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Leiding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "afwijkendedieptelegging")
    private String afwijkendedieptelegging;

    @Column(name = "breedte")
    private String breedte;

    @Column(name = "diameter")
    private String diameter;

    @Column(name = "diepte")
    private String diepte;

    @Column(name = "eisvoorzorgsmaatregel")
    private String eisvoorzorgsmaatregel;

    @Column(name = "geonauwkeurigheidxy")
    private String geonauwkeurigheidxy;

    @Column(name = "hoogte")
    private String hoogte;

    @Column(name = "jaaronderhouduitgevoerd")
    private String jaaronderhouduitgevoerd;

    @Column(name = "lengte")
    private String lengte;

    @Column(name = "leverancier")
    private String leverancier;

    @Column(name = "materiaal")
    private String materiaal;

    @Column(name = "themaimkl")
    private String themaimkl;

    @Column(name = "verhoogdrisico")
    private String verhoogdrisico;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Leiding id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAfwijkendedieptelegging() {
        return this.afwijkendedieptelegging;
    }

    public Leiding afwijkendedieptelegging(String afwijkendedieptelegging) {
        this.setAfwijkendedieptelegging(afwijkendedieptelegging);
        return this;
    }

    public void setAfwijkendedieptelegging(String afwijkendedieptelegging) {
        this.afwijkendedieptelegging = afwijkendedieptelegging;
    }

    public String getBreedte() {
        return this.breedte;
    }

    public Leiding breedte(String breedte) {
        this.setBreedte(breedte);
        return this;
    }

    public void setBreedte(String breedte) {
        this.breedte = breedte;
    }

    public String getDiameter() {
        return this.diameter;
    }

    public Leiding diameter(String diameter) {
        this.setDiameter(diameter);
        return this;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getDiepte() {
        return this.diepte;
    }

    public Leiding diepte(String diepte) {
        this.setDiepte(diepte);
        return this;
    }

    public void setDiepte(String diepte) {
        this.diepte = diepte;
    }

    public String getEisvoorzorgsmaatregel() {
        return this.eisvoorzorgsmaatregel;
    }

    public Leiding eisvoorzorgsmaatregel(String eisvoorzorgsmaatregel) {
        this.setEisvoorzorgsmaatregel(eisvoorzorgsmaatregel);
        return this;
    }

    public void setEisvoorzorgsmaatregel(String eisvoorzorgsmaatregel) {
        this.eisvoorzorgsmaatregel = eisvoorzorgsmaatregel;
    }

    public String getGeonauwkeurigheidxy() {
        return this.geonauwkeurigheidxy;
    }

    public Leiding geonauwkeurigheidxy(String geonauwkeurigheidxy) {
        this.setGeonauwkeurigheidxy(geonauwkeurigheidxy);
        return this;
    }

    public void setGeonauwkeurigheidxy(String geonauwkeurigheidxy) {
        this.geonauwkeurigheidxy = geonauwkeurigheidxy;
    }

    public String getHoogte() {
        return this.hoogte;
    }

    public Leiding hoogte(String hoogte) {
        this.setHoogte(hoogte);
        return this;
    }

    public void setHoogte(String hoogte) {
        this.hoogte = hoogte;
    }

    public String getJaaronderhouduitgevoerd() {
        return this.jaaronderhouduitgevoerd;
    }

    public Leiding jaaronderhouduitgevoerd(String jaaronderhouduitgevoerd) {
        this.setJaaronderhouduitgevoerd(jaaronderhouduitgevoerd);
        return this;
    }

    public void setJaaronderhouduitgevoerd(String jaaronderhouduitgevoerd) {
        this.jaaronderhouduitgevoerd = jaaronderhouduitgevoerd;
    }

    public String getLengte() {
        return this.lengte;
    }

    public Leiding lengte(String lengte) {
        this.setLengte(lengte);
        return this;
    }

    public void setLengte(String lengte) {
        this.lengte = lengte;
    }

    public String getLeverancier() {
        return this.leverancier;
    }

    public Leiding leverancier(String leverancier) {
        this.setLeverancier(leverancier);
        return this;
    }

    public void setLeverancier(String leverancier) {
        this.leverancier = leverancier;
    }

    public String getMateriaal() {
        return this.materiaal;
    }

    public Leiding materiaal(String materiaal) {
        this.setMateriaal(materiaal);
        return this;
    }

    public void setMateriaal(String materiaal) {
        this.materiaal = materiaal;
    }

    public String getThemaimkl() {
        return this.themaimkl;
    }

    public Leiding themaimkl(String themaimkl) {
        this.setThemaimkl(themaimkl);
        return this;
    }

    public void setThemaimkl(String themaimkl) {
        this.themaimkl = themaimkl;
    }

    public String getVerhoogdrisico() {
        return this.verhoogdrisico;
    }

    public Leiding verhoogdrisico(String verhoogdrisico) {
        this.setVerhoogdrisico(verhoogdrisico);
        return this;
    }

    public void setVerhoogdrisico(String verhoogdrisico) {
        this.verhoogdrisico = verhoogdrisico;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Leiding)) {
            return false;
        }
        return getId() != null && getId().equals(((Leiding) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Leiding{" +
            "id=" + getId() +
            ", afwijkendedieptelegging='" + getAfwijkendedieptelegging() + "'" +
            ", breedte='" + getBreedte() + "'" +
            ", diameter='" + getDiameter() + "'" +
            ", diepte='" + getDiepte() + "'" +
            ", eisvoorzorgsmaatregel='" + getEisvoorzorgsmaatregel() + "'" +
            ", geonauwkeurigheidxy='" + getGeonauwkeurigheidxy() + "'" +
            ", hoogte='" + getHoogte() + "'" +
            ", jaaronderhouduitgevoerd='" + getJaaronderhouduitgevoerd() + "'" +
            ", lengte='" + getLengte() + "'" +
            ", leverancier='" + getLeverancier() + "'" +
            ", materiaal='" + getMateriaal() + "'" +
            ", themaimkl='" + getThemaimkl() + "'" +
            ", verhoogdrisico='" + getVerhoogdrisico() + "'" +
            "}";
    }
}
