export interface ILeveringsvorm {
  id?: number;
  leveringsvormcode?: string | null;
  naam?: string | null;
  wet?: string | null;
}

export const defaultValue: Readonly<ILeveringsvorm> = {};
