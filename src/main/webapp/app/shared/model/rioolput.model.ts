export interface IRioolput {
  id?: number;
  aantalbedrijven?: string | null;
  aantalrecreatie?: string | null;
  aantalwoningen?: string | null;
  afvoerendoppervlak?: string | null;
  bergendoppervlak?: string | null;
  rioolputconstructieonderdeel?: string | null;
  rioolputrioolleiding?: string | null;
  risicogebied?: string | null;
  toegangbreedte?: string | null;
  toeganglengte?: string | null;
  type?: string | null;
  typeplus?: string | null;
}

export const defaultValue: Readonly<IRioolput> = {};
