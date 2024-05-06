import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sollicitant.reducer';

export const SollicitantDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const sollicitantEntity = useAppSelector(state => state.sollicitant.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sollicitantDetailsHeading">Sollicitant</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{sollicitantEntity.id}</dd>
          <dt>Kandidaat Sollicitatiegesprek</dt>
          <dd>
            {sollicitantEntity.kandidaatSollicitatiegespreks
              ? sollicitantEntity.kandidaatSollicitatiegespreks.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {sollicitantEntity.kandidaatSollicitatiegespreks && i === sollicitantEntity.kandidaatSollicitatiegespreks.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/sollicitant" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sollicitant/${sollicitantEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SollicitantDetail;
