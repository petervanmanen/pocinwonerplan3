export interface IZaakorigineel {
  id?: number;
  anderzaakobject?: string | null;
  archiefnominatie?: string | null;
  datumeinde?: string | null;
  datumeindegepland?: string | null;
  datumeindeuiterlijkeafdoening?: string | null;
  datumlaatstebetaling?: string | null;
  datumpublicatie?: string | null;
  datumregistratie?: string | null;
  datumstart?: string | null;
  datumvernietigingdossier?: string | null;
  indicatiebetaling?: string | null;
  indicatiedeelzaken?: string | null;
  kenmerk?: string | null;
  omschrijving?: string | null;
  omschrijvingresultaat?: string | null;
  opschorting?: string | null;
  toelichting?: string | null;
  toelichtingresultaat?: string | null;
  verlenging?: string | null;
  zaakidentificatie?: string | null;
  zaakniveau?: string | null;
}

export const defaultValue: Readonly<IZaakorigineel> = {};
