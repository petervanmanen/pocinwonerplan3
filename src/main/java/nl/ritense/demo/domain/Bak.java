package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Bak.
 */
@Entity
@Table(name = "bak")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bak implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "breedte")
    private String breedte;

    @Column(name = "diameter")
    private String diameter;

    @Column(name = "gewichtleeg")
    private String gewichtleeg;

    @Column(name = "gewichtvol")
    private String gewichtvol;

    @Column(name = "hoogte")
    private String hoogte;

    @Column(name = "inhoud")
    private String inhoud;

    @Column(name = "jaaronderhouduitgevoerd")
    private String jaaronderhouduitgevoerd;

    @Column(name = "kwaliteitsniveauactueel")
    private String kwaliteitsniveauactueel;

    @Column(name = "kwaliteitsniveaugewenst")
    private String kwaliteitsniveaugewenst;

    @Column(name = "lengte")
    private String lengte;

    @Column(name = "materiaal")
    private String materiaal;

    @Column(name = "verplaatsbaar")
    private Boolean verplaatsbaar;

    @Column(name = "vorm")
    private String vorm;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bak id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBreedte() {
        return this.breedte;
    }

    public Bak breedte(String breedte) {
        this.setBreedte(breedte);
        return this;
    }

    public void setBreedte(String breedte) {
        this.breedte = breedte;
    }

    public String getDiameter() {
        return this.diameter;
    }

    public Bak diameter(String diameter) {
        this.setDiameter(diameter);
        return this;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getGewichtleeg() {
        return this.gewichtleeg;
    }

    public Bak gewichtleeg(String gewichtleeg) {
        this.setGewichtleeg(gewichtleeg);
        return this;
    }

    public void setGewichtleeg(String gewichtleeg) {
        this.gewichtleeg = gewichtleeg;
    }

    public String getGewichtvol() {
        return this.gewichtvol;
    }

    public Bak gewichtvol(String gewichtvol) {
        this.setGewichtvol(gewichtvol);
        return this;
    }

    public void setGewichtvol(String gewichtvol) {
        this.gewichtvol = gewichtvol;
    }

    public String getHoogte() {
        return this.hoogte;
    }

    public Bak hoogte(String hoogte) {
        this.setHoogte(hoogte);
        return this;
    }

    public void setHoogte(String hoogte) {
        this.hoogte = hoogte;
    }

    public String getInhoud() {
        return this.inhoud;
    }

    public Bak inhoud(String inhoud) {
        this.setInhoud(inhoud);
        return this;
    }

    public void setInhoud(String inhoud) {
        this.inhoud = inhoud;
    }

    public String getJaaronderhouduitgevoerd() {
        return this.jaaronderhouduitgevoerd;
    }

    public Bak jaaronderhouduitgevoerd(String jaaronderhouduitgevoerd) {
        this.setJaaronderhouduitgevoerd(jaaronderhouduitgevoerd);
        return this;
    }

    public void setJaaronderhouduitgevoerd(String jaaronderhouduitgevoerd) {
        this.jaaronderhouduitgevoerd = jaaronderhouduitgevoerd;
    }

    public String getKwaliteitsniveauactueel() {
        return this.kwaliteitsniveauactueel;
    }

    public Bak kwaliteitsniveauactueel(String kwaliteitsniveauactueel) {
        this.setKwaliteitsniveauactueel(kwaliteitsniveauactueel);
        return this;
    }

    public void setKwaliteitsniveauactueel(String kwaliteitsniveauactueel) {
        this.kwaliteitsniveauactueel = kwaliteitsniveauactueel;
    }

    public String getKwaliteitsniveaugewenst() {
        return this.kwaliteitsniveaugewenst;
    }

    public Bak kwaliteitsniveaugewenst(String kwaliteitsniveaugewenst) {
        this.setKwaliteitsniveaugewenst(kwaliteitsniveaugewenst);
        return this;
    }

    public void setKwaliteitsniveaugewenst(String kwaliteitsniveaugewenst) {
        this.kwaliteitsniveaugewenst = kwaliteitsniveaugewenst;
    }

    public String getLengte() {
        return this.lengte;
    }

    public Bak lengte(String lengte) {
        this.setLengte(lengte);
        return this;
    }

    public void setLengte(String lengte) {
        this.lengte = lengte;
    }

    public String getMateriaal() {
        return this.materiaal;
    }

    public Bak materiaal(String materiaal) {
        this.setMateriaal(materiaal);
        return this;
    }

    public void setMateriaal(String materiaal) {
        this.materiaal = materiaal;
    }

    public Boolean getVerplaatsbaar() {
        return this.verplaatsbaar;
    }

    public Bak verplaatsbaar(Boolean verplaatsbaar) {
        this.setVerplaatsbaar(verplaatsbaar);
        return this;
    }

    public void setVerplaatsbaar(Boolean verplaatsbaar) {
        this.verplaatsbaar = verplaatsbaar;
    }

    public String getVorm() {
        return this.vorm;
    }

    public Bak vorm(String vorm) {
        this.setVorm(vorm);
        return this;
    }

    public void setVorm(String vorm) {
        this.vorm = vorm;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bak)) {
            return false;
        }
        return getId() != null && getId().equals(((Bak) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bak{" +
            "id=" + getId() +
            ", breedte='" + getBreedte() + "'" +
            ", diameter='" + getDiameter() + "'" +
            ", gewichtleeg='" + getGewichtleeg() + "'" +
            ", gewichtvol='" + getGewichtvol() + "'" +
            ", hoogte='" + getHoogte() + "'" +
            ", inhoud='" + getInhoud() + "'" +
            ", jaaronderhouduitgevoerd='" + getJaaronderhouduitgevoerd() + "'" +
            ", kwaliteitsniveauactueel='" + getKwaliteitsniveauactueel() + "'" +
            ", kwaliteitsniveaugewenst='" + getKwaliteitsniveaugewenst() + "'" +
            ", lengte='" + getLengte() + "'" +
            ", materiaal='" + getMateriaal() + "'" +
            ", verplaatsbaar='" + getVerplaatsbaar() + "'" +
            ", vorm='" + getVorm() + "'" +
            "}";
    }
}
