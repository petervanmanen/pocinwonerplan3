import { IMuseumrelatie } from 'app/shared/model/museumrelatie.model';

export interface IDoelgroep {
  id?: number;
  branch?: string | null;
  naam?: string | null;
  omschrijving?: string | null;
  segment?: string | null;
  bestaatuitDoelgroep2?: IDoelgroep | null;
  valtbinnenMuseumrelaties?: IMuseumrelatie[] | null;
}

export const defaultValue: Readonly<IDoelgroep> = {};
