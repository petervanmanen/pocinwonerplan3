export interface IReisdocument {
  id?: number;
  aanduidinginhoudingvermissing?: string | null;
  autoriteitvanafgifte?: string | null;
  datumeindegeldigheiddocument?: string | null;
  datumingangdocument?: string | null;
  datuminhoudingofvermissing?: string | null;
  datumuitgifte?: string | null;
  reisdocumentnummer?: string | null;
  soort?: string | null;
}

export const defaultValue: Readonly<IReisdocument> = {};
