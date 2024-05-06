import { IGemeente } from 'app/shared/model/gemeente.model';

export interface IAsielstatushouder {
  id?: number;
  digidaangevraagd?: string | null;
  emailadresverblijfazc?: string | null;
  isgekoppeldaan?: string | null;
  landrijbewijs?: string | null;
  rijbewijs?: string | null;
  telefoonnummerverblijfazc?: string | null;
  isgekoppeldaanGemeente?: IGemeente | null;
}

export const defaultValue: Readonly<IAsielstatushouder> = {};
