import { IRegelingsoort } from 'app/shared/model/regelingsoort.model';
import { IClient } from 'app/shared/model/client.model';

export interface IRegeling {
  id?: number;
  datumeinde?: string | null;
  datumstart?: string | null;
  datumtoekenning?: string | null;
  omschrijving?: string | null;
  isregelingsoortRegelingsoort?: IRegelingsoort | null;
  heeftregelingClient?: IClient | null;
}

export const defaultValue: Readonly<IRegeling> = {};
