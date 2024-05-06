import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './locatieaanduidingadreswozobject.reducer';

export const LocatieaanduidingadreswozobjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const locatieaanduidingadreswozobjectEntity = useAppSelector(state => state.locatieaanduidingadreswozobject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="locatieaanduidingadreswozobjectDetailsHeading">Locatieaanduidingadreswozobject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{locatieaanduidingadreswozobjectEntity.id}</dd>
          <dt>
            <span id="locatieomschrijving">Locatieomschrijving</span>
          </dt>
          <dd>{locatieaanduidingadreswozobjectEntity.locatieomschrijving}</dd>
        </dl>
        <Button tag={Link} to="/locatieaanduidingadreswozobject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/locatieaanduidingadreswozobject/${locatieaanduidingadreswozobjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LocatieaanduidingadreswozobjectDetail;
