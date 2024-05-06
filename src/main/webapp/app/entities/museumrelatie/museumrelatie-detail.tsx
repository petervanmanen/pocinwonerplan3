import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './museumrelatie.reducer';

export const MuseumrelatieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const museumrelatieEntity = useAppSelector(state => state.museumrelatie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="museumrelatieDetailsHeading">Museumrelatie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{museumrelatieEntity.id}</dd>
          <dt>
            <span id="relatiesoort">Relatiesoort</span>
          </dt>
          <dd>{museumrelatieEntity.relatiesoort}</dd>
          <dt>Valtbinnen Doelgroep</dt>
          <dd>
            {museumrelatieEntity.valtbinnenDoelgroeps
              ? museumrelatieEntity.valtbinnenDoelgroeps.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {museumrelatieEntity.valtbinnenDoelgroeps && i === museumrelatieEntity.valtbinnenDoelgroeps.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Versturenaan Mailing</dt>
          <dd>
            {museumrelatieEntity.versturenaanMailings
              ? museumrelatieEntity.versturenaanMailings.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {museumrelatieEntity.versturenaanMailings && i === museumrelatieEntity.versturenaanMailings.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/museumrelatie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/museumrelatie/${museumrelatieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MuseumrelatieDetail;
