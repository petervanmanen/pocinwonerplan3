import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './gebouw.reducer';

export const GebouwDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const gebouwEntity = useAppSelector(state => state.gebouw.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="gebouwDetailsHeading">Gebouw</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{gebouwEntity.id}</dd>
          <dt>
            <span id="aantal">Aantal</span>
          </dt>
          <dd>{gebouwEntity.aantal}</dd>
          <dt>
            <span id="aantaladressen">Aantaladressen</span>
          </dt>
          <dd>{gebouwEntity.aantaladressen}</dd>
          <dt>
            <span id="aantalkamers">Aantalkamers</span>
          </dt>
          <dd>{gebouwEntity.aantalkamers}</dd>
          <dt>
            <span id="aardgasloos">Aardgasloos</span>
          </dt>
          <dd>{gebouwEntity.aardgasloos ? 'true' : 'false'}</dd>
          <dt>
            <span id="duurzaam">Duurzaam</span>
          </dt>
          <dd>{gebouwEntity.duurzaam ? 'true' : 'false'}</dd>
          <dt>
            <span id="energielabel">Energielabel</span>
          </dt>
          <dd>{gebouwEntity.energielabel}</dd>
          <dt>
            <span id="natuurinclusief">Natuurinclusief</span>
          </dt>
          <dd>{gebouwEntity.natuurinclusief ? 'true' : 'false'}</dd>
          <dt>
            <span id="oppervlakte">Oppervlakte</span>
          </dt>
          <dd>{gebouwEntity.oppervlakte}</dd>
          <dt>
            <span id="regenwater">Regenwater</span>
          </dt>
          <dd>{gebouwEntity.regenwater ? 'true' : 'false'}</dd>
          <dt>Bestaatuit Plan</dt>
          <dd>{gebouwEntity.bestaatuitPlan ? gebouwEntity.bestaatuitPlan.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/gebouw" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/gebouw/${gebouwEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GebouwDetail;
