export interface IParkeerzone {
  id?: number;
  aantalparkeervlakken?: string | null;
  alleendagtarief?: boolean | null;
  dagtarief?: number | null;
  eindedag?: string | null;
  eindtijd?: string | null;
  gebruik?: string | null;
  geometrie?: string | null;
  ipmcode?: string | null;
  ipmnaam?: string | null;
  naam?: string | null;
  parkeergarage?: boolean | null;
  sectorcode?: string | null;
  soortcode?: string | null;
  startdag?: string | null;
  starttarief?: number | null;
  starttijd?: string | null;
  typecode?: string | null;
  typenaam?: string | null;
  uurtarief?: number | null;
}

export const defaultValue: Readonly<IParkeerzone> = {
  alleendagtarief: false,
  parkeergarage: false,
};
