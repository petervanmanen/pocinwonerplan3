package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Brug.
 */
@Entity
@Table(name = "brug")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Brug implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aantaloverspanningen")
    private String aantaloverspanningen;

    @Column(name = "bedienaar")
    private String bedienaar;

    @Column(name = "bedieningstijden")
    private String bedieningstijden;

    @Column(name = "belastingklassenieuw")
    private String belastingklassenieuw;

    @Column(name = "belastingklasseoud")
    private String belastingklasseoud;

    @Column(name = "beweegbaar")
    private Boolean beweegbaar;

    @Column(name = "doorrijbreedte")
    private String doorrijbreedte;

    @Column(name = "draagvermogen")
    private String draagvermogen;

    @Column(name = "hoofdroute")
    private String hoofdroute;

    @Column(name = "hoofdvaarroute")
    private String hoofdvaarroute;

    @Column(name = "maximaaltoelaatbaarvoertuiggewicht")
    private String maximaaltoelaatbaarvoertuiggewicht;

    @Column(name = "maximaleasbelasting")
    private String maximaleasbelasting;

    @Column(name = "maximaleoverspanning")
    private String maximaleoverspanning;

    @Column(name = "statischmoment")
    private String statischmoment;

    @Column(name = "type")
    private String type;

    @Column(name = "typeplus")
    private String typeplus;

    @Column(name = "zwaarstevoertuig")
    private String zwaarstevoertuig;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Brug id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAantaloverspanningen() {
        return this.aantaloverspanningen;
    }

    public Brug aantaloverspanningen(String aantaloverspanningen) {
        this.setAantaloverspanningen(aantaloverspanningen);
        return this;
    }

    public void setAantaloverspanningen(String aantaloverspanningen) {
        this.aantaloverspanningen = aantaloverspanningen;
    }

    public String getBedienaar() {
        return this.bedienaar;
    }

    public Brug bedienaar(String bedienaar) {
        this.setBedienaar(bedienaar);
        return this;
    }

    public void setBedienaar(String bedienaar) {
        this.bedienaar = bedienaar;
    }

    public String getBedieningstijden() {
        return this.bedieningstijden;
    }

    public Brug bedieningstijden(String bedieningstijden) {
        this.setBedieningstijden(bedieningstijden);
        return this;
    }

    public void setBedieningstijden(String bedieningstijden) {
        this.bedieningstijden = bedieningstijden;
    }

    public String getBelastingklassenieuw() {
        return this.belastingklassenieuw;
    }

    public Brug belastingklassenieuw(String belastingklassenieuw) {
        this.setBelastingklassenieuw(belastingklassenieuw);
        return this;
    }

    public void setBelastingklassenieuw(String belastingklassenieuw) {
        this.belastingklassenieuw = belastingklassenieuw;
    }

    public String getBelastingklasseoud() {
        return this.belastingklasseoud;
    }

    public Brug belastingklasseoud(String belastingklasseoud) {
        this.setBelastingklasseoud(belastingklasseoud);
        return this;
    }

    public void setBelastingklasseoud(String belastingklasseoud) {
        this.belastingklasseoud = belastingklasseoud;
    }

    public Boolean getBeweegbaar() {
        return this.beweegbaar;
    }

    public Brug beweegbaar(Boolean beweegbaar) {
        this.setBeweegbaar(beweegbaar);
        return this;
    }

    public void setBeweegbaar(Boolean beweegbaar) {
        this.beweegbaar = beweegbaar;
    }

    public String getDoorrijbreedte() {
        return this.doorrijbreedte;
    }

    public Brug doorrijbreedte(String doorrijbreedte) {
        this.setDoorrijbreedte(doorrijbreedte);
        return this;
    }

    public void setDoorrijbreedte(String doorrijbreedte) {
        this.doorrijbreedte = doorrijbreedte;
    }

    public String getDraagvermogen() {
        return this.draagvermogen;
    }

    public Brug draagvermogen(String draagvermogen) {
        this.setDraagvermogen(draagvermogen);
        return this;
    }

    public void setDraagvermogen(String draagvermogen) {
        this.draagvermogen = draagvermogen;
    }

    public String getHoofdroute() {
        return this.hoofdroute;
    }

    public Brug hoofdroute(String hoofdroute) {
        this.setHoofdroute(hoofdroute);
        return this;
    }

    public void setHoofdroute(String hoofdroute) {
        this.hoofdroute = hoofdroute;
    }

    public String getHoofdvaarroute() {
        return this.hoofdvaarroute;
    }

    public Brug hoofdvaarroute(String hoofdvaarroute) {
        this.setHoofdvaarroute(hoofdvaarroute);
        return this;
    }

    public void setHoofdvaarroute(String hoofdvaarroute) {
        this.hoofdvaarroute = hoofdvaarroute;
    }

    public String getMaximaaltoelaatbaarvoertuiggewicht() {
        return this.maximaaltoelaatbaarvoertuiggewicht;
    }

    public Brug maximaaltoelaatbaarvoertuiggewicht(String maximaaltoelaatbaarvoertuiggewicht) {
        this.setMaximaaltoelaatbaarvoertuiggewicht(maximaaltoelaatbaarvoertuiggewicht);
        return this;
    }

    public void setMaximaaltoelaatbaarvoertuiggewicht(String maximaaltoelaatbaarvoertuiggewicht) {
        this.maximaaltoelaatbaarvoertuiggewicht = maximaaltoelaatbaarvoertuiggewicht;
    }

    public String getMaximaleasbelasting() {
        return this.maximaleasbelasting;
    }

    public Brug maximaleasbelasting(String maximaleasbelasting) {
        this.setMaximaleasbelasting(maximaleasbelasting);
        return this;
    }

    public void setMaximaleasbelasting(String maximaleasbelasting) {
        this.maximaleasbelasting = maximaleasbelasting;
    }

    public String getMaximaleoverspanning() {
        return this.maximaleoverspanning;
    }

    public Brug maximaleoverspanning(String maximaleoverspanning) {
        this.setMaximaleoverspanning(maximaleoverspanning);
        return this;
    }

    public void setMaximaleoverspanning(String maximaleoverspanning) {
        this.maximaleoverspanning = maximaleoverspanning;
    }

    public String getStatischmoment() {
        return this.statischmoment;
    }

    public Brug statischmoment(String statischmoment) {
        this.setStatischmoment(statischmoment);
        return this;
    }

    public void setStatischmoment(String statischmoment) {
        this.statischmoment = statischmoment;
    }

    public String getType() {
        return this.type;
    }

    public Brug type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeplus() {
        return this.typeplus;
    }

    public Brug typeplus(String typeplus) {
        this.setTypeplus(typeplus);
        return this;
    }

    public void setTypeplus(String typeplus) {
        this.typeplus = typeplus;
    }

    public String getZwaarstevoertuig() {
        return this.zwaarstevoertuig;
    }

    public Brug zwaarstevoertuig(String zwaarstevoertuig) {
        this.setZwaarstevoertuig(zwaarstevoertuig);
        return this;
    }

    public void setZwaarstevoertuig(String zwaarstevoertuig) {
        this.zwaarstevoertuig = zwaarstevoertuig;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Brug)) {
            return false;
        }
        return getId() != null && getId().equals(((Brug) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Brug{" +
            "id=" + getId() +
            ", aantaloverspanningen='" + getAantaloverspanningen() + "'" +
            ", bedienaar='" + getBedienaar() + "'" +
            ", bedieningstijden='" + getBedieningstijden() + "'" +
            ", belastingklassenieuw='" + getBelastingklassenieuw() + "'" +
            ", belastingklasseoud='" + getBelastingklasseoud() + "'" +
            ", beweegbaar='" + getBeweegbaar() + "'" +
            ", doorrijbreedte='" + getDoorrijbreedte() + "'" +
            ", draagvermogen='" + getDraagvermogen() + "'" +
            ", hoofdroute='" + getHoofdroute() + "'" +
            ", hoofdvaarroute='" + getHoofdvaarroute() + "'" +
            ", maximaaltoelaatbaarvoertuiggewicht='" + getMaximaaltoelaatbaarvoertuiggewicht() + "'" +
            ", maximaleasbelasting='" + getMaximaleasbelasting() + "'" +
            ", maximaleoverspanning='" + getMaximaleoverspanning() + "'" +
            ", statischmoment='" + getStatischmoment() + "'" +
            ", type='" + getType() + "'" +
            ", typeplus='" + getTypeplus() + "'" +
            ", zwaarstevoertuig='" + getZwaarstevoertuig() + "'" +
            "}";
    }
}
