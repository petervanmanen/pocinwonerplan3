export interface IAnderzaakobjectzaak {
  id?: number;
  anderzaakobjectaanduiding?: string | null;
  anderzaakobjectlocatie?: string | null;
  anderzaakobjectomschrijving?: string | null;
  anderzaakobjectregistratie?: string | null;
}

export const defaultValue: Readonly<IAnderzaakobjectzaak> = {};
