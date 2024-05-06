import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './partij.reducer';

export const PartijDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const partijEntity = useAppSelector(state => state.partij.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="partijDetailsHeading">Partij</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{partijEntity.id}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{partijEntity.code}</dd>
          <dt>
            <span id="datumaanvanggeldigheidpartij">Datumaanvanggeldigheidpartij</span>
          </dt>
          <dd>
            {partijEntity.datumaanvanggeldigheidpartij ? (
              <TextFormat value={partijEntity.datumaanvanggeldigheidpartij} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidpartij">Datumeindegeldigheidpartij</span>
          </dt>
          <dd>
            {partijEntity.datumeindegeldigheidpartij ? (
              <TextFormat value={partijEntity.datumeindegeldigheidpartij} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{partijEntity.naam}</dd>
          <dt>
            <span id="soort">Soort</span>
          </dt>
          <dd>{partijEntity.soort}</dd>
          <dt>
            <span id="verstrekkingsbeperkingmogelijk">Verstrekkingsbeperkingmogelijk</span>
          </dt>
          <dd>{partijEntity.verstrekkingsbeperkingmogelijk}</dd>
        </dl>
        <Button tag={Link} to="/partij" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/partij/${partijEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PartijDetail;
