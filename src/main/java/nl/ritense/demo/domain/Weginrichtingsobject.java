package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Weginrichtingsobject.
 */
@Entity
@Table(name = "weginrichtingsobject")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Weginrichtingsobject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aanleghoogte")
    private String aanleghoogte;

    @Column(name = "breedte")
    private String breedte;

    @Column(name = "hoogte")
    private String hoogte;

    @Column(name = "jaarconserveren")
    private String jaarconserveren;

    @Column(name = "jaaronderhouduitgevoerd")
    private String jaaronderhouduitgevoerd;

    @Column(name = "kwaliteitsniveauactueel")
    private String kwaliteitsniveauactueel;

    @Column(name = "kwaliteitsniveaugewenst")
    private String kwaliteitsniveaugewenst;

    @Column(name = "lengte")
    private String lengte;

    @Column(name = "leverancier")
    private String leverancier;

    @Column(name = "materiaal")
    private String materiaal;

    @Column(name = "oppervlakte")
    private String oppervlakte;

    @Column(name = "weginrichtingsobjectwegfunctie")
    private String weginrichtingsobjectwegfunctie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Weginrichtingsobject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAanleghoogte() {
        return this.aanleghoogte;
    }

    public Weginrichtingsobject aanleghoogte(String aanleghoogte) {
        this.setAanleghoogte(aanleghoogte);
        return this;
    }

    public void setAanleghoogte(String aanleghoogte) {
        this.aanleghoogte = aanleghoogte;
    }

    public String getBreedte() {
        return this.breedte;
    }

    public Weginrichtingsobject breedte(String breedte) {
        this.setBreedte(breedte);
        return this;
    }

    public void setBreedte(String breedte) {
        this.breedte = breedte;
    }

    public String getHoogte() {
        return this.hoogte;
    }

    public Weginrichtingsobject hoogte(String hoogte) {
        this.setHoogte(hoogte);
        return this;
    }

    public void setHoogte(String hoogte) {
        this.hoogte = hoogte;
    }

    public String getJaarconserveren() {
        return this.jaarconserveren;
    }

    public Weginrichtingsobject jaarconserveren(String jaarconserveren) {
        this.setJaarconserveren(jaarconserveren);
        return this;
    }

    public void setJaarconserveren(String jaarconserveren) {
        this.jaarconserveren = jaarconserveren;
    }

    public String getJaaronderhouduitgevoerd() {
        return this.jaaronderhouduitgevoerd;
    }

    public Weginrichtingsobject jaaronderhouduitgevoerd(String jaaronderhouduitgevoerd) {
        this.setJaaronderhouduitgevoerd(jaaronderhouduitgevoerd);
        return this;
    }

    public void setJaaronderhouduitgevoerd(String jaaronderhouduitgevoerd) {
        this.jaaronderhouduitgevoerd = jaaronderhouduitgevoerd;
    }

    public String getKwaliteitsniveauactueel() {
        return this.kwaliteitsniveauactueel;
    }

    public Weginrichtingsobject kwaliteitsniveauactueel(String kwaliteitsniveauactueel) {
        this.setKwaliteitsniveauactueel(kwaliteitsniveauactueel);
        return this;
    }

    public void setKwaliteitsniveauactueel(String kwaliteitsniveauactueel) {
        this.kwaliteitsniveauactueel = kwaliteitsniveauactueel;
    }

    public String getKwaliteitsniveaugewenst() {
        return this.kwaliteitsniveaugewenst;
    }

    public Weginrichtingsobject kwaliteitsniveaugewenst(String kwaliteitsniveaugewenst) {
        this.setKwaliteitsniveaugewenst(kwaliteitsniveaugewenst);
        return this;
    }

    public void setKwaliteitsniveaugewenst(String kwaliteitsniveaugewenst) {
        this.kwaliteitsniveaugewenst = kwaliteitsniveaugewenst;
    }

    public String getLengte() {
        return this.lengte;
    }

    public Weginrichtingsobject lengte(String lengte) {
        this.setLengte(lengte);
        return this;
    }

    public void setLengte(String lengte) {
        this.lengte = lengte;
    }

    public String getLeverancier() {
        return this.leverancier;
    }

    public Weginrichtingsobject leverancier(String leverancier) {
        this.setLeverancier(leverancier);
        return this;
    }

    public void setLeverancier(String leverancier) {
        this.leverancier = leverancier;
    }

    public String getMateriaal() {
        return this.materiaal;
    }

    public Weginrichtingsobject materiaal(String materiaal) {
        this.setMateriaal(materiaal);
        return this;
    }

    public void setMateriaal(String materiaal) {
        this.materiaal = materiaal;
    }

    public String getOppervlakte() {
        return this.oppervlakte;
    }

    public Weginrichtingsobject oppervlakte(String oppervlakte) {
        this.setOppervlakte(oppervlakte);
        return this;
    }

    public void setOppervlakte(String oppervlakte) {
        this.oppervlakte = oppervlakte;
    }

    public String getWeginrichtingsobjectwegfunctie() {
        return this.weginrichtingsobjectwegfunctie;
    }

    public Weginrichtingsobject weginrichtingsobjectwegfunctie(String weginrichtingsobjectwegfunctie) {
        this.setWeginrichtingsobjectwegfunctie(weginrichtingsobjectwegfunctie);
        return this;
    }

    public void setWeginrichtingsobjectwegfunctie(String weginrichtingsobjectwegfunctie) {
        this.weginrichtingsobjectwegfunctie = weginrichtingsobjectwegfunctie;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Weginrichtingsobject)) {
            return false;
        }
        return getId() != null && getId().equals(((Weginrichtingsobject) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Weginrichtingsobject{" +
            "id=" + getId() +
            ", aanleghoogte='" + getAanleghoogte() + "'" +
            ", breedte='" + getBreedte() + "'" +
            ", hoogte='" + getHoogte() + "'" +
            ", jaarconserveren='" + getJaarconserveren() + "'" +
            ", jaaronderhouduitgevoerd='" + getJaaronderhouduitgevoerd() + "'" +
            ", kwaliteitsniveauactueel='" + getKwaliteitsniveauactueel() + "'" +
            ", kwaliteitsniveaugewenst='" + getKwaliteitsniveaugewenst() + "'" +
            ", lengte='" + getLengte() + "'" +
            ", leverancier='" + getLeverancier() + "'" +
            ", materiaal='" + getMateriaal() + "'" +
            ", oppervlakte='" + getOppervlakte() + "'" +
            ", weginrichtingsobjectwegfunctie='" + getWeginrichtingsobjectwegfunctie() + "'" +
            "}";
    }
}
