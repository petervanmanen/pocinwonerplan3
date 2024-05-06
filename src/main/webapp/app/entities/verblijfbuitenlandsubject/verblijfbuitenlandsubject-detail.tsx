import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './verblijfbuitenlandsubject.reducer';

export const VerblijfbuitenlandsubjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const verblijfbuitenlandsubjectEntity = useAppSelector(state => state.verblijfbuitenlandsubject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="verblijfbuitenlandsubjectDetailsHeading">Verblijfbuitenlandsubject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{verblijfbuitenlandsubjectEntity.id}</dd>
          <dt>
            <span id="adresbuitenland1">Adresbuitenland 1</span>
          </dt>
          <dd>{verblijfbuitenlandsubjectEntity.adresbuitenland1}</dd>
          <dt>
            <span id="adresbuitenland2">Adresbuitenland 2</span>
          </dt>
          <dd>{verblijfbuitenlandsubjectEntity.adresbuitenland2}</dd>
          <dt>
            <span id="adresbuitenland3">Adresbuitenland 3</span>
          </dt>
          <dd>{verblijfbuitenlandsubjectEntity.adresbuitenland3}</dd>
          <dt>
            <span id="landverblijfadres">Landverblijfadres</span>
          </dt>
          <dd>{verblijfbuitenlandsubjectEntity.landverblijfadres}</dd>
        </dl>
        <Button tag={Link} to="/verblijfbuitenlandsubject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/verblijfbuitenlandsubject/${verblijfbuitenlandsubjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VerblijfbuitenlandsubjectDetail;
