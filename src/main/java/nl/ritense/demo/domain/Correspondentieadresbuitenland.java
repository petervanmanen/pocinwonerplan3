package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Correspondentieadresbuitenland.
 */
@Entity
@Table(name = "correspondentieadresbuitenland")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Correspondentieadresbuitenland implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "adresbuitenland_1")
    private String adresbuitenland1;

    @Column(name = "adresbuitenland_2")
    private String adresbuitenland2;

    @Column(name = "adresbuitenland_3")
    private String adresbuitenland3;

    @Column(name = "adresbuitenland_4")
    private String adresbuitenland4;

    @Column(name = "adresbuitenland_5")
    private String adresbuitenland5;

    @Column(name = "adresbuitenland_6")
    private String adresbuitenland6;

    @Column(name = "landcorrespondentieadres")
    private String landcorrespondentieadres;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Correspondentieadresbuitenland id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresbuitenland1() {
        return this.adresbuitenland1;
    }

    public Correspondentieadresbuitenland adresbuitenland1(String adresbuitenland1) {
        this.setAdresbuitenland1(adresbuitenland1);
        return this;
    }

    public void setAdresbuitenland1(String adresbuitenland1) {
        this.adresbuitenland1 = adresbuitenland1;
    }

    public String getAdresbuitenland2() {
        return this.adresbuitenland2;
    }

    public Correspondentieadresbuitenland adresbuitenland2(String adresbuitenland2) {
        this.setAdresbuitenland2(adresbuitenland2);
        return this;
    }

    public void setAdresbuitenland2(String adresbuitenland2) {
        this.adresbuitenland2 = adresbuitenland2;
    }

    public String getAdresbuitenland3() {
        return this.adresbuitenland3;
    }

    public Correspondentieadresbuitenland adresbuitenland3(String adresbuitenland3) {
        this.setAdresbuitenland3(adresbuitenland3);
        return this;
    }

    public void setAdresbuitenland3(String adresbuitenland3) {
        this.adresbuitenland3 = adresbuitenland3;
    }

    public String getAdresbuitenland4() {
        return this.adresbuitenland4;
    }

    public Correspondentieadresbuitenland adresbuitenland4(String adresbuitenland4) {
        this.setAdresbuitenland4(adresbuitenland4);
        return this;
    }

    public void setAdresbuitenland4(String adresbuitenland4) {
        this.adresbuitenland4 = adresbuitenland4;
    }

    public String getAdresbuitenland5() {
        return this.adresbuitenland5;
    }

    public Correspondentieadresbuitenland adresbuitenland5(String adresbuitenland5) {
        this.setAdresbuitenland5(adresbuitenland5);
        return this;
    }

    public void setAdresbuitenland5(String adresbuitenland5) {
        this.adresbuitenland5 = adresbuitenland5;
    }

    public String getAdresbuitenland6() {
        return this.adresbuitenland6;
    }

    public Correspondentieadresbuitenland adresbuitenland6(String adresbuitenland6) {
        this.setAdresbuitenland6(adresbuitenland6);
        return this;
    }

    public void setAdresbuitenland6(String adresbuitenland6) {
        this.adresbuitenland6 = adresbuitenland6;
    }

    public String getLandcorrespondentieadres() {
        return this.landcorrespondentieadres;
    }

    public Correspondentieadresbuitenland landcorrespondentieadres(String landcorrespondentieadres) {
        this.setLandcorrespondentieadres(landcorrespondentieadres);
        return this;
    }

    public void setLandcorrespondentieadres(String landcorrespondentieadres) {
        this.landcorrespondentieadres = landcorrespondentieadres;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Correspondentieadresbuitenland)) {
            return false;
        }
        return getId() != null && getId().equals(((Correspondentieadresbuitenland) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Correspondentieadresbuitenland{" +
            "id=" + getId() +
            ", adresbuitenland1='" + getAdresbuitenland1() + "'" +
            ", adresbuitenland2='" + getAdresbuitenland2() + "'" +
            ", adresbuitenland3='" + getAdresbuitenland3() + "'" +
            ", adresbuitenland4='" + getAdresbuitenland4() + "'" +
            ", adresbuitenland5='" + getAdresbuitenland5() + "'" +
            ", adresbuitenland6='" + getAdresbuitenland6() + "'" +
            ", landcorrespondentieadres='" + getLandcorrespondentieadres() + "'" +
            "}";
    }
}
