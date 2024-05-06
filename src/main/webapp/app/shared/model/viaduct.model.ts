export interface IViaduct {
  id?: number;
  aantaloverspanningen?: string | null;
  belastingklassenieuw?: string | null;
  belastingklasseoud?: string | null;
  draagvermogen?: string | null;
  maximaaltoelaatbaarvoertuiggewicht?: string | null;
  maximaleasbelasting?: string | null;
  maximaleoverspanning?: string | null;
  overbruggingsobjectdoorrijopening?: string | null;
  type?: string | null;
  waterobject?: string | null;
  zwaarstevoertuig?: string | null;
}

export const defaultValue: Readonly<IViaduct> = {};
