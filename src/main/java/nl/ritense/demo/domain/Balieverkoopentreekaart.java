package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Balieverkoopentreekaart.
 */
@Entity
@Table(name = "balieverkoopentreekaart")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Balieverkoopentreekaart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumeindegeldigheid")
    private String datumeindegeldigheid;

    @Column(name = "datumstart")
    private String datumstart;

    @Column(name = "gebruiktop")
    private String gebruiktop;

    @Column(name = "rondleiding")
    private String rondleiding;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Balieverkoopentreekaart id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatumeindegeldigheid() {
        return this.datumeindegeldigheid;
    }

    public Balieverkoopentreekaart datumeindegeldigheid(String datumeindegeldigheid) {
        this.setDatumeindegeldigheid(datumeindegeldigheid);
        return this;
    }

    public void setDatumeindegeldigheid(String datumeindegeldigheid) {
        this.datumeindegeldigheid = datumeindegeldigheid;
    }

    public String getDatumstart() {
        return this.datumstart;
    }

    public Balieverkoopentreekaart datumstart(String datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(String datumstart) {
        this.datumstart = datumstart;
    }

    public String getGebruiktop() {
        return this.gebruiktop;
    }

    public Balieverkoopentreekaart gebruiktop(String gebruiktop) {
        this.setGebruiktop(gebruiktop);
        return this;
    }

    public void setGebruiktop(String gebruiktop) {
        this.gebruiktop = gebruiktop;
    }

    public String getRondleiding() {
        return this.rondleiding;
    }

    public Balieverkoopentreekaart rondleiding(String rondleiding) {
        this.setRondleiding(rondleiding);
        return this;
    }

    public void setRondleiding(String rondleiding) {
        this.rondleiding = rondleiding;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Balieverkoopentreekaart)) {
            return false;
        }
        return getId() != null && getId().equals(((Balieverkoopentreekaart) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Balieverkoopentreekaart{" +
            "id=" + getId() +
            ", datumeindegeldigheid='" + getDatumeindegeldigheid() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", gebruiktop='" + getGebruiktop() + "'" +
            ", rondleiding='" + getRondleiding() + "'" +
            "}";
    }
}
