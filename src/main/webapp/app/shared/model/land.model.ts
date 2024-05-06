export interface ILand {
  id?: number;
  datumeindefictief?: boolean | null;
  datumeindeland?: string | null;
  datumingangland?: string | null;
  landcode?: string | null;
  landcodeisodrieletterig?: string | null;
  landcodeisotweeletterig?: string | null;
  landnaam?: string | null;
}

export const defaultValue: Readonly<ILand> = {
  datumeindefictief: false,
};
