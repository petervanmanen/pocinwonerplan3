export interface IStudentenwoningen {
  id?: number;
  huurprijs?: number | null;
  zelfstandig?: boolean | null;
}

export const defaultValue: Readonly<IStudentenwoningen> = {
  zelfstandig: false,
};
