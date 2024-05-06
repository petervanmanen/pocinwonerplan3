import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './overigescheiding.reducer';

export const OverigescheidingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const overigescheidingEntity = useAppSelector(state => state.overigescheiding.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="overigescheidingDetailsHeading">Overigescheiding</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{overigescheidingEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheidoverigescheiding">Datumbegingeldigheidoverigescheiding</span>
          </dt>
          <dd>
            {overigescheidingEntity.datumbegingeldigheidoverigescheiding ? (
              <TextFormat value={overigescheidingEntity.datumbegingeldigheidoverigescheiding} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidoverigescheiding">Datumeindegeldigheidoverigescheiding</span>
          </dt>
          <dd>
            {overigescheidingEntity.datumeindegeldigheidoverigescheiding ? (
              <TextFormat value={overigescheidingEntity.datumeindegeldigheidoverigescheiding} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="geometrieoverigescheiding">Geometrieoverigescheiding</span>
          </dt>
          <dd>{overigescheidingEntity.geometrieoverigescheiding}</dd>
          <dt>
            <span id="identificatieoverigescheiding">Identificatieoverigescheiding</span>
          </dt>
          <dd>{overigescheidingEntity.identificatieoverigescheiding}</dd>
          <dt>
            <span id="lod0geometrieoverigescheiding">Lod 0 Geometrieoverigescheiding</span>
          </dt>
          <dd>{overigescheidingEntity.lod0geometrieoverigescheiding}</dd>
          <dt>
            <span id="lod1geometrieoverigescheiding">Lod 1 Geometrieoverigescheiding</span>
          </dt>
          <dd>{overigescheidingEntity.lod1geometrieoverigescheiding}</dd>
          <dt>
            <span id="lod2geometrieoverigescheiding">Lod 2 Geometrieoverigescheiding</span>
          </dt>
          <dd>{overigescheidingEntity.lod2geometrieoverigescheiding}</dd>
          <dt>
            <span id="lod3geometrieoverigescheiding">Lod 3 Geometrieoverigescheiding</span>
          </dt>
          <dd>{overigescheidingEntity.lod3geometrieoverigescheiding}</dd>
          <dt>
            <span id="relatievehoogteliggingoverigescheiding">Relatievehoogteliggingoverigescheiding</span>
          </dt>
          <dd>{overigescheidingEntity.relatievehoogteliggingoverigescheiding}</dd>
          <dt>
            <span id="statusoverigescheiding">Statusoverigescheiding</span>
          </dt>
          <dd>{overigescheidingEntity.statusoverigescheiding}</dd>
          <dt>
            <span id="typeoverigescheiding">Typeoverigescheiding</span>
          </dt>
          <dd>{overigescheidingEntity.typeoverigescheiding}</dd>
        </dl>
        <Button tag={Link} to="/overigescheiding" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/overigescheiding/${overigescheidingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OverigescheidingDetail;
