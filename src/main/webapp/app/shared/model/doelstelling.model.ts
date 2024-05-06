import { IDoelstellingsoort } from 'app/shared/model/doelstellingsoort.model';
import { IHoofdstuk } from 'app/shared/model/hoofdstuk.model';

export interface IDoelstelling {
  id?: number;
  omschrijving?: string | null;
  isvansoortDoelstellingsoort?: IDoelstellingsoort | null;
  heeftHoofdstuk?: IHoofdstuk | null;
}

export const defaultValue: Readonly<IDoelstelling> = {};
