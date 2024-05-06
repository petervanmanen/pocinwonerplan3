export interface IAanwezigedeelnemer {
  id?: number;
  aanvangaanwezigheid?: string | null;
  eindeaanwezigheid?: string | null;
  naam?: string | null;
  rol?: string | null;
  vertegenwoordigtorganisatie?: string | null;
}

export const defaultValue: Readonly<IAanwezigedeelnemer> = {};
