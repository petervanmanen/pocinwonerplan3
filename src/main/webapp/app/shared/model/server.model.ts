import { ILeverancier } from 'app/shared/model/leverancier.model';

export interface IServer {
  id?: number;
  actief?: boolean | null;
  ipadres?: string | null;
  locatie?: string | null;
  organisatie?: string | null;
  serienummer?: string | null;
  serverid?: string | null;
  servertype?: string | null;
  vlan?: string | null;
  heeftleverancierLeverancier?: ILeverancier | null;
}

export const defaultValue: Readonly<IServer> = {
  actief: false,
};
