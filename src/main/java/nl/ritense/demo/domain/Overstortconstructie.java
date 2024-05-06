package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Overstortconstructie.
 */
@Entity
@Table(name = "overstortconstructie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Overstortconstructie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bassin")
    private String bassin;

    @Column(name = "drempelbreedte")
    private String drempelbreedte;

    @Column(name = "drempelniveau")
    private String drempelniveau;

    @Column(name = "klep")
    private Boolean klep;

    @Column(name = "type")
    private String type;

    @Column(name = "vormdrempel")
    private String vormdrempel;

    @Column(name = "waking")
    private String waking;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Overstortconstructie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBassin() {
        return this.bassin;
    }

    public Overstortconstructie bassin(String bassin) {
        this.setBassin(bassin);
        return this;
    }

    public void setBassin(String bassin) {
        this.bassin = bassin;
    }

    public String getDrempelbreedte() {
        return this.drempelbreedte;
    }

    public Overstortconstructie drempelbreedte(String drempelbreedte) {
        this.setDrempelbreedte(drempelbreedte);
        return this;
    }

    public void setDrempelbreedte(String drempelbreedte) {
        this.drempelbreedte = drempelbreedte;
    }

    public String getDrempelniveau() {
        return this.drempelniveau;
    }

    public Overstortconstructie drempelniveau(String drempelniveau) {
        this.setDrempelniveau(drempelniveau);
        return this;
    }

    public void setDrempelniveau(String drempelniveau) {
        this.drempelniveau = drempelniveau;
    }

    public Boolean getKlep() {
        return this.klep;
    }

    public Overstortconstructie klep(Boolean klep) {
        this.setKlep(klep);
        return this;
    }

    public void setKlep(Boolean klep) {
        this.klep = klep;
    }

    public String getType() {
        return this.type;
    }

    public Overstortconstructie type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVormdrempel() {
        return this.vormdrempel;
    }

    public Overstortconstructie vormdrempel(String vormdrempel) {
        this.setVormdrempel(vormdrempel);
        return this;
    }

    public void setVormdrempel(String vormdrempel) {
        this.vormdrempel = vormdrempel;
    }

    public String getWaking() {
        return this.waking;
    }

    public Overstortconstructie waking(String waking) {
        this.setWaking(waking);
        return this;
    }

    public void setWaking(String waking) {
        this.waking = waking;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Overstortconstructie)) {
            return false;
        }
        return getId() != null && getId().equals(((Overstortconstructie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Overstortconstructie{" +
            "id=" + getId() +
            ", bassin='" + getBassin() + "'" +
            ", drempelbreedte='" + getDrempelbreedte() + "'" +
            ", drempelniveau='" + getDrempelniveau() + "'" +
            ", klep='" + getKlep() + "'" +
            ", type='" + getType() + "'" +
            ", vormdrempel='" + getVormdrempel() + "'" +
            ", waking='" + getWaking() + "'" +
            "}";
    }
}
