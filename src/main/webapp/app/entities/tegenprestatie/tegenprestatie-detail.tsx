import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tegenprestatie.reducer';

export const TegenprestatieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tegenprestatieEntity = useAppSelector(state => state.tegenprestatie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tegenprestatieDetailsHeading">Tegenprestatie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{tegenprestatieEntity.id}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {tegenprestatieEntity.datumeinde ? (
              <TextFormat value={tegenprestatieEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {tegenprestatieEntity.datumstart ? (
              <TextFormat value={tegenprestatieEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Leverttegenprestatie Client</dt>
          <dd>{tegenprestatieEntity.leverttegenprestatieClient ? tegenprestatieEntity.leverttegenprestatieClient.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/tegenprestatie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tegenprestatie/${tegenprestatieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TegenprestatieDetail;
