export interface IVideoopname {
  id?: number;
  bestandsgrootte?: string | null;
  datumtijd?: string | null;
  lengte?: string | null;
  videoformaat?: string | null;
}

export const defaultValue: Readonly<IVideoopname> = {};
