import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './soortkunstwerk.reducer';

export const SoortkunstwerkDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const soortkunstwerkEntity = useAppSelector(state => state.soortkunstwerk.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="soortkunstwerkDetailsHeading">Soortkunstwerk</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{soortkunstwerkEntity.id}</dd>
          <dt>
            <span id="indicatieplusbrpopulatie">Indicatieplusbrpopulatie</span>
          </dt>
          <dd>{soortkunstwerkEntity.indicatieplusbrpopulatie}</dd>
          <dt>
            <span id="typekunstwerk">Typekunstwerk</span>
          </dt>
          <dd>{soortkunstwerkEntity.typekunstwerk}</dd>
        </dl>
        <Button tag={Link} to="/soortkunstwerk" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/soortkunstwerk/${soortkunstwerkEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SoortkunstwerkDetail;
