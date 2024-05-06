import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pgbtoekenning.reducer';

export const PgbtoekenningDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const pgbtoekenningEntity = useAppSelector(state => state.pgbtoekenning.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pgbtoekenningDetailsHeading">Pgbtoekenning</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{pgbtoekenningEntity.id}</dd>
          <dt>
            <span id="budget">Budget</span>
          </dt>
          <dd>{pgbtoekenningEntity.budget}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {pgbtoekenningEntity.datumeinde ? (
              <TextFormat value={pgbtoekenningEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumtoekenning">Datumtoekenning</span>
          </dt>
          <dd>
            {pgbtoekenningEntity.datumtoekenning ? (
              <TextFormat value={pgbtoekenningEntity.datumtoekenning} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/pgbtoekenning" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pgbtoekenning/${pgbtoekenningEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PgbtoekenningDetail;
