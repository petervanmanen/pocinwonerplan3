import { IStartkwalificatie } from 'app/shared/model/startkwalificatie.model';

export interface ILeerling {
  id?: number;
  kwetsbarejongere?: boolean | null;
  heeftStartkwalificatie?: IStartkwalificatie;
}

export const defaultValue: Readonly<ILeerling> = {
  kwetsbarejongere: false,
};
