package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Besluittype.
 */
@Entity
@Table(name = "besluittype")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Besluittype implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "besluitcategorie")
    private String besluitcategorie;

    @Column(name = "besluittypeomschrijving")
    private String besluittypeomschrijving;

    @Column(name = "besluittypeomschrijvinggeneriek")
    private String besluittypeomschrijvinggeneriek;

    @Column(name = "datumbegingeldigheidbesluittype")
    private String datumbegingeldigheidbesluittype;

    @Column(name = "datumeindegeldigheidbesluittype")
    private String datumeindegeldigheidbesluittype;

    @Column(name = "indicatiepublicatie")
    private String indicatiepublicatie;

    @Column(name = "publicatietekst")
    private String publicatietekst;

    @Column(name = "publicatietermijn")
    private String publicatietermijn;

    @Column(name = "reactietermijn")
    private String reactietermijn;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "isvanBesluittype")
    @JsonIgnoreProperties(
        value = { "isvastgelegdinDocument", "isuitkomstvanZaak", "isvanBesluittype", "kanvastgelegdzijnalsDocuments" },
        allowSetters = true
    )
    private Set<Besluit> isvanBesluits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Besluittype id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBesluitcategorie() {
        return this.besluitcategorie;
    }

    public Besluittype besluitcategorie(String besluitcategorie) {
        this.setBesluitcategorie(besluitcategorie);
        return this;
    }

    public void setBesluitcategorie(String besluitcategorie) {
        this.besluitcategorie = besluitcategorie;
    }

    public String getBesluittypeomschrijving() {
        return this.besluittypeomschrijving;
    }

    public Besluittype besluittypeomschrijving(String besluittypeomschrijving) {
        this.setBesluittypeomschrijving(besluittypeomschrijving);
        return this;
    }

    public void setBesluittypeomschrijving(String besluittypeomschrijving) {
        this.besluittypeomschrijving = besluittypeomschrijving;
    }

    public String getBesluittypeomschrijvinggeneriek() {
        return this.besluittypeomschrijvinggeneriek;
    }

    public Besluittype besluittypeomschrijvinggeneriek(String besluittypeomschrijvinggeneriek) {
        this.setBesluittypeomschrijvinggeneriek(besluittypeomschrijvinggeneriek);
        return this;
    }

    public void setBesluittypeomschrijvinggeneriek(String besluittypeomschrijvinggeneriek) {
        this.besluittypeomschrijvinggeneriek = besluittypeomschrijvinggeneriek;
    }

    public String getDatumbegingeldigheidbesluittype() {
        return this.datumbegingeldigheidbesluittype;
    }

    public Besluittype datumbegingeldigheidbesluittype(String datumbegingeldigheidbesluittype) {
        this.setDatumbegingeldigheidbesluittype(datumbegingeldigheidbesluittype);
        return this;
    }

    public void setDatumbegingeldigheidbesluittype(String datumbegingeldigheidbesluittype) {
        this.datumbegingeldigheidbesluittype = datumbegingeldigheidbesluittype;
    }

    public String getDatumeindegeldigheidbesluittype() {
        return this.datumeindegeldigheidbesluittype;
    }

    public Besluittype datumeindegeldigheidbesluittype(String datumeindegeldigheidbesluittype) {
        this.setDatumeindegeldigheidbesluittype(datumeindegeldigheidbesluittype);
        return this;
    }

    public void setDatumeindegeldigheidbesluittype(String datumeindegeldigheidbesluittype) {
        this.datumeindegeldigheidbesluittype = datumeindegeldigheidbesluittype;
    }

    public String getIndicatiepublicatie() {
        return this.indicatiepublicatie;
    }

    public Besluittype indicatiepublicatie(String indicatiepublicatie) {
        this.setIndicatiepublicatie(indicatiepublicatie);
        return this;
    }

    public void setIndicatiepublicatie(String indicatiepublicatie) {
        this.indicatiepublicatie = indicatiepublicatie;
    }

    public String getPublicatietekst() {
        return this.publicatietekst;
    }

    public Besluittype publicatietekst(String publicatietekst) {
        this.setPublicatietekst(publicatietekst);
        return this;
    }

    public void setPublicatietekst(String publicatietekst) {
        this.publicatietekst = publicatietekst;
    }

    public String getPublicatietermijn() {
        return this.publicatietermijn;
    }

    public Besluittype publicatietermijn(String publicatietermijn) {
        this.setPublicatietermijn(publicatietermijn);
        return this;
    }

    public void setPublicatietermijn(String publicatietermijn) {
        this.publicatietermijn = publicatietermijn;
    }

    public String getReactietermijn() {
        return this.reactietermijn;
    }

    public Besluittype reactietermijn(String reactietermijn) {
        this.setReactietermijn(reactietermijn);
        return this;
    }

    public void setReactietermijn(String reactietermijn) {
        this.reactietermijn = reactietermijn;
    }

    public Set<Besluit> getIsvanBesluits() {
        return this.isvanBesluits;
    }

    public void setIsvanBesluits(Set<Besluit> besluits) {
        if (this.isvanBesluits != null) {
            this.isvanBesluits.forEach(i -> i.setIsvanBesluittype(null));
        }
        if (besluits != null) {
            besluits.forEach(i -> i.setIsvanBesluittype(this));
        }
        this.isvanBesluits = besluits;
    }

    public Besluittype isvanBesluits(Set<Besluit> besluits) {
        this.setIsvanBesluits(besluits);
        return this;
    }

    public Besluittype addIsvanBesluit(Besluit besluit) {
        this.isvanBesluits.add(besluit);
        besluit.setIsvanBesluittype(this);
        return this;
    }

    public Besluittype removeIsvanBesluit(Besluit besluit) {
        this.isvanBesluits.remove(besluit);
        besluit.setIsvanBesluittype(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Besluittype)) {
            return false;
        }
        return getId() != null && getId().equals(((Besluittype) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Besluittype{" +
            "id=" + getId() +
            ", besluitcategorie='" + getBesluitcategorie() + "'" +
            ", besluittypeomschrijving='" + getBesluittypeomschrijving() + "'" +
            ", besluittypeomschrijvinggeneriek='" + getBesluittypeomschrijvinggeneriek() + "'" +
            ", datumbegingeldigheidbesluittype='" + getDatumbegingeldigheidbesluittype() + "'" +
            ", datumeindegeldigheidbesluittype='" + getDatumeindegeldigheidbesluittype() + "'" +
            ", indicatiepublicatie='" + getIndicatiepublicatie() + "'" +
            ", publicatietekst='" + getPublicatietekst() + "'" +
            ", publicatietermijn='" + getPublicatietermijn() + "'" +
            ", reactietermijn='" + getReactietermijn() + "'" +
            "}";
    }
}
