import { IArchiefstuk } from 'app/shared/model/archiefstuk.model';

export interface IOrdeningsschema {
  id?: number;
  naam?: string | null;
  text?: string | null;
  heeftArchiefstuks?: IArchiefstuk[] | null;
}

export const defaultValue: Readonly<IOrdeningsschema> = {};
