package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Bouwwerk.
 */
@Entity
@Table(name = "bouwwerk")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bouwwerk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aanleghoogte")
    private String aanleghoogte;

    @Column(name = "bouwwerkmateriaal")
    private String bouwwerkmateriaal;

    @Column(name = "breedte")
    private String breedte;

    @Column(name = "fabrikant")
    private String fabrikant;

    @Column(name = "hoogte")
    private String hoogte;

    @Column(name = "jaaronderhouduitgevoerd")
    private String jaaronderhouduitgevoerd;

    @Column(name = "lengte")
    private String lengte;

    @Column(name = "leverancier")
    private String leverancier;

    @Column(name = "oppervlakte")
    private String oppervlakte;

    @Column(name = "typefundering")
    private String typefundering;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bouwwerk id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAanleghoogte() {
        return this.aanleghoogte;
    }

    public Bouwwerk aanleghoogte(String aanleghoogte) {
        this.setAanleghoogte(aanleghoogte);
        return this;
    }

    public void setAanleghoogte(String aanleghoogte) {
        this.aanleghoogte = aanleghoogte;
    }

    public String getBouwwerkmateriaal() {
        return this.bouwwerkmateriaal;
    }

    public Bouwwerk bouwwerkmateriaal(String bouwwerkmateriaal) {
        this.setBouwwerkmateriaal(bouwwerkmateriaal);
        return this;
    }

    public void setBouwwerkmateriaal(String bouwwerkmateriaal) {
        this.bouwwerkmateriaal = bouwwerkmateriaal;
    }

    public String getBreedte() {
        return this.breedte;
    }

    public Bouwwerk breedte(String breedte) {
        this.setBreedte(breedte);
        return this;
    }

    public void setBreedte(String breedte) {
        this.breedte = breedte;
    }

    public String getFabrikant() {
        return this.fabrikant;
    }

    public Bouwwerk fabrikant(String fabrikant) {
        this.setFabrikant(fabrikant);
        return this;
    }

    public void setFabrikant(String fabrikant) {
        this.fabrikant = fabrikant;
    }

    public String getHoogte() {
        return this.hoogte;
    }

    public Bouwwerk hoogte(String hoogte) {
        this.setHoogte(hoogte);
        return this;
    }

    public void setHoogte(String hoogte) {
        this.hoogte = hoogte;
    }

    public String getJaaronderhouduitgevoerd() {
        return this.jaaronderhouduitgevoerd;
    }

    public Bouwwerk jaaronderhouduitgevoerd(String jaaronderhouduitgevoerd) {
        this.setJaaronderhouduitgevoerd(jaaronderhouduitgevoerd);
        return this;
    }

    public void setJaaronderhouduitgevoerd(String jaaronderhouduitgevoerd) {
        this.jaaronderhouduitgevoerd = jaaronderhouduitgevoerd;
    }

    public String getLengte() {
        return this.lengte;
    }

    public Bouwwerk lengte(String lengte) {
        this.setLengte(lengte);
        return this;
    }

    public void setLengte(String lengte) {
        this.lengte = lengte;
    }

    public String getLeverancier() {
        return this.leverancier;
    }

    public Bouwwerk leverancier(String leverancier) {
        this.setLeverancier(leverancier);
        return this;
    }

    public void setLeverancier(String leverancier) {
        this.leverancier = leverancier;
    }

    public String getOppervlakte() {
        return this.oppervlakte;
    }

    public Bouwwerk oppervlakte(String oppervlakte) {
        this.setOppervlakte(oppervlakte);
        return this;
    }

    public void setOppervlakte(String oppervlakte) {
        this.oppervlakte = oppervlakte;
    }

    public String getTypefundering() {
        return this.typefundering;
    }

    public Bouwwerk typefundering(String typefundering) {
        this.setTypefundering(typefundering);
        return this;
    }

    public void setTypefundering(String typefundering) {
        this.typefundering = typefundering;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bouwwerk)) {
            return false;
        }
        return getId() != null && getId().equals(((Bouwwerk) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bouwwerk{" +
            "id=" + getId() +
            ", aanleghoogte='" + getAanleghoogte() + "'" +
            ", bouwwerkmateriaal='" + getBouwwerkmateriaal() + "'" +
            ", breedte='" + getBreedte() + "'" +
            ", fabrikant='" + getFabrikant() + "'" +
            ", hoogte='" + getHoogte() + "'" +
            ", jaaronderhouduitgevoerd='" + getJaaronderhouduitgevoerd() + "'" +
            ", lengte='" + getLengte() + "'" +
            ", leverancier='" + getLeverancier() + "'" +
            ", oppervlakte='" + getOppervlakte() + "'" +
            ", typefundering='" + getTypefundering() + "'" +
            "}";
    }
}
