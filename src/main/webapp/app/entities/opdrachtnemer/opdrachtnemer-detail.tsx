import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './opdrachtnemer.reducer';

export const OpdrachtnemerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const opdrachtnemerEntity = useAppSelector(state => state.opdrachtnemer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="opdrachtnemerDetailsHeading">Opdrachtnemer</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{opdrachtnemerEntity.id}</dd>
          <dt>
            <span id="clustercode">Clustercode</span>
          </dt>
          <dd>{opdrachtnemerEntity.clustercode}</dd>
          <dt>
            <span id="clustercodeomschrijving">Clustercodeomschrijving</span>
          </dt>
          <dd>{opdrachtnemerEntity.clustercodeomschrijving}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{opdrachtnemerEntity.naam}</dd>
          <dt>
            <span id="nummer">Nummer</span>
          </dt>
          <dd>{opdrachtnemerEntity.nummer}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{opdrachtnemerEntity.omschrijving}</dd>
          <dt>Uitgevoerddoor Functie</dt>
          <dd>{opdrachtnemerEntity.uitgevoerddoorFunctie ? opdrachtnemerEntity.uitgevoerddoorFunctie.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/opdrachtnemer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/opdrachtnemer/${opdrachtnemerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OpdrachtnemerDetail;
