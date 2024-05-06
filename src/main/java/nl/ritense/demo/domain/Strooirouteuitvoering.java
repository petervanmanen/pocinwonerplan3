package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Strooirouteuitvoering.
 */
@Entity
@Table(name = "strooirouteuitvoering")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Strooirouteuitvoering implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "geplandeinde")
    private String geplandeinde;

    @Column(name = "geplandstart")
    private String geplandstart;

    @Column(name = "eroute")
    private String eroute;

    @Column(name = "werkelijkeinde")
    private String werkelijkeinde;

    @Column(name = "werkelijkestart")
    private String werkelijkestart;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Strooirouteuitvoering id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGeplandeinde() {
        return this.geplandeinde;
    }

    public Strooirouteuitvoering geplandeinde(String geplandeinde) {
        this.setGeplandeinde(geplandeinde);
        return this;
    }

    public void setGeplandeinde(String geplandeinde) {
        this.geplandeinde = geplandeinde;
    }

    public String getGeplandstart() {
        return this.geplandstart;
    }

    public Strooirouteuitvoering geplandstart(String geplandstart) {
        this.setGeplandstart(geplandstart);
        return this;
    }

    public void setGeplandstart(String geplandstart) {
        this.geplandstart = geplandstart;
    }

    public String getEroute() {
        return this.eroute;
    }

    public Strooirouteuitvoering eroute(String eroute) {
        this.setEroute(eroute);
        return this;
    }

    public void setEroute(String eroute) {
        this.eroute = eroute;
    }

    public String getWerkelijkeinde() {
        return this.werkelijkeinde;
    }

    public Strooirouteuitvoering werkelijkeinde(String werkelijkeinde) {
        this.setWerkelijkeinde(werkelijkeinde);
        return this;
    }

    public void setWerkelijkeinde(String werkelijkeinde) {
        this.werkelijkeinde = werkelijkeinde;
    }

    public String getWerkelijkestart() {
        return this.werkelijkestart;
    }

    public Strooirouteuitvoering werkelijkestart(String werkelijkestart) {
        this.setWerkelijkestart(werkelijkestart);
        return this;
    }

    public void setWerkelijkestart(String werkelijkestart) {
        this.werkelijkestart = werkelijkestart;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Strooirouteuitvoering)) {
            return false;
        }
        return getId() != null && getId().equals(((Strooirouteuitvoering) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Strooirouteuitvoering{" +
            "id=" + getId() +
            ", geplandeinde='" + getGeplandeinde() + "'" +
            ", geplandstart='" + getGeplandstart() + "'" +
            ", eroute='" + getEroute() + "'" +
            ", werkelijkeinde='" + getWerkelijkeinde() + "'" +
            ", werkelijkestart='" + getWerkelijkestart() + "'" +
            "}";
    }
}
