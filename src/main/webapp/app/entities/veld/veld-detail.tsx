import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './veld.reducer';

export const VeldDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const veldEntity = useAppSelector(state => state.veld.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="veldDetailsHeading">Veld</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{veldEntity.id}</dd>
          <dt>Heeft Belijning</dt>
          <dd>
            {veldEntity.heeftBelijnings
              ? veldEntity.heeftBelijnings.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {veldEntity.heeftBelijnings && i === veldEntity.heeftBelijnings.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Heeft Sportpark</dt>
          <dd>{veldEntity.heeftSportpark ? veldEntity.heeftSportpark.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/veld" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/veld/${veldEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VeldDetail;
