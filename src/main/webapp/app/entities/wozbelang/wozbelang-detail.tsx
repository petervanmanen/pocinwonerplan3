import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './wozbelang.reducer';

export const WozbelangDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const wozbelangEntity = useAppSelector(state => state.wozbelang.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="wozbelangDetailsHeading">Wozbelang</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{wozbelangEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheid">Datumbegingeldigheid</span>
          </dt>
          <dd>
            {wozbelangEntity.datumbegingeldigheid ? (
              <TextFormat value={wozbelangEntity.datumbegingeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheid">Datumeindegeldigheid</span>
          </dt>
          <dd>
            {wozbelangEntity.datumeindegeldigheid ? (
              <TextFormat value={wozbelangEntity.datumeindegeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="eigenaargebruiker">Eigenaargebruiker</span>
          </dt>
          <dd>{wozbelangEntity.eigenaargebruiker}</dd>
        </dl>
        <Button tag={Link} to="/wozbelang" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/wozbelang/${wozbelangEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WozbelangDetail;
