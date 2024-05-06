export interface IBrug {
  id?: number;
  aantaloverspanningen?: string | null;
  bedienaar?: string | null;
  bedieningstijden?: string | null;
  belastingklassenieuw?: string | null;
  belastingklasseoud?: string | null;
  beweegbaar?: boolean | null;
  doorrijbreedte?: string | null;
  draagvermogen?: string | null;
  hoofdroute?: string | null;
  hoofdvaarroute?: string | null;
  maximaaltoelaatbaarvoertuiggewicht?: string | null;
  maximaleasbelasting?: string | null;
  maximaleoverspanning?: string | null;
  statischmoment?: string | null;
  type?: string | null;
  typeplus?: string | null;
  zwaarstevoertuig?: string | null;
}

export const defaultValue: Readonly<IBrug> = {
  beweegbaar: false,
};
