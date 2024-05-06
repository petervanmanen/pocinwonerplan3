import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './classificatie.reducer';

export const ClassificatieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const classificatieEntity = useAppSelector(state => state.classificatie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="classificatieDetailsHeading">Classificatie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="bevatpersoonsgegevens">Bevatpersoonsgegevens</span>
          </dt>
          <dd>{classificatieEntity.bevatpersoonsgegevens}</dd>
          <dt>
            <span id="gerelateerdpersoonsgegevens">Gerelateerdpersoonsgegevens</span>
          </dt>
          <dd>{classificatieEntity.gerelateerdpersoonsgegevens}</dd>
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{classificatieEntity.id}</dd>
          <dt>Geclassificeerdals Gegeven</dt>
          <dd>
            {classificatieEntity.geclassificeerdalsGegevens
              ? classificatieEntity.geclassificeerdalsGegevens.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {classificatieEntity.geclassificeerdalsGegevens && i === classificatieEntity.geclassificeerdalsGegevens.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/classificatie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/classificatie/${classificatieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClassificatieDetail;
