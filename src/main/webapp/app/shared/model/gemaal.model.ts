export interface IGemaal {
  id?: number;
  aantalbedrijfsaansluitingen?: string | null;
  aantalhuisaansluitingen?: string | null;
  aantalpompen?: string | null;
  bedienaar?: string | null;
  effectievegemaalcapaciteit?: string | null;
  hijsinrichting?: boolean | null;
  lanceerinrichting?: boolean | null;
  pompeninsamenloop?: boolean | null;
  type?: string | null;
  veiligheidsrooster?: boolean | null;
}

export const defaultValue: Readonly<IGemaal> = {
  hijsinrichting: false,
  lanceerinrichting: false,
  pompeninsamenloop: false,
  veiligheidsrooster: false,
};
