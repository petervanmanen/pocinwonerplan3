export interface IAommeldingwmojeugd {
  id?: number;
  aanmelder?: string | null;
  aanmeldingdoor?: string | null;
  aanmeldingdoorlandelijk?: string | null;
  aanmeldwijze?: string | null;
  deskundigheid?: string | null;
  isclientopdehoogte?: string | null;
  onderzoekswijze?: string | null;
  redenafsluiting?: string | null;
  vervolg?: string | null;
  verwezen?: string | null;
}

export const defaultValue: Readonly<IAommeldingwmojeugd> = {};
