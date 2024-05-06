export interface IMoormelding {
  id?: number;
  adresaanduiding?: string | null;
  datumaanmelding?: string | null;
  datumgoedkeuring?: string | null;
  eindtijd?: string | null;
  goedgekeurd?: boolean | null;
  herstelwerkzaamhedenvereist?: boolean | null;
  omschrijvingherstelwerkzaamheden?: string | null;
  publiceren?: boolean | null;
  starttijd?: string | null;
  wegbeheerder?: string | null;
}

export const defaultValue: Readonly<IMoormelding> = {
  goedgekeurd: false,
  herstelwerkzaamhedenvereist: false,
  publiceren: false,
};
