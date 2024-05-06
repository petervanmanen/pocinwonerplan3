package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Bouwstijl.
 */
@Entity
@Table(name = "bouwstijl")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bouwstijl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "hoofdstijl")
    private String hoofdstijl;

    @Column(name = "substijl")
    private String substijl;

    @Column(name = "toelichting")
    private String toelichting;

    @Column(name = "zuiverheid")
    private String zuiverheid;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bouwstijl id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHoofdstijl() {
        return this.hoofdstijl;
    }

    public Bouwstijl hoofdstijl(String hoofdstijl) {
        this.setHoofdstijl(hoofdstijl);
        return this;
    }

    public void setHoofdstijl(String hoofdstijl) {
        this.hoofdstijl = hoofdstijl;
    }

    public String getSubstijl() {
        return this.substijl;
    }

    public Bouwstijl substijl(String substijl) {
        this.setSubstijl(substijl);
        return this;
    }

    public void setSubstijl(String substijl) {
        this.substijl = substijl;
    }

    public String getToelichting() {
        return this.toelichting;
    }

    public Bouwstijl toelichting(String toelichting) {
        this.setToelichting(toelichting);
        return this;
    }

    public void setToelichting(String toelichting) {
        this.toelichting = toelichting;
    }

    public String getZuiverheid() {
        return this.zuiverheid;
    }

    public Bouwstijl zuiverheid(String zuiverheid) {
        this.setZuiverheid(zuiverheid);
        return this;
    }

    public void setZuiverheid(String zuiverheid) {
        this.zuiverheid = zuiverheid;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bouwstijl)) {
            return false;
        }
        return getId() != null && getId().equals(((Bouwstijl) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bouwstijl{" +
            "id=" + getId() +
            ", hoofdstijl='" + getHoofdstijl() + "'" +
            ", substijl='" + getSubstijl() + "'" +
            ", toelichting='" + getToelichting() + "'" +
            ", zuiverheid='" + getZuiverheid() + "'" +
            "}";
    }
}
