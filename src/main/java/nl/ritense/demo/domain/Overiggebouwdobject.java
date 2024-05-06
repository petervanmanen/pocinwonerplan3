package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Overiggebouwdobject.
 */
@Entity
@Table(name = "overiggebouwdobject")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Overiggebouwdobject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bouwjaar")
    private String bouwjaar;

    @Column(name = "indicatieplanobject")
    private String indicatieplanobject;

    @Column(name = "overiggebouwdobjectidentificatie")
    private String overiggebouwdobjectidentificatie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Overiggebouwdobject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBouwjaar() {
        return this.bouwjaar;
    }

    public Overiggebouwdobject bouwjaar(String bouwjaar) {
        this.setBouwjaar(bouwjaar);
        return this;
    }

    public void setBouwjaar(String bouwjaar) {
        this.bouwjaar = bouwjaar;
    }

    public String getIndicatieplanobject() {
        return this.indicatieplanobject;
    }

    public Overiggebouwdobject indicatieplanobject(String indicatieplanobject) {
        this.setIndicatieplanobject(indicatieplanobject);
        return this;
    }

    public void setIndicatieplanobject(String indicatieplanobject) {
        this.indicatieplanobject = indicatieplanobject;
    }

    public String getOveriggebouwdobjectidentificatie() {
        return this.overiggebouwdobjectidentificatie;
    }

    public Overiggebouwdobject overiggebouwdobjectidentificatie(String overiggebouwdobjectidentificatie) {
        this.setOveriggebouwdobjectidentificatie(overiggebouwdobjectidentificatie);
        return this;
    }

    public void setOveriggebouwdobjectidentificatie(String overiggebouwdobjectidentificatie) {
        this.overiggebouwdobjectidentificatie = overiggebouwdobjectidentificatie;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Overiggebouwdobject)) {
            return false;
        }
        return getId() != null && getId().equals(((Overiggebouwdobject) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Overiggebouwdobject{" +
            "id=" + getId() +
            ", bouwjaar='" + getBouwjaar() + "'" +
            ", indicatieplanobject='" + getIndicatieplanobject() + "'" +
            ", overiggebouwdobjectidentificatie='" + getOveriggebouwdobjectidentificatie() + "'" +
            "}";
    }
}
