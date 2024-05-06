package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Videoopname.
 */
@Entity
@Table(name = "videoopname")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Videoopname implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bestandsgrootte")
    private String bestandsgrootte;

    @Column(name = "datumtijd")
    private String datumtijd;

    @Column(name = "lengte")
    private String lengte;

    @Column(name = "videoformaat")
    private String videoformaat;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Videoopname id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBestandsgrootte() {
        return this.bestandsgrootte;
    }

    public Videoopname bestandsgrootte(String bestandsgrootte) {
        this.setBestandsgrootte(bestandsgrootte);
        return this;
    }

    public void setBestandsgrootte(String bestandsgrootte) {
        this.bestandsgrootte = bestandsgrootte;
    }

    public String getDatumtijd() {
        return this.datumtijd;
    }

    public Videoopname datumtijd(String datumtijd) {
        this.setDatumtijd(datumtijd);
        return this;
    }

    public void setDatumtijd(String datumtijd) {
        this.datumtijd = datumtijd;
    }

    public String getLengte() {
        return this.lengte;
    }

    public Videoopname lengte(String lengte) {
        this.setLengte(lengte);
        return this;
    }

    public void setLengte(String lengte) {
        this.lengte = lengte;
    }

    public String getVideoformaat() {
        return this.videoformaat;
    }

    public Videoopname videoformaat(String videoformaat) {
        this.setVideoformaat(videoformaat);
        return this;
    }

    public void setVideoformaat(String videoformaat) {
        this.videoformaat = videoformaat;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Videoopname)) {
            return false;
        }
        return getId() != null && getId().equals(((Videoopname) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Videoopname{" +
            "id=" + getId() +
            ", bestandsgrootte='" + getBestandsgrootte() + "'" +
            ", datumtijd='" + getDatumtijd() + "'" +
            ", lengte='" + getLengte() + "'" +
            ", videoformaat='" + getVideoformaat() + "'" +
            "}";
    }
}
