package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Waterobject.
 */
@Entity
@Table(name = "waterobject")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Waterobject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "breedte")
    private String breedte;

    @Column(name = "folie")
    private Boolean folie;

    @Column(name = "hoogte")
    private String hoogte;

    @Column(name = "infiltrerendoppervlak")
    private String infiltrerendoppervlak;

    @Column(name = "infiltrerendvermogen")
    private String infiltrerendvermogen;

    @Column(name = "lengte")
    private String lengte;

    @Column(name = "lozingspunt")
    private String lozingspunt;

    @Column(name = "oppervlakte")
    private String oppervlakte;

    @Column(name = "porositeit")
    private String porositeit;

    @Column(name = "streefdiepte")
    private String streefdiepte;

    @Column(name = "type")
    private String type;

    @Column(name = "typeplus")
    private String typeplus;

    @Size(max = 100)
    @Column(name = "typeplus_2", length = 100)
    private String typeplus2;

    @Column(name = "typevaarwater")
    private String typevaarwater;

    @Column(name = "typewaterplant")
    private String typewaterplant;

    @Column(name = "uitstroomniveau")
    private String uitstroomniveau;

    @Column(name = "vaarwegtraject")
    private String vaarwegtraject;

    @Column(name = "vorm")
    private String vorm;

    @Size(max = 100)
    @Column(name = "waternaam", length = 100)
    private String waternaam;

    @Column(name = "waterpeil")
    private String waterpeil;

    @Column(name = "waterpeilwinter")
    private String waterpeilwinter;

    @Column(name = "waterpeilzomer")
    private String waterpeilzomer;

    @Column(name = "waterplanten")
    private Boolean waterplanten;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Waterobject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBreedte() {
        return this.breedte;
    }

    public Waterobject breedte(String breedte) {
        this.setBreedte(breedte);
        return this;
    }

    public void setBreedte(String breedte) {
        this.breedte = breedte;
    }

    public Boolean getFolie() {
        return this.folie;
    }

    public Waterobject folie(Boolean folie) {
        this.setFolie(folie);
        return this;
    }

    public void setFolie(Boolean folie) {
        this.folie = folie;
    }

    public String getHoogte() {
        return this.hoogte;
    }

    public Waterobject hoogte(String hoogte) {
        this.setHoogte(hoogte);
        return this;
    }

    public void setHoogte(String hoogte) {
        this.hoogte = hoogte;
    }

    public String getInfiltrerendoppervlak() {
        return this.infiltrerendoppervlak;
    }

    public Waterobject infiltrerendoppervlak(String infiltrerendoppervlak) {
        this.setInfiltrerendoppervlak(infiltrerendoppervlak);
        return this;
    }

    public void setInfiltrerendoppervlak(String infiltrerendoppervlak) {
        this.infiltrerendoppervlak = infiltrerendoppervlak;
    }

    public String getInfiltrerendvermogen() {
        return this.infiltrerendvermogen;
    }

    public Waterobject infiltrerendvermogen(String infiltrerendvermogen) {
        this.setInfiltrerendvermogen(infiltrerendvermogen);
        return this;
    }

    public void setInfiltrerendvermogen(String infiltrerendvermogen) {
        this.infiltrerendvermogen = infiltrerendvermogen;
    }

    public String getLengte() {
        return this.lengte;
    }

    public Waterobject lengte(String lengte) {
        this.setLengte(lengte);
        return this;
    }

    public void setLengte(String lengte) {
        this.lengte = lengte;
    }

    public String getLozingspunt() {
        return this.lozingspunt;
    }

    public Waterobject lozingspunt(String lozingspunt) {
        this.setLozingspunt(lozingspunt);
        return this;
    }

    public void setLozingspunt(String lozingspunt) {
        this.lozingspunt = lozingspunt;
    }

    public String getOppervlakte() {
        return this.oppervlakte;
    }

    public Waterobject oppervlakte(String oppervlakte) {
        this.setOppervlakte(oppervlakte);
        return this;
    }

    public void setOppervlakte(String oppervlakte) {
        this.oppervlakte = oppervlakte;
    }

    public String getPorositeit() {
        return this.porositeit;
    }

    public Waterobject porositeit(String porositeit) {
        this.setPorositeit(porositeit);
        return this;
    }

    public void setPorositeit(String porositeit) {
        this.porositeit = porositeit;
    }

    public String getStreefdiepte() {
        return this.streefdiepte;
    }

    public Waterobject streefdiepte(String streefdiepte) {
        this.setStreefdiepte(streefdiepte);
        return this;
    }

    public void setStreefdiepte(String streefdiepte) {
        this.streefdiepte = streefdiepte;
    }

    public String getType() {
        return this.type;
    }

    public Waterobject type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeplus() {
        return this.typeplus;
    }

    public Waterobject typeplus(String typeplus) {
        this.setTypeplus(typeplus);
        return this;
    }

    public void setTypeplus(String typeplus) {
        this.typeplus = typeplus;
    }

    public String getTypeplus2() {
        return this.typeplus2;
    }

    public Waterobject typeplus2(String typeplus2) {
        this.setTypeplus2(typeplus2);
        return this;
    }

    public void setTypeplus2(String typeplus2) {
        this.typeplus2 = typeplus2;
    }

    public String getTypevaarwater() {
        return this.typevaarwater;
    }

    public Waterobject typevaarwater(String typevaarwater) {
        this.setTypevaarwater(typevaarwater);
        return this;
    }

    public void setTypevaarwater(String typevaarwater) {
        this.typevaarwater = typevaarwater;
    }

    public String getTypewaterplant() {
        return this.typewaterplant;
    }

    public Waterobject typewaterplant(String typewaterplant) {
        this.setTypewaterplant(typewaterplant);
        return this;
    }

    public void setTypewaterplant(String typewaterplant) {
        this.typewaterplant = typewaterplant;
    }

    public String getUitstroomniveau() {
        return this.uitstroomniveau;
    }

    public Waterobject uitstroomniveau(String uitstroomniveau) {
        this.setUitstroomniveau(uitstroomniveau);
        return this;
    }

    public void setUitstroomniveau(String uitstroomniveau) {
        this.uitstroomniveau = uitstroomniveau;
    }

    public String getVaarwegtraject() {
        return this.vaarwegtraject;
    }

    public Waterobject vaarwegtraject(String vaarwegtraject) {
        this.setVaarwegtraject(vaarwegtraject);
        return this;
    }

    public void setVaarwegtraject(String vaarwegtraject) {
        this.vaarwegtraject = vaarwegtraject;
    }

    public String getVorm() {
        return this.vorm;
    }

    public Waterobject vorm(String vorm) {
        this.setVorm(vorm);
        return this;
    }

    public void setVorm(String vorm) {
        this.vorm = vorm;
    }

    public String getWaternaam() {
        return this.waternaam;
    }

    public Waterobject waternaam(String waternaam) {
        this.setWaternaam(waternaam);
        return this;
    }

    public void setWaternaam(String waternaam) {
        this.waternaam = waternaam;
    }

    public String getWaterpeil() {
        return this.waterpeil;
    }

    public Waterobject waterpeil(String waterpeil) {
        this.setWaterpeil(waterpeil);
        return this;
    }

    public void setWaterpeil(String waterpeil) {
        this.waterpeil = waterpeil;
    }

    public String getWaterpeilwinter() {
        return this.waterpeilwinter;
    }

    public Waterobject waterpeilwinter(String waterpeilwinter) {
        this.setWaterpeilwinter(waterpeilwinter);
        return this;
    }

    public void setWaterpeilwinter(String waterpeilwinter) {
        this.waterpeilwinter = waterpeilwinter;
    }

    public String getWaterpeilzomer() {
        return this.waterpeilzomer;
    }

    public Waterobject waterpeilzomer(String waterpeilzomer) {
        this.setWaterpeilzomer(waterpeilzomer);
        return this;
    }

    public void setWaterpeilzomer(String waterpeilzomer) {
        this.waterpeilzomer = waterpeilzomer;
    }

    public Boolean getWaterplanten() {
        return this.waterplanten;
    }

    public Waterobject waterplanten(Boolean waterplanten) {
        this.setWaterplanten(waterplanten);
        return this;
    }

    public void setWaterplanten(Boolean waterplanten) {
        this.waterplanten = waterplanten;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Waterobject)) {
            return false;
        }
        return getId() != null && getId().equals(((Waterobject) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Waterobject{" +
            "id=" + getId() +
            ", breedte='" + getBreedte() + "'" +
            ", folie='" + getFolie() + "'" +
            ", hoogte='" + getHoogte() + "'" +
            ", infiltrerendoppervlak='" + getInfiltrerendoppervlak() + "'" +
            ", infiltrerendvermogen='" + getInfiltrerendvermogen() + "'" +
            ", lengte='" + getLengte() + "'" +
            ", lozingspunt='" + getLozingspunt() + "'" +
            ", oppervlakte='" + getOppervlakte() + "'" +
            ", porositeit='" + getPorositeit() + "'" +
            ", streefdiepte='" + getStreefdiepte() + "'" +
            ", type='" + getType() + "'" +
            ", typeplus='" + getTypeplus() + "'" +
            ", typeplus2='" + getTypeplus2() + "'" +
            ", typevaarwater='" + getTypevaarwater() + "'" +
            ", typewaterplant='" + getTypewaterplant() + "'" +
            ", uitstroomniveau='" + getUitstroomniveau() + "'" +
            ", vaarwegtraject='" + getVaarwegtraject() + "'" +
            ", vorm='" + getVorm() + "'" +
            ", waternaam='" + getWaternaam() + "'" +
            ", waterpeil='" + getWaterpeil() + "'" +
            ", waterpeilwinter='" + getWaterpeilwinter() + "'" +
            ", waterpeilzomer='" + getWaterpeilzomer() + "'" +
            ", waterplanten='" + getWaterplanten() + "'" +
            "}";
    }
}
