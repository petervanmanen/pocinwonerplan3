package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Afwijkendbuitenlandscorrespondentieadresrol.
 */
@Entity
@Table(name = "afwijkendbuitenlandscorrespondentieadresrol")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Afwijkendbuitenlandscorrespondentieadresrol implements Serializable {

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

    @Column(name = "landpostadres")
    private String landpostadres;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Afwijkendbuitenlandscorrespondentieadresrol id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresbuitenland1() {
        return this.adresbuitenland1;
    }

    public Afwijkendbuitenlandscorrespondentieadresrol adresbuitenland1(String adresbuitenland1) {
        this.setAdresbuitenland1(adresbuitenland1);
        return this;
    }

    public void setAdresbuitenland1(String adresbuitenland1) {
        this.adresbuitenland1 = adresbuitenland1;
    }

    public String getAdresbuitenland2() {
        return this.adresbuitenland2;
    }

    public Afwijkendbuitenlandscorrespondentieadresrol adresbuitenland2(String adresbuitenland2) {
        this.setAdresbuitenland2(adresbuitenland2);
        return this;
    }

    public void setAdresbuitenland2(String adresbuitenland2) {
        this.adresbuitenland2 = adresbuitenland2;
    }

    public String getAdresbuitenland3() {
        return this.adresbuitenland3;
    }

    public Afwijkendbuitenlandscorrespondentieadresrol adresbuitenland3(String adresbuitenland3) {
        this.setAdresbuitenland3(adresbuitenland3);
        return this;
    }

    public void setAdresbuitenland3(String adresbuitenland3) {
        this.adresbuitenland3 = adresbuitenland3;
    }

    public String getLandpostadres() {
        return this.landpostadres;
    }

    public Afwijkendbuitenlandscorrespondentieadresrol landpostadres(String landpostadres) {
        this.setLandpostadres(landpostadres);
        return this;
    }

    public void setLandpostadres(String landpostadres) {
        this.landpostadres = landpostadres;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Afwijkendbuitenlandscorrespondentieadresrol)) {
            return false;
        }
        return getId() != null && getId().equals(((Afwijkendbuitenlandscorrespondentieadresrol) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Afwijkendbuitenlandscorrespondentieadresrol{" +
            "id=" + getId() +
            ", adresbuitenland1='" + getAdresbuitenland1() + "'" +
            ", adresbuitenland2='" + getAdresbuitenland2() + "'" +
            ", adresbuitenland3='" + getAdresbuitenland3() + "'" +
            ", landpostadres='" + getLandpostadres() + "'" +
            "}";
    }
}
