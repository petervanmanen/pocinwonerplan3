export interface IVthzaak {
  id?: number;
  behandelaar?: string | null;
  bevoegdgezag?: string | null;
  prioritering?: string | null;
  teambehandelaar?: string | null;
  uitvoerendeinstantie?: string | null;
  verkamering?: string | null;
}

export const defaultValue: Readonly<IVthzaak> = {};
