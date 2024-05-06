import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './schouwronde.reducer';

export const SchouwrondeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const schouwrondeEntity = useAppSelector(state => state.schouwronde.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="schouwrondeDetailsHeading">Schouwronde</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{schouwrondeEntity.id}</dd>
          <dt>Voertuit Medewerker</dt>
          <dd>{schouwrondeEntity.voertuitMedewerker ? schouwrondeEntity.voertuitMedewerker.id : ''}</dd>
          <dt>Binnen Areaal</dt>
          <dd>
            {schouwrondeEntity.binnenAreaals
              ? schouwrondeEntity.binnenAreaals.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {schouwrondeEntity.binnenAreaals && i === schouwrondeEntity.binnenAreaals.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/schouwronde" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/schouwronde/${schouwrondeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SchouwrondeDetail;
