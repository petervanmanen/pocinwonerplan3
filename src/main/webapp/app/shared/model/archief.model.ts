import { IRechthebbende } from 'app/shared/model/rechthebbende.model';
import { IArchiefcategorie } from 'app/shared/model/archiefcategorie.model';
import { IPeriode } from 'app/shared/model/periode.model';

export interface IArchief {
  id?: number;
  archiefnummer?: string | null;
  naam?: string | null;
  omschrijving?: string | null;
  openbaarheidsbeperking?: string | null;
  heeftRechthebbende?: IRechthebbende | null;
  valtbinnenArchiefcategories?: IArchiefcategorie[] | null;
  stamtuitPeriodes?: IPeriode[] | null;
}

export const defaultValue: Readonly<IArchief> = {};
