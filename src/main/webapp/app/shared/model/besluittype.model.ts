export interface IBesluittype {
  id?: number;
  besluitcategorie?: string | null;
  besluittypeomschrijving?: string | null;
  besluittypeomschrijvinggeneriek?: string | null;
  datumbegingeldigheidbesluittype?: string | null;
  datumeindegeldigheidbesluittype?: string | null;
  indicatiepublicatie?: string | null;
  publicatietekst?: string | null;
  publicatietermijn?: string | null;
  reactietermijn?: string | null;
}

export const defaultValue: Readonly<IBesluittype> = {};
