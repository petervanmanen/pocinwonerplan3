import { IArchief } from 'app/shared/model/archief.model';

export interface IArchiefcategorie {
  id?: number;
  naam?: string | null;
  nummer?: string | null;
  omschrijving?: string | null;
  valtbinnenArchiefs?: IArchief[] | null;
}

export const defaultValue: Readonly<IArchiefcategorie> = {};
