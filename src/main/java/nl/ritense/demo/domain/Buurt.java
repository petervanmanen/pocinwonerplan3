package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Buurt.
 */
@Entity
@Table(name = "buurt")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Buurt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "buurtcode")
    private String buurtcode;

    @Column(name = "buurtgeometrie")
    private String buurtgeometrie;

    @Column(name = "buurtnaam")
    private String buurtnaam;

    @Column(name = "datumbegingeldigheidbuurt")
    private LocalDate datumbegingeldigheidbuurt;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumeindegeldigheidbuurt")
    private LocalDate datumeindegeldigheidbuurt;

    @Column(name = "datumingang")
    private LocalDate datumingang;

    @Column(name = "geconstateerd")
    private Boolean geconstateerd;

    @Column(name = "identificatie")
    private String identificatie;

    @Column(name = "inonderzoek")
    private Boolean inonderzoek;

    @Column(name = "status")
    private String status;

    @Column(name = "versie")
    private String versie;

    @JsonIgnoreProperties(value = { "komtovereenBuurt", "ligtinNummeraanduidings" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "komtovereenBuurt")
    private Gebied komtovereenGebied;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ligtinBuurt")
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
    private Set<Nummeraanduiding> ligtinNummeraanduidings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "zonderverblijfsobjectligtinBuurt")
    @JsonIgnoreProperties(
        value = { "heeftVastgoedobject", "zonderverblijfsobjectligtinBuurt", "betreftVastgoedobject", "maaktdeeluitvanVerblijfsobjects" },
        allowSetters = true
    )
    private Set<Pand> zonderverblijfsobjectligtinPands = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "ligtinBuurts")
    @JsonIgnoreProperties(value = { "ligtinBuurts", "valtbinnenWijks", "binnenSchouwrondes" }, allowSetters = true)
    private Set<Areaal> ligtinAreaals = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Buurt id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuurtcode() {
        return this.buurtcode;
    }

    public Buurt buurtcode(String buurtcode) {
        this.setBuurtcode(buurtcode);
        return this;
    }

    public void setBuurtcode(String buurtcode) {
        this.buurtcode = buurtcode;
    }

    public String getBuurtgeometrie() {
        return this.buurtgeometrie;
    }

    public Buurt buurtgeometrie(String buurtgeometrie) {
        this.setBuurtgeometrie(buurtgeometrie);
        return this;
    }

    public void setBuurtgeometrie(String buurtgeometrie) {
        this.buurtgeometrie = buurtgeometrie;
    }

    public String getBuurtnaam() {
        return this.buurtnaam;
    }

    public Buurt buurtnaam(String buurtnaam) {
        this.setBuurtnaam(buurtnaam);
        return this;
    }

    public void setBuurtnaam(String buurtnaam) {
        this.buurtnaam = buurtnaam;
    }

    public LocalDate getDatumbegingeldigheidbuurt() {
        return this.datumbegingeldigheidbuurt;
    }

    public Buurt datumbegingeldigheidbuurt(LocalDate datumbegingeldigheidbuurt) {
        this.setDatumbegingeldigheidbuurt(datumbegingeldigheidbuurt);
        return this;
    }

    public void setDatumbegingeldigheidbuurt(LocalDate datumbegingeldigheidbuurt) {
        this.datumbegingeldigheidbuurt = datumbegingeldigheidbuurt;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Buurt datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumeindegeldigheidbuurt() {
        return this.datumeindegeldigheidbuurt;
    }

    public Buurt datumeindegeldigheidbuurt(LocalDate datumeindegeldigheidbuurt) {
        this.setDatumeindegeldigheidbuurt(datumeindegeldigheidbuurt);
        return this;
    }

    public void setDatumeindegeldigheidbuurt(LocalDate datumeindegeldigheidbuurt) {
        this.datumeindegeldigheidbuurt = datumeindegeldigheidbuurt;
    }

    public LocalDate getDatumingang() {
        return this.datumingang;
    }

    public Buurt datumingang(LocalDate datumingang) {
        this.setDatumingang(datumingang);
        return this;
    }

    public void setDatumingang(LocalDate datumingang) {
        this.datumingang = datumingang;
    }

    public Boolean getGeconstateerd() {
        return this.geconstateerd;
    }

    public Buurt geconstateerd(Boolean geconstateerd) {
        this.setGeconstateerd(geconstateerd);
        return this;
    }

    public void setGeconstateerd(Boolean geconstateerd) {
        this.geconstateerd = geconstateerd;
    }

    public String getIdentificatie() {
        return this.identificatie;
    }

    public Buurt identificatie(String identificatie) {
        this.setIdentificatie(identificatie);
        return this;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public Boolean getInonderzoek() {
        return this.inonderzoek;
    }

    public Buurt inonderzoek(Boolean inonderzoek) {
        this.setInonderzoek(inonderzoek);
        return this;
    }

    public void setInonderzoek(Boolean inonderzoek) {
        this.inonderzoek = inonderzoek;
    }

    public String getStatus() {
        return this.status;
    }

    public Buurt status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersie() {
        return this.versie;
    }

    public Buurt versie(String versie) {
        this.setVersie(versie);
        return this;
    }

    public void setVersie(String versie) {
        this.versie = versie;
    }

    public Gebied getKomtovereenGebied() {
        return this.komtovereenGebied;
    }

    public void setKomtovereenGebied(Gebied gebied) {
        if (this.komtovereenGebied != null) {
            this.komtovereenGebied.setKomtovereenBuurt(null);
        }
        if (gebied != null) {
            gebied.setKomtovereenBuurt(this);
        }
        this.komtovereenGebied = gebied;
    }

    public Buurt komtovereenGebied(Gebied gebied) {
        this.setKomtovereenGebied(gebied);
        return this;
    }

    public Set<Nummeraanduiding> getLigtinNummeraanduidings() {
        return this.ligtinNummeraanduidings;
    }

    public void setLigtinNummeraanduidings(Set<Nummeraanduiding> nummeraanduidings) {
        if (this.ligtinNummeraanduidings != null) {
            this.ligtinNummeraanduidings.forEach(i -> i.setLigtinBuurt(null));
        }
        if (nummeraanduidings != null) {
            nummeraanduidings.forEach(i -> i.setLigtinBuurt(this));
        }
        this.ligtinNummeraanduidings = nummeraanduidings;
    }

    public Buurt ligtinNummeraanduidings(Set<Nummeraanduiding> nummeraanduidings) {
        this.setLigtinNummeraanduidings(nummeraanduidings);
        return this;
    }

    public Buurt addLigtinNummeraanduiding(Nummeraanduiding nummeraanduiding) {
        this.ligtinNummeraanduidings.add(nummeraanduiding);
        nummeraanduiding.setLigtinBuurt(this);
        return this;
    }

    public Buurt removeLigtinNummeraanduiding(Nummeraanduiding nummeraanduiding) {
        this.ligtinNummeraanduidings.remove(nummeraanduiding);
        nummeraanduiding.setLigtinBuurt(null);
        return this;
    }

    public Set<Pand> getZonderverblijfsobjectligtinPands() {
        return this.zonderverblijfsobjectligtinPands;
    }

    public void setZonderverblijfsobjectligtinPands(Set<Pand> pands) {
        if (this.zonderverblijfsobjectligtinPands != null) {
            this.zonderverblijfsobjectligtinPands.forEach(i -> i.setZonderverblijfsobjectligtinBuurt(null));
        }
        if (pands != null) {
            pands.forEach(i -> i.setZonderverblijfsobjectligtinBuurt(this));
        }
        this.zonderverblijfsobjectligtinPands = pands;
    }

    public Buurt zonderverblijfsobjectligtinPands(Set<Pand> pands) {
        this.setZonderverblijfsobjectligtinPands(pands);
        return this;
    }

    public Buurt addZonderverblijfsobjectligtinPand(Pand pand) {
        this.zonderverblijfsobjectligtinPands.add(pand);
        pand.setZonderverblijfsobjectligtinBuurt(this);
        return this;
    }

    public Buurt removeZonderverblijfsobjectligtinPand(Pand pand) {
        this.zonderverblijfsobjectligtinPands.remove(pand);
        pand.setZonderverblijfsobjectligtinBuurt(null);
        return this;
    }

    public Set<Areaal> getLigtinAreaals() {
        return this.ligtinAreaals;
    }

    public void setLigtinAreaals(Set<Areaal> areaals) {
        if (this.ligtinAreaals != null) {
            this.ligtinAreaals.forEach(i -> i.removeLigtinBuurt(this));
        }
        if (areaals != null) {
            areaals.forEach(i -> i.addLigtinBuurt(this));
        }
        this.ligtinAreaals = areaals;
    }

    public Buurt ligtinAreaals(Set<Areaal> areaals) {
        this.setLigtinAreaals(areaals);
        return this;
    }

    public Buurt addLigtinAreaal(Areaal areaal) {
        this.ligtinAreaals.add(areaal);
        areaal.getLigtinBuurts().add(this);
        return this;
    }

    public Buurt removeLigtinAreaal(Areaal areaal) {
        this.ligtinAreaals.remove(areaal);
        areaal.getLigtinBuurts().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Buurt)) {
            return false;
        }
        return getId() != null && getId().equals(((Buurt) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Buurt{" +
            "id=" + getId() +
            ", buurtcode='" + getBuurtcode() + "'" +
            ", buurtgeometrie='" + getBuurtgeometrie() + "'" +
            ", buurtnaam='" + getBuurtnaam() + "'" +
            ", datumbegingeldigheidbuurt='" + getDatumbegingeldigheidbuurt() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumeindegeldigheidbuurt='" + getDatumeindegeldigheidbuurt() + "'" +
            ", datumingang='" + getDatumingang() + "'" +
            ", geconstateerd='" + getGeconstateerd() + "'" +
            ", identificatie='" + getIdentificatie() + "'" +
            ", inonderzoek='" + getInonderzoek() + "'" +
            ", status='" + getStatus() + "'" +
            ", versie='" + getVersie() + "'" +
            "}";
    }
}
