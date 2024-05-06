import { IKadastralemutatie } from 'app/shared/model/kadastralemutatie.model';

export interface IRechtspersoon {
  id?: number;
  adresbinnenland?: string | null;
  adresbuitenland?: string | null;
  adrescorrespondentie?: string | null;
  emailadres?: string | null;
  faxnummer?: string | null;
  identificatie?: string | null;
  kvknummer?: string | null;
  naam?: string | null;
  rechtsvorm?: string | null;
  rekeningnummer?: string | null;
  telefoonnummer?: string | null;
  betrokkenenKadastralemutaties?: IKadastralemutatie[];
}

export const defaultValue: Readonly<IRechtspersoon> = {};
