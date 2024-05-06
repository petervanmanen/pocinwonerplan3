import { IArchiefstuk } from 'app/shared/model/archiefstuk.model';
import { IBezoeker } from 'app/shared/model/bezoeker.model';

export interface IAanvraag {
  id?: number;
  datumtijd?: string | null;
  voorArchiefstuks?: IArchiefstuk[] | null;
  doetBezoeker?: IBezoeker | null;
}

export const defaultValue: Readonly<IAanvraag> = {};
