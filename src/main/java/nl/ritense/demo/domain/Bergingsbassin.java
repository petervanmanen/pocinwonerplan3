package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Bergingsbassin.
 */
@Entity
@Table(name = "bergingsbassin")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bergingsbassin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bergendvermogen")
    private String bergendvermogen;

    @Column(name = "pompledigingsvoorziening")
    private String pompledigingsvoorziening;

    @Column(name = "pompspoelvoorziening")
    private String pompspoelvoorziening;

    @Column(name = "spoelleiding")
    private String spoelleiding;

    @Column(name = "vorm")
    private String vorm;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bergingsbassin id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBergendvermogen() {
        return this.bergendvermogen;
    }

    public Bergingsbassin bergendvermogen(String bergendvermogen) {
        this.setBergendvermogen(bergendvermogen);
        return this;
    }

    public void setBergendvermogen(String bergendvermogen) {
        this.bergendvermogen = bergendvermogen;
    }

    public String getPompledigingsvoorziening() {
        return this.pompledigingsvoorziening;
    }

    public Bergingsbassin pompledigingsvoorziening(String pompledigingsvoorziening) {
        this.setPompledigingsvoorziening(pompledigingsvoorziening);
        return this;
    }

    public void setPompledigingsvoorziening(String pompledigingsvoorziening) {
        this.pompledigingsvoorziening = pompledigingsvoorziening;
    }

    public String getPompspoelvoorziening() {
        return this.pompspoelvoorziening;
    }

    public Bergingsbassin pompspoelvoorziening(String pompspoelvoorziening) {
        this.setPompspoelvoorziening(pompspoelvoorziening);
        return this;
    }

    public void setPompspoelvoorziening(String pompspoelvoorziening) {
        this.pompspoelvoorziening = pompspoelvoorziening;
    }

    public String getSpoelleiding() {
        return this.spoelleiding;
    }

    public Bergingsbassin spoelleiding(String spoelleiding) {
        this.setSpoelleiding(spoelleiding);
        return this;
    }

    public void setSpoelleiding(String spoelleiding) {
        this.spoelleiding = spoelleiding;
    }

    public String getVorm() {
        return this.vorm;
    }

    public Bergingsbassin vorm(String vorm) {
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
        if (!(o instanceof Bergingsbassin)) {
            return false;
        }
        return getId() != null && getId().equals(((Bergingsbassin) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bergingsbassin{" +
            "id=" + getId() +
            ", bergendvermogen='" + getBergendvermogen() + "'" +
            ", pompledigingsvoorziening='" + getPompledigingsvoorziening() + "'" +
            ", pompspoelvoorziening='" + getPompspoelvoorziening() + "'" +
            ", spoelleiding='" + getSpoelleiding() + "'" +
            ", vorm='" + getVorm() + "'" +
            "}";
    }
}
