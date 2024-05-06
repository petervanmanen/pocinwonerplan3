export interface IBergingsbassin {
  id?: number;
  bergendvermogen?: string | null;
  pompledigingsvoorziening?: string | null;
  pompspoelvoorziening?: string | null;
  spoelleiding?: string | null;
  vorm?: string | null;
}

export const defaultValue: Readonly<IBergingsbassin> = {};
