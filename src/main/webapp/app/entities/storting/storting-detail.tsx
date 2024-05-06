import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './storting.reducer';

export const StortingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const stortingEntity = useAppSelector(state => state.storting.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="stortingDetailsHeading">Storting</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{stortingEntity.id}</dd>
          <dt>
            <span id="datumtijd">Datumtijd</span>
          </dt>
          <dd>{stortingEntity.datumtijd}</dd>
          <dt>
            <span id="gewicht">Gewicht</span>
          </dt>
          <dd>{stortingEntity.gewicht}</dd>
          <dt>Bij Milieustraat</dt>
          <dd>{stortingEntity.bijMilieustraat ? stortingEntity.bijMilieustraat.id : ''}</dd>
          <dt>Fractie Fractie</dt>
          <dd>
            {stortingEntity.fractieFracties
              ? stortingEntity.fractieFracties.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {stortingEntity.fractieFracties && i === stortingEntity.fractieFracties.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Uitgevoerdestorting Pas</dt>
          <dd>{stortingEntity.uitgevoerdestortingPas ? stortingEntity.uitgevoerdestortingPas.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/storting" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/storting/${stortingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default StortingDetail;
