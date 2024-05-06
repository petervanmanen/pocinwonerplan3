import { IZaaktype } from 'app/shared/model/zaaktype.model';

export interface IStatustype {
  id?: number;
  datumbegingeldigheidstatustype?: string | null;
  datumeindegeldigheidstatustype?: string | null;
  doorlooptijdstatus?: string | null;
  statustypeomschrijving?: string | null;
  statustypeomschrijvinggeneriek?: string | null;
  statustypevolgnummer?: string | null;
  heeftZaaktype?: IZaaktype;
}

export const defaultValue: Readonly<IStatustype> = {};
