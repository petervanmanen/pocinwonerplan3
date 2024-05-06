import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './benoemdobject.reducer';

export const BenoemdobjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const benoemdobjectEntity = useAppSelector(state => state.benoemdobject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="benoemdobjectDetailsHeading">Benoemdobject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{benoemdobjectEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheid">Datumbegingeldigheid</span>
          </dt>
          <dd>
            {benoemdobjectEntity.datumbegingeldigheid ? (
              <TextFormat value={benoemdobjectEntity.datumbegingeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheid">Datumeindegeldigheid</span>
          </dt>
          <dd>
            {benoemdobjectEntity.datumeindegeldigheid ? (
              <TextFormat value={benoemdobjectEntity.datumeindegeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="geometriepunt">Geometriepunt</span>
          </dt>
          <dd>{benoemdobjectEntity.geometriepunt}</dd>
          <dt>
            <span id="geometrievlak">Geometrievlak</span>
          </dt>
          <dd>{benoemdobjectEntity.geometrievlak}</dd>
          <dt>
            <span id="identificatie">Identificatie</span>
          </dt>
          <dd>{benoemdobjectEntity.identificatie}</dd>
        </dl>
        <Button tag={Link} to="/benoemdobject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/benoemdobject/${benoemdobjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BenoemdobjectDetail;
