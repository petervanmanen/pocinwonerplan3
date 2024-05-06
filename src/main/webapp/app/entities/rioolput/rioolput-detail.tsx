import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './rioolput.reducer';

export const RioolputDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const rioolputEntity = useAppSelector(state => state.rioolput.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="rioolputDetailsHeading">Rioolput</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{rioolputEntity.id}</dd>
          <dt>
            <span id="aantalbedrijven">Aantalbedrijven</span>
          </dt>
          <dd>{rioolputEntity.aantalbedrijven}</dd>
          <dt>
            <span id="aantalrecreatie">Aantalrecreatie</span>
          </dt>
          <dd>{rioolputEntity.aantalrecreatie}</dd>
          <dt>
            <span id="aantalwoningen">Aantalwoningen</span>
          </dt>
          <dd>{rioolputEntity.aantalwoningen}</dd>
          <dt>
            <span id="afvoerendoppervlak">Afvoerendoppervlak</span>
          </dt>
          <dd>{rioolputEntity.afvoerendoppervlak}</dd>
          <dt>
            <span id="bergendoppervlak">Bergendoppervlak</span>
          </dt>
          <dd>{rioolputEntity.bergendoppervlak}</dd>
          <dt>
            <span id="rioolputconstructieonderdeel">Rioolputconstructieonderdeel</span>
          </dt>
          <dd>{rioolputEntity.rioolputconstructieonderdeel}</dd>
          <dt>
            <span id="rioolputrioolleiding">Rioolputrioolleiding</span>
          </dt>
          <dd>{rioolputEntity.rioolputrioolleiding}</dd>
          <dt>
            <span id="risicogebied">Risicogebied</span>
          </dt>
          <dd>{rioolputEntity.risicogebied}</dd>
          <dt>
            <span id="toegangbreedte">Toegangbreedte</span>
          </dt>
          <dd>{rioolputEntity.toegangbreedte}</dd>
          <dt>
            <span id="toeganglengte">Toeganglengte</span>
          </dt>
          <dd>{rioolputEntity.toeganglengte}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{rioolputEntity.type}</dd>
          <dt>
            <span id="typeplus">Typeplus</span>
          </dt>
          <dd>{rioolputEntity.typeplus}</dd>
        </dl>
        <Button tag={Link} to="/rioolput" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rioolput/${rioolputEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RioolputDetail;
