import { IArchief } from 'app/shared/model/archief.model';
import { IUitgever } from 'app/shared/model/uitgever.model';
import { IVindplaats } from 'app/shared/model/vindplaats.model';
import { IOrdeningsschema } from 'app/shared/model/ordeningsschema.model';
import { IPeriode } from 'app/shared/model/periode.model';
import { IIndeling } from 'app/shared/model/indeling.model';
import { IAanvraag } from 'app/shared/model/aanvraag.model';

export interface IArchiefstuk {
  id?: number;
  beschrijving?: string | null;
  inventarisnummer?: string | null;
  omvang?: string | null;
  openbaarheidsbeperking?: string | null;
  trefwoorden?: string | null;
  uiterlijkevorm?: string | null;
  isonderdeelvanArchief?: IArchief | null;
  heeftUitgever?: IUitgever | null;
  heeftVindplaats?: IVindplaats | null;
  heeftOrdeningsschemas?: IOrdeningsschema[] | null;
  stamtuitPeriodes?: IPeriode[] | null;
  valtbinnenIndeling?: IIndeling | null;
  voorAanvraags?: IAanvraag[] | null;
}

export const defaultValue: Readonly<IArchiefstuk> = {};
