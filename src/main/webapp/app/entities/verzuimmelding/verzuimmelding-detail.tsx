import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './verzuimmelding.reducer';

export const VerzuimmeldingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const verzuimmeldingEntity = useAppSelector(state => state.verzuimmelding.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="verzuimmeldingDetailsHeading">Verzuimmelding</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{verzuimmeldingEntity.id}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {verzuimmeldingEntity.datumeinde ? (
              <TextFormat value={verzuimmeldingEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {verzuimmeldingEntity.datumstart ? (
              <TextFormat value={verzuimmeldingEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="voorstelschool">Voorstelschool</span>
          </dt>
          <dd>{verzuimmeldingEntity.voorstelschool}</dd>
          <dt>Heeft School</dt>
          <dd>{verzuimmeldingEntity.heeftSchool ? verzuimmeldingEntity.heeftSchool.id : ''}</dd>
          <dt>Heeft Leerling</dt>
          <dd>{verzuimmeldingEntity.heeftLeerling ? verzuimmeldingEntity.heeftLeerling.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/verzuimmelding" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/verzuimmelding/${verzuimmeldingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VerzuimmeldingDetail;
