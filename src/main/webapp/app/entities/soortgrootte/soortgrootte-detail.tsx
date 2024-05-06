import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './soortgrootte.reducer';

export const SoortgrootteDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const soortgrootteEntity = useAppSelector(state => state.soortgrootte.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="soortgrootteDetailsHeading">Soortgrootte</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{soortgrootteEntity.id}</dd>
          <dt>
            <span id="codesoortgrootte">Codesoortgrootte</span>
          </dt>
          <dd>{soortgrootteEntity.codesoortgrootte}</dd>
          <dt>
            <span id="datumbegingeldigheidsoortgrootte">Datumbegingeldigheidsoortgrootte</span>
          </dt>
          <dd>
            {soortgrootteEntity.datumbegingeldigheidsoortgrootte ? (
              <TextFormat value={soortgrootteEntity.datumbegingeldigheidsoortgrootte} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidsoortgrootte">Datumeindegeldigheidsoortgrootte</span>
          </dt>
          <dd>
            {soortgrootteEntity.datumeindegeldigheidsoortgrootte ? (
              <TextFormat value={soortgrootteEntity.datumeindegeldigheidsoortgrootte} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="naamsoortgrootte">Naamsoortgrootte</span>
          </dt>
          <dd>{soortgrootteEntity.naamsoortgrootte}</dd>
        </dl>
        <Button tag={Link} to="/soortgrootte" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/soortgrootte/${soortgrootteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SoortgrootteDetail;
