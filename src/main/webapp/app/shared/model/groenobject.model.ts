export interface IGroenobject {
  id?: number;
  aantalobstakels?: string | null;
  aantalzijden?: string | null;
  afvoeren?: boolean | null;
  bereikbaarheid?: string | null;
  bergendvermogen?: string | null;
  bewerkingspercentage?: string | null;
  bgtfysiekvoorkomen?: string | null;
  bollen?: boolean | null;
  breedte?: string | null;
  breedteklassehaag?: string | null;
  bvc?: boolean | null;
  cultuurhistorischwaardevol?: string | null;
  draagkrachtig?: boolean | null;
  ecologischbeheer?: boolean | null;
  fysiekvoorkomenimgeo?: string | null;
  gewenstsluitingspercentage?: string | null;
  groenobjectbereikbaarheidplus?: string | null;
  groenobjectconstructielaag?: string | null;
  groenobjectrand?: string | null;
  groenobjectsoortnaam?: string | null;
  haagvoetlengte?: string | null;
  haagvoetoppervlakte?: string | null;
  herplantplicht?: boolean | null;
  hoogte?: string | null;
  hoogteklassehaag?: string | null;
  knipfrequentie?: string | null;
  knipoppervlakte?: string | null;
  kwaliteitsniveauactueel?: string | null;
  kwaliteitsniveaugewenst?: string | null;
  lengte?: string | null;
  leverancier?: string | null;
  maaifrequentie?: string | null;
  maximalevalhoogte?: string | null;
  eobjectnummer?: string | null;
  obstakels?: boolean | null;
  omtrek?: string | null;
  ondergroei?: string | null;
  oppervlakte?: string | null;
  optalud?: string | null;
  taludsteilte?: string | null;
  type?: string | null;
  typebewerking?: string | null;
  typeomgevingsrisicoklasse?: string | null;
  typeplus?: string | null;
  typeplus2?: string | null;
  veiligheidsklasseboom?: string | null;
}

export const defaultValue: Readonly<IGroenobject> = {
  afvoeren: false,
  bollen: false,
  bvc: false,
  draagkrachtig: false,
  ecologischbeheer: false,
  herplantplicht: false,
  obstakels: false,
};
