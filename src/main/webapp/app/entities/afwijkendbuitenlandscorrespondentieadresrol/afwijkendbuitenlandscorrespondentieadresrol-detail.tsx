import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './afwijkendbuitenlandscorrespondentieadresrol.reducer';

export const AfwijkendbuitenlandscorrespondentieadresrolDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const afwijkendbuitenlandscorrespondentieadresrolEntity = useAppSelector(
    state => state.afwijkendbuitenlandscorrespondentieadresrol.entity,
  );
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="afwijkendbuitenlandscorrespondentieadresrolDetailsHeading">Afwijkendbuitenlandscorrespondentieadresrol</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{afwijkendbuitenlandscorrespondentieadresrolEntity.id}</dd>
          <dt>
            <span id="adresbuitenland1">Adresbuitenland 1</span>
          </dt>
          <dd>{afwijkendbuitenlandscorrespondentieadresrolEntity.adresbuitenland1}</dd>
          <dt>
            <span id="adresbuitenland2">Adresbuitenland 2</span>
          </dt>
          <dd>{afwijkendbuitenlandscorrespondentieadresrolEntity.adresbuitenland2}</dd>
          <dt>
            <span id="adresbuitenland3">Adresbuitenland 3</span>
          </dt>
          <dd>{afwijkendbuitenlandscorrespondentieadresrolEntity.adresbuitenland3}</dd>
          <dt>
            <span id="landpostadres">Landpostadres</span>
          </dt>
          <dd>{afwijkendbuitenlandscorrespondentieadresrolEntity.landpostadres}</dd>
        </dl>
        <Button tag={Link} to="/afwijkendbuitenlandscorrespondentieadresrol" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/afwijkendbuitenlandscorrespondentieadresrol/${afwijkendbuitenlandscorrespondentieadresrolEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AfwijkendbuitenlandscorrespondentieadresrolDetail;
