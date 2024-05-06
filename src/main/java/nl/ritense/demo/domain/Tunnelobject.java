package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Tunnelobject.
 */
@Entity
@Table(name = "tunnelobject")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tunnelobject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aanleghoogte")
    private String aanleghoogte;

    @Column(name = "aantaltunnelbuizen")
    private String aantaltunnelbuizen;

    @Column(name = "breedte")
    private String breedte;

    @Column(name = "doorrijbreedte")
    private String doorrijbreedte;

    @Column(name = "doorrijhoogte")
    private String doorrijhoogte;

    @Column(name = "hoogte")
    private String hoogte;

    @Column(name = "jaarconserveren")
    private String jaarconserveren;

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

    @Column(name = "tunnelobjectmateriaal")
    private String tunnelobjectmateriaal;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tunnelobject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAanleghoogte() {
        return this.aanleghoogte;
    }

    public Tunnelobject aanleghoogte(String aanleghoogte) {
        this.setAanleghoogte(aanleghoogte);
        return this;
    }

    public void setAanleghoogte(String aanleghoogte) {
        this.aanleghoogte = aanleghoogte;
    }

    public String getAantaltunnelbuizen() {
        return this.aantaltunnelbuizen;
    }

    public Tunnelobject aantaltunnelbuizen(String aantaltunnelbuizen) {
        this.setAantaltunnelbuizen(aantaltunnelbuizen);
        return this;
    }

    public void setAantaltunnelbuizen(String aantaltunnelbuizen) {
        this.aantaltunnelbuizen = aantaltunnelbuizen;
    }

    public String getBreedte() {
        return this.breedte;
    }

    public Tunnelobject breedte(String breedte) {
        this.setBreedte(breedte);
        return this;
    }

    public void setBreedte(String breedte) {
        this.breedte = breedte;
    }

    public String getDoorrijbreedte() {
        return this.doorrijbreedte;
    }

    public Tunnelobject doorrijbreedte(String doorrijbreedte) {
        this.setDoorrijbreedte(doorrijbreedte);
        return this;
    }

    public void setDoorrijbreedte(String doorrijbreedte) {
        this.doorrijbreedte = doorrijbreedte;
    }

    public String getDoorrijhoogte() {
        return this.doorrijhoogte;
    }

    public Tunnelobject doorrijhoogte(String doorrijhoogte) {
        this.setDoorrijhoogte(doorrijhoogte);
        return this;
    }

    public void setDoorrijhoogte(String doorrijhoogte) {
        this.doorrijhoogte = doorrijhoogte;
    }

    public String getHoogte() {
        return this.hoogte;
    }

    public Tunnelobject hoogte(String hoogte) {
        this.setHoogte(hoogte);
        return this;
    }

    public void setHoogte(String hoogte) {
        this.hoogte = hoogte;
    }

    public String getJaarconserveren() {
        return this.jaarconserveren;
    }

    public Tunnelobject jaarconserveren(String jaarconserveren) {
        this.setJaarconserveren(jaarconserveren);
        return this;
    }

    public void setJaarconserveren(String jaarconserveren) {
        this.jaarconserveren = jaarconserveren;
    }

    public String getJaaronderhouduitgevoerd() {
        return this.jaaronderhouduitgevoerd;
    }

    public Tunnelobject jaaronderhouduitgevoerd(String jaaronderhouduitgevoerd) {
        this.setJaaronderhouduitgevoerd(jaaronderhouduitgevoerd);
        return this;
    }

    public void setJaaronderhouduitgevoerd(String jaaronderhouduitgevoerd) {
        this.jaaronderhouduitgevoerd = jaaronderhouduitgevoerd;
    }

    public String getLengte() {
        return this.lengte;
    }

    public Tunnelobject lengte(String lengte) {
        this.setLengte(lengte);
        return this;
    }

    public void setLengte(String lengte) {
        this.lengte = lengte;
    }

    public String getLeverancier() {
        return this.leverancier;
    }

    public Tunnelobject leverancier(String leverancier) {
        this.setLeverancier(leverancier);
        return this;
    }

    public void setLeverancier(String leverancier) {
        this.leverancier = leverancier;
    }

    public String getEobjectnaam() {
        return this.eobjectnaam;
    }

    public Tunnelobject eobjectnaam(String eobjectnaam) {
        this.setEobjectnaam(eobjectnaam);
        return this;
    }

    public void setEobjectnaam(String eobjectnaam) {
        this.eobjectnaam = eobjectnaam;
    }

    public String getEobjectnummer() {
        return this.eobjectnummer;
    }

    public Tunnelobject eobjectnummer(String eobjectnummer) {
        this.setEobjectnummer(eobjectnummer);
        return this;
    }

    public void setEobjectnummer(String eobjectnummer) {
        this.eobjectnummer = eobjectnummer;
    }

    public String getOppervlakte() {
        return this.oppervlakte;
    }

    public Tunnelobject oppervlakte(String oppervlakte) {
        this.setOppervlakte(oppervlakte);
        return this;
    }

    public void setOppervlakte(String oppervlakte) {
        this.oppervlakte = oppervlakte;
    }

    public String getTunnelobjectmateriaal() {
        return this.tunnelobjectmateriaal;
    }

    public Tunnelobject tunnelobjectmateriaal(String tunnelobjectmateriaal) {
        this.setTunnelobjectmateriaal(tunnelobjectmateriaal);
        return this;
    }

    public void setTunnelobjectmateriaal(String tunnelobjectmateriaal) {
        this.tunnelobjectmateriaal = tunnelobjectmateriaal;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tunnelobject)) {
            return false;
        }
        return getId() != null && getId().equals(((Tunnelobject) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tunnelobject{" +
            "id=" + getId() +
            ", aanleghoogte='" + getAanleghoogte() + "'" +
            ", aantaltunnelbuizen='" + getAantaltunnelbuizen() + "'" +
            ", breedte='" + getBreedte() + "'" +
            ", doorrijbreedte='" + getDoorrijbreedte() + "'" +
            ", doorrijhoogte='" + getDoorrijhoogte() + "'" +
            ", hoogte='" + getHoogte() + "'" +
            ", jaarconserveren='" + getJaarconserveren() + "'" +
            ", jaaronderhouduitgevoerd='" + getJaaronderhouduitgevoerd() + "'" +
            ", lengte='" + getLengte() + "'" +
            ", leverancier='" + getLeverancier() + "'" +
            ", eobjectnaam='" + getEobjectnaam() + "'" +
            ", eobjectnummer='" + getEobjectnummer() + "'" +
            ", oppervlakte='" + getOppervlakte() + "'" +
            ", tunnelobjectmateriaal='" + getTunnelobjectmateriaal() + "'" +
            "}";
    }
}
