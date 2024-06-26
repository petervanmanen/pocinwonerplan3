package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Hotelbezoek.
 */
@Entity
@Table(name = "hotelbezoek")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Hotelbezoek implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftHotelbezoeks" }, allowSetters = true)
    private Hotel heeftHotel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Hotelbezoek id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Hotelbezoek datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Hotelbezoek datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public Hotel getHeeftHotel() {
        return this.heeftHotel;
    }

    public void setHeeftHotel(Hotel hotel) {
        this.heeftHotel = hotel;
    }

    public Hotelbezoek heeftHotel(Hotel hotel) {
        this.setHeeftHotel(hotel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hotelbezoek)) {
            return false;
        }
        return getId() != null && getId().equals(((Hotelbezoek) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Hotelbezoek{" +
            "id=" + getId() +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            "}";
    }
}
