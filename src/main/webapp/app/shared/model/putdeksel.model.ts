export interface IPutdeksel {
  id?: number;
  diameter?: string | null;
  put?: string | null;
  type?: string | null;
  vorm?: string | null;
}

export const defaultValue: Readonly<IPutdeksel> = {};
