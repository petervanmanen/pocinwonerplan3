export interface IAanvraaginkooporder {
  id?: number;
  betalingovermeerjaren?: string | null;
  correspondentienummer?: string | null;
  inhuuranders?: string | null;
  leveringofdienst?: string | null;
  nettototaalbedrag?: number | null;
  omschrijving?: string | null;
  onderwerp?: string | null;
  reactie?: string | null;
  status?: string | null;
  wijzevaninhuur?: string | null;
}

export const defaultValue: Readonly<IAanvraaginkooporder> = {};
