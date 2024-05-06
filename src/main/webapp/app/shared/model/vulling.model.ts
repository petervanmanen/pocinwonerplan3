import { ISpoor } from 'app/shared/model/spoor.model';

export interface IVulling {
  id?: number;
  grondsoort?: string | null;
  key?: string | null;
  keyspoor?: string | null;
  kleur?: string | null;
  projectcd?: string | null;
  putnummer?: string | null;
  spoornummer?: string | null;
  structuur?: string | null;
  vlaknummer?: string | null;
  vullingnummer?: string | null;
  heeftSpoor?: ISpoor | null;
}

export const defaultValue: Readonly<IVulling> = {};
