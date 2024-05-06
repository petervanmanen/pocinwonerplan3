package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Enumenumerationa.
 */
@Entity
@Table(name = "enumenumerationa")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Enumenumerationa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "optie_1")
    private String optie1;

    @Column(name = "optie_2")
    private String optie2;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Integer id;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getOptie1() {
        return this.optie1;
    }

    public Enumenumerationa optie1(String optie1) {
        this.setOptie1(optie1);
        return this;
    }

    public void setOptie1(String optie1) {
        this.optie1 = optie1;
    }

    public String getOptie2() {
        return this.optie2;
    }

    public Enumenumerationa optie2(String optie2) {
        this.setOptie2(optie2);
        return this;
    }

    public void setOptie2(String optie2) {
        this.optie2 = optie2;
    }

    public Integer getId() {
        return this.id;
    }

    public Enumenumerationa id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Enumenumerationa)) {
            return false;
        }
        return getId() != null && getId().equals(((Enumenumerationa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Enumenumerationa{" +
            "id=" + getId() +
            ", optie1='" + getOptie1() + "'" +
            ", optie2='" + getOptie2() + "'" +
            "}";
    }
}
