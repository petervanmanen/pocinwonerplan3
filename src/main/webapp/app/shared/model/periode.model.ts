import { IArchief } from 'app/shared/model/archief.model';
import { IArchiefstuk } from 'app/shared/model/archiefstuk.model';
import { IHoofdstuk } from 'app/shared/model/hoofdstuk.model';

export interface IPeriode {
  id?: number;
  datumeinde?: string | null;
  datumstart?: string | null;
  omschrijving?: string | null;
  stamtuitArchiefs?: IArchief[];
  stamtuitArchiefstuks?: IArchiefstuk[];
  binnenHoofdstuks?: IHoofdstuk[];
}

export const defaultValue: Readonly<IPeriode> = {};
