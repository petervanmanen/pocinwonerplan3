import { IRechtspersoon } from 'app/shared/model/rechtspersoon.model';

export interface IKadastralemutatie {
  id?: number;
  betrokkenenRechtspersoons?: IRechtspersoon[] | null;
}

export const defaultValue: Readonly<IKadastralemutatie> = {};
