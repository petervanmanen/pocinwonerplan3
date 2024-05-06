export interface IKenmerkenzaak {
  id?: number;
  kenmerk?: string | null;
  kenmerkbron?: string | null;
}

export const defaultValue: Readonly<IKenmerkenzaak> = {};
