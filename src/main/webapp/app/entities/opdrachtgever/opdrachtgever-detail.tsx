import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './opdrachtgever.reducer';

export const OpdrachtgeverDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const opdrachtgeverEntity = useAppSelector(state => state.opdrachtgever.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="opdrachtgeverDetailsHeading">Opdrachtgever</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{opdrachtgeverEntity.id}</dd>
          <dt>
            <span id="clustercode">Clustercode</span>
          </dt>
          <dd>{opdrachtgeverEntity.clustercode}</dd>
          <dt>
            <span id="clusteromschrijving">Clusteromschrijving</span>
          </dt>
          <dd>{opdrachtgeverEntity.clusteromschrijving}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{opdrachtgeverEntity.naam}</dd>
          <dt>
            <span id="nummer">Nummer</span>
          </dt>
          <dd>{opdrachtgeverEntity.nummer}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{opdrachtgeverEntity.omschrijving}</dd>
          <dt>Uitgevoerddoor Functie</dt>
          <dd>{opdrachtgeverEntity.uitgevoerddoorFunctie ? opdrachtgeverEntity.uitgevoerddoorFunctie.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/opdrachtgever" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/opdrachtgever/${opdrachtgeverEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OpdrachtgeverDetail;
