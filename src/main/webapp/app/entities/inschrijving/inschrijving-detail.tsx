import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './inschrijving.reducer';

export const InschrijvingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const inschrijvingEntity = useAppSelector(state => state.inschrijving.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="inschrijvingDetailsHeading">Inschrijving</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{inschrijvingEntity.id}</dd>
          <dt>
            <span id="datum">Datum</span>
          </dt>
          <dd>
            {inschrijvingEntity.datum ? <TextFormat value={inschrijvingEntity.datum} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>Heeft School</dt>
          <dd>{inschrijvingEntity.heeftSchool ? inschrijvingEntity.heeftSchool.id : ''}</dd>
          <dt>Betreft Aanbesteding</dt>
          <dd>{inschrijvingEntity.betreftAanbesteding ? inschrijvingEntity.betreftAanbesteding.id : ''}</dd>
          <dt>Heeft Leerling</dt>
          <dd>{inschrijvingEntity.heeftLeerling ? inschrijvingEntity.heeftLeerling.id : ''}</dd>
          <dt>Heeft Leverancier</dt>
          <dd>{inschrijvingEntity.heeftLeverancier ? inschrijvingEntity.heeftLeverancier.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/inschrijving" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/inschrijving/${inschrijvingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default InschrijvingDetail;
