import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './taalniveau.reducer';

export const TaalniveauDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const taalniveauEntity = useAppSelector(state => state.taalniveau.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="taalniveauDetailsHeading">Taalniveau</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{taalniveauEntity.id}</dd>
          <dt>
            <span id="mondeling">Mondeling</span>
          </dt>
          <dd>{taalniveauEntity.mondeling}</dd>
          <dt>
            <span id="schriftelijk">Schriftelijk</span>
          </dt>
          <dd>{taalniveauEntity.schriftelijk}</dd>
          <dt>Heefttaalniveau Client</dt>
          <dd>
            {taalniveauEntity.heefttaalniveauClients
              ? taalniveauEntity.heefttaalniveauClients.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {taalniveauEntity.heefttaalniveauClients && i === taalniveauEntity.heefttaalniveauClients.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/taalniveau" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/taalniveau/${taalniveauEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TaalniveauDetail;
