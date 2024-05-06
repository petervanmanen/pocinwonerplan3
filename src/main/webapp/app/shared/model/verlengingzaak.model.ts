export interface IVerlengingzaak {
  id?: number;
  duurverlenging?: string | null;
  redenverlenging?: string | null;
}

export const defaultValue: Readonly<IVerlengingzaak> = {};
