import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './openbareactiviteit.reducer';

export const OpenbareactiviteitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const openbareactiviteitEntity = useAppSelector(state => state.openbareactiviteit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="openbareactiviteitDetailsHeading">Openbareactiviteit</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{openbareactiviteitEntity.id}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {openbareactiviteitEntity.datumeinde ? (
              <TextFormat value={openbareactiviteitEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {openbareactiviteitEntity.datumstart ? (
              <TextFormat value={openbareactiviteitEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="evenmentnaam">Evenmentnaam</span>
          </dt>
          <dd>{openbareactiviteitEntity.evenmentnaam}</dd>
          <dt>
            <span id="locatieomschrijving">Locatieomschrijving</span>
          </dt>
          <dd>{openbareactiviteitEntity.locatieomschrijving}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{openbareactiviteitEntity.status}</dd>
        </dl>
        <Button tag={Link} to="/openbareactiviteit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/openbareactiviteit/${openbareactiviteitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OpenbareactiviteitDetail;
