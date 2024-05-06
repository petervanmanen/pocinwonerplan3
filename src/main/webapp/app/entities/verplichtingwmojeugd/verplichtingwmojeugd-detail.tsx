import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './verplichtingwmojeugd.reducer';

export const VerplichtingwmojeugdDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const verplichtingwmojeugdEntity = useAppSelector(state => state.verplichtingwmojeugd.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="verplichtingwmojeugdDetailsHeading">Verplichtingwmojeugd</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{verplichtingwmojeugdEntity.id}</dd>
          <dt>
            <span id="budgetsoort">Budgetsoort</span>
          </dt>
          <dd>{verplichtingwmojeugdEntity.budgetsoort}</dd>
          <dt>
            <span id="budgetsoortgroep">Budgetsoortgroep</span>
          </dt>
          <dd>{verplichtingwmojeugdEntity.budgetsoortgroep}</dd>
          <dt>
            <span id="einddatumgepland">Einddatumgepland</span>
          </dt>
          <dd>{verplichtingwmojeugdEntity.einddatumgepland}</dd>
          <dt>
            <span id="feitelijkeeinddatum">Feitelijkeeinddatum</span>
          </dt>
          <dd>{verplichtingwmojeugdEntity.feitelijkeeinddatum}</dd>
          <dt>
            <span id="jaar">Jaar</span>
          </dt>
          <dd>{verplichtingwmojeugdEntity.jaar}</dd>
          <dt>
            <span id="periodiciteit">Periodiciteit</span>
          </dt>
          <dd>{verplichtingwmojeugdEntity.periodiciteit}</dd>
          <dt>
            <span id="verplichtingsoort">Verplichtingsoort</span>
          </dt>
          <dd>{verplichtingwmojeugdEntity.verplichtingsoort}</dd>
        </dl>
        <Button tag={Link} to="/verplichtingwmojeugd" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/verplichtingwmojeugd/${verplichtingwmojeugdEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VerplichtingwmojeugdDetail;
