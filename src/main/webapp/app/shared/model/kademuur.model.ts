export interface IKademuur {
  id?: number;
  belastingklassenieuw?: string | null;
  belastingklasseoud?: string | null;
  grijpstenen?: boolean | null;
  hoogtebovenkantkademuur?: string | null;
  materiaalbovenkantkademuur?: string | null;
  oppervlaktebovenkantkademuur?: string | null;
  reddingslijn?: boolean | null;
  type?: string | null;
  typebovenkantkademuur?: string | null;
  typefundering?: string | null;
  typeverankering?: string | null;
}

export const defaultValue: Readonly<IKademuur> = {
  grijpstenen: false,
  reddingslijn: false,
};
