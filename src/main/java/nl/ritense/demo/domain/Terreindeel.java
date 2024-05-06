package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Terreindeel.
 */
@Entity
@Table(name = "terreindeel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Terreindeel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "breedte")
    private String breedte;

    @Column(name = "cultuurhistorischwaardevol")
    private String cultuurhistorischwaardevol;

    @Column(name = "herplantplicht")
    private Boolean herplantplicht;

    @Column(name = "oppervlakte")
    private String oppervlakte;

    @Column(name = "optalud")
    private String optalud;

    @Column(name = "percentageloofbos")
    private String percentageloofbos;

    @Column(name = "terreindeelsoortnaam")
    private String terreindeelsoortnaam;

    @Column(name = "type")
    private String type;

    @Column(name = "typebewerking")
    private String typebewerking;

    @Column(name = "typeplus")
    private String typeplus;

    @Size(max = 100)
    @Column(name = "typeplus_2", length = 100)
    private String typeplus2;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Terreindeel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBreedte() {
        return this.breedte;
    }

    public Terreindeel breedte(String breedte) {
        this.setBreedte(breedte);
        return this;
    }

    public void setBreedte(String breedte) {
        this.breedte = breedte;
    }

    public String getCultuurhistorischwaardevol() {
        return this.cultuurhistorischwaardevol;
    }

    public Terreindeel cultuurhistorischwaardevol(String cultuurhistorischwaardevol) {
        this.setCultuurhistorischwaardevol(cultuurhistorischwaardevol);
        return this;
    }

    public void setCultuurhistorischwaardevol(String cultuurhistorischwaardevol) {
        this.cultuurhistorischwaardevol = cultuurhistorischwaardevol;
    }

    public Boolean getHerplantplicht() {
        return this.herplantplicht;
    }

    public Terreindeel herplantplicht(Boolean herplantplicht) {
        this.setHerplantplicht(herplantplicht);
        return this;
    }

    public void setHerplantplicht(Boolean herplantplicht) {
        this.herplantplicht = herplantplicht;
    }

    public String getOppervlakte() {
        return this.oppervlakte;
    }

    public Terreindeel oppervlakte(String oppervlakte) {
        this.setOppervlakte(oppervlakte);
        return this;
    }

    public void setOppervlakte(String oppervlakte) {
        this.oppervlakte = oppervlakte;
    }

    public String getOptalud() {
        return this.optalud;
    }

    public Terreindeel optalud(String optalud) {
        this.setOptalud(optalud);
        return this;
    }

    public void setOptalud(String optalud) {
        this.optalud = optalud;
    }

    public String getPercentageloofbos() {
        return this.percentageloofbos;
    }

    public Terreindeel percentageloofbos(String percentageloofbos) {
        this.setPercentageloofbos(percentageloofbos);
        return this;
    }

    public void setPercentageloofbos(String percentageloofbos) {
        this.percentageloofbos = percentageloofbos;
    }

    public String getTerreindeelsoortnaam() {
        return this.terreindeelsoortnaam;
    }

    public Terreindeel terreindeelsoortnaam(String terreindeelsoortnaam) {
        this.setTerreindeelsoortnaam(terreindeelsoortnaam);
        return this;
    }

    public void setTerreindeelsoortnaam(String terreindeelsoortnaam) {
        this.terreindeelsoortnaam = terreindeelsoortnaam;
    }

    public String getType() {
        return this.type;
    }

    public Terreindeel type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypebewerking() {
        return this.typebewerking;
    }

    public Terreindeel typebewerking(String typebewerking) {
        this.setTypebewerking(typebewerking);
        return this;
    }

    public void setTypebewerking(String typebewerking) {
        this.typebewerking = typebewerking;
    }

    public String getTypeplus() {
        return this.typeplus;
    }

    public Terreindeel typeplus(String typeplus) {
        this.setTypeplus(typeplus);
        return this;
    }

    public void setTypeplus(String typeplus) {
        this.typeplus = typeplus;
    }

    public String getTypeplus2() {
        return this.typeplus2;
    }

    public Terreindeel typeplus2(String typeplus2) {
        this.setTypeplus2(typeplus2);
        return this;
    }

    public void setTypeplus2(String typeplus2) {
        this.typeplus2 = typeplus2;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Terreindeel)) {
            return false;
        }
        return getId() != null && getId().equals(((Terreindeel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Terreindeel{" +
            "id=" + getId() +
            ", breedte='" + getBreedte() + "'" +
            ", cultuurhistorischwaardevol='" + getCultuurhistorischwaardevol() + "'" +
            ", herplantplicht='" + getHerplantplicht() + "'" +
            ", oppervlakte='" + getOppervlakte() + "'" +
            ", optalud='" + getOptalud() + "'" +
            ", percentageloofbos='" + getPercentageloofbos() + "'" +
            ", terreindeelsoortnaam='" + getTerreindeelsoortnaam() + "'" +
            ", type='" + getType() + "'" +
            ", typebewerking='" + getTypebewerking() + "'" +
            ", typeplus='" + getTypeplus() + "'" +
            ", typeplus2='" + getTypeplus2() + "'" +
            "}";
    }
}
