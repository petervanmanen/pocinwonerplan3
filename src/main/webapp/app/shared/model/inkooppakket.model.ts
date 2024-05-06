export interface IInkooppakket {
  id?: number;
  code?: string | null;
  naam?: string | null;
  type?: string | null;
}

export const defaultValue: Readonly<IInkooppakket> = {};
