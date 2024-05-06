export interface IWaterobject {
  id?: number;
  breedte?: string | null;
  folie?: boolean | null;
  hoogte?: string | null;
  infiltrerendoppervlak?: string | null;
  infiltrerendvermogen?: string | null;
  lengte?: string | null;
  lozingspunt?: string | null;
  oppervlakte?: string | null;
  porositeit?: string | null;
  streefdiepte?: string | null;
  type?: string | null;
  typeplus?: string | null;
  typeplus2?: string | null;
  typevaarwater?: string | null;
  typewaterplant?: string | null;
  uitstroomniveau?: string | null;
  vaarwegtraject?: string | null;
  vorm?: string | null;
  waternaam?: string | null;
  waterpeil?: string | null;
  waterpeilwinter?: string | null;
  waterpeilzomer?: string | null;
  waterplanten?: boolean | null;
}

export const defaultValue: Readonly<IWaterobject> = {
  folie: false,
  waterplanten: false,
};
