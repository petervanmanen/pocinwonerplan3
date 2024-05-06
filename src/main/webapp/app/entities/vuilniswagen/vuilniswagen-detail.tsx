import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vuilniswagen.reducer';

export const VuilniswagenDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vuilniswagenEntity = useAppSelector(state => state.vuilniswagen.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vuilniswagenDetailsHeading">Vuilniswagen</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{vuilniswagenEntity.id}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{vuilniswagenEntity.code}</dd>
          <dt>
            <span id="kenteken">Kenteken</span>
          </dt>
          <dd>{vuilniswagenEntity.kenteken}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{vuilniswagenEntity.type}</dd>
          <dt>Geschiktvoor Containertype</dt>
          <dd>
            {vuilniswagenEntity.geschiktvoorContainertypes
              ? vuilniswagenEntity.geschiktvoorContainertypes.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {vuilniswagenEntity.geschiktvoorContainertypes && i === vuilniswagenEntity.geschiktvoorContainertypes.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/vuilniswagen" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vuilniswagen/${vuilniswagenEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VuilniswagenDetail;
