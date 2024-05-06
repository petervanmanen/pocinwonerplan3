import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './nadaanvullingbrp.reducer';

export const NadaanvullingbrpDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const nadaanvullingbrpEntity = useAppSelector(state => state.nadaanvullingbrp.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="nadaanvullingbrpDetailsHeading">Nadaanvullingbrp</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{nadaanvullingbrpEntity.id}</dd>
          <dt>
            <span id="opmerkingen">Opmerkingen</span>
          </dt>
          <dd>{nadaanvullingbrpEntity.opmerkingen}</dd>
        </dl>
        <Button tag={Link} to="/nadaanvullingbrp" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/nadaanvullingbrp/${nadaanvullingbrpEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default NadaanvullingbrpDetail;
