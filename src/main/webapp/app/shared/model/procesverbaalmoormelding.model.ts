export interface IProcesverbaalmoormelding {
  id?: number;
  datum?: string | null;
  goedkeuring?: boolean | null;
  opmerkingen?: string | null;
}

export const defaultValue: Readonly<IProcesverbaalmoormelding> = {
  goedkeuring: false,
};
