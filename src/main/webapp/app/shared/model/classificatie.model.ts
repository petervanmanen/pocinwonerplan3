import { IGegeven } from 'app/shared/model/gegeven.model';

export interface IClassificatie {
  bevatpersoonsgegevens?: string | null;
  gerelateerdpersoonsgegevens?: string | null;
  id?: string;
  geclassificeerdalsGegevens?: IGegeven[] | null;
}

export const defaultValue: Readonly<IClassificatie> = {};
