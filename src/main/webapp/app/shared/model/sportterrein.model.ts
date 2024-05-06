export interface ISportterrein {
  id?: number;
  drainage?: boolean | null;
  gebruiksvorm?: string | null;
  sportcomplex?: string | null;
  sportterreintypesport?: string | null;
  type?: string | null;
  typeplus?: string | null;
  veldnummer?: string | null;
  verlicht?: boolean | null;
}

export const defaultValue: Readonly<ISportterrein> = {
  drainage: false,
  verlicht: false,
};
