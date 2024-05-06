export interface ISelectietabelaanbesteding {
  id?: number;
  aanbestedingsoort?: string | null;
  drempelbedragtot?: number | null;
  drempelbedragvanaf?: number | null;
  opdrachtcategorie?: string | null;
  openbaar?: string | null;
}

export const defaultValue: Readonly<ISelectietabelaanbesteding> = {};
