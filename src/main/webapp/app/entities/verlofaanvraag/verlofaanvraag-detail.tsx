import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './verlofaanvraag.reducer';

export const VerlofaanvraagDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const verlofaanvraagEntity = useAppSelector(state => state.verlofaanvraag.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="verlofaanvraagDetailsHeading">Verlofaanvraag</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{verlofaanvraagEntity.id}</dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {verlofaanvraagEntity.datumstart ? (
              <TextFormat value={verlofaanvraagEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumtot">Datumtot</span>
          </dt>
          <dd>
            {verlofaanvraagEntity.datumtot ? (
              <TextFormat value={verlofaanvraagEntity.datumtot} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="soortverlof">Soortverlof</span>
          </dt>
          <dd>{verlofaanvraagEntity.soortverlof}</dd>
        </dl>
        <Button tag={Link} to="/verlofaanvraag" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/verlofaanvraag/${verlofaanvraagEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VerlofaanvraagDetail;
