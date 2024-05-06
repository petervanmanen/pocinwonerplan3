import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './verhardingsobject.reducer';

export const VerhardingsobjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const verhardingsobjectEntity = useAppSelector(state => state.verhardingsobject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="verhardingsobjectDetailsHeading">Verhardingsobject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{verhardingsobjectEntity.id}</dd>
          <dt>
            <span id="aanleghoogte">Aanleghoogte</span>
          </dt>
          <dd>{verhardingsobjectEntity.aanleghoogte}</dd>
          <dt>
            <span id="aanofvrijliggend">Aanofvrijliggend</span>
          </dt>
          <dd>{verhardingsobjectEntity.aanofvrijliggend}</dd>
          <dt>
            <span id="aantaldeklagen">Aantaldeklagen</span>
          </dt>
          <dd>{verhardingsobjectEntity.aantaldeklagen}</dd>
          <dt>
            <span id="aantalonderlagen">Aantalonderlagen</span>
          </dt>
          <dd>{verhardingsobjectEntity.aantalonderlagen}</dd>
          <dt>
            <span id="aantaltussenlagen">Aantaltussenlagen</span>
          </dt>
          <dd>{verhardingsobjectEntity.aantaltussenlagen}</dd>
          <dt>
            <span id="afmeting">Afmeting</span>
          </dt>
          <dd>{verhardingsobjectEntity.afmeting}</dd>
          <dt>
            <span id="belasting">Belasting</span>
          </dt>
          <dd>{verhardingsobjectEntity.belasting}</dd>
          <dt>
            <span id="bergendvermogen">Bergendvermogen</span>
          </dt>
          <dd>{verhardingsobjectEntity.bergendvermogen}</dd>
          <dt>
            <span id="bgtfysiekvoorkomen">Bgtfysiekvoorkomen</span>
          </dt>
          <dd>{verhardingsobjectEntity.bgtfysiekvoorkomen}</dd>
          <dt>
            <span id="breedte">Breedte</span>
          </dt>
          <dd>{verhardingsobjectEntity.breedte}</dd>
          <dt>
            <span id="dikteconstructie">Dikteconstructie</span>
          </dt>
          <dd>{verhardingsobjectEntity.dikteconstructie}</dd>
          <dt>
            <span id="draagkrachtig">Draagkrachtig</span>
          </dt>
          <dd>{verhardingsobjectEntity.draagkrachtig ? 'true' : 'false'}</dd>
          <dt>
            <span id="formaat">Formaat</span>
          </dt>
          <dd>{verhardingsobjectEntity.formaat}</dd>
          <dt>
            <span id="fysiekvoorkomenimgeo">Fysiekvoorkomenimgeo</span>
          </dt>
          <dd>{verhardingsobjectEntity.fysiekvoorkomenimgeo}</dd>
          <dt>
            <span id="geluidsreducerend">Geluidsreducerend</span>
          </dt>
          <dd>{verhardingsobjectEntity.geluidsreducerend ? 'true' : 'false'}</dd>
          <dt>
            <span id="jaarconserveren">Jaarconserveren</span>
          </dt>
          <dd>{verhardingsobjectEntity.jaarconserveren}</dd>
          <dt>
            <span id="jaaronderhouduitgevoerd">Jaaronderhouduitgevoerd</span>
          </dt>
          <dd>{verhardingsobjectEntity.jaaronderhouduitgevoerd}</dd>
          <dt>
            <span id="jaarpraktischeinde">Jaarpraktischeinde</span>
          </dt>
          <dd>{verhardingsobjectEntity.jaarpraktischeinde}</dd>
          <dt>
            <span id="kleur">Kleur</span>
          </dt>
          <dd>{verhardingsobjectEntity.kleur}</dd>
          <dt>
            <span id="kwaliteitsniveauactueel">Kwaliteitsniveauactueel</span>
          </dt>
          <dd>{verhardingsobjectEntity.kwaliteitsniveauactueel}</dd>
          <dt>
            <span id="kwaliteitsniveaugewenst">Kwaliteitsniveaugewenst</span>
          </dt>
          <dd>{verhardingsobjectEntity.kwaliteitsniveaugewenst}</dd>
          <dt>
            <span id="lengte">Lengte</span>
          </dt>
          <dd>{verhardingsobjectEntity.lengte}</dd>
          <dt>
            <span id="lengtekunstgras">Lengtekunstgras</span>
          </dt>
          <dd>{verhardingsobjectEntity.lengtekunstgras}</dd>
          <dt>
            <span id="lengtevoegen">Lengtevoegen</span>
          </dt>
          <dd>{verhardingsobjectEntity.lengtevoegen}</dd>
          <dt>
            <span id="levensduur">Levensduur</span>
          </dt>
          <dd>{verhardingsobjectEntity.levensduur}</dd>
          <dt>
            <span id="materiaal">Materiaal</span>
          </dt>
          <dd>{verhardingsobjectEntity.materiaal}</dd>
          <dt>
            <span id="maximalevalhoogte">Maximalevalhoogte</span>
          </dt>
          <dd>{verhardingsobjectEntity.maximalevalhoogte}</dd>
          <dt>
            <span id="omtrek">Omtrek</span>
          </dt>
          <dd>{verhardingsobjectEntity.omtrek}</dd>
          <dt>
            <span id="ondergrondcode">Ondergrondcode</span>
          </dt>
          <dd>{verhardingsobjectEntity.ondergrondcode}</dd>
          <dt>
            <span id="oppervlakte">Oppervlakte</span>
          </dt>
          <dd>{verhardingsobjectEntity.oppervlakte}</dd>
          <dt>
            <span id="optalud">Optalud</span>
          </dt>
          <dd>{verhardingsobjectEntity.optalud}</dd>
          <dt>
            <span id="plaatsorientatie">Plaatsorientatie</span>
          </dt>
          <dd>{verhardingsobjectEntity.plaatsorientatie}</dd>
          <dt>
            <span id="prijsaanschaf">Prijsaanschaf</span>
          </dt>
          <dd>{verhardingsobjectEntity.prijsaanschaf}</dd>
          <dt>
            <span id="rijstrook">Rijstrook</span>
          </dt>
          <dd>{verhardingsobjectEntity.rijstrook}</dd>
          <dt>
            <span id="soortvoeg">Soortvoeg</span>
          </dt>
          <dd>{verhardingsobjectEntity.soortvoeg}</dd>
          <dt>
            <span id="toelichtinggemengdebestrating">Toelichtinggemengdebestrating</span>
          </dt>
          <dd>{verhardingsobjectEntity.toelichtinggemengdebestrating}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{verhardingsobjectEntity.type}</dd>
          <dt>
            <span id="typeconstructie">Typeconstructie</span>
          </dt>
          <dd>{verhardingsobjectEntity.typeconstructie}</dd>
          <dt>
            <span id="typefundering">Typefundering</span>
          </dt>
          <dd>{verhardingsobjectEntity.typefundering}</dd>
          <dt>
            <span id="typeplus">Typeplus</span>
          </dt>
          <dd>{verhardingsobjectEntity.typeplus}</dd>
          <dt>
            <span id="typeplus2">Typeplus 2</span>
          </dt>
          <dd>{verhardingsobjectEntity.typeplus2}</dd>
          <dt>
            <span id="typerijstrook">Typerijstrook</span>
          </dt>
          <dd>{verhardingsobjectEntity.typerijstrook}</dd>
          <dt>
            <span id="typevoeg">Typevoeg</span>
          </dt>
          <dd>{verhardingsobjectEntity.typevoeg}</dd>
          <dt>
            <span id="typevoegvulling">Typevoegvulling</span>
          </dt>
          <dd>{verhardingsobjectEntity.typevoegvulling}</dd>
          <dt>
            <span id="vegen">Vegen</span>
          </dt>
          <dd>{verhardingsobjectEntity.vegen}</dd>
          <dt>
            <span id="verhardingsobjectconstructielaag">Verhardingsobjectconstructielaag</span>
          </dt>
          <dd>{verhardingsobjectEntity.verhardingsobjectconstructielaag}</dd>
          <dt>
            <span id="verhardingsobjectmodaliteit">Verhardingsobjectmodaliteit</span>
          </dt>
          <dd>{verhardingsobjectEntity.verhardingsobjectmodaliteit}</dd>
          <dt>
            <span id="verhardingsobjectrand">Verhardingsobjectrand</span>
          </dt>
          <dd>{verhardingsobjectEntity.verhardingsobjectrand}</dd>
          <dt>
            <span id="verhardingsobjectwegfunctie">Verhardingsobjectwegfunctie</span>
          </dt>
          <dd>{verhardingsobjectEntity.verhardingsobjectwegfunctie}</dd>
          <dt>
            <span id="verhoogdeligging">Verhoogdeligging</span>
          </dt>
          <dd>{verhardingsobjectEntity.verhoogdeligging ? 'true' : 'false'}</dd>
          <dt>
            <span id="vulmateriaalkunstgras">Vulmateriaalkunstgras</span>
          </dt>
          <dd>{verhardingsobjectEntity.vulmateriaalkunstgras}</dd>
          <dt>
            <span id="waterdoorlatendheid">Waterdoorlatendheid</span>
          </dt>
          <dd>{verhardingsobjectEntity.waterdoorlatendheid}</dd>
          <dt>
            <span id="wegas">Wegas</span>
          </dt>
          <dd>{verhardingsobjectEntity.wegas}</dd>
          <dt>
            <span id="wegcategoriedv">Wegcategoriedv</span>
          </dt>
          <dd>{verhardingsobjectEntity.wegcategoriedv}</dd>
          <dt>
            <span id="wegcategoriedvplus">Wegcategoriedvplus</span>
          </dt>
          <dd>{verhardingsobjectEntity.wegcategoriedvplus}</dd>
          <dt>
            <span id="wegnummer">Wegnummer</span>
          </dt>
          <dd>{verhardingsobjectEntity.wegnummer}</dd>
          <dt>
            <span id="wegtypebestaand">Wegtypebestaand</span>
          </dt>
          <dd>{verhardingsobjectEntity.wegtypebestaand}</dd>
          <dt>
            <span id="wegvak">Wegvak</span>
          </dt>
          <dd>{verhardingsobjectEntity.wegvak}</dd>
          <dt>
            <span id="wegvaknummer">Wegvaknummer</span>
          </dt>
          <dd>{verhardingsobjectEntity.wegvaknummer}</dd>
        </dl>
        <Button tag={Link} to="/verhardingsobject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/verhardingsobject/${verhardingsobjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VerhardingsobjectDetail;
