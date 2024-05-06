import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './rapportagemoment.reducer';

export const RapportagemomentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const rapportagemomentEntity = useAppSelector(state => state.rapportagemoment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="rapportagemomentDetailsHeading">Rapportagemoment</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{rapportagemomentEntity.id}</dd>
          <dt>
            <span id="datum">Datum</span>
          </dt>
          <dd>
            {rapportagemomentEntity.datum ? (
              <TextFormat value={rapportagemomentEntity.datum} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{rapportagemomentEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{rapportagemomentEntity.omschrijving}</dd>
          <dt>
            <span id="termijn">Termijn</span>
          </dt>
          <dd>{rapportagemomentEntity.termijn}</dd>
          <dt>Heeft Subsidie</dt>
          <dd>{rapportagemomentEntity.heeftSubsidie ? rapportagemomentEntity.heeftSubsidie.id : ''}</dd>
          <dt>Projectleider Rechtspersoon</dt>
          <dd>{rapportagemomentEntity.projectleiderRechtspersoon ? rapportagemomentEntity.projectleiderRechtspersoon.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/rapportagemoment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rapportagemoment/${rapportagemomentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RapportagemomentDetail;
