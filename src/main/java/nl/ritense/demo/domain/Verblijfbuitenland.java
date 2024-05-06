package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Verblijfbuitenland.
 */
@Entity
@Table(name = "verblijfbuitenland")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Verblijfbuitenland implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "adresregelbuitenland_1")
    private String adresregelbuitenland1;

    @Column(name = "adresregelbuitenland_2")
    private String adresregelbuitenland2;

    @Column(name = "adresregelbuitenland_3")
    private String adresregelbuitenland3;

    @Column(name = "adresregelbuitenland_4")
    private String adresregelbuitenland4;

    @Column(name = "adresregelbuitenland_5")
    private String adresregelbuitenland5;

    @Column(name = "adresregelbuitenland_6")
    private String adresregelbuitenland6;

    @Column(name = "landofgebiedverblijfadres")
    private String landofgebiedverblijfadres;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Verblijfbuitenland id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresregelbuitenland1() {
        return this.adresregelbuitenland1;
    }

    public Verblijfbuitenland adresregelbuitenland1(String adresregelbuitenland1) {
        this.setAdresregelbuitenland1(adresregelbuitenland1);
        return this;
    }

    public void setAdresregelbuitenland1(String adresregelbuitenland1) {
        this.adresregelbuitenland1 = adresregelbuitenland1;
    }

    public String getAdresregelbuitenland2() {
        return this.adresregelbuitenland2;
    }

    public Verblijfbuitenland adresregelbuitenland2(String adresregelbuitenland2) {
        this.setAdresregelbuitenland2(adresregelbuitenland2);
        return this;
    }

    public void setAdresregelbuitenland2(String adresregelbuitenland2) {
        this.adresregelbuitenland2 = adresregelbuitenland2;
    }

    public String getAdresregelbuitenland3() {
        return this.adresregelbuitenland3;
    }

    public Verblijfbuitenland adresregelbuitenland3(String adresregelbuitenland3) {
        this.setAdresregelbuitenland3(adresregelbuitenland3);
        return this;
    }

    public void setAdresregelbuitenland3(String adresregelbuitenland3) {
        this.adresregelbuitenland3 = adresregelbuitenland3;
    }

    public String getAdresregelbuitenland4() {
        return this.adresregelbuitenland4;
    }

    public Verblijfbuitenland adresregelbuitenland4(String adresregelbuitenland4) {
        this.setAdresregelbuitenland4(adresregelbuitenland4);
        return this;
    }

    public void setAdresregelbuitenland4(String adresregelbuitenland4) {
        this.adresregelbuitenland4 = adresregelbuitenland4;
    }

    public String getAdresregelbuitenland5() {
        return this.adresregelbuitenland5;
    }

    public Verblijfbuitenland adresregelbuitenland5(String adresregelbuitenland5) {
        this.setAdresregelbuitenland5(adresregelbuitenland5);
        return this;
    }

    public void setAdresregelbuitenland5(String adresregelbuitenland5) {
        this.adresregelbuitenland5 = adresregelbuitenland5;
    }

    public String getAdresregelbuitenland6() {
        return this.adresregelbuitenland6;
    }

    public Verblijfbuitenland adresregelbuitenland6(String adresregelbuitenland6) {
        this.setAdresregelbuitenland6(adresregelbuitenland6);
        return this;
    }

    public void setAdresregelbuitenland6(String adresregelbuitenland6) {
        this.adresregelbuitenland6 = adresregelbuitenland6;
    }

    public String getLandofgebiedverblijfadres() {
        return this.landofgebiedverblijfadres;
    }

    public Verblijfbuitenland landofgebiedverblijfadres(String landofgebiedverblijfadres) {
        this.setLandofgebiedverblijfadres(landofgebiedverblijfadres);
        return this;
    }

    public void setLandofgebiedverblijfadres(String landofgebiedverblijfadres) {
        this.landofgebiedverblijfadres = landofgebiedverblijfadres;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Verblijfbuitenland)) {
            return false;
        }
        return getId() != null && getId().equals(((Verblijfbuitenland) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Verblijfbuitenland{" +
            "id=" + getId() +
            ", adresregelbuitenland1='" + getAdresregelbuitenland1() + "'" +
            ", adresregelbuitenland2='" + getAdresregelbuitenland2() + "'" +
            ", adresregelbuitenland3='" + getAdresregelbuitenland3() + "'" +
            ", adresregelbuitenland4='" + getAdresregelbuitenland4() + "'" +
            ", adresregelbuitenland5='" + getAdresregelbuitenland5() + "'" +
            ", adresregelbuitenland6='" + getAdresregelbuitenland6() + "'" +
            ", landofgebiedverblijfadres='" + getLandofgebiedverblijfadres() + "'" +
            "}";
    }
}
