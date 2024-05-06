package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Briefadres.
 */
@Entity
@Table(name = "briefadres")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Briefadres implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "adresfunctie")
    private String adresfunctie;

    @Column(name = "datumaanvang")
    private LocalDate datumaanvang;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "omschrijvingaangifte")
    private String omschrijvingaangifte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "ligtinWoonplaats",
            "ligtinBuurt",
            "ligtinGebieds",
            "verwijstnaarAdresaanduiding",
            "emptyBriefadres",
            "heeftalslocatieadresVestigings",
        },
        allowSetters = true
    )
    private Nummeraanduiding emptyNummeraanduiding;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Briefadres id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresfunctie() {
        return this.adresfunctie;
    }

    public Briefadres adresfunctie(String adresfunctie) {
        this.setAdresfunctie(adresfunctie);
        return this;
    }

    public void setAdresfunctie(String adresfunctie) {
        this.adresfunctie = adresfunctie;
    }

    public LocalDate getDatumaanvang() {
        return this.datumaanvang;
    }

    public Briefadres datumaanvang(LocalDate datumaanvang) {
        this.setDatumaanvang(datumaanvang);
        return this;
    }

    public void setDatumaanvang(LocalDate datumaanvang) {
        this.datumaanvang = datumaanvang;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Briefadres datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public String getOmschrijvingaangifte() {
        return this.omschrijvingaangifte;
    }

    public Briefadres omschrijvingaangifte(String omschrijvingaangifte) {
        this.setOmschrijvingaangifte(omschrijvingaangifte);
        return this;
    }

    public void setOmschrijvingaangifte(String omschrijvingaangifte) {
        this.omschrijvingaangifte = omschrijvingaangifte;
    }

    public Nummeraanduiding getEmptyNummeraanduiding() {
        return this.emptyNummeraanduiding;
    }

    public void setEmptyNummeraanduiding(Nummeraanduiding nummeraanduiding) {
        this.emptyNummeraanduiding = nummeraanduiding;
    }

    public Briefadres emptyNummeraanduiding(Nummeraanduiding nummeraanduiding) {
        this.setEmptyNummeraanduiding(nummeraanduiding);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Briefadres)) {
            return false;
        }
        return getId() != null && getId().equals(((Briefadres) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Briefadres{" +
            "id=" + getId() +
            ", adresfunctie='" + getAdresfunctie() + "'" +
            ", datumaanvang='" + getDatumaanvang() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", omschrijvingaangifte='" + getOmschrijvingaangifte() + "'" +
            "}";
    }
}
