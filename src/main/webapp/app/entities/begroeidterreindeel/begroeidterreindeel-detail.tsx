import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './begroeidterreindeel.reducer';

export const BegroeidterreindeelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const begroeidterreindeelEntity = useAppSelector(state => state.begroeidterreindeel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="begroeidterreindeelDetailsHeading">Begroeidterreindeel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{begroeidterreindeelEntity.id}</dd>
          <dt>
            <span id="begroeidterreindeeloptalud">Begroeidterreindeeloptalud</span>
          </dt>
          <dd>{begroeidterreindeelEntity.begroeidterreindeeloptalud}</dd>
          <dt>
            <span id="datumbegingeldigheidbegroeidterreindeel">Datumbegingeldigheidbegroeidterreindeel</span>
          </dt>
          <dd>
            {begroeidterreindeelEntity.datumbegingeldigheidbegroeidterreindeel ? (
              <TextFormat
                value={begroeidterreindeelEntity.datumbegingeldigheidbegroeidterreindeel}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidbegroeidterreindeel">Datumeindegeldigheidbegroeidterreindeel</span>
          </dt>
          <dd>
            {begroeidterreindeelEntity.datumeindegeldigheidbegroeidterreindeel ? (
              <TextFormat
                value={begroeidterreindeelEntity.datumeindegeldigheidbegroeidterreindeel}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="fysiekvoorkomenbegroeidterreindeel">Fysiekvoorkomenbegroeidterreindeel</span>
          </dt>
          <dd>{begroeidterreindeelEntity.fysiekvoorkomenbegroeidterreindeel}</dd>
          <dt>
            <span id="geometriebegroeidterreindeel">Geometriebegroeidterreindeel</span>
          </dt>
          <dd>{begroeidterreindeelEntity.geometriebegroeidterreindeel}</dd>
          <dt>
            <span id="identificatiebegroeidterreindeel">Identificatiebegroeidterreindeel</span>
          </dt>
          <dd>{begroeidterreindeelEntity.identificatiebegroeidterreindeel}</dd>
          <dt>
            <span id="kruinlijngeometriebegroeidterreindeel">Kruinlijngeometriebegroeidterreindeel</span>
          </dt>
          <dd>{begroeidterreindeelEntity.kruinlijngeometriebegroeidterreindeel}</dd>
          <dt>
            <span id="lod0geometriebegroeidterreindeel">Lod 0 Geometriebegroeidterreindeel</span>
          </dt>
          <dd>{begroeidterreindeelEntity.lod0geometriebegroeidterreindeel}</dd>
          <dt>
            <span id="plusfysiekvoorkomenbegroeidterreindeel">Plusfysiekvoorkomenbegroeidterreindeel</span>
          </dt>
          <dd>{begroeidterreindeelEntity.plusfysiekvoorkomenbegroeidterreindeel}</dd>
          <dt>
            <span id="relatievehoogteliggingbegroeidterreindeel">Relatievehoogteliggingbegroeidterreindeel</span>
          </dt>
          <dd>{begroeidterreindeelEntity.relatievehoogteliggingbegroeidterreindeel}</dd>
          <dt>
            <span id="statusbegroeidterreindeel">Statusbegroeidterreindeel</span>
          </dt>
          <dd>{begroeidterreindeelEntity.statusbegroeidterreindeel}</dd>
        </dl>
        <Button tag={Link} to="/begroeidterreindeel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/begroeidterreindeel/${begroeidterreindeelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BegroeidterreindeelDetail;
