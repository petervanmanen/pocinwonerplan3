export interface IVerhardingsobject {
  id?: number;
  aanleghoogte?: string | null;
  aanofvrijliggend?: string | null;
  aantaldeklagen?: string | null;
  aantalonderlagen?: string | null;
  aantaltussenlagen?: string | null;
  afmeting?: string | null;
  belasting?: string | null;
  bergendvermogen?: string | null;
  bgtfysiekvoorkomen?: string | null;
  breedte?: string | null;
  dikteconstructie?: string | null;
  draagkrachtig?: boolean | null;
  formaat?: string | null;
  fysiekvoorkomenimgeo?: string | null;
  geluidsreducerend?: boolean | null;
  jaarconserveren?: string | null;
  jaaronderhouduitgevoerd?: string | null;
  jaarpraktischeinde?: string | null;
  kleur?: string | null;
  kwaliteitsniveauactueel?: string | null;
  kwaliteitsniveaugewenst?: string | null;
  lengte?: string | null;
  lengtekunstgras?: string | null;
  lengtevoegen?: string | null;
  levensduur?: string | null;
  materiaal?: string | null;
  maximalevalhoogte?: string | null;
  omtrek?: string | null;
  ondergrondcode?: string | null;
  oppervlakte?: string | null;
  optalud?: string | null;
  plaatsorientatie?: string | null;
  prijsaanschaf?: string | null;
  rijstrook?: string | null;
  soortvoeg?: string | null;
  toelichtinggemengdebestrating?: string | null;
  type?: string | null;
  typeconstructie?: string | null;
  typefundering?: string | null;
  typeplus?: string | null;
  typeplus2?: string | null;
  typerijstrook?: string | null;
  typevoeg?: string | null;
  typevoegvulling?: string | null;
  vegen?: string | null;
  verhardingsobjectconstructielaag?: string | null;
  verhardingsobjectmodaliteit?: string | null;
  verhardingsobjectrand?: string | null;
  verhardingsobjectwegfunctie?: string | null;
  verhoogdeligging?: boolean | null;
  vulmateriaalkunstgras?: string | null;
  waterdoorlatendheid?: string | null;
  wegas?: string | null;
  wegcategoriedv?: string | null;
  wegcategoriedvplus?: string | null;
  wegnummer?: string | null;
  wegtypebestaand?: string | null;
  wegvak?: string | null;
  wegvaknummer?: string | null;
}

export const defaultValue: Readonly<IVerhardingsobject> = {
  draagkrachtig: false,
  geluidsreducerend: false,
  verhoogdeligging: false,
};
