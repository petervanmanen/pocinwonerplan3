import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './loopbaanstap.reducer';

export const LoopbaanstapDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const loopbaanstapEntity = useAppSelector(state => state.loopbaanstap.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="loopbaanstapDetailsHeading">Loopbaanstap</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{loopbaanstapEntity.id}</dd>
          <dt>
            <span id="klas">Klas</span>
          </dt>
          <dd>{loopbaanstapEntity.klas}</dd>
          <dt>
            <span id="onderwijstype">Onderwijstype</span>
          </dt>
          <dd>{loopbaanstapEntity.onderwijstype}</dd>
          <dt>
            <span id="schooljaar">Schooljaar</span>
          </dt>
          <dd>{loopbaanstapEntity.schooljaar}</dd>
          <dt>Empty Onderwijsloopbaan</dt>
          <dd>{loopbaanstapEntity.emptyOnderwijsloopbaan ? loopbaanstapEntity.emptyOnderwijsloopbaan.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/loopbaanstap" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/loopbaanstap/${loopbaanstapEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LoopbaanstapDetail;
