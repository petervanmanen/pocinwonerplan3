export interface ISpeelterrein {
  id?: number;
  jaarherinrichting?: string | null;
  speelterreinleeftijddoelgroep?: string | null;
  type?: string | null;
  typeplus?: string | null;
}

export const defaultValue: Readonly<ISpeelterrein> = {};
