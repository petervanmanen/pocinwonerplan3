package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Leidingelement.
 */
@Entity
@Table(name = "leidingelement")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Leidingelement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "afwijkendedieptelegging")
    private String afwijkendedieptelegging;

    @Column(name = "diepte")
    private String diepte;

    @Column(name = "geonauwkeurigheidxy")
    private String geonauwkeurigheidxy;

    @Column(name = "jaaronderhouduitgevoerd")
    private String jaaronderhouduitgevoerd;

    @Column(name = "leverancier")
    private String leverancier;

    @Column(name = "themaimkl")
    private String themaimkl;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Leidingelement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAfwijkendedieptelegging() {
        return this.afwijkendedieptelegging;
    }

    public Leidingelement afwijkendedieptelegging(String afwijkendedieptelegging) {
        this.setAfwijkendedieptelegging(afwijkendedieptelegging);
        return this;
    }

    public void setAfwijkendedieptelegging(String afwijkendedieptelegging) {
        this.afwijkendedieptelegging = afwijkendedieptelegging;
    }

    public String getDiepte() {
        return this.diepte;
    }

    public Leidingelement diepte(String diepte) {
        this.setDiepte(diepte);
        return this;
    }

    public void setDiepte(String diepte) {
        this.diepte = diepte;
    }

    public String getGeonauwkeurigheidxy() {
        return this.geonauwkeurigheidxy;
    }

    public Leidingelement geonauwkeurigheidxy(String geonauwkeurigheidxy) {
        this.setGeonauwkeurigheidxy(geonauwkeurigheidxy);
        return this;
    }

    public void setGeonauwkeurigheidxy(String geonauwkeurigheidxy) {
        this.geonauwkeurigheidxy = geonauwkeurigheidxy;
    }

    public String getJaaronderhouduitgevoerd() {
        return this.jaaronderhouduitgevoerd;
    }

    public Leidingelement jaaronderhouduitgevoerd(String jaaronderhouduitgevoerd) {
        this.setJaaronderhouduitgevoerd(jaaronderhouduitgevoerd);
        return this;
    }

    public void setJaaronderhouduitgevoerd(String jaaronderhouduitgevoerd) {
        this.jaaronderhouduitgevoerd = jaaronderhouduitgevoerd;
    }

    public String getLeverancier() {
        return this.leverancier;
    }

    public Leidingelement leverancier(String leverancier) {
        this.setLeverancier(leverancier);
        return this;
    }

    public void setLeverancier(String leverancier) {
        this.leverancier = leverancier;
    }

    public String getThemaimkl() {
        return this.themaimkl;
    }

    public Leidingelement themaimkl(String themaimkl) {
        this.setThemaimkl(themaimkl);
        return this;
    }

    public void setThemaimkl(String themaimkl) {
        this.themaimkl = themaimkl;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Leidingelement)) {
            return false;
        }
        return getId() != null && getId().equals(((Leidingelement) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Leidingelement{" +
            "id=" + getId() +
            ", afwijkendedieptelegging='" + getAfwijkendedieptelegging() + "'" +
            ", diepte='" + getDiepte() + "'" +
            ", geonauwkeurigheidxy='" + getGeonauwkeurigheidxy() + "'" +
            ", jaaronderhouduitgevoerd='" + getJaaronderhouduitgevoerd() + "'" +
            ", leverancier='" + getLeverancier() + "'" +
            ", themaimkl='" + getThemaimkl() + "'" +
            "}";
    }
}
