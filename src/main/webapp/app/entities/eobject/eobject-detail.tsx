import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './eobject.reducer';

export const EobjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const eobjectEntity = useAppSelector(state => state.eobject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="eobjectDetailsHeading">Eobject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{eobjectEntity.id}</dd>
          <dt>
            <span id="adresbinnenland">Adresbinnenland</span>
          </dt>
          <dd>{eobjectEntity.adresbinnenland}</dd>
          <dt>
            <span id="adresbuitenland">Adresbuitenland</span>
          </dt>
          <dd>{eobjectEntity.adresbuitenland}</dd>
          <dt>
            <span id="domein">Domein</span>
          </dt>
          <dd>{eobjectEntity.domein}</dd>
          <dt>
            <span id="geometrie">Geometrie</span>
          </dt>
          <dd>{eobjectEntity.geometrie}</dd>
          <dt>
            <span id="identificatie">Identificatie</span>
          </dt>
          <dd>{eobjectEntity.identificatie}</dd>
          <dt>
            <span id="indicatierisico">Indicatierisico</span>
          </dt>
          <dd>{eobjectEntity.indicatierisico}</dd>
          <dt>
            <span id="kadastraleaanduiding">Kadastraleaanduiding</span>
          </dt>
          <dd>{eobjectEntity.kadastraleaanduiding}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{eobjectEntity.naam}</dd>
          <dt>
            <span id="eobjecttype">Eobjecttype</span>
          </dt>
          <dd>{eobjectEntity.eobjecttype}</dd>
          <dt>
            <span id="toelichting">Toelichting</span>
          </dt>
          <dd>{eobjectEntity.toelichting}</dd>
        </dl>
        <Button tag={Link} to="/eobject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/eobject/${eobjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EobjectDetail;
