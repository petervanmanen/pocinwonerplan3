export interface IVerplichtingwmojeugd {
  id?: number;
  budgetsoort?: string | null;
  budgetsoortgroep?: string | null;
  einddatumgepland?: string | null;
  feitelijkeeinddatum?: string | null;
  jaar?: string | null;
  periodiciteit?: string | null;
  verplichtingsoort?: string | null;
}

export const defaultValue: Readonly<IVerplichtingwmojeugd> = {};
