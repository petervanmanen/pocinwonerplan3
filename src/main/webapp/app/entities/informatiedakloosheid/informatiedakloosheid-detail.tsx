import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './informatiedakloosheid.reducer';

export const InformatiedakloosheidDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const informatiedakloosheidEntity = useAppSelector(state => state.informatiedakloosheid.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="informatiedakloosheidDetailsHeading">Informatiedakloosheid</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{informatiedakloosheidEntity.id}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {informatiedakloosheidEntity.datumeinde ? (
              <TextFormat value={informatiedakloosheidEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {informatiedakloosheidEntity.datumstart ? (
              <TextFormat value={informatiedakloosheidEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="gemeenteoorsprong">Gemeenteoorsprong</span>
          </dt>
          <dd>{informatiedakloosheidEntity.gemeenteoorsprong}</dd>
          <dt>
            <span id="toestemminggemeentelijkbriefadres">Toestemminggemeentelijkbriefadres</span>
          </dt>
          <dd>{informatiedakloosheidEntity.toestemminggemeentelijkbriefadres ? 'true' : 'false'}</dd>
          <dt>
            <span id="toestemmingnachtopvang">Toestemmingnachtopvang</span>
          </dt>
          <dd>{informatiedakloosheidEntity.toestemmingnachtopvang ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/informatiedakloosheid" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/informatiedakloosheid/${informatiedakloosheidEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default InformatiedakloosheidDetail;
