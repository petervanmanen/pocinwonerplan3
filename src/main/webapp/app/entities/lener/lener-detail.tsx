import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './lener.reducer';

export const LenerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const lenerEntity = useAppSelector(state => state.lener.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="lenerDetailsHeading">Lener</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{lenerEntity.id}</dd>
          <dt>
            <span id="opmerkingen">Opmerkingen</span>
          </dt>
          <dd>{lenerEntity.opmerkingen}</dd>
          <dt>Is Bruikleen</dt>
          <dd>
            {lenerEntity.isBruikleens
              ? lenerEntity.isBruikleens.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {lenerEntity.isBruikleens && i === lenerEntity.isBruikleens.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/lener" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/lener/${lenerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LenerDetail;
