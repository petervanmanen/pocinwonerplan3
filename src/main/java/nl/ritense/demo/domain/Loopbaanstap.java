package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Loopbaanstap.
 */
@Entity
@Table(name = "loopbaanstap")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Loopbaanstap implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "klas")
    private String klas;

    @Column(name = "onderwijstype")
    private String onderwijstype;

    @Column(name = "schooljaar")
    private String schooljaar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "emptyLoopbaanstaps", "heeftLeerling", "kentSchools" }, allowSetters = true)
    private Onderwijsloopbaan emptyOnderwijsloopbaan;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Loopbaanstap id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKlas() {
        return this.klas;
    }

    public Loopbaanstap klas(String klas) {
        this.setKlas(klas);
        return this;
    }

    public void setKlas(String klas) {
        this.klas = klas;
    }

    public String getOnderwijstype() {
        return this.onderwijstype;
    }

    public Loopbaanstap onderwijstype(String onderwijstype) {
        this.setOnderwijstype(onderwijstype);
        return this;
    }

    public void setOnderwijstype(String onderwijstype) {
        this.onderwijstype = onderwijstype;
    }

    public String getSchooljaar() {
        return this.schooljaar;
    }

    public Loopbaanstap schooljaar(String schooljaar) {
        this.setSchooljaar(schooljaar);
        return this;
    }

    public void setSchooljaar(String schooljaar) {
        this.schooljaar = schooljaar;
    }

    public Onderwijsloopbaan getEmptyOnderwijsloopbaan() {
        return this.emptyOnderwijsloopbaan;
    }

    public void setEmptyOnderwijsloopbaan(Onderwijsloopbaan onderwijsloopbaan) {
        this.emptyOnderwijsloopbaan = onderwijsloopbaan;
    }

    public Loopbaanstap emptyOnderwijsloopbaan(Onderwijsloopbaan onderwijsloopbaan) {
        this.setEmptyOnderwijsloopbaan(onderwijsloopbaan);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Loopbaanstap)) {
            return false;
        }
        return getId() != null && getId().equals(((Loopbaanstap) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Loopbaanstap{" +
            "id=" + getId() +
            ", klas='" + getKlas() + "'" +
            ", onderwijstype='" + getOnderwijstype() + "'" +
            ", schooljaar='" + getSchooljaar() + "'" +
            "}";
    }
}
