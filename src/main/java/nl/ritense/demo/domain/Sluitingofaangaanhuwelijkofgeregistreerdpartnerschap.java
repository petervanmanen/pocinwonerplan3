package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.
 */
@Entity
@Table(name = "sluitingofaangaanhuwelijkofgeregistreerdpartnerschap")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "buitenlandseplaatsaanvang")
    private String buitenlandseplaatsaanvang;

    @Column(name = "buitenlandseregioaanvang")
    private String buitenlandseregioaanvang;

    @Column(name = "datumaanvang")
    private String datumaanvang;

    @Column(name = "gemeenteaanvang")
    private String gemeenteaanvang;

    @Column(name = "landofgebiedaanvang")
    private String landofgebiedaanvang;

    @Column(name = "omschrijvinglocatieaanvang")
    private String omschrijvinglocatieaanvang;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuitenlandseplaatsaanvang() {
        return this.buitenlandseplaatsaanvang;
    }

    public Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap buitenlandseplaatsaanvang(String buitenlandseplaatsaanvang) {
        this.setBuitenlandseplaatsaanvang(buitenlandseplaatsaanvang);
        return this;
    }

    public void setBuitenlandseplaatsaanvang(String buitenlandseplaatsaanvang) {
        this.buitenlandseplaatsaanvang = buitenlandseplaatsaanvang;
    }

    public String getBuitenlandseregioaanvang() {
        return this.buitenlandseregioaanvang;
    }

    public Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap buitenlandseregioaanvang(String buitenlandseregioaanvang) {
        this.setBuitenlandseregioaanvang(buitenlandseregioaanvang);
        return this;
    }

    public void setBuitenlandseregioaanvang(String buitenlandseregioaanvang) {
        this.buitenlandseregioaanvang = buitenlandseregioaanvang;
    }

    public String getDatumaanvang() {
        return this.datumaanvang;
    }

    public Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap datumaanvang(String datumaanvang) {
        this.setDatumaanvang(datumaanvang);
        return this;
    }

    public void setDatumaanvang(String datumaanvang) {
        this.datumaanvang = datumaanvang;
    }

    public String getGemeenteaanvang() {
        return this.gemeenteaanvang;
    }

    public Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap gemeenteaanvang(String gemeenteaanvang) {
        this.setGemeenteaanvang(gemeenteaanvang);
        return this;
    }

    public void setGemeenteaanvang(String gemeenteaanvang) {
        this.gemeenteaanvang = gemeenteaanvang;
    }

    public String getLandofgebiedaanvang() {
        return this.landofgebiedaanvang;
    }

    public Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap landofgebiedaanvang(String landofgebiedaanvang) {
        this.setLandofgebiedaanvang(landofgebiedaanvang);
        return this;
    }

    public void setLandofgebiedaanvang(String landofgebiedaanvang) {
        this.landofgebiedaanvang = landofgebiedaanvang;
    }

    public String getOmschrijvinglocatieaanvang() {
        return this.omschrijvinglocatieaanvang;
    }

    public Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap omschrijvinglocatieaanvang(String omschrijvinglocatieaanvang) {
        this.setOmschrijvinglocatieaanvang(omschrijvinglocatieaanvang);
        return this;
    }

    public void setOmschrijvinglocatieaanvang(String omschrijvinglocatieaanvang) {
        this.omschrijvinglocatieaanvang = omschrijvinglocatieaanvang;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap)) {
            return false;
        }
        return getId() != null && getId().equals(((Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap{" +
            "id=" + getId() +
            ", buitenlandseplaatsaanvang='" + getBuitenlandseplaatsaanvang() + "'" +
            ", buitenlandseregioaanvang='" + getBuitenlandseregioaanvang() + "'" +
            ", datumaanvang='" + getDatumaanvang() + "'" +
            ", gemeenteaanvang='" + getGemeenteaanvang() + "'" +
            ", landofgebiedaanvang='" + getLandofgebiedaanvang() + "'" +
            ", omschrijvinglocatieaanvang='" + getOmschrijvinglocatieaanvang() + "'" +
            "}";
    }
}
