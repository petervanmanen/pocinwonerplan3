export interface IGeboorteingeschrevenpersoon {
  id?: number;
  datumgeboorte?: string | null;
  geboorteland?: string | null;
  geboorteplaats?: string | null;
}

export const defaultValue: Readonly<IGeboorteingeschrevenpersoon> = {};
