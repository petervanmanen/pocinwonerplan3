import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vrijstelling.reducer';

export const VrijstellingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vrijstellingEntity = useAppSelector(state => state.vrijstelling.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vrijstellingDetailsHeading">Vrijstelling</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{vrijstellingEntity.id}</dd>
          <dt>
            <span id="aanvraagtoegekend">Aanvraagtoegekend</span>
          </dt>
          <dd>{vrijstellingEntity.aanvraagtoegekend ? 'true' : 'false'}</dd>
          <dt>
            <span id="buitenlandseschoollocatie">Buitenlandseschoollocatie</span>
          </dt>
          <dd>{vrijstellingEntity.buitenlandseschoollocatie}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {vrijstellingEntity.datumeinde ? (
              <TextFormat value={vrijstellingEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {vrijstellingEntity.datumstart ? (
              <TextFormat value={vrijstellingEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="verzuimsoort">Verzuimsoort</span>
          </dt>
          <dd>{vrijstellingEntity.verzuimsoort}</dd>
          <dt>Heeft School</dt>
          <dd>{vrijstellingEntity.heeftSchool ? vrijstellingEntity.heeftSchool.id : ''}</dd>
          <dt>Heeft Leerling</dt>
          <dd>{vrijstellingEntity.heeftLeerling ? vrijstellingEntity.heeftLeerling.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/vrijstelling" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vrijstelling/${vrijstellingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VrijstellingDetail;
