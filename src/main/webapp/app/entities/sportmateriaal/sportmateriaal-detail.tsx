import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sportmateriaal.reducer';

export const SportmateriaalDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const sportmateriaalEntity = useAppSelector(state => state.sportmateriaal.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sportmateriaalDetailsHeading">Sportmateriaal</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{sportmateriaalEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{sportmateriaalEntity.naam}</dd>
          <dt>Heeft Binnenlocatie</dt>
          <dd>
            {sportmateriaalEntity.heeftBinnenlocaties
              ? sportmateriaalEntity.heeftBinnenlocaties.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {sportmateriaalEntity.heeftBinnenlocaties && i === sportmateriaalEntity.heeftBinnenlocaties.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/sportmateriaal" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sportmateriaal/${sportmateriaalEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SportmateriaalDetail;
