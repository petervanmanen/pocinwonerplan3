import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './kandidaat.reducer';

export const KandidaatDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const kandidaatEntity = useAppSelector(state => state.kandidaat.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="kandidaatDetailsHeading">Kandidaat</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{kandidaatEntity.id}</dd>
          <dt>
            <span id="datumingestuurd">Datumingestuurd</span>
          </dt>
          <dd>{kandidaatEntity.datumingestuurd}</dd>
          <dt>Biedtaan Leverancier</dt>
          <dd>{kandidaatEntity.biedtaanLeverancier ? kandidaatEntity.biedtaanLeverancier.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/kandidaat" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/kandidaat/${kandidaatEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default KandidaatDetail;
