import { IVerzuimsoort } from 'app/shared/model/verzuimsoort.model';
import { IWerknemer } from 'app/shared/model/werknemer.model';

export interface IVerzuim {
  id?: number;
  datumtijdeinde?: string | null;
  datumtijdstart?: string | null;
  soortverzuimVerzuimsoort?: IVerzuimsoort | null;
  heeftverzuimWerknemer?: IWerknemer | null;
}

export const defaultValue: Readonly<IVerzuim> = {};
