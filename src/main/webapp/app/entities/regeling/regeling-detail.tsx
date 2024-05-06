import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './regeling.reducer';

export const RegelingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const regelingEntity = useAppSelector(state => state.regeling.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="regelingDetailsHeading">Regeling</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{regelingEntity.id}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>{regelingEntity.datumeinde}</dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>{regelingEntity.datumstart}</dd>
          <dt>
            <span id="datumtoekenning">Datumtoekenning</span>
          </dt>
          <dd>{regelingEntity.datumtoekenning}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{regelingEntity.omschrijving}</dd>
          <dt>Isregelingsoort Regelingsoort</dt>
          <dd>{regelingEntity.isregelingsoortRegelingsoort ? regelingEntity.isregelingsoortRegelingsoort.id : ''}</dd>
          <dt>Heeftregeling Client</dt>
          <dd>{regelingEntity.heeftregelingClient ? regelingEntity.heeftregelingClient.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/regeling" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/regeling/${regelingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RegelingDetail;
