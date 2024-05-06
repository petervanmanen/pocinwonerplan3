import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './werkorder.reducer';

export const WerkorderDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const werkorderEntity = useAppSelector(state => state.werkorder.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="werkorderDetailsHeading">Werkorder</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{werkorderEntity.id}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{werkorderEntity.code}</dd>
          <dt>
            <span id="documentnummer">Documentnummer</span>
          </dt>
          <dd>{werkorderEntity.documentnummer}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{werkorderEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{werkorderEntity.omschrijving}</dd>
          <dt>
            <span id="werkordertype">Werkordertype</span>
          </dt>
          <dd>{werkorderEntity.werkordertype}</dd>
          <dt>Heeft Hoofdrekening</dt>
          <dd>{werkorderEntity.heeftHoofdrekening ? werkorderEntity.heeftHoofdrekening.id : ''}</dd>
          <dt>Heeft Kostenplaats</dt>
          <dd>{werkorderEntity.heeftKostenplaats ? werkorderEntity.heeftKostenplaats.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/werkorder" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/werkorder/${werkorderEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WerkorderDetail;
