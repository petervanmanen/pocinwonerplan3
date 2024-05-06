import { IBehandelsoort } from 'app/shared/model/behandelsoort.model';

export interface IBehandeling {
  id?: number;
  datumeinde?: string | null;
  datumstart?: string | null;
  toelichting?: string | null;
  isvansoortBehandelsoort?: IBehandelsoort | null;
}

export const defaultValue: Readonly<IBehandeling> = {};
