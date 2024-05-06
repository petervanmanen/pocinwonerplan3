import { IHeffinggrondslag } from 'app/shared/model/heffinggrondslag.model';

export interface IHeffing {
  id?: number;
  bedrag?: string | null;
  code?: string | null;
  datumindiening?: string | null;
  gefactureerd?: boolean | null;
  inrekening?: string | null;
  nummer?: string | null;
  runnummer?: string | null;
  heeftgrondslagHeffinggrondslag?: IHeffinggrondslag | null;
}

export const defaultValue: Readonly<IHeffing> = {
  gefactureerd: false,
};
