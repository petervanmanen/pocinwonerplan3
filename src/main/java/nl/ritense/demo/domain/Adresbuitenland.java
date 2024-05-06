package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Adresbuitenland.
 */
@Entity
@Table(name = "adresbuitenland")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Adresbuitenland implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "adresregelbuitenland_1")
    private String adresregelbuitenland1;

    @Column(name = "adresregelbuitenland_2")
    private String adresregelbuitenland2;

    @Column(name = "adresregelbuitenland_3")
    private String adresregelbuitenland3;

    @Column(name = "datumaanvangadresbuitenland")
    private LocalDate datumaanvangadresbuitenland;

    @Column(name = "datuminschrijvinggemeente")
    private LocalDate datuminschrijvinggemeente;

    @Column(name = "datumvestigingnederland")
    private LocalDate datumvestigingnederland;

    @Size(max = 100)
    @Column(name = "gemeentevaninschrijving", length = 100)
    private String gemeentevaninschrijving;

    @Column(name = "landadresbuitenland")
    private String landadresbuitenland;

    @Column(name = "landwaarvandaaningeschreven")
    private String landwaarvandaaningeschreven;

    @Size(max = 100)
    @Column(name = "omschrijvingvandeaangifteadreshouding", length = 100)
    private String omschrijvingvandeaangifteadreshouding;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Adresbuitenland id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresregelbuitenland1() {
        return this.adresregelbuitenland1;
    }

    public Adresbuitenland adresregelbuitenland1(String adresregelbuitenland1) {
        this.setAdresregelbuitenland1(adresregelbuitenland1);
        return this;
    }

    public void setAdresregelbuitenland1(String adresregelbuitenland1) {
        this.adresregelbuitenland1 = adresregelbuitenland1;
    }

    public String getAdresregelbuitenland2() {
        return this.adresregelbuitenland2;
    }

    public Adresbuitenland adresregelbuitenland2(String adresregelbuitenland2) {
        this.setAdresregelbuitenland2(adresregelbuitenland2);
        return this;
    }

    public void setAdresregelbuitenland2(String adresregelbuitenland2) {
        this.adresregelbuitenland2 = adresregelbuitenland2;
    }

    public String getAdresregelbuitenland3() {
        return this.adresregelbuitenland3;
    }

    public Adresbuitenland adresregelbuitenland3(String adresregelbuitenland3) {
        this.setAdresregelbuitenland3(adresregelbuitenland3);
        return this;
    }

    public void setAdresregelbuitenland3(String adresregelbuitenland3) {
        this.adresregelbuitenland3 = adresregelbuitenland3;
    }

    public LocalDate getDatumaanvangadresbuitenland() {
        return this.datumaanvangadresbuitenland;
    }

    public Adresbuitenland datumaanvangadresbuitenland(LocalDate datumaanvangadresbuitenland) {
        this.setDatumaanvangadresbuitenland(datumaanvangadresbuitenland);
        return this;
    }

    public void setDatumaanvangadresbuitenland(LocalDate datumaanvangadresbuitenland) {
        this.datumaanvangadresbuitenland = datumaanvangadresbuitenland;
    }

    public LocalDate getDatuminschrijvinggemeente() {
        return this.datuminschrijvinggemeente;
    }

    public Adresbuitenland datuminschrijvinggemeente(LocalDate datuminschrijvinggemeente) {
        this.setDatuminschrijvinggemeente(datuminschrijvinggemeente);
        return this;
    }

    public void setDatuminschrijvinggemeente(LocalDate datuminschrijvinggemeente) {
        this.datuminschrijvinggemeente = datuminschrijvinggemeente;
    }

    public LocalDate getDatumvestigingnederland() {
        return this.datumvestigingnederland;
    }

    public Adresbuitenland datumvestigingnederland(LocalDate datumvestigingnederland) {
        this.setDatumvestigingnederland(datumvestigingnederland);
        return this;
    }

    public void setDatumvestigingnederland(LocalDate datumvestigingnederland) {
        this.datumvestigingnederland = datumvestigingnederland;
    }

    public String getGemeentevaninschrijving() {
        return this.gemeentevaninschrijving;
    }

    public Adresbuitenland gemeentevaninschrijving(String gemeentevaninschrijving) {
        this.setGemeentevaninschrijving(gemeentevaninschrijving);
        return this;
    }

    public void setGemeentevaninschrijving(String gemeentevaninschrijving) {
        this.gemeentevaninschrijving = gemeentevaninschrijving;
    }

    public String getLandadresbuitenland() {
        return this.landadresbuitenland;
    }

    public Adresbuitenland landadresbuitenland(String landadresbuitenland) {
        this.setLandadresbuitenland(landadresbuitenland);
        return this;
    }

    public void setLandadresbuitenland(String landadresbuitenland) {
        this.landadresbuitenland = landadresbuitenland;
    }

    public String getLandwaarvandaaningeschreven() {
        return this.landwaarvandaaningeschreven;
    }

    public Adresbuitenland landwaarvandaaningeschreven(String landwaarvandaaningeschreven) {
        this.setLandwaarvandaaningeschreven(landwaarvandaaningeschreven);
        return this;
    }

    public void setLandwaarvandaaningeschreven(String landwaarvandaaningeschreven) {
        this.landwaarvandaaningeschreven = landwaarvandaaningeschreven;
    }

    public String getOmschrijvingvandeaangifteadreshouding() {
        return this.omschrijvingvandeaangifteadreshouding;
    }

    public Adresbuitenland omschrijvingvandeaangifteadreshouding(String omschrijvingvandeaangifteadreshouding) {
        this.setOmschrijvingvandeaangifteadreshouding(omschrijvingvandeaangifteadreshouding);
        return this;
    }

    public void setOmschrijvingvandeaangifteadreshouding(String omschrijvingvandeaangifteadreshouding) {
        this.omschrijvingvandeaangifteadreshouding = omschrijvingvandeaangifteadreshouding;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Adresbuitenland)) {
            return false;
        }
        return getId() != null && getId().equals(((Adresbuitenland) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Adresbuitenland{" +
            "id=" + getId() +
            ", adresregelbuitenland1='" + getAdresregelbuitenland1() + "'" +
            ", adresregelbuitenland2='" + getAdresregelbuitenland2() + "'" +
            ", adresregelbuitenland3='" + getAdresregelbuitenland3() + "'" +
            ", datumaanvangadresbuitenland='" + getDatumaanvangadresbuitenland() + "'" +
            ", datuminschrijvinggemeente='" + getDatuminschrijvinggemeente() + "'" +
            ", datumvestigingnederland='" + getDatumvestigingnederland() + "'" +
            ", gemeentevaninschrijving='" + getGemeentevaninschrijving() + "'" +
            ", landadresbuitenland='" + getLandadresbuitenland() + "'" +
            ", landwaarvandaaningeschreven='" + getLandwaarvandaaningeschreven() + "'" +
            ", omschrijvingvandeaangifteadreshouding='" + getOmschrijvingvandeaangifteadreshouding() + "'" +
            "}";
    }
}
