import { IKostenplaats } from 'app/shared/model/kostenplaats.model';
import { IProgrammasoort } from 'app/shared/model/programmasoort.model';
import { IMuseumrelatie } from 'app/shared/model/museumrelatie.model';
import { IRaadsstuk } from 'app/shared/model/raadsstuk.model';

export interface IProgramma {
  id?: number;
  naam?: string | null;
  heeftKostenplaats?: IKostenplaats | null;
  voorProgrammasoorts?: IProgrammasoort[] | null;
  voorMuseumrelatie?: IMuseumrelatie | null;
  hoortbijRaadsstuks?: IRaadsstuk[] | null;
}

export const defaultValue: Readonly<IProgramma> = {};
