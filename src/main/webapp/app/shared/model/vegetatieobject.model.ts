export interface IVegetatieobject {
  id?: number;
  afvoeren?: boolean | null;
  bereikbaarheid?: string | null;
  ecologischbeheer?: boolean | null;
  kwaliteitsniveauactueel?: string | null;
  kwaliteitsniveaugewenst?: string | null;
  kweker?: string | null;
  leverancier?: string | null;
  eobjectnummer?: string | null;
  soortnaam?: string | null;
  typestandplaats?: string | null;
  typestandplaatsplus?: string | null;
  vegetatieobjectbereikbaarheidplus?: string | null;
}

export const defaultValue: Readonly<IVegetatieobject> = {
  afvoeren: false,
  ecologischbeheer: false,
};
