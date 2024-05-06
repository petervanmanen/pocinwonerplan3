package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Vestiging.
 */
@Entity
@Table(name = "vestiging")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vestiging implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "commercielevestiging")
    private String commercielevestiging;

    @Column(name = "datumaanvang")
    private LocalDate datumaanvang;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumvoortzetting")
    private LocalDate datumvoortzetting;

    @Column(name = "fulltimewerkzamemannen")
    private String fulltimewerkzamemannen;

    @Column(name = "fulltimewerkzamevrouwen")
    private String fulltimewerkzamevrouwen;

    @Column(name = "handelsnaam")
    private String handelsnaam;

    @Column(name = "parttimewerkzamemannen")
    private String parttimewerkzamemannen;

    @Column(name = "parttimewerkzamevrouwen")
    private String parttimewerkzamevrouwen;

    @Size(max = 100)
    @Column(name = "toevoegingadres", length = 100)
    private String toevoegingadres;

    @Column(name = "totaalwerkzamepersonen")
    private String totaalwerkzamepersonen;

    @Column(name = "verkortenaam")
    private String verkortenaam;

    @Column(name = "vestigingsnummer")
    private String vestigingsnummer;

    @JsonIgnoreProperties(value = { "heeftVestiging" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Werkgelegenheid heeftWerkgelegenheid;

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
    private Nummeraanduiding heeftalslocatieadresNummeraanduiding;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bijVestiging")
    @JsonIgnoreProperties(value = { "bijVestiging" }, allowSetters = true)
    private Set<Contact> bijContacts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vestiging id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommercielevestiging() {
        return this.commercielevestiging;
    }

    public Vestiging commercielevestiging(String commercielevestiging) {
        this.setCommercielevestiging(commercielevestiging);
        return this;
    }

    public void setCommercielevestiging(String commercielevestiging) {
        this.commercielevestiging = commercielevestiging;
    }

    public LocalDate getDatumaanvang() {
        return this.datumaanvang;
    }

    public Vestiging datumaanvang(LocalDate datumaanvang) {
        this.setDatumaanvang(datumaanvang);
        return this;
    }

    public void setDatumaanvang(LocalDate datumaanvang) {
        this.datumaanvang = datumaanvang;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Vestiging datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumvoortzetting() {
        return this.datumvoortzetting;
    }

    public Vestiging datumvoortzetting(LocalDate datumvoortzetting) {
        this.setDatumvoortzetting(datumvoortzetting);
        return this;
    }

    public void setDatumvoortzetting(LocalDate datumvoortzetting) {
        this.datumvoortzetting = datumvoortzetting;
    }

    public String getFulltimewerkzamemannen() {
        return this.fulltimewerkzamemannen;
    }

    public Vestiging fulltimewerkzamemannen(String fulltimewerkzamemannen) {
        this.setFulltimewerkzamemannen(fulltimewerkzamemannen);
        return this;
    }

    public void setFulltimewerkzamemannen(String fulltimewerkzamemannen) {
        this.fulltimewerkzamemannen = fulltimewerkzamemannen;
    }

    public String getFulltimewerkzamevrouwen() {
        return this.fulltimewerkzamevrouwen;
    }

    public Vestiging fulltimewerkzamevrouwen(String fulltimewerkzamevrouwen) {
        this.setFulltimewerkzamevrouwen(fulltimewerkzamevrouwen);
        return this;
    }

    public void setFulltimewerkzamevrouwen(String fulltimewerkzamevrouwen) {
        this.fulltimewerkzamevrouwen = fulltimewerkzamevrouwen;
    }

    public String getHandelsnaam() {
        return this.handelsnaam;
    }

    public Vestiging handelsnaam(String handelsnaam) {
        this.setHandelsnaam(handelsnaam);
        return this;
    }

    public void setHandelsnaam(String handelsnaam) {
        this.handelsnaam = handelsnaam;
    }

    public String getParttimewerkzamemannen() {
        return this.parttimewerkzamemannen;
    }

    public Vestiging parttimewerkzamemannen(String parttimewerkzamemannen) {
        this.setParttimewerkzamemannen(parttimewerkzamemannen);
        return this;
    }

    public void setParttimewerkzamemannen(String parttimewerkzamemannen) {
        this.parttimewerkzamemannen = parttimewerkzamemannen;
    }

    public String getParttimewerkzamevrouwen() {
        return this.parttimewerkzamevrouwen;
    }

    public Vestiging parttimewerkzamevrouwen(String parttimewerkzamevrouwen) {
        this.setParttimewerkzamevrouwen(parttimewerkzamevrouwen);
        return this;
    }

    public void setParttimewerkzamevrouwen(String parttimewerkzamevrouwen) {
        this.parttimewerkzamevrouwen = parttimewerkzamevrouwen;
    }

    public String getToevoegingadres() {
        return this.toevoegingadres;
    }

    public Vestiging toevoegingadres(String toevoegingadres) {
        this.setToevoegingadres(toevoegingadres);
        return this;
    }

    public void setToevoegingadres(String toevoegingadres) {
        this.toevoegingadres = toevoegingadres;
    }

    public String getTotaalwerkzamepersonen() {
        return this.totaalwerkzamepersonen;
    }

    public Vestiging totaalwerkzamepersonen(String totaalwerkzamepersonen) {
        this.setTotaalwerkzamepersonen(totaalwerkzamepersonen);
        return this;
    }

    public void setTotaalwerkzamepersonen(String totaalwerkzamepersonen) {
        this.totaalwerkzamepersonen = totaalwerkzamepersonen;
    }

    public String getVerkortenaam() {
        return this.verkortenaam;
    }

    public Vestiging verkortenaam(String verkortenaam) {
        this.setVerkortenaam(verkortenaam);
        return this;
    }

    public void setVerkortenaam(String verkortenaam) {
        this.verkortenaam = verkortenaam;
    }

    public String getVestigingsnummer() {
        return this.vestigingsnummer;
    }

    public Vestiging vestigingsnummer(String vestigingsnummer) {
        this.setVestigingsnummer(vestigingsnummer);
        return this;
    }

    public void setVestigingsnummer(String vestigingsnummer) {
        this.vestigingsnummer = vestigingsnummer;
    }

    public Werkgelegenheid getHeeftWerkgelegenheid() {
        return this.heeftWerkgelegenheid;
    }

    public void setHeeftWerkgelegenheid(Werkgelegenheid werkgelegenheid) {
        this.heeftWerkgelegenheid = werkgelegenheid;
    }

    public Vestiging heeftWerkgelegenheid(Werkgelegenheid werkgelegenheid) {
        this.setHeeftWerkgelegenheid(werkgelegenheid);
        return this;
    }

    public Nummeraanduiding getHeeftalslocatieadresNummeraanduiding() {
        return this.heeftalslocatieadresNummeraanduiding;
    }

    public void setHeeftalslocatieadresNummeraanduiding(Nummeraanduiding nummeraanduiding) {
        this.heeftalslocatieadresNummeraanduiding = nummeraanduiding;
    }

    public Vestiging heeftalslocatieadresNummeraanduiding(Nummeraanduiding nummeraanduiding) {
        this.setHeeftalslocatieadresNummeraanduiding(nummeraanduiding);
        return this;
    }

    public Set<Contact> getBijContacts() {
        return this.bijContacts;
    }

    public void setBijContacts(Set<Contact> contacts) {
        if (this.bijContacts != null) {
            this.bijContacts.forEach(i -> i.setBijVestiging(null));
        }
        if (contacts != null) {
            contacts.forEach(i -> i.setBijVestiging(this));
        }
        this.bijContacts = contacts;
    }

    public Vestiging bijContacts(Set<Contact> contacts) {
        this.setBijContacts(contacts);
        return this;
    }

    public Vestiging addBijContact(Contact contact) {
        this.bijContacts.add(contact);
        contact.setBijVestiging(this);
        return this;
    }

    public Vestiging removeBijContact(Contact contact) {
        this.bijContacts.remove(contact);
        contact.setBijVestiging(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vestiging)) {
            return false;
        }
        return getId() != null && getId().equals(((Vestiging) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vestiging{" +
            "id=" + getId() +
            ", commercielevestiging='" + getCommercielevestiging() + "'" +
            ", datumaanvang='" + getDatumaanvang() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumvoortzetting='" + getDatumvoortzetting() + "'" +
            ", fulltimewerkzamemannen='" + getFulltimewerkzamemannen() + "'" +
            ", fulltimewerkzamevrouwen='" + getFulltimewerkzamevrouwen() + "'" +
            ", handelsnaam='" + getHandelsnaam() + "'" +
            ", parttimewerkzamemannen='" + getParttimewerkzamemannen() + "'" +
            ", parttimewerkzamevrouwen='" + getParttimewerkzamevrouwen() + "'" +
            ", toevoegingadres='" + getToevoegingadres() + "'" +
            ", totaalwerkzamepersonen='" + getTotaalwerkzamepersonen() + "'" +
            ", verkortenaam='" + getVerkortenaam() + "'" +
            ", vestigingsnummer='" + getVestigingsnummer() + "'" +
            "}";
    }
}
