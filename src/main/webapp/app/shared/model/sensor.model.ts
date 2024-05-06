export interface ISensor {
  id?: number;
  aanleghoogte?: string | null;
  elektrakast?: string | null;
  frequentieomvormer?: string | null;
  hoogte?: string | null;
  jaaronderhouduitgevoerd?: string | null;
  leverancier?: string | null;
  meetpunt?: string | null;
  plc?: string | null;
}

export const defaultValue: Readonly<ISensor> = {};
