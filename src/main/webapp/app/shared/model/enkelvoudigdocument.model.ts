export interface IEnkelvoudigdocument {
  id?: number;
  bestandsnaam?: string | null;
  documentformaat?: string | null;
  documentinhoud?: string | null;
  documentlink?: string | null;
  documentstatus?: string | null;
  documenttaal?: string | null;
  documentversie?: string | null;
}

export const defaultValue: Readonly<IEnkelvoudigdocument> = {};
