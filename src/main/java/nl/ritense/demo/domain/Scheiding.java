package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Scheiding.
 */
@Entity
@Table(name = "scheiding")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Scheiding implements Serializable {

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

    @Column(name = "jaaronderhouduitgevoerd")
    private String jaaronderhouduitgevoerd;

    @Column(name = "lengte")
    private String lengte;

    @Column(name = "leverancier")
    private String leverancier;

    @Column(name = "eobjectnaam")
    private String eobjectnaam;

    @Size(max = 20)
    @Column(name = "eobjectnummer", length = 20)
    private String eobjectnummer;

    @Column(name = "oppervlakte")
    private String oppervlakte;

    @Column(name = "scheidingmateriaal")
    private String scheidingmateriaal;

    @Column(name = "verplaatsbaar")
    private Boolean verplaatsbaar;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Scheiding id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAanleghoogte() {
        return this.aanleghoogte;
    }

    public Scheiding aanleghoogte(String aanleghoogte) {
        this.setAanleghoogte(aanleghoogte);
        return this;
    }

    public void setAanleghoogte(String aanleghoogte) {
        this.aanleghoogte = aanleghoogte;
    }

    public String getBreedte() {
        return this.breedte;
    }

    public Scheiding breedte(String breedte) {
        this.setBreedte(breedte);
        return this;
    }

    public void setBreedte(String breedte) {
        this.breedte = breedte;
    }

    public String getHoogte() {
        return this.hoogte;
    }

    public Scheiding hoogte(String hoogte) {
        this.setHoogte(hoogte);
        return this;
    }

    public void setHoogte(String hoogte) {
        this.hoogte = hoogte;
    }

    public String getJaaronderhouduitgevoerd() {
        return this.jaaronderhouduitgevoerd;
    }

    public Scheiding jaaronderhouduitgevoerd(String jaaronderhouduitgevoerd) {
        this.setJaaronderhouduitgevoerd(jaaronderhouduitgevoerd);
        return this;
    }

    public void setJaaronderhouduitgevoerd(String jaaronderhouduitgevoerd) {
        this.jaaronderhouduitgevoerd = jaaronderhouduitgevoerd;
    }

    public String getLengte() {
        return this.lengte;
    }

    public Scheiding lengte(String lengte) {
        this.setLengte(lengte);
        return this;
    }

    public void setLengte(String lengte) {
        this.lengte = lengte;
    }

    public String getLeverancier() {
        return this.leverancier;
    }

    public Scheiding leverancier(String leverancier) {
        this.setLeverancier(leverancier);
        return this;
    }

    public void setLeverancier(String leverancier) {
        this.leverancier = leverancier;
    }

    public String getEobjectnaam() {
        return this.eobjectnaam;
    }

    public Scheiding eobjectnaam(String eobjectnaam) {
        this.setEobjectnaam(eobjectnaam);
        return this;
    }

    public void setEobjectnaam(String eobjectnaam) {
        this.eobjectnaam = eobjectnaam;
    }

    public String getEobjectnummer() {
        return this.eobjectnummer;
    }

    public Scheiding eobjectnummer(String eobjectnummer) {
        this.setEobjectnummer(eobjectnummer);
        return this;
    }

    public void setEobjectnummer(String eobjectnummer) {
        this.eobjectnummer = eobjectnummer;
    }

    public String getOppervlakte() {
        return this.oppervlakte;
    }

    public Scheiding oppervlakte(String oppervlakte) {
        this.setOppervlakte(oppervlakte);
        return this;
    }

    public void setOppervlakte(String oppervlakte) {
        this.oppervlakte = oppervlakte;
    }

    public String getScheidingmateriaal() {
        return this.scheidingmateriaal;
    }

    public Scheiding scheidingmateriaal(String scheidingmateriaal) {
        this.setScheidingmateriaal(scheidingmateriaal);
        return this;
    }

    public void setScheidingmateriaal(String scheidingmateriaal) {
        this.scheidingmateriaal = scheidingmateriaal;
    }

    public Boolean getVerplaatsbaar() {
        return this.verplaatsbaar;
    }

    public Scheiding verplaatsbaar(Boolean verplaatsbaar) {
        this.setVerplaatsbaar(verplaatsbaar);
        return this;
    }

    public void setVerplaatsbaar(Boolean verplaatsbaar) {
        this.verplaatsbaar = verplaatsbaar;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Scheiding)) {
            return false;
        }
        return getId() != null && getId().equals(((Scheiding) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Scheiding{" +
            "id=" + getId() +
            ", aanleghoogte='" + getAanleghoogte() + "'" +
            ", breedte='" + getBreedte() + "'" +
            ", hoogte='" + getHoogte() + "'" +
            ", jaaronderhouduitgevoerd='" + getJaaronderhouduitgevoerd() + "'" +
            ", lengte='" + getLengte() + "'" +
            ", leverancier='" + getLeverancier() + "'" +
            ", eobjectnaam='" + getEobjectnaam() + "'" +
            ", eobjectnummer='" + getEobjectnummer() + "'" +
            ", oppervlakte='" + getOppervlakte() + "'" +
            ", scheidingmateriaal='" + getScheidingmateriaal() + "'" +
            ", verplaatsbaar='" + getVerplaatsbaar() + "'" +
            "}";
    }
}
