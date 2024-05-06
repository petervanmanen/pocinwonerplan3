export interface IBank {
  id?: number;
  type?: string | null;
  typeplus?: string | null;
}

export const defaultValue: Readonly<IBank> = {};
