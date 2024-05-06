package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Eobject.
 */
@Entity
@Table(name = "eobject")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Eobject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "adresbinnenland")
    private String adresbinnenland;

    @Column(name = "adresbuitenland")
    private String adresbuitenland;

    @Column(name = "domein")
    private String domein;

    @Column(name = "geometrie")
    private String geometrie;

    @Column(name = "identificatie")
    private String identificatie;

    @Column(name = "indicatierisico")
    private String indicatierisico;

    @Column(name = "kadastraleaanduiding")
    private String kadastraleaanduiding;

    @Column(name = "naam")
    private String naam;

    @Column(name = "eobjecttype")
    private String eobjecttype;

    @Column(name = "toelichting")
    private String toelichting;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Eobject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresbinnenland() {
        return this.adresbinnenland;
    }

    public Eobject adresbinnenland(String adresbinnenland) {
        this.setAdresbinnenland(adresbinnenland);
        return this;
    }

    public void setAdresbinnenland(String adresbinnenland) {
        this.adresbinnenland = adresbinnenland;
    }

    public String getAdresbuitenland() {
        return this.adresbuitenland;
    }

    public Eobject adresbuitenland(String adresbuitenland) {
        this.setAdresbuitenland(adresbuitenland);
        return this;
    }

    public void setAdresbuitenland(String adresbuitenland) {
        this.adresbuitenland = adresbuitenland;
    }

    public String getDomein() {
        return this.domein;
    }

    public Eobject domein(String domein) {
        this.setDomein(domein);
        return this;
    }

    public void setDomein(String domein) {
        this.domein = domein;
    }

    public String getGeometrie() {
        return this.geometrie;
    }

    public Eobject geometrie(String geometrie) {
        this.setGeometrie(geometrie);
        return this;
    }

    public void setGeometrie(String geometrie) {
        this.geometrie = geometrie;
    }

    public String getIdentificatie() {
        return this.identificatie;
    }

    public Eobject identificatie(String identificatie) {
        this.setIdentificatie(identificatie);
        return this;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public String getIndicatierisico() {
        return this.indicatierisico;
    }

    public Eobject indicatierisico(String indicatierisico) {
        this.setIndicatierisico(indicatierisico);
        return this;
    }

    public void setIndicatierisico(String indicatierisico) {
        this.indicatierisico = indicatierisico;
    }

    public String getKadastraleaanduiding() {
        return this.kadastraleaanduiding;
    }

    public Eobject kadastraleaanduiding(String kadastraleaanduiding) {
        this.setKadastraleaanduiding(kadastraleaanduiding);
        return this;
    }

    public void setKadastraleaanduiding(String kadastraleaanduiding) {
        this.kadastraleaanduiding = kadastraleaanduiding;
    }

    public String getNaam() {
        return this.naam;
    }

    public Eobject naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getEobjecttype() {
        return this.eobjecttype;
    }

    public Eobject eobjecttype(String eobjecttype) {
        this.setEobjecttype(eobjecttype);
        return this;
    }

    public void setEobjecttype(String eobjecttype) {
        this.eobjecttype = eobjecttype;
    }

    public String getToelichting() {
        return this.toelichting;
    }

    public Eobject toelichting(String toelichting) {
        this.setToelichting(toelichting);
        return this;
    }

    public void setToelichting(String toelichting) {
        this.toelichting = toelichting;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Eobject)) {
            return false;
        }
        return getId() != null && getId().equals(((Eobject) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Eobject{" +
            "id=" + getId() +
            ", adresbinnenland='" + getAdresbinnenland() + "'" +
            ", adresbuitenland='" + getAdresbuitenland() + "'" +
            ", domein='" + getDomein() + "'" +
            ", geometrie='" + getGeometrie() + "'" +
            ", identificatie='" + getIdentificatie() + "'" +
            ", indicatierisico='" + getIndicatierisico() + "'" +
            ", kadastraleaanduiding='" + getKadastraleaanduiding() + "'" +
            ", naam='" + getNaam() + "'" +
            ", eobjecttype='" + getEobjecttype() + "'" +
            ", toelichting='" + getToelichting() + "'" +
            "}";
    }
}
