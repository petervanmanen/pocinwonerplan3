package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Sbiactiviteitvestiging.
 */
@Entity
@Table(name = "sbiactiviteitvestiging")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sbiactiviteitvestiging implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 100)
    @Column(name = "indicatiehoofdactiviteit", length = 100)
    private String indicatiehoofdactiviteit;

    @Column(name = "sbicode")
    private String sbicode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sbiactiviteitvestiging id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndicatiehoofdactiviteit() {
        return this.indicatiehoofdactiviteit;
    }

    public Sbiactiviteitvestiging indicatiehoofdactiviteit(String indicatiehoofdactiviteit) {
        this.setIndicatiehoofdactiviteit(indicatiehoofdactiviteit);
        return this;
    }

    public void setIndicatiehoofdactiviteit(String indicatiehoofdactiviteit) {
        this.indicatiehoofdactiviteit = indicatiehoofdactiviteit;
    }

    public String getSbicode() {
        return this.sbicode;
    }

    public Sbiactiviteitvestiging sbicode(String sbicode) {
        this.setSbicode(sbicode);
        return this;
    }

    public void setSbicode(String sbicode) {
        this.sbicode = sbicode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sbiactiviteitvestiging)) {
            return false;
        }
        return getId() != null && getId().equals(((Sbiactiviteitvestiging) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sbiactiviteitvestiging{" +
            "id=" + getId() +
            ", indicatiehoofdactiviteit='" + getIndicatiehoofdactiviteit() + "'" +
            ", sbicode='" + getSbicode() + "'" +
            "}";
    }
}
