export interface IBoom {
  id?: number;
  beleidsstatus?: string | null;
  beoogdeomlooptijd?: string | null;
  boombeeld?: string | null;
  boombeschermer?: string | null;
  boomgroep?: string | null;
  boomhoogteactueel?: string | null;
  boomhoogteklasseactueel?: string | null;
  boomhoogteklasseeindebeeld?: string | null;
  boomspiegel?: string | null;
  boomtypebeschermingsstatusplus?: string | null;
  boomvoorziening?: string | null;
  controlefrequentie?: string | null;
  feestverlichting?: string | null;
  groeifase?: string | null;
  groeiplaatsinrichting?: string | null;
  herplantplicht?: boolean | null;
  kiemjaar?: string | null;
  kroondiameterklasseactueel?: string | null;
  kroondiameterklasseeindebeeld?: string | null;
  kroonvolume?: string | null;
  leeftijd?: string | null;
  meerstammig?: boolean | null;
  monetaireboomwaarde?: string | null;
  snoeifase?: string | null;
  stamdiameter?: string | null;
  stamdiameterklasse?: string | null;
  takvrijeruimtetotgebouw?: string | null;
  takvrijestam?: string | null;
  takvrijezoneprimair?: string | null;
  takvrijezonesecundair?: string | null;
  transponder?: string | null;
  type?: string | null;
  typebeschermingsstatus?: string | null;
  typeomgevingsrisicoklasse?: string | null;
  typeplus?: string | null;
  typevermeerderingsvorm?: string | null;
  veiligheidsklasseboom?: string | null;
  verplant?: boolean | null;
  verplantbaar?: boolean | null;
  vrijedoorrijhoogte?: string | null;
  vrijedoorrijhoogteprimair?: string | null;
  vrijedoorrijhoogtesecundair?: string | null;
  vrijetakval?: string | null;
}

export const defaultValue: Readonly<IBoom> = {
  herplantplicht: false,
  meerstammig: false,
  verplant: false,
  verplantbaar: false,
};
