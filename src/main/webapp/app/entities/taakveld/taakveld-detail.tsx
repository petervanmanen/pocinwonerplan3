import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './taakveld.reducer';

export const TaakveldDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const taakveldEntity = useAppSelector(state => state.taakveld.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="taakveldDetailsHeading">Taakveld</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{taakveldEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{taakveldEntity.naam}</dd>
          <dt>Heeft Kostenplaats</dt>
          <dd>
            {taakveldEntity.heeftKostenplaats
              ? taakveldEntity.heeftKostenplaats.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {taakveldEntity.heeftKostenplaats && i === taakveldEntity.heeftKostenplaats.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/taakveld" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/taakveld/${taakveldEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TaakveldDetail;
