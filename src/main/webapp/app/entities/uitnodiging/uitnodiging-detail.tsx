import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './uitnodiging.reducer';

export const UitnodigingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const uitnodigingEntity = useAppSelector(state => state.uitnodiging.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="uitnodigingDetailsHeading">Uitnodiging</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{uitnodigingEntity.id}</dd>
          <dt>
            <span id="afgewezen">Afgewezen</span>
          </dt>
          <dd>{uitnodigingEntity.afgewezen}</dd>
          <dt>
            <span id="datum">Datum</span>
          </dt>
          <dd>{uitnodigingEntity.datum}</dd>
          <dt>
            <span id="geaccepteerd">Geaccepteerd</span>
          </dt>
          <dd>{uitnodigingEntity.geaccepteerd}</dd>
          <dt>Gerichtaan Leverancier</dt>
          <dd>{uitnodigingEntity.gerichtaanLeverancier ? uitnodigingEntity.gerichtaanLeverancier.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/uitnodiging" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/uitnodiging/${uitnodigingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default UitnodigingDetail;
