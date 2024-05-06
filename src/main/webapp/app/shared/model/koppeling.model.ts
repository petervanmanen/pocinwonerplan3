export interface IKoppeling {
  id?: number;
  beschrijving?: string | null;
  direct?: boolean | null;
  toelichting?: string | null;
}

export const defaultValue: Readonly<IKoppeling> = {
  direct: false,
};
