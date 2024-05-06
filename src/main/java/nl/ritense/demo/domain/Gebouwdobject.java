package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Gebouwdobject.
 */
@Entity
@Table(name = "gebouwdobject")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Gebouwdobject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bouwkundigebestemmingactueel")
    private String bouwkundigebestemmingactueel;

    @Column(name = "brutoinhoud")
    private String brutoinhoud;

    @Size(max = 100)
    @Column(name = "identificatie", length = 100)
    private String identificatie;

    @Column(name = "inwinningoppervlakte")
    private String inwinningoppervlakte;

    @Column(name = "oppervlakteobject")
    private String oppervlakteobject;

    @Column(name = "statusvoortgangbouw")
    private String statusvoortgangbouw;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Gebouwdobject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBouwkundigebestemmingactueel() {
        return this.bouwkundigebestemmingactueel;
    }

    public Gebouwdobject bouwkundigebestemmingactueel(String bouwkundigebestemmingactueel) {
        this.setBouwkundigebestemmingactueel(bouwkundigebestemmingactueel);
        return this;
    }

    public void setBouwkundigebestemmingactueel(String bouwkundigebestemmingactueel) {
        this.bouwkundigebestemmingactueel = bouwkundigebestemmingactueel;
    }

    public String getBrutoinhoud() {
        return this.brutoinhoud;
    }

    public Gebouwdobject brutoinhoud(String brutoinhoud) {
        this.setBrutoinhoud(brutoinhoud);
        return this;
    }

    public void setBrutoinhoud(String brutoinhoud) {
        this.brutoinhoud = brutoinhoud;
    }

    public String getIdentificatie() {
        return this.identificatie;
    }

    public Gebouwdobject identificatie(String identificatie) {
        this.setIdentificatie(identificatie);
        return this;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public String getInwinningoppervlakte() {
        return this.inwinningoppervlakte;
    }

    public Gebouwdobject inwinningoppervlakte(String inwinningoppervlakte) {
        this.setInwinningoppervlakte(inwinningoppervlakte);
        return this;
    }

    public void setInwinningoppervlakte(String inwinningoppervlakte) {
        this.inwinningoppervlakte = inwinningoppervlakte;
    }

    public String getOppervlakteobject() {
        return this.oppervlakteobject;
    }

    public Gebouwdobject oppervlakteobject(String oppervlakteobject) {
        this.setOppervlakteobject(oppervlakteobject);
        return this;
    }

    public void setOppervlakteobject(String oppervlakteobject) {
        this.oppervlakteobject = oppervlakteobject;
    }

    public String getStatusvoortgangbouw() {
        return this.statusvoortgangbouw;
    }

    public Gebouwdobject statusvoortgangbouw(String statusvoortgangbouw) {
        this.setStatusvoortgangbouw(statusvoortgangbouw);
        return this;
    }

    public void setStatusvoortgangbouw(String statusvoortgangbouw) {
        this.statusvoortgangbouw = statusvoortgangbouw;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gebouwdobject)) {
            return false;
        }
        return getId() != null && getId().equals(((Gebouwdobject) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Gebouwdobject{" +
            "id=" + getId() +
            ", bouwkundigebestemmingactueel='" + getBouwkundigebestemmingactueel() + "'" +
            ", brutoinhoud='" + getBrutoinhoud() + "'" +
            ", identificatie='" + getIdentificatie() + "'" +
            ", inwinningoppervlakte='" + getInwinningoppervlakte() + "'" +
            ", oppervlakteobject='" + getOppervlakteobject() + "'" +
            ", statusvoortgangbouw='" + getStatusvoortgangbouw() + "'" +
            "}";
    }
}
