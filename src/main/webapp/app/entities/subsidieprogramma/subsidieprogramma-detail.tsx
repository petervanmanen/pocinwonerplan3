import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './subsidieprogramma.reducer';

export const SubsidieprogrammaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const subsidieprogrammaEntity = useAppSelector(state => state.subsidieprogramma.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="subsidieprogrammaDetailsHeading">Subsidieprogramma</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{subsidieprogrammaEntity.id}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {subsidieprogrammaEntity.datumeinde ? (
              <TextFormat value={subsidieprogrammaEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {subsidieprogrammaEntity.datumstart ? (
              <TextFormat value={subsidieprogrammaEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{subsidieprogrammaEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{subsidieprogrammaEntity.omschrijving}</dd>
          <dt>
            <span id="programmabegroting">Programmabegroting</span>
          </dt>
          <dd>{subsidieprogrammaEntity.programmabegroting}</dd>
        </dl>
        <Button tag={Link} to="/subsidieprogramma" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/subsidieprogramma/${subsidieprogrammaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SubsidieprogrammaDetail;
