import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './aanduidingverblijfsrecht.reducer';

export const AanduidingverblijfsrechtDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const aanduidingverblijfsrechtEntity = useAppSelector(state => state.aanduidingverblijfsrecht.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="aanduidingverblijfsrechtDetailsHeading">Aanduidingverblijfsrecht</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{aanduidingverblijfsrechtEntity.id}</dd>
          <dt>
            <span id="datumaanvanggeldigheidverblijfsrecht">Datumaanvanggeldigheidverblijfsrecht</span>
          </dt>
          <dd>
            {aanduidingverblijfsrechtEntity.datumaanvanggeldigheidverblijfsrecht ? (
              <TextFormat
                value={aanduidingverblijfsrechtEntity.datumaanvanggeldigheidverblijfsrecht}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidverblijfsrecht">Datumeindegeldigheidverblijfsrecht</span>
          </dt>
          <dd>
            {aanduidingverblijfsrechtEntity.datumeindegeldigheidverblijfsrecht ? (
              <TextFormat
                value={aanduidingverblijfsrechtEntity.datumeindegeldigheidverblijfsrecht}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="verblijfsrechtnummer">Verblijfsrechtnummer</span>
          </dt>
          <dd>{aanduidingverblijfsrechtEntity.verblijfsrechtnummer}</dd>
          <dt>
            <span id="verblijfsrechtomschrijving">Verblijfsrechtomschrijving</span>
          </dt>
          <dd>{aanduidingverblijfsrechtEntity.verblijfsrechtomschrijving}</dd>
        </dl>
        <Button tag={Link} to="/aanduidingverblijfsrecht" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/aanduidingverblijfsrecht/${aanduidingverblijfsrechtEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AanduidingverblijfsrechtDetail;
