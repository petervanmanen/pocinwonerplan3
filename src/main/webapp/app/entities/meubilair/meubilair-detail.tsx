import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './meubilair.reducer';

export const MeubilairDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const meubilairEntity = useAppSelector(state => state.meubilair.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="meubilairDetailsHeading">Meubilair</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{meubilairEntity.id}</dd>
          <dt>
            <span id="aanleghoogte">Aanleghoogte</span>
          </dt>
          <dd>{meubilairEntity.aanleghoogte}</dd>
          <dt>
            <span id="bouwjaar">Bouwjaar</span>
          </dt>
          <dd>{meubilairEntity.bouwjaar}</dd>
          <dt>
            <span id="breedte">Breedte</span>
          </dt>
          <dd>{meubilairEntity.breedte}</dd>
          <dt>
            <span id="datumaanschaf">Datumaanschaf</span>
          </dt>
          <dd>
            {meubilairEntity.datumaanschaf ? (
              <TextFormat value={meubilairEntity.datumaanschaf} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="diameter">Diameter</span>
          </dt>
          <dd>{meubilairEntity.diameter}</dd>
          <dt>
            <span id="fabrikant">Fabrikant</span>
          </dt>
          <dd>{meubilairEntity.fabrikant}</dd>
          <dt>
            <span id="gewicht">Gewicht</span>
          </dt>
          <dd>{meubilairEntity.gewicht}</dd>
          <dt>
            <span id="hoogte">Hoogte</span>
          </dt>
          <dd>{meubilairEntity.hoogte}</dd>
          <dt>
            <span id="installateur">Installateur</span>
          </dt>
          <dd>{meubilairEntity.installateur}</dd>
          <dt>
            <span id="jaaronderhouduitgevoerd">Jaaronderhouduitgevoerd</span>
          </dt>
          <dd>{meubilairEntity.jaaronderhouduitgevoerd}</dd>
          <dt>
            <span id="jaarpraktischeinde">Jaarpraktischeinde</span>
          </dt>
          <dd>{meubilairEntity.jaarpraktischeinde}</dd>
          <dt>
            <span id="kleur">Kleur</span>
          </dt>
          <dd>{meubilairEntity.kleur}</dd>
          <dt>
            <span id="kwaliteitsniveauactueel">Kwaliteitsniveauactueel</span>
          </dt>
          <dd>{meubilairEntity.kwaliteitsniveauactueel}</dd>
          <dt>
            <span id="kwaliteitsniveaugewenst">Kwaliteitsniveaugewenst</span>
          </dt>
          <dd>{meubilairEntity.kwaliteitsniveaugewenst}</dd>
          <dt>
            <span id="lengte">Lengte</span>
          </dt>
          <dd>{meubilairEntity.lengte}</dd>
          <dt>
            <span id="leverancier">Leverancier</span>
          </dt>
          <dd>{meubilairEntity.leverancier}</dd>
          <dt>
            <span id="meubilairmateriaal">Meubilairmateriaal</span>
          </dt>
          <dd>{meubilairEntity.meubilairmateriaal}</dd>
          <dt>
            <span id="model">Model</span>
          </dt>
          <dd>{meubilairEntity.model}</dd>
          <dt>
            <span id="ondergrond">Ondergrond</span>
          </dt>
          <dd>{meubilairEntity.ondergrond}</dd>
          <dt>
            <span id="oppervlakte">Oppervlakte</span>
          </dt>
          <dd>{meubilairEntity.oppervlakte}</dd>
          <dt>
            <span id="prijsaanschaf">Prijsaanschaf</span>
          </dt>
          <dd>{meubilairEntity.prijsaanschaf}</dd>
          <dt>
            <span id="serienummer">Serienummer</span>
          </dt>
          <dd>{meubilairEntity.serienummer}</dd>
          <dt>
            <span id="transponder">Transponder</span>
          </dt>
          <dd>{meubilairEntity.transponder}</dd>
          <dt>
            <span id="transponderlocatie">Transponderlocatie</span>
          </dt>
          <dd>{meubilairEntity.transponderlocatie}</dd>
          <dt>
            <span id="typefundering">Typefundering</span>
          </dt>
          <dd>{meubilairEntity.typefundering}</dd>
          <dt>
            <span id="typeplaat">Typeplaat</span>
          </dt>
          <dd>{meubilairEntity.typeplaat ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/meubilair" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/meubilair/${meubilairEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MeubilairDetail;
