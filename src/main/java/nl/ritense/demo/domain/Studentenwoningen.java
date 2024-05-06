package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Studentenwoningen.
 */
@Entity
@Table(name = "studentenwoningen")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Studentenwoningen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "huurprijs", precision = 21, scale = 2)
    private BigDecimal huurprijs;

    @Column(name = "zelfstandig")
    private Boolean zelfstandig;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Studentenwoningen id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getHuurprijs() {
        return this.huurprijs;
    }

    public Studentenwoningen huurprijs(BigDecimal huurprijs) {
        this.setHuurprijs(huurprijs);
        return this;
    }

    public void setHuurprijs(BigDecimal huurprijs) {
        this.huurprijs = huurprijs;
    }

    public Boolean getZelfstandig() {
        return this.zelfstandig;
    }

    public Studentenwoningen zelfstandig(Boolean zelfstandig) {
        this.setZelfstandig(zelfstandig);
        return this;
    }

    public void setZelfstandig(Boolean zelfstandig) {
        this.zelfstandig = zelfstandig;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Studentenwoningen)) {
            return false;
        }
        return getId() != null && getId().equals(((Studentenwoningen) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Studentenwoningen{" +
            "id=" + getId() +
            ", huurprijs=" + getHuurprijs() +
            ", zelfstandig='" + getZelfstandig() + "'" +
            "}";
    }
}
