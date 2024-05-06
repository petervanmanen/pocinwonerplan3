export interface IEcoduct {
  id?: number;
  aantaloverspanningen?: string | null;
  draagvermogen?: string | null;
  maximaaltoelaatbaarvoertuiggewicht?: string | null;
  maximaleasbelasting?: string | null;
  maximaleoverspanning?: string | null;
  overbruggingsobjectdoorrijopening?: string | null;
  type?: string | null;
  zwaarstevoertuig?: string | null;
}

export const defaultValue: Readonly<IEcoduct> = {};
