import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './overigbenoemdterrein.reducer';

export const OverigbenoemdterreinDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const overigbenoemdterreinEntity = useAppSelector(state => state.overigbenoemdterrein.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="overigbenoemdterreinDetailsHeading">Overigbenoemdterrein</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{overigbenoemdterreinEntity.id}</dd>
          <dt>
            <span id="gebruiksdoeloverigbenoemdterrein">Gebruiksdoeloverigbenoemdterrein</span>
          </dt>
          <dd>{overigbenoemdterreinEntity.gebruiksdoeloverigbenoemdterrein}</dd>
          <dt>
            <span id="overigbenoemdterreinidentificatie">Overigbenoemdterreinidentificatie</span>
          </dt>
          <dd>{overigbenoemdterreinEntity.overigbenoemdterreinidentificatie}</dd>
        </dl>
        <Button tag={Link} to="/overigbenoemdterrein" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/overigbenoemdterrein/${overigbenoemdterreinEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OverigbenoemdterreinDetail;
