import { IServer } from 'app/shared/model/server.model';

export interface IDatabase {
  id?: number;
  architectuur?: string | null;
  database?: string | null;
  databaseversie?: string | null;
  dbms?: string | null;
  omschrijving?: string | null;
  otap?: string | null;
  vlan?: string | null;
  servervandatabaseServer?: IServer | null;
}

export const defaultValue: Readonly<IDatabase> = {};
