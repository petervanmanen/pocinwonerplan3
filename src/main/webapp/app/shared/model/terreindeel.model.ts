export interface ITerreindeel {
  id?: number;
  breedte?: string | null;
  cultuurhistorischwaardevol?: string | null;
  herplantplicht?: boolean | null;
  oppervlakte?: string | null;
  optalud?: string | null;
  percentageloofbos?: string | null;
  terreindeelsoortnaam?: string | null;
  type?: string | null;
  typebewerking?: string | null;
  typeplus?: string | null;
  typeplus2?: string | null;
}

export const defaultValue: Readonly<ITerreindeel> = {
  herplantplicht: false,
};
