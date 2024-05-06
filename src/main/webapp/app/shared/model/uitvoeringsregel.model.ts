export interface IUitvoeringsregel {
  id?: number;
  naam?: string | null;
  omschrijving?: string | null;
  regel?: string | null;
}

export const defaultValue: Readonly<IUitvoeringsregel> = {};
